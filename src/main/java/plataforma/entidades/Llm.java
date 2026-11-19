package plataforma.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import plataforma.seguranca.ChaveAcessoConverter;

@Entity
@Table(name = "llms")
public class Llm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String nomeModelo;

    @Column(nullable = false, length = 1000)
    public String urlApi;

    @JsonIgnore
    @Convert(converter = ChaveAcessoConverter.class)
    @Column(nullable = false, length = 3000)
    public String chaveAcesso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    public Professor professor;
}
