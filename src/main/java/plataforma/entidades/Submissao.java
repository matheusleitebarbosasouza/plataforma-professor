package plataforma.entidades;

import jakarta.persistence.*;
import plataforma.enums.StatusSubmissao;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissoes")
public class Submissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudante_id", nullable = false)
    public Estudante estudante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "atividade_id", nullable = false)
    public Atividade atividade;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String codigoFonte;

    @Column(nullable = false)
    public Integer versao;

    @Column(nullable = false)
    public LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public StatusSubmissao status;
}
