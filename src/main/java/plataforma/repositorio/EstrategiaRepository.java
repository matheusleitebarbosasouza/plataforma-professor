package br.edu.plataforma.repository;
import br.edu.plataforma.entity.EstrategiaAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EstrategiaRepository extends JpaRepository<EstrategiaAvaliacao,Long> {  }
