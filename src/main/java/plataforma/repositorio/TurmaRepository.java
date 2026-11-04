package plataforma.repositorio;
import plataforma.entidades.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TurmaRepository extends JpaRepository<Turma,Long> { java.util.List<Turma> findByProfessorResponsavelId(Long professorId); }
