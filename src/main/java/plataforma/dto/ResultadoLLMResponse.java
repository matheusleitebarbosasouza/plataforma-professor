

package plataforma.dto;

import plataforma.entidades.ResultadoLLM;

public record ResultadoLLMResponse(
        Long llmId,
        String nomeModelo,
        Double nota,
        String evidencias,
        String justificativa
) {

    public static ResultadoLLMResponse from(ResultadoLLM r) {
        return new ResultadoLLMResponse(
            r.llm.id,
            r.llm.nomeModelo,
            r.nota,
            r.evidencias,
            r.justificativa
        );
    }
}
