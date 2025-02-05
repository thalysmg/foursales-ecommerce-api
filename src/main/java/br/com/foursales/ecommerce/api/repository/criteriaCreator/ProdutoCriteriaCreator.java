package br.com.foursales.ecommerce.api.repository.criteriaCreator;

import br.com.foursales.ecommerce.api.dto.ProdutoFiltro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

public class ProdutoCriteriaCreator {

    public static CriteriaQuery createCriteriaQueryPorFiltro(ProdutoFiltro filtro, Pageable pageable) {
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
        criteria = criteria.and(new Criteria("qtdEstoque").greaterThan(0));

        return new CriteriaQuery(criteria, pageable);
    }
}
