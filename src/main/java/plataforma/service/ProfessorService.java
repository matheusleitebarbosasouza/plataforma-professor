package plataforma.service;

import plataforma.dto.ProfessorRequest;
import plataforma.entidades.Professor;
import plataforma.repositorio.ProfessorRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository repo;

    public ProfessorService(ProfessorRepository repo) {
        this.repo = repo;
    }

    public Professor buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "Professor não encontrado"
                    )
                );
    }

    public List<Professor> listar() {
        return repo.findAll();
    }

    @Transactional
    public Professor criar(ProfessorRequest r) {
        Professor p = new Professor();

        p.nome = r.nome();
        p.email = r.email();

        return repo.save(p);
    }
}
