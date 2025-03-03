package br.com.foursales.ecommerce.api.repository.elasticSearch;

import br.com.foursales.ecommerce.api.model.elasticSearch.ProdutoElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProdutoElasticRepository extends ElasticsearchRepository<ProdutoElastic, String> {
}
