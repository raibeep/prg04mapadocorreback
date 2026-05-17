@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;
    private String email;
    private String senha;
    private String fotoPerfil;
    private String bio;
    private Date criadoEm;

}