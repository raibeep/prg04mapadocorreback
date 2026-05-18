package br.com.ifba.mapadocorreapi.service;
import br.com.ifba.mapadocorreapi.entity.Categoria;
import java.util.List;

public interface CategoriaIService {
    public Categoria save(Categoria categoria);
    public List<Categoria> listCategorias();
    public Categoria findById(String id);
    public Categoria updateCategoria(String id, Categoria categoria);
    public void deleteCategoria(String id);
}
