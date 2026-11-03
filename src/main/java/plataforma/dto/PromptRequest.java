package br.edu.plataforma.dto;
import jakarta.validation.constraints.*;
import java.util.Set;
import br.edu.plataforma.enums.TipoEstrategia;
public record PromptRequest(@NotBlank String promptAvaliacao) {}
