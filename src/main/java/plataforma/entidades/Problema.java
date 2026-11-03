package plataforma.entidades;
import br.edu.plataforma.enums.StatusProblema;
import jakarta.persistence.*;
@Entity @Table(name="problemas") public class Problema {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
@Column(nullable=false) public String titulo;
@Column(nullable=false,columnDefinition="TEXT") public String descricao;
@Column(nullable=false,columnDefinition="TEXT") public String requisitosFuncionais;
@Column(nullable=false,columnDefinition="TEXT") public String requisitosProjeto;
@Enumerated(EnumType.STRING) @Column(nullable=false) public StatusProblema status=StatusProblema.ATIVO;
}
