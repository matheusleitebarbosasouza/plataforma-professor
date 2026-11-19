package plataforma.entidades;

import plataforma.enums.StatusSubmissao;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_status")
public class HistoricoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "submissao_id")
    public Submissao submissao;

    @Enumerated(EnumType.STRING)
    public StatusSubmissao statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusSubmissao statusNovo;

    @Column(nullable = false)
    public LocalDateTime dataHora;
}
