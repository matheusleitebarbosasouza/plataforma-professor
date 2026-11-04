package plataforma.service;

import plataforma.dto.EstrategiaRequest;
import plataforma.dto.PromptRequest;
import plataforma.entidades.EstrategiaAvaliacao;
import plataforma.repositorio.EstrategiaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstrategiaService {

    private final EstrategiaRepository repo;

    public EstrategiaService(EstrategiaRepository repo) {
        this.repo = repo;
    }

    public EstrategiaAvaliacao buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "Estratégia não encontrada"
                    )
                );
    }

    public List<EstrategiaAvaliacao> listar() {
        return repo.findAll();
    }

    @Transactional
    public EstrategiaAvaliacao criar(EstrategiaRequest r) {
        EstrategiaAvaliacao e = new EstrategiaAvaliacao();

        copiar(e, r);

        return repo.save(e);
    }

    @Transactional
    public EstrategiaAvaliacao editar(Long id, EstrategiaRequest r) {
        EstrategiaAvaliacao e = buscar(id);

        copiar(e, r);

        return repo.save(e);
    }

    @Transactional
    public EstrategiaAvaliacao alterarPrompt(Long id, PromptRequest r) {
        EstrategiaAvaliacao e = buscar(id);

        e.promptAvaliacao = r.promptAvaliacao();

        return repo.save(e);
    }

    private void copiar(EstrategiaAvaliacao e, EstrategiaRequest r) {
        e.nome = r.nome();
        e.tipo = r.tipo();
        e.descricao = r.descricao();
        e.promptAvaliacao = r.promptAvaliacao();
    }
}
