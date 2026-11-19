
package plataforma.repositorio;

import plataforma.entidades.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvaliacaoRepository
        extends JpaRepository<Avaliacao, Long> {

    Optional<Avaliacao> findBySubmissaoId(Long submissaoId);

    boolean existsBySubmissaoId(Long submissaoId);
}
