package com.panarello.mpandroidchart.dominio;

/**
 * Created by panar on 05/03/2017.
 */

public enum TipoLarguraBarra {
    MUITO_PEQUENO(0.2f),PEQUENO(0.4f),NORMAL(0.6f),GRANDE(0.8f),MUITO_GRANDE(1f);
    private float valor;

    private TipoLarguraBarra(float valor){
        this.valor = valor;
    }

    public float getValor(){
        return this.valor;
    }
}
