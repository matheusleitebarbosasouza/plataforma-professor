package plataforma.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plataforma.dto.AtividadeRequest;
import plataforma.entidades.*;
import plataforma.enums.StatusAtividade;
import plataforma.enums.StatusProblema;
import plataforma.enums.StatusTurma;
import plataforma.repositorio.AtividadeRepository;

import java.util.List;

@Service
public class AtividadeService {
    private final AtividadeRepository repo;
    private final TurmaService turmas;
    private final ProblemaService problemas;
    private final EstrategiaService estrategias;
    private final EstudanteService estudantes;
    private final LlmService llms;

    public AtividadeService(AtividadeRepository repo, TurmaService turmas,
            ProblemaService problemas, EstrategiaService estrategias,
            EstudanteService estudantes, LlmService llms) {
        this.repo = repo;
        this.turmas = turmas;
        this.problemas = problemas;
        this.estrategias = estrategias;
        this.estudantes = estudantes;
        this.llms = llms;
    }

    @Transactional(readOnly = true)
    public Atividade buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atividade não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Atividade> listar() {
        return repo.findAll();
    }

    @Transactional
    public Atividade criar(AtividadeRequest request, Long professorId) {
        Turma turma = turmas.buscarDoProfessor(request.turmaId(), professorId);
        if (turma.status != StatusTurma.ATIVA) {
            throw new IllegalStateException("Turma encerrada não recebe atividades");
        }

        Problema problema = problemas.buscar(request.problemaId());
        if (problema.status != StatusProblema.ATIVO) {
            throw new IllegalStateException("Problema inativo não pode ser associado");
        }

        Atividade atividade = new Atividade();
        atividade.titulo = request.titulo();
        atividade.descricao = request.descricao();
        atividade.prazoSubmissao = request.prazoSubmissao();
        atividade.turma = turma;
        atividade.problema = problema;
        atividade.estrategia = estrategias.buscar(request.estrategiaId());
        atividade.llmPadrao = llms.buscarDoProfessor(request.llmId(), professorId);
        atividade.status = StatusAtividade.ABERTA;
        return repo.save(atividade);
    }

    @Transactional
    public Atividade concluir(Long id, Long professorId) {
        Atividade atividade = buscar(id);
        turmas.buscarDoProfessor(atividade.turma.id, professorId);
        if (atividade.status == StatusAtividade.CONCLUIDA) {
            throw new IllegalStateException("Atividade já está concluída");
        }
        atividade.status = StatusAtividade.CONCLUIDA;
        return repo.save(atividade);
    }

    @Transactional
    public void participar(Long atividadeId, Long estudanteId) {
        Atividade atividade = buscar(atividadeId);
        if (atividade.status != StatusAtividade.ABERTA) {
            throw new IllegalStateException("Atividade concluída não aceita participantes");
        }
        Estudante estudante = estudantes.buscar(estudanteId);
        boolean pertenceATurma = atividade.turma.estudantes.stream()
                .anyMatch(s -> s.id.equals(estudanteId));
        if (!pertenceATurma) {
            throw new IllegalStateException("Estudante não pertence à turma");
        }
        boolean jaParticipa = atividade.participantes.stream()
                .anyMatch(s -> s.id.equals(estudanteId));
        if (jaParticipa) {
            throw new IllegalStateException("Estudante já participa da atividade");
        }
        atividade.participantes.add(estudante);
        repo.save(atividade);
    }
}
