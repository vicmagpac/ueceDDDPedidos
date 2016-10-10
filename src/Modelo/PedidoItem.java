package Modelo;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
public class PedidoItem {

    @Id
    @GeneratedValue
    private Pedido pedido;

    @NotNull(message = "Informe o produto")
    @ManyToOne(optional = false)
    private Produto produto;

    @Min(value = 1, message = "Informe a quantidade")
    @Column(precision = 4)
    private int quantidade;

    @Column(precision = 6, scale = 2)
    @Min(value = 0, message = "Informe o valor")
    private BigDecimal valor;

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    } 
    
    public void remover() {
        this.pedido.getItens().remove(this);
    }
}
