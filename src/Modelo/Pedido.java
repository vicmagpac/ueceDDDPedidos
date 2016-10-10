package Modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(of = {"id", "cliente"})
public class Pedido {

    @Id
    @GeneratedValue
    private int id;

    
    @ManyToOne(optional = false)
    private Cliente cliente;

    @Column(precision = 8, scale = 2)
    private BigDecimal valorTotal;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Valid
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private ArrayList<PedidoItem> itens;

    @Past
    @Temporal(TemporalType.DATE)
    private Calendar data;

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setStatus(Status status) {
        this.status = status;
    }    
    
    public Pedido() {
        this.status = Status.NovoPedido;
        this.data = Calendar.getInstance();
        this.itens = new ArrayList<PedidoItem>();
    }
    
    public void addItem() {
        PedidoItem item = new PedidoItem();
        item.setPedido(this);
        this.itens.add(item);
    }
    
    public String aceitar() {
        this.status.aceitar(this);
        return "Pedido aceito";
    }

    public String pagar() {
        this.status.pagar(this);
        return "Pedido pago";
    }

    public String cancelar() {        
        this.status.cancelar(this);
        return "Pedido cancelado";
    }
    
    /*
    public boolean temLimite() {
        if (this.cliente == null) {
            throw new IllegalArgumentException("Informe o cliente do pedido");
        }
    }*/
}