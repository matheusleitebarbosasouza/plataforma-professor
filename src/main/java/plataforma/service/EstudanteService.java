package plataforma.service;

import plataforma.dto.EstudanteRequest;
import plataforma.entidades.Estudante;
import plataforma.repositorio.EstudanteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstudanteService {

    private final EstudanteRepository repo;

    public EstudanteService(EstudanteRepository repo) {
        this.repo = repo;
    }

    public Estudante buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "Estudante não encontrado"
                    )
                );
    }

    public List<Estudante> listar() {
        return repo.findAll();
    }

    @Transactional
    public Estudante criar(EstudanteRequest r) {

        if (repo.existsByMatricula(r.matricula())) {
            throw new IllegalStateException(
                "Matrícula já cadastrada"
            );
        }

        Estudante e = new Estudante();

        copiar(e, r);

        return repo.save(e);
    }

    @Transactional
    public Estudante editar(Long id, EstudanteRequest r) {

        Estudante e = buscar(id);

        if (repo.existsByMatriculaAndIdNot(r.matricula(), id)) {
            throw new IllegalStateException(
                "Matrícula já cadastrada"
            );
        }

        copiar(e, r);

        return repo.save(e);
    }

    private void copiar(Estudante e, EstudanteRequest r) {
        e.nome = r.nome();
        e.matricula = r.matricula();
        e.email = r.email();
    }
}

