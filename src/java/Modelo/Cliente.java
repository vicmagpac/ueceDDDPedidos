package Modelo;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotEmpty;



@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Cliente {

    @Id
    @GeneratedValue
    private int id;
    
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
    private BigDecimal limiteDeCredito;

    public int getId() {
        return id;
    }

    public Cliente setId(int id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Cliente setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Cliente setCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public String getCidade() {
        return cidade;
    }

    public Cliente setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public BigDecimal getLimiteDeCredito() {
        return limiteDeCredito;
    }

    public Cliente setLimiteDeCredito(BigDecimal limiteDeCredito) {
        this.limiteDeCredito = limiteDeCredito;
        return this;
    }
}
