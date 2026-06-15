package br.com.ifba.mapadocorreapi.pessoa.entity;

import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pessoa extends PersistenceEntity {
    private String nome;
    private String cpf;
    private String telefone;
    private String fotoPerfil; //na minha ideia será passado a URL da imagem
    private String bio;
}
