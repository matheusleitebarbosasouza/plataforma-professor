package plataforma.service;

import plataforma.dto.SubmissaoRequest;
import plataforma.entidades.*;
import plataforma.enums.*;
import plataforma.repositorio.SubmissaoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissaoService {

    private final ApplicationEventPublisher eventPublisher;
    private final SubmissaoRepository repo;
    private final EstudanteService estudantes;
    private final AtividadeService atividades;
    private final FilaSubmissaoService fila;
    private final HistoricoStatusRepository historicos;
    private final NotificacaoService notificacoes;

    public SubmissaoService(
            SubmissaoRepository repo,
            EstudanteService estudantes,
            AtividadeService atividades) {

        this.repo = repo;
        this.estudantes = estudantes;
        this.atividades = atividades;
    }

    @Transactional
    public Submissao criar(SubmissaoRequest r) {

        Estudante estudante = estudantes.buscar(r.estudanteId());
        Atividade atividade = atividades.buscar(r.atividadeId());

        // Verificar se a atividade está aberta
        if (atividade.status != StatusAtividade.ABERTA) {
            throw new IllegalStateException(
                "A atividade não aceita submissões"
            );
        }

        // Verificar se o estudante pertence à turma
        boolean pertence = atividade.turma.estudantes
                .stream()
                .anyMatch(e -> e.id.equals(estudante.id));

        if (!pertence) {
            throw new IllegalStateException(
                "Estudante não pertence à turma da atividade"
            );
        }

        // Consultar a última versão
        int ultimaVersao = repo
                .findFirstByEstudanteIdAndAtividadeIdOrderByVersaoDesc(
                    estudante.id,
                    atividade.id
                )
                .map(s -> s.versao)
                .orElse(0);

        // Criar uma nova submissão
        Submissao submissao = new Submissao();

        submissao.estudante = estudante;
        submissao.atividade = atividade;
        submissao.codigoFonte = r.codigoFonte();
        submissao.dataHora = LocalDateTime.now();
        submissao.versao = ultimaVersao + 1;
        submissao.status = StatusSubmissao.SUBMETIDA;
        
        Submissao salva = repo.save(submissao);

        HistoricoStatus historico = new HistoricoStatus();
        historico.submissao = salva;
        historico.statusAnterior = null;
        historico.statusNovo = StatusSubmissao.SUBMETIDA;
        historico.dataHora = LocalDateTime.now();
        
        historicos.save(historico);
        
        notificacoes.notificar(
            salva,
            StatusSubmissao.SUBMETIDA
        );
        
        fila.enfileirar(salva.id);
        
        return salva;
    }

    @Transactional(readOnly = true)
    public List<Submissao> listarVersoes(
            Long estudanteId,
            Long atividadeId) {

        return repo
                .findByEstudanteIdAndAtividadeIdOrderByVersaoDesc(
                    estudanteId,
                    atividadeId
                );
    }
}
