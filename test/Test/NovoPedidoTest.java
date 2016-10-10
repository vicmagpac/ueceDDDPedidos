package Test;

import Modelo.Cliente;
import Modelo.Pedido;
import Modelo.PedidoItem;
import Modelo.Produto;
import Modelo.Status;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class NovoPedidoTest {
    
    private Pedido pedido;
    
    @Test
    public void testAceitar() {
        this.montaCenario();
        this.pedido.aceitar();
        
        Assert.assertEquals(Status.Aceito, this.pedido.getStatus());
    }
    
    @Test(expected = IllegalStateException.class)
    public void testPagar() {
        this.montaCenario();
        this.pedido.pagar();
    }
    
    @Test(expected = IllegalStateException.class)
    public void testCancelar() {
        this.montaCenario();
        this.pedido.cancelar();
    }   
    
    private void montaCenario() {
        Cliente cliente = new Cliente();
        cliente.setNome("UECE")
               .setCnpj("49.887.826/0001-03")
               .setCidade("Fortaleza");
        
        
        Produto produto = new Produto();
        produto.setNome("Livro")
               .setPreco(new BigDecimal("50.0"));
        
        
        this.pedido = new Pedido();
        this.pedido.setCliente(cliente);
                
        PedidoItem item = new PedidoItem();
        item.setProduto(produto)
            .setQuantidade(2)
            .setPedido(this.pedido);   
    }
    
}
