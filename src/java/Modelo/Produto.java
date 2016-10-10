package Modelo;

import java.math.BigDecimal;

public class Produto {

    private String nome;
    private BigDecimal preco;

    public String getNome() {
        return nome;
    }

    public Produto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Produto setPreco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }
        
        

}
