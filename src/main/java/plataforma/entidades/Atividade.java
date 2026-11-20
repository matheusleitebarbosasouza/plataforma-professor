package plataforma.entidades;

import jakarta.persistence.*;
import plataforma.enums.StatusAtividade;
import java.time.LocalDateTime;
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

    @Column(nullable = false, columnDefinition = "TEXT")
    public String descricao;

    @Column(nullable = false)
    public LocalDateTime prazoSubmissao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusAtividade status = StatusAtividade.ABERTA;

    @ManyToOne(optional = false)
    @JoinColumn(name = "turma_id", nullable = false)
    public Turma turma;

    @ManyToOne(optional = false)
    @JoinColumn(name = "problema_id", nullable = false)
    public Problema problema;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estrategia_id", nullable = false)
    public EstrategiaAvaliacao estrategia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "llm_id", nullable = false)
    public Llm llmPadrao;

    @ManyToMany
    @JoinTable(name = "atividade_participante",
        joinColumns = @JoinColumn(name = "atividade_id"),
        inverseJoinColumns = @JoinColumn(name = "estudante_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"atividade_id", "estudante_id"}))
    public Set<Estudante> participantes = new HashSet<>();
}
