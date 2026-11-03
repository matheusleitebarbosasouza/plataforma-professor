package br.edu.plataforma.repository;
import br.edu.plataforma.entity.Problema;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProblemaRepository extends JpaRepository<Problema,Long> {  }
