package plataforma.repositorio;
import plataforma.entidades.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EstudanteRepository extends JpaRepository<Estudante,Long> { boolean existsByMatricula(String matricula);
boolean existsByMatriculaAndIdNot(String matricula,Long id); }
