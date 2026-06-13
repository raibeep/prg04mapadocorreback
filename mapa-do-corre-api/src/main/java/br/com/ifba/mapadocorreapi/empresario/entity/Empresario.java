package br.com.ifba.mapadocorreapi.empresario.entity;

import br.com.ifba.mapadocorreapi.pessoa.entity.Pessoa;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Empresario extends Pessoa {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
