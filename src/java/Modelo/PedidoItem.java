package Modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
public class PedidoItem  implements Serializable {

    @Id @GeneratedValue
    private Integer id;
    
    @ManyToOne(optional = false)
    @NotNull(message = "Informe o pedido")
    private Pedido pedido;

    @NotNull(message = "Informe o produto")
    @ManyToOne(optional = false)
    private Produto produto;

    @Min(value = 1, message = "Informe a quantidade")
    @Column(precision = 4)
    private int quantidade;

    @Column(precision = 6, scale = 2)
    @Min(value = 0, message = "Informe o valor")
    private Double valor;

    public Integer getId() {
        return id;
    }

    public PedidoItem setId(Integer id) {
        this.id = id;
        return this;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public PedidoItem setPedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public Produto getProduto() {
        return produto;
    }

    public PedidoItem setProduto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public PedidoItem setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public Double getValor() {
        return this.valor * this.quantidade;
    }

    public void remover() {
        this.pedido.getItens().remove(this);
    }
}
