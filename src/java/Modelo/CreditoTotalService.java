package Modelo;

import entities.Repository;

public class CreditoTotalService {

    static public double getCreditoAtual(Cliente cliente) {
        Double credito;
        credito = (Double) Repository.query("AtualDebitoPorCliente", cliente).get(0);
        if (credito == null) {
            credito = 0d;
        }
        return credito;
    }
}
