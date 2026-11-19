package plataforma.repositorio;

import plataforma.entidades.HistoricoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoricoStatusRepository
        extends JpaRepository<HistoricoStatus, Long> {

    List<HistoricoStatus> findBySubmissaoIdOrderByDataHoraAsc(
        Long submissaoId
    );
}
