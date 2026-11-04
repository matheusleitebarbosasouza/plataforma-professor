package plataforma.service;

import plataforma.dto.TurmaRequest;
import plataforma.entidades.*;
import plataforma.enums.StatusTurma;
import plataforma.repositorio.TurmaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TurmaService {

    private final TurmaRepository repo;
    private final ProfessorService professores;
    private final EstudanteService estudantes;

    public TurmaService(
            TurmaRepository repo,
            ProfessorService professores,
            EstudanteService estudantes) {

        this.repo = repo;
        this.professores = professores;
        this.estudantes = estudantes;
    }

    public Turma buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException("Turma não encontrada")
                );
    }

    public List<Turma> listarPorProfessor(Long professorId) {
        return repo.findByProfessorResponsavelId(professorId);
    }

    @Transactional
    public Turma criar(TurmaRequest r) {
        Turma t = new Turma();

        copiar(t, r);

        t.professorResponsavel = professores.buscar(r.professorId());
        t.status = StatusTurma.ATIVA;

        return repo.save(t);
    }

    @Transactional
    public Turma editar(Long id, Long professorId, TurmaRequest r) {
        Turma t = buscarDoProfessor(id, professorId);

        verificarAtiva(t);

        copiar(t, r);

        return repo.save(t);
    }

    @Transactional
    public Turma encerrar(Long id, Long professorId) {
        Turma t = buscarDoProfessor(id, professorId);

        verificarAtiva(t);

        t.status = StatusTurma.ENCERRADA;

        return repo.save(t);
    }

    @Transactional
    public void adicionar(
            Long id,
            Long estudanteId,
            Long professorId) {

        Turma t = buscarDoProfessor(id, professorId);

        verificarAtiva(t);

        Estudante e = estudantes.buscar(estudanteId);

        boolean jaAssociado = t.estudantes.stream()
                .anyMatch(s -> s.id.equals(estudanteId));

        if (jaAssociado) {
            throw new IllegalStateException(
                "Estudante já associado"
            );
        }

        t.estudantes.add(e);

        repo.save(t);
    }

    @Transactional
    public void remover(
            Long id,
            Long estudanteId,
            Long professorId) {

        Turma t = buscarDoProfessor(id, professorId);

        verificarAtiva(t);

        boolean removido = t.estudantes.removeIf(
                e -> e.id.equals(estudanteId)
        );

        if (!removido) {
            throw new IllegalStateException(
                "Estudante não associado"
            );
        }

        repo.save(t);
    }

    @Transactional(readOnly = true)
    public Set<Estudante> listarEstudantes(
            Long id,
            Long professorId) {

        Turma t = buscarDoProfessor(id, professorId);

        return new HashSet<>(t.estudantes);
    }

    @Transactional(readOnly = true)
    public Turma buscarDoProfessor(Long id, Long professorId) {
        Turma t = buscar(id);

        if (!t.professorResponsavel.id.equals(professorId)) {
            throw new IllegalStateException(
                "Turma não pertence ao professor"
            );
        }

        return t;
    }

    private void verificarAtiva(Turma t) {
        if (t.status != StatusTurma.ATIVA) {
            throw new IllegalStateException(
                "Turma encerrada"
            );
        }
    }

    private void copiar(Turma t, TurmaRequest r) {
        t.nome = r.nome();
        t.codigo = r.codigo();
        t.periodoLetivo = r.periodoLetivo();
    }
}

