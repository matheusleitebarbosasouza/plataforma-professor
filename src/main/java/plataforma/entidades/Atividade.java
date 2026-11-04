package plataforma.entidades;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "atividades")
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String titulo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "turma_id")
    public Turma turma;

    @ManyToOne(optional = false)
    @JoinColumn(name = "problema_id")
    public Problema problema;

    @ManyToMany
    @JoinTable(
        name = "atividade_estrategia",
        joinColumns = @JoinColumn(name = "atividade_id"),
        inverseJoinColumns = @JoinColumn(name = "estrategia_id")
    )
    public Set<EstrategiaAvaliacao> estrategias = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "atividade_participante",
        joinColumns = @JoinColumn(name = "atividade_id"),
        inverseJoinColumns = @JoinColumn(name = "estudante_id"),
        uniqueConstraints = @UniqueConstraint(
            columnNames = {"atividade_id", "estudante_id"}
        )
    )
    public Set<Estudante> participantes = new HashSet<>();
}
