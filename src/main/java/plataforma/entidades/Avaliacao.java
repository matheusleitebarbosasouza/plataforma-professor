
package plataforma.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "avaliacoes")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "submissao_id", unique = true)
    public Submissao submissao;

    @Column(nullable = false)
    public Double nota;

    @Column(columnDefinition = "TEXT")
    public String evidencias;

    @Column(columnDefinition = "TEXT")
    public String justificativa;

    @Column(nullable = false)
    public LocalDateTime dataHora;

    @OneToMany(
        mappedBy = "avaliacao",
        cascade = CascadeType.ALL
    )
    public List<ResultadoLLM> resultados = new ArrayList<>();
}
