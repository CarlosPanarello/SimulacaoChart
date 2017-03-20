package com.panarello.mpandroidchart.dominio;

import java.math.BigDecimal;

/**
 * Created by panar on 20/03/2017.
 */

public class Fundos{
    private int id;
    private String descricao;
    private BigDecimal valor;
    private boolean selecionado;
    private int color;

    public Fundos(int id, String descricao, BigDecimal valor,boolean selecionado,int color) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.selecionado = selecionado;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
