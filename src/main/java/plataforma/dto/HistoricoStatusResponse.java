package plataforma.dto;

import plataforma.entidades.HistoricoStatus;
import plataforma.enums.StatusSubmissao;
import java.time.LocalDateTime;

public record HistoricoStatusResponse(
    StatusSubmissao statusAnterior,
    StatusSubmissao statusNovo,
    LocalDateTime dataHora
) {
    public static HistoricoStatusResponse from(HistoricoStatus h) {
        return new HistoricoStatusResponse(
            h.statusAnterior,
            h.statusNovo,
            h.dataHora
        );
    }
}
