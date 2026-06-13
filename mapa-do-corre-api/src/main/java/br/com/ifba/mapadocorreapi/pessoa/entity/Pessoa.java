package br.com.ifba.mapadocorreapi.pessoa.entity;

import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public abstract class Pessoa extends PersistenceEntity {
    private String nome;
    private String cpf;
    private String telefone;
    private String fotoPerfil; //na minha ideia será passado a URL da imagem
    private String bio;
}
