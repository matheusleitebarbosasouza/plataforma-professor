package plataforma.entidades;
import br.edu.plataforma.enums.StatusTurma;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity @Table(name="turmas") public class Turma {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
@Column(nullable=false) public String nome;
@Column(nullable=false) public String codigo;
@Column(nullable=false) public String periodoLetivo;
@ManyToOne(optional=false) @JoinColumn(name="professor_id") public Professor professorResponsavel;
@Enumerated(EnumType.STRING) @Column(nullable=false) public StatusTurma status=StatusTurma.ATIVA;
@ManyToMany @JoinTable(name="turma_estudante",joinColumns=@JoinColumn(name="turma_id"),inverseJoinColumns=@JoinColumn(name="estudante_id"),uniqueConstraints=@UniqueConstraint(columnNames={"turma_id","estudante_id"})) public Set<Estudante> estudantes=new HashSet<>();
}
