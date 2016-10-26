package Modelo;

import entities.annotations.ActionDescriptor;
import entities.annotations.EntityDescriptor;
import entities.annotations.ParameterDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Version;
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
    @View(title = "Registrar Pedido",
         name = "Pedidos",
         namedQuery = "Select new Modelo.Pedido()",
      members = "["
              + "#numero;#cliente;*data;*numeroDeItens;"
              + "itens<*produto,*quantidade,*valor,remover()>;"
              + "*valorTotal;"
              + "[addItem(),aceitar()]]"
      ),    

  @View(title = "Lista de pedidos",
         name = "ListaDePedidos",
      filters = "cliente;data",
      members = "numero,cliente,data,numeroDeItens,valorTotal,status",       
     template = "@FILTER+@PAGER",
   namedQuery = "from Pedido order by numero"),
        
  @View(title = "Caixa",
         name = "Caixa",
         filters="numero,cliente",
      members = "cliente,valorTotal,pagar(),cancelar()",       
     template = "@FILTER+@PAGER",
   namedQuery = "from Pedido where status = 'Aceito' order by numero")
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
    
    @Version
    private Timestamp version;

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

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public Pedido() {
        this.status = Status.NovoPedido;
        this.data = new Date();
        this.itens = new ArrayList<PedidoItem>();
    }
    
    public void addItem(@ParameterDescriptor(displayName = "Produto") Produto produto, @ParameterDescriptor(displayName = "Quantidade") Integer quantidade) {
        
        PedidoItem item = new PedidoItem();
        item.setPedido(this);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorTotal(quantidade * produto.getPreco());
        item.setValor(produto.getPreco());
        this.itens.add(item);
        this.numeroDeItens++;
    }
    
    @ActionDescriptor(refreshView = true)
    public String aceitar() {
        this.status.aceitar(this);
        return "Pedido aceito";
    }

    @ActionDescriptor(refreshView = true)
    public String pagar() {
        this.status.pagar(this);
        return "Pedido pago";
    }

    @ActionDescriptor(refreshView = true)
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
    
    protected boolean verificaCreditoDoCliente() {
        if (this.cliente == null) {
            throw new IllegalArgumentException("Informe o cliente");
        }
        double creditoAtual = CreditoTotalService.getCreditoAtual(this.cliente);
        return creditoAtual + this.valorTotal <= this.cliente.getLimiteDeCredito();
    }
}