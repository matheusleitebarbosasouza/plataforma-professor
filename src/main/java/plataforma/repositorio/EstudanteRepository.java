package plataforma.repository;
import plataforma.entity.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EstudanteRepository extends JpaRepository<Estudante,Long> { boolean existsByMatricula(String matricula);
boolean existsByMatriculaAndIdNot(String matricula,Long id); }
