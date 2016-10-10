
package Modelo;

import entities.Repository;

public class NovoPedido implements IStatus {

    @Override
    public void aceitar(Pedido pedido) {
        pedido.setStatus(Status.Aceito);
        Repository.save(pedido);
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
