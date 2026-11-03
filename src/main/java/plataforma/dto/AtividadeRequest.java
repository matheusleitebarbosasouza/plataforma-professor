package br.edu.plataforma.dto;
import jakarta.validation.constraints.*;
import java.util.Set;
import br.edu.plataforma.enums.TipoEstrategia;
public record AtividadeRequest(@NotBlank String titulo, @NotNull Long turmaId, @NotNull Long problemaId, @NotNull Set<Long> estrategiaIds) {}
