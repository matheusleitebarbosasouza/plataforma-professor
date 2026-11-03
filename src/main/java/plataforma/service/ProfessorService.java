package br.edu.plataforma.service;
import br.edu.plataforma.dto.ProfessorRequest;
import br.edu.plataforma.entity.Professor;
import br.edu.plataforma.repository.ProfessorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service public class ProfessorService {
private final ProfessorRepository repo;
public ProfessorService(ProfessorRepository repo){this.repo=repo;}
public Professor buscar(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Professor não encontrado"));}
public List<Professor> listar(){return repo.findAll();}
public Professor criar(ProfessorRequest r){Professor p=new Professor();p.nome=r.nome();p.email=r.email();return repo.save(p);}
}
