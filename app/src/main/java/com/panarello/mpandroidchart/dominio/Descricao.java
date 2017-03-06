package com.panarello.mpandroidchart.dominio;

import android.graphics.Color;

import org.json.JSONObject;

/**
 * Created by panar on 19/02/2017.
 */

public class Descricao {

    private int cor;
    private float tamanhoFonte;
    private TipoAlinhamento alinhamento;
    private boolean habilitar;
    private String texto;
    private float posicaoEixoX;
    private float posicaoEixoY;

    public Descricao(String texto) {
        this.texto = texto;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    public float getTamanhoFonte() {
        return tamanhoFonte;
    }

    public void setTamanhoFonte(float tamanhoFonte) {
        this.tamanhoFonte = tamanhoFonte;
    }

    public TipoAlinhamento getAlinhamento() {
        return alinhamento;
    }

    public void setAlinhamento(TipoAlinhamento alinhamento) {
        this.alinhamento = alinhamento;
    }

    public boolean isHabilitar() {
        return habilitar;
    }

    public void setHabilitar(boolean habilitar) {
        this.habilitar = habilitar;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public float getPosicaoEixoX() {
        return posicaoEixoX;
    }

    public void setPosicaoEixoX(int posicaoEixoX) {
        this.posicaoEixoX = posicaoEixoX;
    }

    public float getPosicaoEixoY() {
        return posicaoEixoY;
    }

    public void setPosicaoEixoY(int posicaoEixoY) {
        this.posicaoEixoY = posicaoEixoY;
    }

    public static  Descricao populaDescricao(JSONObject json){
        Descricao desc = new Descricao("Vertical");
        desc.setAlinhamento(TipoAlinhamento.CENTER);
        desc.setCor(Color.BLACK);
        desc.setHabilitar(false);
        desc.setPosicaoEixoX(10);
        desc.setPosicaoEixoY(20);
        desc.setTamanhoFonte(15);
        return desc;
    }
}
