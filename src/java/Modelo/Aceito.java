package Modelo;

public class Aceito implements IStatus {

    @Override
    public void aceitar(Pedido pedido) {
        throw new IllegalStateException("Pedido já ACEITO");
    }

    @Override
    public void pagar(Pedido pedido) {
        pedido.setStatus(Status.Pago);
        System.out.println("Pedido PAGO com sucesso");
    }

    @Override
    public void cancelar(Pedido pedido) {
        pedido.setStatus(Status.Cancelado);
        System.out.println("Pedido CANCELADO com sucesso");
    }

}
