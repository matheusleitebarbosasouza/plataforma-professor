package plataforma.dto;
import jakarta.validation.constraints.*;
import java.util.Set;
import plataforma.enums.TipoEstrategia;
public record ProfessorRequest(@NotBlank String nome, @NotBlank @Email String email) {}
