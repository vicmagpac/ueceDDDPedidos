
package Modelo;

import entities.Repository;
import javax.persistence.EntityManager;

public class NovoPedido implements IStatus {

    @Override
    public void aceitar(Pedido pedido) {
        
        if (!pedido.valorTotalDoPedidoMenorQueValorTotalPadraoPorPedido()){
            throw new IllegalStateException("Valor total do pedido maior que 1000.");
        }
        
        pedido.setStatus(Status.Aceito); 
        Repository.save(pedido);
        System.out.println("Pedido aceito com sucesso");
    }

    @Override
    public void pagar(Pedido pedido) {
        throw new IllegalStateException("Não é possivel PAGAR, pedido ainda não ACEITO");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setStatus(Status.Cancelado);
        Repository.save(pedido);
        System.out.println("Pedido CANCELADO com sucesso");
    }
    
}
