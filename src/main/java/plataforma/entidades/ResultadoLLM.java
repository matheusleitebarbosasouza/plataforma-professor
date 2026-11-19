
package plataforma.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "resultados_llm")
public class ResultadoLLM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "avaliacao_id")
    public Avaliacao avaliacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "llm_id")
    public LLM llm;

    @Column(nullable = false)
    public Double nota;

    @Column(columnDefinition = "TEXT")
    public String evidencias;

    @Column(columnDefinition = "TEXT")
    public String justificativa;
}
