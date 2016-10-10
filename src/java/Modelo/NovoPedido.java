
package Modelo;

import entities.Repository;
import javax.persistence.EntityManager;

public class NovoPedido implements IStatus {

    @Override
    public void aceitar(Pedido pedido) {
        pedido.setStatus(Status.Aceito);        
        System.out.println("Pedido aceito com sucesso");
    }

    @Override
    public void pagar(Pedido pedido) {
        throw new IllegalStateException("Não é possivel PAGAR, pedido ainda não ACEITO");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("Não é possivel cancelar, pedido ainda não ACEITO");
    }
    
}
