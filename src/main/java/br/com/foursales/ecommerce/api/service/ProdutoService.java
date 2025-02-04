package br.com.foursales.ecommerce.api.service;

import br.com.foursales.ecommerce.api.dto.ProdutoDTO;
import br.com.foursales.ecommerce.api.dto.ProdutoFiltro;
import br.com.foursales.ecommerce.api.exceptions.RegistroNaoEncontradoException;
import br.com.foursales.ecommerce.api.model.Produto;
import br.com.foursales.ecommerce.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public ProdutoDTO cadastrar(ProdutoDTO produto) {
        return ProdutoDTO.converterParaDTO(repository.save(produto.converterParaEntidade()));
    }

    @Transactional(rollbackFor = Exception.class)
    public ProdutoDTO atualizar(String id, ProdutoDTO produtoDTO) throws RegistroNaoEncontradoException {
        Produto produto = buscarPorId(id);
        produtoDTO.atualizarEntidade(produto);
        return ProdutoDTO.converterParaDTO(repository.save(produto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void excluirProduto(String id) throws RegistroNaoEncontradoException {
        Produto produto = buscarPorId(id);
        repository.delete(produto);
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(String id) throws RegistroNaoEncontradoException {
        return repository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<ProdutoDTO> listarProdutos(ProdutoFiltro filtro, Pageable pageable) {
        return repository.findAll(pageable).map(ProdutoDTO::converterParaDTO);
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorIds(List<String> idsProdutos) {
        return repository.findAllByIdIn(idsProdutos);
    }
}
