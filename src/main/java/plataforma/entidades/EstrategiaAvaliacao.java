@Entity
@Table(name = "estrategias")
public class EstrategiaAvaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public TipoEstrategia tipo;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String descricao;

    @Column(nullable = false, columnDefinition = "TEXT")
    public String promptAvaliacao;
}

