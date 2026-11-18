package plataforma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubmissaoRequest(
    @NotNull Long estudanteId,
    @NotNull Long atividadeId,
    @NotBlank String codigoFonte
) {
}
