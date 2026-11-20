package plataforma.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.entidades.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    boolean existsByProblemaId(Long problemaId);
    boolean existsByEstrategiaId(Long estrategiaId);
    boolean existsByLlmPadraoId(Long llmId);
}
