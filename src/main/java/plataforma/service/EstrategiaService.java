package br.edu.plataforma.service;
import br.edu.plataforma.dto.*;
import br.edu.plataforma.entity.EstrategiaAvaliacao;
import br.edu.plataforma.repository.EstrategiaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service public class EstrategiaService {
private final EstrategiaRepository repo;
public EstrategiaService(EstrategiaRepository repo){this.repo=repo;}
public EstrategiaAvaliacao buscar(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Estratégia não encontrada"));}
public List<EstrategiaAvaliacao> listar(){return repo.findAll();}
public EstrategiaAvaliacao criar(EstrategiaRequest r){EstrategiaAvaliacao e=new EstrategiaAvaliacao();copiar(e,r);return repo.save(e);}
public EstrategiaAvaliacao editar(Long id,EstrategiaRequest r){EstrategiaAvaliacao e=buscar(id);copiar(e,r);return repo.save(e);}
public EstrategiaAvaliacao alterarPrompt(Long id,PromptRequest r){EstrategiaAvaliacao e=buscar(id);e.promptAvaliacao=r.promptAvaliacao();return repo.save(e);}
private void copiar(EstrategiaAvaliacao e,EstrategiaRequest r){e.nome=r.nome();e.tipo=r.tipo();e.descricao=r.descricao();e.promptAvaliacao=r.promptAvaliacao();}
}
