package plataforma.repositorio;
import plataforma.entidade.Problema;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProblemaRepository extends JpaRepository<Problema,Long> {  }
