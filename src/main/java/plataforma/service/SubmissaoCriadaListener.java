package plataforma.service;

import plataforma.entidades.Submissao;
import plataforma.repositorio.SubmissaoRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
public class SubmissaoCriadaListener {

    private final SubmissaoRepository repo;
    private final FilaSubmissaoService fila;
    private final NotificacaoService notificacoes;

    public SubmissaoCriadaListener(
            SubmissaoRepository repo,
            FilaSubmissaoService fila,
            NotificacaoService notificacoes) {

        this.repo = repo;
        this.fila = fila;
        this.notificacoes = notificacoes;
    }

    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void receber(SubmissaoCriadaEvent evento) {

        Submissao submissao = repo.findById(evento.submissaoId())
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Submissão não encontrada"
                )
            );

        notificacoes.notificar(
            submissao,
            submissao.status
        );

        fila.enfileirar(submissao.id);
    }
}
