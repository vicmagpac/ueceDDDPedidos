package Modelo;

import entities.annotations.EntityDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@EqualsAndHashCode(of = {"id", "numero"})
@EntityDescriptor(template="@FORM_CRUD")
@Views({
    @View(title = "Pedidos",
         name = "Pedidos",
         filters="numero;cliente",
      members = "["
              + "numero;cliente;data;*numeroDeItens;"
              + "itens<produto,quantidade,valor,remover()>;"
              + "*valorTotal;status;"
              + "[addItem(),aceitar(),pagar(),cancelar()]]",
      template="@CRUD_PAGE+@FILTER"),
    /*
  @View(title = "Adicionar pedido",
         name = "AddOrder",
      members = "["
              + "Pedido[#numero,#data;#cliente:2];"
              + " Itens[addItem();"
              + "       itens<[#produto:3;"
               + "             #quantidade,#valor,#remover()]>;"
              + "       *valorTotal];"
              + "aceitar()]",
   namedQuery = "Select new Modelo.Pedido()")*/
})
public class Pedido implements Serializable {
    
    private static final int MAXIMO_VENDA_TOTAL = 1000;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Length(max = 8)
    @Column(length = 8, unique = true, nullable = false)
    @NotEmpty(message = "Informe o numero do pedido")
    private String numero;
    
    @ManyToOne(optional = false)
    private Cliente cliente;

    @Formula("(select sum(pi.quantidade*pi.valor)"
           + "  from PedidoItem pi where pi.pedido_id = id)")
    @Column(precision = 8, scale = 2)
    private Double valorTotal;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Valid
    @OneToMany(mappedBy = "pedido",
                cascade = CascadeType.ALL,
          orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<PedidoItem> itens;
    
    @Column(precision = 4)
    @Min(value = 0, message = "Informe quantidade de itens")
    private Integer numeroDeItens = 0;

    @Past
    @Temporal(TemporalType.DATE)
    private Date data;

    public List<PedidoItem> getItens() {
        return itens;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }    
    
    public Pedido() {
        this.status = Status.NovoPedido;
        this.data = new Date();
        this.itens = new ArrayList<PedidoItem>();
    }
    
    public void addItem() {
                
        PedidoItem item = new PedidoItem();
        item.setPedido(this);
        this.itens.add(item);
        this.numeroDeItens++;
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
   
    public boolean valorTotalDoPedidoMenorQueValorTotalPadraoPorPedido() {
        this.valorTotal = 0d;
        for (PedidoItem item : this.itens) {
            this.valorTotal += item.getValorTotal();
        }
        
        return this.valorTotal <= MAXIMO_VENDA_TOTAL;
    }
}