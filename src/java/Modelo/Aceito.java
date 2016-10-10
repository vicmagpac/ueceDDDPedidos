package Modelo;

import entities.Repository;

public class Aceito implements IStatus {

    @Override
    public void aceitar(Pedido pedido) {
        throw new IllegalStateException("Pedido já ACEITO");
    }

    @Override
    public void pagar(Pedido pedido) {
        pedido.setStatus(Status.Pago);
        Repository.save(pedido);
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setStatus(Status.Cancelado);
        Repository.save(pedido);
    }

}
