package br.com.ifba.mapadocorreapi.produto.service;

import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.itempedido.repository.ItemPedidoRepository;
import br.com.ifba.mapadocorreapi.produto.entity.Produto;
import br.com.ifba.mapadocorreapi.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;


@RequiredArgsConstructor
@Service
public class ProdutoService implements ProdutoIService{
    private final ProdutoRepository produtoRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    public Produto save(Produto produto) {
        if (produtoRepository.findByNomeAndNegocioId(
                produto.getNome(),
                produto.getNegocio().getId()).isPresent()) {

            throw new BusinessException("Já existe um produto com esse nome neste negócio.");
        }

        produto.setCriadoEm(new Date());

        return produtoRepository.save(produto);
    }

    @Override
    public Produto findById(Long id) {
       return produtoRepository.findById(id).orElseThrow(() ->
                new BusinessException("Produto não encontrado!"));
    }

    @Override
    public Page<Produto> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    @Override
    public Produto update(Long id, Produto produto) {
        Produto produtoExistente = findById(id);

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setFoto(produto.getFoto());
        produtoExistente.setTipo(produto.getTipo());

        return produtoRepository.save(produtoExistente);
    }

    @Override
    public void delete(Long id) {
        Produto produto = findById(id);

        if(itemPedidoRepository.existsByProduto(produto)){
            throw new BusinessException(
                    "Não é possível excluir um produto que já faz parte de um pedido."
            );
        }

        produtoRepository.delete(produto);
    }
}
