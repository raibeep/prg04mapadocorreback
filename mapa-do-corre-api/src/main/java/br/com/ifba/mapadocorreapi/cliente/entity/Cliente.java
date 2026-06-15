package br.com.ifba.mapadocorreapi.cliente.entity;

import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import br.com.ifba.mapadocorreapi.pessoa.entity.Pessoa;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Pessoa {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
