package Modelo;

public interface IStatus {
    
    void aceitar(Pedido pedido);
    void pagar(Pedido pedido);
    void cancelar(Pedido pedido);

}
