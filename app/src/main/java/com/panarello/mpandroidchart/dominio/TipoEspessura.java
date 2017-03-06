package com.panarello.mpandroidchart.dominio;

/**
 * Created by panar on 26/02/2017.
 */

public enum TipoEspessura {
    MUITO_FINO(0.5f),FINO(1f),NORMAL(1.5f),ESPESSO(3f),MUITO_ESPESSO(5f);
    private float valor;

    private TipoEspessura(float valor){
        this.valor = valor;
    }

    public float getValor(){
        return this.valor;
    }
}
