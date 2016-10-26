
package Modelo;

public enum Status implements IStatus {

    NovoPedido(new NovoPedido()),
    Aceito(new Aceito()),
    Pago(new Pago()),
    Cancelado(new Cancelado());
    
    private IStatus status;

    Status(IStatus status) {
        this.status = status;
    }

    @Override
    public void aceitar(Pedido pedido) {
        this.status.aceitar(pedido);
    }

    @Override
    public void pagar(Pedido pedido) {
        this.status.pagar(pedido);
    }

    @Override
    public void cancelar(Pedido pedido) {
        this.status.cancelar(pedido);
    }
}