package plataforma.service;

import plataforma.dto.ProblemaRequest;
import plataforma.entidades.Problema;
import plataforma.enums.StatusProblema;
import plataforma.repositorio.ProblemaRepository;
import plataforma.repositorio.AtividadeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProblemaService {

    private final ProblemaRepository repo;
    private final AtividadeRepository atividades;

    public ProblemaService(
            ProblemaRepository repo,
            AtividadeRepository atividades) {

        this.repo = repo;
        this.atividades = atividades;
    }

    public Problema buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "Problema não encontrado"
                    )
                );
    }

    public List<Problema> listar() {
        return repo.findAll();
    }

    @Transactional
    public Problema criar(ProblemaRequest r) {
        Problema p = new Problema();

        copiar(p, r);
        p.status = StatusProblema.ATIVO;

        return repo.save(p);
    }

    @Transactional
    public Problema editar(Long id, ProblemaRequest r) {
        Problema p = buscar(id);

        copiar(p, r);

        return repo.save(p);
    }

    @Transactional
    public Problema desativar(Long id) {
        Problema p = buscar(id);

        p.status = StatusProblema.INATIVO;

        return repo.save(p);
    }

    @Transactional
    public void remover(Long id) {
        Problema p = buscar(id);

        if (atividades.existsByProblemaId(id)) {
            throw new IllegalStateException(
                "Problema já utilizado em atividade"
            );
        }

        repo.delete(p);
    }

    private void copiar(Problema p, ProblemaRequest r) {
        p.titulo = r.titulo();
        p.descricao = r.descricao();
        p.requisitosFuncionais = r.requisitosFuncionais();
        p.requisitosProjeto = r.requisitosProjeto();
    }
}
