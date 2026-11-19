package plataforma.service;

import plataforma.entidades.*;
import plataforma.enums.StatusSubmissao;
import plataforma.repositorio.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusSubmissaoService {

    private final SubmissaoRepository submissoes;
    private final HistoricoStatusRepository historicos;
    private final NotificacaoService notificacoes;

    public StatusSubmissaoService(
            SubmissaoRepository submissoes,
            HistoricoStatusRepository historicos,
            NotificacaoService notificacoes) {

        this.submissoes = submissoes;
        this.historicos = historicos;
        this.notificacoes = notificacoes;
    }

    @Transactional
    public void alterar(
            Long submissaoId,
            StatusSubmissao novoStatus) {

        Submissao submissao = submissoes.findById(submissaoId)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Submissão não encontrada"
                )
            );

        StatusSubmissao anterior = submissao.status;

        boolean permitida = switch (anterior) {
            case SUBMETIDA ->
                novoStatus == StatusSubmissao.EM_FILA;

            case EM_FILA ->
                novoStatus == StatusSubmissao.PROCESSANDO;

            case PROCESSANDO ->
                novoStatus == StatusSubmissao.CONCLUIDA
                || novoStatus == StatusSubmissao.FALHA;

            default -> false;
        };

        if (!permitida) {
            throw new IllegalStateException(
                "Transição de estado inválida: "
                + anterior + " -> " + novoStatus
            );
        }

        submissao.status = novoStatus;

        HistoricoStatus historico = new HistoricoStatus();
        historico.submissao = submissao;
        historico.statusAnterior = anterior;
        historico.statusNovo = novoStatus;
        historico.dataHora = LocalDateTime.now();

        submissoes.save(submissao);
        historicos.save(historico);

        notificacoes.notificar(submissao, novoStatus);
    }

    @Transactional(readOnly = true)
    public List<HistoricoStatus> listarHistorico(
            Long submissaoId) {

        if (!submissoes.existsById(submissaoId)) {
            throw new IllegalArgumentException(
                "Submissão não encontrada"
            );
        }

        return historicos
            .findBySubmissaoIdOrderByDataHoraAsc(submissaoId);
    }
}
