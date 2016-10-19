package Modelo;

import entities.Repository;

public class Pago implements IStatus {

    @Override
    public void aceitar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está PAGO");
    }

    @Override
    public void pagar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está PAGO");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setStatus(Status.Cancelado);
        Repository.save(pedido);
        System.out.println("Pedido CANCELADO com sucesso");
    }

}
