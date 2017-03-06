package com.panarello.mpandroidchart.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panar on 26/02/2017.
 */

public class ConjutoDado {

    private List<Dado> listaValores;
    private boolean exibirValores;

    private float tamanhoTextoItens;
    private String descricaoConjunto;
    private TipoFormatacao tipoFormatacao;


    public ConjutoDado(boolean exibirValores, float tamanhoTextoItens, String descricaoConjunto, TipoFormatacao tipoFormatacao) {
        this.exibirValores = exibirValores;
        this.tamanhoTextoItens = tamanhoTextoItens;
        this.descricaoConjunto = descricaoConjunto;
        this.tipoFormatacao = tipoFormatacao;
        this.listaValores = new ArrayList<Dado>();
    }



    public List<Dado> getListaValores() {
        return listaValores;
    }

    public boolean isExibirValores() {
        return exibirValores;
    }

    public float getTamanhoTextoItens() {
        return tamanhoTextoItens;
    }

    public String getDescricaoConjunto() {
        return descricaoConjunto;
    }

    public TipoFormatacao getTipoFormatacao() {
        return tipoFormatacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConjutoDado that = (ConjutoDado) o;

        if (exibirValores != that.exibirValores) return false;
        if (Float.compare(that.tamanhoTextoItens, tamanhoTextoItens) != 0) return false;
        if (!descricaoConjunto.equals(that.descricaoConjunto)) return false;
        return tipoFormatacao == that.tipoFormatacao;

    }

    @Override
    public int hashCode() {
        int result = (exibirValores ? 1 : 0);
        result = 31 * result + (tamanhoTextoItens != +0.0f ? Float.floatToIntBits(tamanhoTextoItens) : 0);
        result = 31 * result + descricaoConjunto.hashCode();
        result = 31 * result + (tipoFormatacao != null ? tipoFormatacao.hashCode() : 0);
        return result;
    }
}
