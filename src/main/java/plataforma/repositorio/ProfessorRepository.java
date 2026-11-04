package plataforma.repositorio;
import plataforma.entidades.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProfessorRepository extends JpaRepository<Professor,Long> {  }
