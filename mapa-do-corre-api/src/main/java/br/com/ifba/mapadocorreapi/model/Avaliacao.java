@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Integer nota;
    private Date criadoEm;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;
}