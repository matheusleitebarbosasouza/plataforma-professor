package plataforma.seguranca;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/** Criptografa a chave do provedor antes de persistir no banco (AES-256-GCM). */
@Converter
public class ChaveAcessoConverter implements AttributeConverter<String, String> {
    private static final int IV_SIZE = 12;
    private static final int TAG_SIZE = 128;
    private static final SecureRandom RANDOM = new SecureRandom();

    private SecretKeySpec key() {
        String configured = System.getenv("LLM_ENCRYPTION_KEY");
        if (configured == null || configured.isBlank()) {
            throw new IllegalStateException("Defina LLM_ENCRYPTION_KEY com uma chave Base64 de 32 bytes");
        }
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(configured);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("LLM_ENCRYPTION_KEY deve estar em Base64", ex);
        }
        if (bytes.length != 32) {
            throw new IllegalStateException("LLM_ENCRYPTION_KEY deve representar exatamente 32 bytes");
        }
        return new SecretKeySpec(bytes, "AES");
    }

    @Override
    public String convertToDatabaseColumn(String value) {
        if (value == null) return null;
        try {
            byte[] iv = new byte[IV_SIZE];
            RANDOM.nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key(), new GCMParameterSpec(TAG_SIZE, iv));
            byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(ByteBuffer.allocate(iv.length + encrypted.length)
                    .put(iv).put(encrypted).array());
        } catch (Exception ex) {
            throw new IllegalStateException("Não foi possível criptografar a chave do LLM", ex);
        }
    }

    @Override
    public String convertToEntityAttribute(String stored) {
        if (stored == null) return null;
        try {
            byte[] payload = Base64.getDecoder().decode(stored);
            if (payload.length <= IV_SIZE) throw new IllegalArgumentException("Conteúdo criptografado inválido");
            ByteBuffer buffer = ByteBuffer.wrap(payload);
            byte[] iv = new byte[IV_SIZE];
            buffer.get(iv);
            byte[] encrypted = new byte[buffer.remaining()];
            buffer.get(encrypted);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key(), new GCMParameterSpec(TAG_SIZE, iv));
            return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new IllegalStateException("Não foi possível descriptografar a chave do LLM", ex);
        }
    }
}
