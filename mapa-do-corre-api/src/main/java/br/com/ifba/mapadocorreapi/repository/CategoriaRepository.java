package br.com.ifba.mapadocorreapi.repository;

import br.com.ifba.mapadocorreapi.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String> {

}