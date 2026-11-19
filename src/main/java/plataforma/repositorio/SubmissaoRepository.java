package plataforma.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.entidades.Submissao;
import java.util.List;
import java.util.Optional;

public interface SubmissaoRepository extends JpaRepository<Submissao, Long> {
    Optional<Submissao> findFirstByEstudanteIdAndAtividadeIdOrderByVersaoDesc(Long estudanteId, Long atividadeId);
    List<Submissao> findByEstudanteIdAndAtividadeIdOrderByVersaoDesc(Long estudanteId, Long atividadeId);
}
