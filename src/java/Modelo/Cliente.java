package Modelo;

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
    @Column(length = 14)
    private String cnpj;

    @NotEmpty(message = "Informe a cidade do cliente")
    @Column(length = 40, nullable = false)
    private String cidade;

    @Min(0)
    @Column(precision = 8, scale = 2)
    private double limiteDeCredito;
}
