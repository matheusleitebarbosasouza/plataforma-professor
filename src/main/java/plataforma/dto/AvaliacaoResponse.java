
package plataforma.dto;

import plataforma.entidades.Avaliacao;
import java.util.List;

public record AvaliacaoResponse(
        Long id,
        Long submissaoId,
        Double nota,
        String evidencias,
        String justificativa,
        List<ResultadoLLMResponse> resultados
) {

    public static AvaliacaoResponse from(Avaliacao a) {

        return new AvaliacaoResponse(
            a.id,
            a.submissao.id,
            a.nota,
            a.evidencias,
            a.justificativa,
            a.resultados.stream()
                .map(ResultadoLLMResponse::from)
                .toList()
        );
    }
}
