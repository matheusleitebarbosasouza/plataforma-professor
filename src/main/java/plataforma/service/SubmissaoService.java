
package plataforma.service;

import plataforma.dto.SubmissaoRequest;
import plataforma.entidades.Atividade;
import plataforma.entidades.Estudante;
import plataforma.entidades.HistoricoStatus;
import plataforma.entidades.Submissao;
import plataforma.enums.StatusAtividade;
import plataforma.enums.StatusSubmissao;
import plataforma.repositorio.HistoricoStatusRepository;
import plataforma.repositorio.SubmissaoRepository;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubmissaoService {

    private final SubmissaoRepository repo;
    private final EstudanteService estudantes;
    private final AtividadeService atividades;
    private final HistoricoStatusRepository historicos;
    private final ApplicationEventPublisher eventPublisher;

    public SubmissaoService(
            SubmissaoRepository repo,
            EstudanteService estudantes,
            AtividadeService atividades,
            HistoricoStatusRepository historicos,
            ApplicationEventPublisher eventPublisher) {

        this.repo = repo;
        this.estudantes = estudantes;
        this.atividades = atividades;
        this.historicos = historicos;
        this.eventPublisher = eventPublisher;
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

        // Salvar a submissão
        Submissao salva = repo.save(submissao);

        // Registrar o status inicial no histórico
        HistoricoStatus historico = new HistoricoStatus();

        historico.submissao = salva;
        historico.statusAnterior = null;
        historico.statusNovo = StatusSubmissao.SUBMETIDA;
        historico.dataHora = LocalDateTime.now();

        historicos.save(historico);

        // Publicar evento para processamento após o commit
        eventPublisher.publishEvent(
            new SubmissaoCriadaEvent(salva.id)
        );

        return salva;
    }

    @Transactional(readOnly = true)
    public Submissao buscar(Long id) {

        return repo.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "Submissão não encontrada"
                    )
                );
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
