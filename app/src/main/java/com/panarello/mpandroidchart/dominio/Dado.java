package com.panarello.mpandroidchart.dominio;

/**
 * Created by panar on 26/02/2017.
 */

public class Dado {

    private Integer hashConjuntoDado;
    private int corTexto;
    private int corItem;
    private float valorX;
    private float valorY;


    public Dado(int hashConjuntoDado, int corTexto, int corItem,float valorX, float valorY) {
        this.hashConjuntoDado = hashConjuntoDado;
        this.corTexto = corTexto;
        this.corItem = corItem;
        this.valorY = valorY;
        this.valorX = valorX;
     }

    public int getHashConjuntoDado() {
        return hashConjuntoDado;
    }

    public int getCorTexto() {
        return corTexto;
    }

    public int getCorItem() {
        return corItem;
    }

    public float getValorY() {
        return valorY;
    }

    public float getValorX() {
        return valorX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dado dado = (Dado) o;

        if (corTexto != dado.corTexto) return false;
        if (corItem != dado.corItem) return false;
        if (Float.compare(dado.valorX, valorX) != 0) return false;
        if (Float.compare(dado.valorY, valorY) != 0) return false;
        return hashConjuntoDado.equals(dado.hashConjuntoDado);
    }

    @Override
    public int hashCode() {
        int result = hashConjuntoDado.hashCode();
        result = 31 * result + corTexto;
        result = 31 * result + corItem;
        result = 31 * result + (valorX != +0.0f ? Float.floatToIntBits(valorX) : 0);
        result = 31 * result + (valorY != +0.0f ? Float.floatToIntBits(valorY) : 0);
        return result;
    }
}
