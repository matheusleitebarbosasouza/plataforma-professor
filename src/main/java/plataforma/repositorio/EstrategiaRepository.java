package plataforma.repositorio;
import plataforma.entidades.EstrategiaAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EstrategiaRepository extends JpaRepository<EstrategiaAvaliacao,Long> {  }
