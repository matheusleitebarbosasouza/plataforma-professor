package br.edu.plataforma.repository;
import br.edu.plataforma.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProfessorRepository extends JpaRepository<Professor,Long> {  }
