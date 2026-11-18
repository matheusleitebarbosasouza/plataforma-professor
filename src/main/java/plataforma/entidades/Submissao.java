package plataforma.entidades;

import plataforma.enums.StatusSubmissao;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "submissoes",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {
            "estudante_id",
            "atividade_id",
            "versao"
        }
    )
)
public class Submissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudante_id")
    public Estudante estudante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "atividade_id")
    public Atividade atividade;

    @Column(nullable = false)
    public LocalDateTime dataHora;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String codigoFonte;

    @Column(nullable = false)
    public Integer versao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusSubmissao status = StatusSubmissao.SUBMETIDA;
}
