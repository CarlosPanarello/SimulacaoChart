package com.panarello.mpandroidchart.dominio;

/**
 * Created by panar on 05/03/2017.
 */

public enum TipoTamanhoTexto {
    MUITO_PEQUENO(7),PEQUENO(10),NORMAL(14),GRANDE(20),MUITO_GRANDE(30);
    private float valor;

    TipoTamanhoTexto(float valor){
        this.valor = valor;
    }

    public float getValor(){
        return this.valor;
    }
}
