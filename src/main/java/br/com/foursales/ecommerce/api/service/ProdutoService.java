package br.com.foursales.ecommerce.api.service;

import br.com.foursales.ecommerce.api.dto.ProdutoDTO;
import br.com.foursales.ecommerce.api.dto.ProdutoFiltro;
import br.com.foursales.ecommerce.api.exceptions.RegistroNaoEncontradoException;
import br.com.foursales.ecommerce.api.model.Produto;
import br.com.foursales.ecommerce.api.model.esDocument.ProdutoElastic;
import br.com.foursales.ecommerce.api.repository.ProdutoRepository;
import br.com.foursales.ecommerce.api.repository.elasticSearch.ProdutoElasticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoElasticRepository elasticRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Transactional(rollbackFor = Exception.class)
    public ProdutoDTO cadastrar(ProdutoDTO produtoDTO) {
        Produto produto = repository.save(produtoDTO.converterParaEntidade());
        elasticRepository.save(new ProdutoElastic(produto));
        return ProdutoDTO.converterParaDTO(produto);
    }

    @Transactional(rollbackFor = Exception.class)
    public ProdutoDTO atualizar(String id, ProdutoDTO produtoDTO) throws RegistroNaoEncontradoException {
        Produto produto = buscarPorId(id);
        produtoDTO.atualizarEntidade(produto);
        produto = repository.save(produto);

        ProdutoElastic produtoDocument = elasticRepository.findById(produto.getId())
            .orElse(new ProdutoElastic(produto));
        elasticRepository.save(produtoDocument);

        return ProdutoDTO.converterParaDTO(produto);
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
        Criteria criteria = new Criteria();

        if (isNotBlank(filtro.nome())) {
            criteria = criteria.and(new Criteria("nome").expression("\""+ filtro.nome() + "\""));
        }
        if (isNotBlank(filtro.categoria())) {
            criteria = criteria.and(new Criteria("categoria").is(filtro.categoria()));
        }
        if (filtro.valorMin() != null) {
            criteria = criteria.and(new Criteria("preco").greaterThanEqual(filtro.valorMin()));
        }
        if (filtro.valorMax() != null) {
            criteria = criteria.and(new Criteria("preco").lessThanEqual(filtro.valorMax()));
        }
        CriteriaQuery query = new CriteriaQuery(criteria, pageable);
        SearchHits<ProdutoElastic> searchHits = elasticsearchTemplate.search(query, ProdutoElastic.class);
        List<ProdutoDTO> produtos = searchHits.getSearchHits().stream().map(SearchHit::getContent).map(ProdutoDTO::converterParaDTO).toList();
        return new PageImpl<>(produtos, pageable, searchHits.getTotalHits());
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorIds(List<String> idsProdutos) {
        return repository.findAllByIdIn(idsProdutos);
    }
}
