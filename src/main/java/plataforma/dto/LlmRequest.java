package plataforma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LlmRequest(
        @NotBlank String nomeModelo,
        @NotBlank String urlApi,
        @NotBlank String chaveAcesso,
        @NotNull Long professorId
) {}
