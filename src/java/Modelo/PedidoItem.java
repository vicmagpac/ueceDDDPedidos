package Modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
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
    private double valor;
    
    @Transient
    @Column(precision = 8, scale = 2)
    private double valorTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorTotal() {
        return this.valor * this.quantidade;
    }

    public void remover() {
        this.pedido.getItens().remove(this);
    }
}
