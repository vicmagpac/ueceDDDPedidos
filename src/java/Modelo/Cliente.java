package Modelo;

import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@NamedQueries({
    @NamedQuery(name = "AtualDebitoPorCliente",
               query = "Select sum(valorTotal)"
                     + "  From Pedido "
                     + " Where cliente = :cliente"
                     + "   and status = 'Aceito'")})
@Views(
  @View(name = "Clientes",
       title = "Clientes",
     filters = "nome",
     members = "[numero;nome;cnpj;cidade;limiteDeCredito;]",
    template = "@CRUD_PAGE+@FILTER"))
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"numero"})})
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column(length = 8)
    @NotEmpty(message = "Informe o numero do cliente")
    private String numero;
    
    @NotEmpty(message = "Informe o nome do cliente")
    @Column(length = 40, nullable = false)
    private String nome;

    @NotEmpty(message = "Informe o CNPJ do cliente")
    @Column(length = 18)
    private String cnpj;

    @NotEmpty(message = "Informe a cidade do cliente")
    @Column(length = 40, nullable = false)
    private String cidade;

    @Min(0)
    @Column(precision = 8, scale = 2)
    private Double limiteDeCredito;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Double getLimiteDeCredito() {
        return limiteDeCredito;
    }

    public void setLimiteDeCredito(Double limiteDeCredito) {
        this.limiteDeCredito = limiteDeCredito;
    }
    
     @Override
    public String toString() {
        return this.numero + " - " + this.nome; 
    }
}
