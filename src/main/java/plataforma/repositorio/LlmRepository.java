package plataforma.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import plataforma.entidades.Llm;

import java.util.List;
import java.util.Optional;

public interface LlmRepository extends JpaRepository<Llm, Long> {
    List<Llm> findByProfessorId(Long professorId);
    Optional<Llm> findByIdAndProfessorId(Long id, Long professorId);
}
