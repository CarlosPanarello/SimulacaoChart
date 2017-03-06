package com.panarello.mpandroidchart.dominio;

import android.graphics.Color;

import org.json.JSONObject;

/**
 * Created by panar on 26/02/2017.
 */

public class LinhaLimite {
    private TipoEspessura espessuraLinha;
    private Descricao descricao;
    private int corLinha;
    private boolean habilitado;
    private TipoPontilhado tipoPontilhado;
    private TipoPosicaoDescricao tipoPosicaoDescricao;
    private float valorLimite;

    public TipoEspessura getEspessuraLinha() {
        return espessuraLinha;
    }

    public void setEspessuraLinha(TipoEspessura espessuraLinha) {
        this.espessuraLinha = espessuraLinha;
    }

    public Descricao getDescricao() {
        return descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    public int getCorLinha() {
        return corLinha;
    }

    public void setCorLinha(int corLinha) {
        this.corLinha = corLinha;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public TipoPontilhado getTipoPontilhado() {
        return tipoPontilhado;
    }

    public void setTipoPontilhado(TipoPontilhado tipoPontilhado) {
        this.tipoPontilhado = tipoPontilhado;
    }

    public TipoPosicaoDescricao getTipoPosicaoDescricao() {
        return tipoPosicaoDescricao;
    }

    public void setTipoPosicaoDescricao(TipoPosicaoDescricao tipoPosicaoDescricao) {
        this.tipoPosicaoDescricao = tipoPosicaoDescricao;
    }

    public float getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(float valorLimite) {
        this.valorLimite = valorLimite;
    }

    public static LinhaLimite populaLinhaLimiteY(JSONObject json){
        LinhaLimite linhaLimite = new LinhaLimite();
        Descricao desc = new Descricao("Limite");

        desc.setAlinhamento(TipoAlinhamento.CENTER);
        desc.setCor(Color.BLACK);
        desc.setHabilitar(true);
        desc.setTamanhoFonte(15);

        linhaLimite.setDescricao(desc);
        linhaLimite.setEspessuraLinha(TipoEspessura.FINO);
        linhaLimite.setTipoPontilhado(TipoPontilhado.LONGO);
        linhaLimite.setCorLinha(Color.RED);
        linhaLimite.setTipoPosicaoDescricao(TipoPosicaoDescricao.RIGHT_TOP);
        linhaLimite.setValorLimite(1500f);
        linhaLimite.setHabilitado(false);
        return linhaLimite;
    }

    public static LinhaLimite populaLinhaLimiteX(JSONObject json){
        LinhaLimite linhaLimite = new LinhaLimite();
        Descricao desc = new Descricao("Limite");

        desc.setAlinhamento(TipoAlinhamento.CENTER);
        desc.setCor(Color.BLACK);
        desc.setHabilitar(true);
        desc.setTamanhoFonte(15);

        linhaLimite.setDescricao(desc);
        linhaLimite.setEspessuraLinha(TipoEspessura.FINO);
        linhaLimite.setTipoPontilhado(TipoPontilhado.LONGO);
        linhaLimite.setCorLinha(Color.RED);
        linhaLimite.setTipoPosicaoDescricao(TipoPosicaoDescricao.RIGHT_TOP);
        linhaLimite.setValorLimite(0.5f);

        linhaLimite.setValorLimite(10f);
        linhaLimite.setHabilitado(true);
        return linhaLimite;
    }
}
