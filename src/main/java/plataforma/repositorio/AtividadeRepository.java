package plataforma.repositorio;
import plataforma.entidades.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AtividadeRepository extends JpaRepository<Atividade,Long> { boolean existsByProblemaId(Long problemaId); }
