package com.panarello.mpandroidchart.dominio;

/**
 * Created by panar on 26/02/2017.
 */

public enum TipoAnimacao {
    SEM_ANIMACAO(0),MUITO_RAPIDO(1000),RAPIDO(2000),NORMAL(3000),LENTO(4000),MUITO_LENTO(5000);

    private int valor;

    private TipoAnimacao(int valor){
        this.valor = valor;
    }

    public int getValor(){
        return this.valor;
    }
}
