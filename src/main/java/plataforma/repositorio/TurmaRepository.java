package br.edu.plataforma.repository;
import br.edu.plataforma.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TurmaRepository extends JpaRepository<Turma,Long> { java.util.List<Turma> findByProfessorResponsavelId(Long professorId); }
