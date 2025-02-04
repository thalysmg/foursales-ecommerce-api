package br.com.foursales.ecommerce.api.model;

import br.com.foursales.ecommerce.api.enums.SituacaoPedido;
import br.com.foursales.ecommerce.api.exceptions.RegraDeNegocioException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static br.com.foursales.ecommerce.api.enums.SituacaoPedido.PAGO;
import static br.com.foursales.ecommerce.api.enums.SituacaoPedido.PENDENTE;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;
import static java.math.BigDecimal.ZERO;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "pedido")
@EntityListeners(AuditingEntityListener.class)
public class Pedido {

    @Id
    @Size(max = 36)
    @GeneratedValue(strategy = UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @NotNull
    @Column(name = "valor_total", nullable = false, precision = 11, scale = 2)
    private BigDecimal valorTotal;

    @NotNull
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuario cliente;

    @NotNull
    @CreatedDate
    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @NotNull
    @Enumerated(STRING)
    @Column(name = "situacao", nullable = false)
    private SituacaoPedido situacao;

    @JoinColumn(name = "id_pedido", nullable = false)
    @OneToMany(cascade = ALL, orphanRemoval = true)
    private List<PedidoProduto> pedidosProdutos;

    public Pedido(List<PedidoProduto> pedidosProdutos) throws RegraDeNegocioException {
        if (CollectionUtils.isEmpty(pedidosProdutos)) {
            throw new RegraDeNegocioException("Não é possível criar um pedido sem itens");
        }
        this.pedidosProdutos = pedidosProdutos;
        this.situacao = PENDENTE;
        setValorTotal();
    }

    private void setValorTotal() {
        valorTotal = pedidosProdutos.stream().map(PedidoProduto::getValorTotalProdutos).reduce(ZERO, BigDecimal::add);
    }

    public void darBaixa() {
        situacao = PAGO;
        pedidosProdutos.forEach(pedidoProduto -> pedidoProduto.getProduto().atualizarQtdEstoque(pedidoProduto.getQtdProduto()));
    }
}