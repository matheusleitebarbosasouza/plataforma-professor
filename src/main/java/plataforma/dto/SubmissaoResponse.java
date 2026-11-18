package plataforma.dto;

import plataforma.entidades.Submissao;
import plataforma.enums.StatusSubmissao;
import java.time.LocalDateTime;

public record SubmissaoResponse(
    Long id,
    Long estudanteId,
    Long atividadeId,
    Integer versao,
    LocalDateTime dataHora,
    StatusSubmissao status,
    String mensagem
) {

    public static SubmissaoResponse from(Submissao s) {
        return new SubmissaoResponse(
            s.id,
            s.estudante.id,
            s.atividade.id,
            s.versao,
            s.dataHora,
            s.status,
            "Submissão recebida com sucesso"
        );
    }
}
