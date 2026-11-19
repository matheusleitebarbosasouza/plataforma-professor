
package plataforma.repositorio;

import plataforma.entidades.ResultadoLLM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultadoLLMRepository
        extends JpaRepository<ResultadoLLM, Long> {

    List<ResultadoLLM> findByAvaliacaoId(Long avaliacaoId);
}
