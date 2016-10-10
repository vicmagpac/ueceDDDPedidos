package Modelo;

public class Cancelado implements IStatus {

    @Override
    public void aceitar(Pedido pedido) {
        throw new IllegalStateException("Esse pedido está cancelado");
    }

    @Override
    public void pagar(Pedido pedido) {
        throw new IllegalStateException("Esse pedido está cancelado");
    }

    @Override
    public void cancelar(Pedido pedido) {
        throw new IllegalStateException("Esse pedido está cancelado");
    }

}
