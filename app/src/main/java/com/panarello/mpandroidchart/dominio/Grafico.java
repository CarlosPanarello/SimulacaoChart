package com.panarello.mpandroidchart.dominio;

import android.graphics.Color;

import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panar on 19/02/2017.
 */

public class Grafico {

    //Grafico de Pizza so aceita 1 DataSet

    private TipoGrafico tipo;
    private Descricao descricaoGrafico;

    private boolean incluirDescricaoDentroDoGrafico = false;
    private Eixo eixoX;
    private Eixo eixoY;

    private EstiloDoGrafico estilo;
    private boolean habilitarToque = false;
    private boolean habilitarArrastar = false;
    private boolean habilitarZoom = false;
    private List<ConjutoDado> conjutoDados;
    private TipoAnimacao tipoAnimacao;
    private float larguraBarra;

    private boolean habilitarBuracoCentroPizza = false;
    private boolean habilitarGirarAoToque = false;
    private boolean habilitarDestaqueAoToquePizza = true;
    private boolean habilitarValoresPorPorcentagemPizza = false;
    private int corCentroPizza = Color.TRANSPARENT;
    private float tamanhoDestaquePorcaoSelecionadaPizza = 18f;
    private float porcentagemRaioCentroPizza = 50;
    private float porcentagemTransparenciaCentroPizza = 55;
    private Descricao textoCentroPizza;

    private float offSetDireita;
    private float offSetEsquerda;
    private float offSetCima;
    private float offSetBaixo;

    public boolean isHabilitarGirarAoToque() {
        return habilitarGirarAoToque;
    }

    public void setHabilitarGirarAoToque(boolean habilitarGirarAoToque) {
        this.habilitarGirarAoToque = habilitarGirarAoToque;
    }

    public float getLarguraBarra() {
        return larguraBarra;
    }

    public void setLarguraBarra(float larguraBarra) {
        this.larguraBarra = larguraBarra;
    }

    public Descricao getTextoCentroPizza() {
        return textoCentroPizza;
    }

    public void setTextoCentroPizza(Descricao textoCentroPizza) {
        this.textoCentroPizza = textoCentroPizza;
    }

    public Grafico(TipoGrafico tipo, String descricao) {
        this.tipo = tipo;
        this.descricaoGrafico = new Descricao(descricao);
        conjutoDados = new ArrayList<>();
    }

    public TipoGrafico getTipo() {
        return tipo;
    }

    public void setTipo(TipoGrafico tipo) {
        this.tipo = tipo;
    }

    public Descricao getDescricaoGrafico() {
        return descricaoGrafico;
    }

    public void setDescricaoGrafico(Descricao descricaoGrafico) {
        this.descricaoGrafico = descricaoGrafico;
    }

    public boolean isIncluirDescricaoDentroDoGrafico() {
        return incluirDescricaoDentroDoGrafico;
    }

    public void setIncluirDescricaoDentroDoGrafico(boolean incluirDescricaoDentroDoGrafico) {
        this.incluirDescricaoDentroDoGrafico = incluirDescricaoDentroDoGrafico;
    }

    public Eixo getEixoX() {
        return eixoX;
    }

    public void setEixoX(Eixo eixoX) {
        this.eixoX = eixoX;
    }

    public Eixo getEixoY() {
        return eixoY;
    }

    public void setEixoY(Eixo eixoY) {
        this.eixoY = eixoY;
    }

    public EstiloDoGrafico getEstilo() {
        return estilo;
    }

    public void setEstilo(EstiloDoGrafico estilo) {
        this.estilo = estilo;
    }

    public boolean isHabilitarToque() {
        return habilitarToque;
    }

    public void setHabilitarToque(boolean habilitarToque) {
        this.habilitarToque = habilitarToque;
    }

    public boolean isHabilitarArrastar() {
        return habilitarArrastar;
    }

    public void setHabilitarArrastar(boolean habilitarArrastar) {
        this.habilitarArrastar = habilitarArrastar;
    }

    public boolean isHabilitarZoom() {
        return habilitarZoom;
    }

    public void setHabilitarZoom(boolean habilitarZoom) {
        this.habilitarZoom = habilitarZoom;
    }

    public TipoAnimacao getTipoAnimacao() {
        return tipoAnimacao;
    }

    public void setTipoAnimacao(TipoAnimacao tipoAnimacao) {
        this.tipoAnimacao = tipoAnimacao;
    }

    public List<ConjutoDado> getConjutoDados() {
        return conjutoDados;
    }

    public void setConjutoDados(List<ConjutoDado> conjutoDados) {
        this.conjutoDados = conjutoDados;
    }

    public boolean isHabilitarBuracoCentroPizza() {
        return habilitarBuracoCentroPizza;
    }

    public void setHabilitarBuracoCentroPizza(boolean habilitarBuracoCentroPizza) {
        this.habilitarBuracoCentroPizza = habilitarBuracoCentroPizza;
    }

    public int getCorCentroPizza() {
        return corCentroPizza;
    }

    public void setCorCentroPizza(int corCentroPizza) {
        this.corCentroPizza = corCentroPizza;
    }

    public float getPorcentagemRaioCentroPizza() {
        return porcentagemRaioCentroPizza;
    }

    public void setPorcentagemRaioCentroPizza(float porcentagemRaioCentroPizza) {
        this.porcentagemRaioCentroPizza = porcentagemRaioCentroPizza;
    }

    public float getPorcentagemTransparenciaCentroPizza() {
        return porcentagemTransparenciaCentroPizza;
    }

    public void setPorcentagemTransparenciaCentroPizza(float porcentagemTransparenciaCentroPizza) {
        this.porcentagemTransparenciaCentroPizza = porcentagemTransparenciaCentroPizza;
    }

    public boolean isHabilitarDestaqueAoToquePizza() {
        return habilitarDestaqueAoToquePizza;
    }

    public void setHabilitarDestaqueAoToquePizza(boolean habilitarDestaqueAoToquePizza) {
        this.habilitarDestaqueAoToquePizza = habilitarDestaqueAoToquePizza;
    }

    public boolean isHabilitarValoresPorPorcentagemPizza() {
        return habilitarValoresPorPorcentagemPizza;
    }

    public void setHabilitarValoresPorPorcentagemPizza(boolean habilitarValoresPorPorcentagemPizza) {
        this.habilitarValoresPorPorcentagemPizza = habilitarValoresPorPorcentagemPizza;
    }

    public float getTamanhoDestaquePorcaoSelecionadaPizza() {
        return tamanhoDestaquePorcaoSelecionadaPizza;
    }

    public void setTamanhoDestaquePorcaoSelecionadaPizza(float tamanhoDestaquePorcaoSelecionadaPizza) {
        this.tamanhoDestaquePorcaoSelecionadaPizza = tamanhoDestaquePorcaoSelecionadaPizza;
    }

    public float getOffSetDireita() {
        return offSetDireita;
    }

    public void setOffSetDireita(float offSetDireita) {
        this.offSetDireita = offSetDireita;
    }

    public float getOffSetEsquerda() {
        return offSetEsquerda;
    }

    public void setOffSetEsquerda(float offSetEsquerda) {
        this.offSetEsquerda = offSetEsquerda;
    }

    public float getOffSetCima() {
        return offSetCima;
    }

    public void setOffSetCima(float offSetCima) {
        this.offSetCima = offSetCima;
    }

    public float getOffSetBaixo() {
        return offSetBaixo;
    }

    public void setOffSetBaixo(float offSetBaixo) {
        this.offSetBaixo = offSetBaixo;
    }
}
