package com.panarello.mpandroidchart.dominio;

import android.graphics.Color;

import org.json.JSONObject;

/**
 * Created by panar on 19/02/2017.
 */

public class EstiloDoGrafico {
    private int corDeFundoGrade;
    private int corDeFundo;
    private int corDaBorda;
    private TipoEspessura espessuraBorda;
    private boolean exibirGradeDeFundo;
    private boolean exibirBordas;
    private TipoTamanhoTexto tamanhoTextoLegendas;
    private int corLegendas;
    private boolean habilitarLegendas = false;

    public int getCorDeFundoGrade() {
        return corDeFundoGrade;
    }

    public void setCorDeFundoGrade(int corDeFundoGrade) {
        this.corDeFundoGrade = corDeFundoGrade;
    }

    public int getCorDeFundo() {
        return corDeFundo;
    }

    public void setCorDeFundo(int corDeFundo) {
        this.corDeFundo = corDeFundo;
    }

    public int getCorDaBorda() {
        return corDaBorda;
    }

    public void setCorDaBorda(int corDaBorda) {
        this.corDaBorda = corDaBorda;
    }

    public TipoEspessura getEspessuraBorda() {
        return espessuraBorda;
    }

    public void setEspessuraBorda(TipoEspessura espessuraBorda) {
        this.espessuraBorda = espessuraBorda;
    }

    public boolean isExibirGradeDeFundo() {
        return exibirGradeDeFundo;
    }

    public void setExibirGradeDeFundo(boolean exibirGradeDeFundo) {
        this.exibirGradeDeFundo = exibirGradeDeFundo;
    }

    public boolean isExibirBordas() {
        return exibirBordas;
    }

    public void setExibirBordas(boolean exibirBordas) {
        this.exibirBordas = exibirBordas;
    }

    public TipoTamanhoTexto getTamanhoTextoLegendas() {
        return tamanhoTextoLegendas;
    }

    public void setTamanhoTextoLegendas(TipoTamanhoTexto tamanhoTextoLegendas) {
        this.tamanhoTextoLegendas = tamanhoTextoLegendas;
    }

    public int getCorLegendas() {
        return corLegendas;
    }

    public void setCorLegendas(int corLegendas) {
        this.corLegendas = corLegendas;
    }

    public boolean isHabilitarLegendas() {
        return habilitarLegendas;
    }

    public void setHabilitarLegendas(boolean habilitarLegendas) {
        this.habilitarLegendas = habilitarLegendas;
    }

    public static EstiloDoGrafico populaEstilo(JSONObject json){
        EstiloDoGrafico estilo = new EstiloDoGrafico();
        estilo.setCorDaBorda(Color.BLACK);
        estilo.setCorDeFundo(Color.TRANSPARENT);
        estilo.setCorDeFundoGrade(Color.TRANSPARENT);
        estilo.setEspessuraBorda(TipoEspessura.FINO);
        estilo.setExibirBordas(false);
        estilo.setExibirGradeDeFundo(false);
        estilo.setHabilitarLegendas(true);
        estilo.setTamanhoTextoLegendas(TipoTamanhoTexto.NORMAL);
        estilo.setCorLegendas(Color.BLACK);
        return estilo;
    }
}
