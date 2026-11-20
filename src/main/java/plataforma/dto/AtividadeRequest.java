package plataforma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AtividadeRequest(
        @NotBlank String titulo,
        @NotBlank String descricao,
        @NotNull LocalDateTime prazoSubmissao,
        @NotNull Long turmaId,
        @NotNull Long problemaId,
        @NotNull Long estrategiaId,
        @NotNull Long llmId
) {}
