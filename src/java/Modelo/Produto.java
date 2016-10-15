package Modelo;

import entities.annotations.EntityDescriptor;
import entities.annotations.View;
import entities.annotations.Views;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
@Views(
  @View(name = "Produtos",
       title = "Produtos",
     filters = "nome,preco",
     members = "nome,preco",
    template = "@CRUD+@PAGER+@FILTER"))
@EntityDescriptor(template="@TABLE+@CRUD+@PAGER")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String nome;
    private Double preco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
    
     @Override
    public String toString() {
        return this.nome; 
    }
}
