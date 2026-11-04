package plataforma.dto;
import jakarta.validation.constraints.*;
import java.util.Set;
import plataforma.enums.TipoEstrategia;
public record PromptRequest(@NotBlank String promptAvaliacao) {}
