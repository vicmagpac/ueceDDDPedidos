package Test;

import Modelo.Cliente;
import Modelo.Pedido;
import Modelo.PedidoItem;
import Modelo.Produto;
import Modelo.Status;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

public class PagoTest {
    
    private Pedido pedido;
    
    @Test(expected = IllegalStateException.class)
    public void testAceitar() {
        this.montaCenario();
        this.pedido.aceitar();
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
        cliente.setNome("UECE");
        cliente.setCnpj("49.887.826/0001-03");
        cliente.setCidade("Fortaleza");
        
        
        Produto produto = new Produto();
        produto.setNome("Livro");
        produto.setPreco(50.0);
        
        
        this.pedido = new Pedido();
        this.pedido.setCliente(cliente);
        this.pedido.setStatus(Status.Pago);
        this.pedido.setNumero("123");
                
        PedidoItem item = new PedidoItem();
        item.setProduto(produto);
        item.setQuantidade(2);
        item.setPedido(this.pedido);   
    }
    
}
