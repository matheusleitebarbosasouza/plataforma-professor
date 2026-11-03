package br.edu.plataforma.service;
import br.edu.plataforma.dto.AtividadeRequest;
import br.edu.plataforma.entity.*;
import br.edu.plataforma.enums.*;
import br.edu.plataforma.repository.AtividadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service public class AtividadeService {
private final AtividadeRepository repo;private final TurmaService turmas;private final ProblemaService problemas;private final EstrategiaService estrategias;private final EstudanteService estudantes;
public AtividadeService(AtividadeRepository repo,TurmaService turmas,ProblemaService problemas,EstrategiaService estrategias,EstudanteService estudantes){this.repo=repo;this.turmas=turmas;this.problemas=problemas;this.estrategias=estrategias;this.estudantes=estudantes;}
public Atividade buscar(Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Atividade não encontrada"));}
public List<Atividade> listar(){return repo.findAll();}
@Transactional public Atividade criar(AtividadeRequest r,Long professorId){Turma t=turmas.buscarDoProfessor(r.turmaId(),professorId);if(t.status!=StatusTurma.ATIVA)throw new IllegalStateException("Turma encerrada não recebe atividades");Problema p=problemas.buscar(r.problemaId());if(p.status!=StatusProblema.ATIVO)throw new IllegalStateException("Problema inativo não pode ser associado");Atividade a=new Atividade();a.titulo=r.titulo();a.turma=t;a.problema=p;for(Long id:r.estrategiaIds())a.estrategias.add(estrategias.buscar(id));return repo.save(a);}
@Transactional public void participar(Long atividadeId,Long estudanteId){Atividade a=buscar(atividadeId);Estudante e=estudantes.buscar(estudanteId);if(a.turma.estudantes.stream().noneMatch(s->s.id.equals(estudanteId)))throw new IllegalStateException("Estudante não pertence à turma");if(a.participantes.stream().anyMatch(s->s.id.equals(estudanteId)))throw new IllegalStateException("Estudante já participa da atividade");a.participantes.add(e);repo.save(a);}
}
