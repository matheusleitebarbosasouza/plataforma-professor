package plataforma.dto;
import jakarta.validation.constraints.*;
import java.util.Set;
import plataforma.enums.TipoEstrategia;
public record TurmaRequest(@NotBlank String nome, @NotBlank String codigo, @NotBlank String periodoLetivo, @NotNull Long professorId) {}
