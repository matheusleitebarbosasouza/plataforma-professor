package plataforma.dto;
import jakarta.validation.constraints.*;
import java.util.Set;
import plataforma.enums.TipoEstrategia;
public record EstrategiaRequest(@NotBlank String nome, @NotNull TipoEstrategia tipo, @NotBlank String descricao, @NotBlank String promptAvaliacao) {}
