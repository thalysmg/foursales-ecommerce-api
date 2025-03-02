package br.com.foursales.ecommerce.api.model;

import br.com.foursales.ecommerce.api.exceptions.RegraDeNegocioException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "pedido_produto")
public class PedidoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @NotNull
    @Column(name = "qtd_produto", nullable = false)
    private int qtdProduto;

    @NotNull
    @Column(name = "preco_unitario", nullable = false, precision = 11, scale = 2)
    private BigDecimal precoUnitario;

    public PedidoProduto(Produto produto, int qtdProduto, BigDecimal precoUnitario) throws RegraDeNegocioException {
        if (isNull(precoUnitario)) {
            throw new RegraDeNegocioException("Preço Unitário do produto não pode ser nulo");
        }
        if (qtdProduto == 0) {
            throw new RegraDeNegocioException("Não é possível adicionar um produto com a quantidade igual a zero");
        }
        this.produto = produto;
        this.qtdProduto = qtdProduto;
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getValorTotalProdutos() {
        BigDecimal qtd = BigDecimal.valueOf(qtdProduto);
        return precoUnitario.multiply(qtd);
    }
}