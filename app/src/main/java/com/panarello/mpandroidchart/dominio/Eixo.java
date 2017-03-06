package com.panarello.mpandroidchart.dominio;

import android.graphics.Color;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by panar on 25/02/2017.
 */

public class Eixo {
    private boolean habilitado;
    private boolean exibirLabel;
    private boolean exibirGrade;

    private float valorMinimo;
    private float valorMaximo;

    private Descricao label;
    private TipoEixo tipoEixo;
    private int corGrade;

    private TipoEspessura espessuraGrade;
    private TipoFormatacao tipoFormatacao;
    private String formatacao;
    private LinhaLimite linhaLimite;

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public boolean isExibirLabel() {
        return exibirLabel;
    }

    public void setExibirLabel(boolean exibirLabel) {
        this.exibirLabel = exibirLabel;
    }

    public boolean isExibirGrade() {
        return exibirGrade;
    }

    public void setExibirGrade(boolean exibirGrade) {
        this.exibirGrade = exibirGrade;
    }

    public Descricao getLabel() {
        return label;
    }

    public void setLabel(Descricao label) {
        this.label = label;
    }

    public TipoEixo getTipoEixo() {
        return tipoEixo;
    }

    public void setTipoEixo(TipoEixo tipoEixo) {
        this.tipoEixo = tipoEixo;
    }

    public int getCorGrade() {
        return corGrade;
    }

    public void setCorGrade(int corGrade) {
        this.corGrade = corGrade;
    }



    public TipoFormatacao getTipoFormatacao() {
        return tipoFormatacao;
    }

    public void setTipoFormatacao(TipoFormatacao tipoFormatacao) {
        this.tipoFormatacao = tipoFormatacao;
    }

    public TipoEspessura getEspessuraGrade() {
        return espessuraGrade;
    }

    public void setEspessuraGrade(TipoEspessura espessuraGrade) {
        this.espessuraGrade = espessuraGrade;
    }

    public LinhaLimite getLinhaLimite() {
        return linhaLimite;
    }

    public void setLinhaLimite(LinhaLimite linhaLimite) {
        this.linhaLimite = linhaLimite;
    }

    public String getFormatacao() {
        return formatacao;
    }

    public void setFormatacao(String formatacao) {
        this.formatacao = formatacao;
    }

    public float getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(float valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public float getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(float valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public static Eixo populaEixoX(float max,JSONObject json) throws JSONException{
        Eixo eixo = new Eixo();
        eixo.setHabilitado(false);
        eixo.setTipoEixo(TipoEixo.EIXO_X);

        eixo.setExibirLabel(false);
        Descricao desc = new Descricao("Eixo X");
        desc.setTamanhoFonte(TipoTamanhoTexto.NORMAL.getValor());
        desc.setCor(Color.BLACK);
        eixo.setLabel(desc);

        eixo.setExibirGrade(false);
        eixo.setEspessuraGrade(TipoEspessura.FINO);
        eixo.setCorGrade(Color.BLACK);

        eixo.setTipoFormatacao(TipoFormatacao.MONETARIO);
        eixo.setFormatacao("");

        eixo.setValorMaximo(max);
        eixo.setValorMinimo(0);

        eixo.setLinhaLimite(LinhaLimite.populaLinhaLimiteX(json));

        return eixo;
    }

    public static Eixo populaBarraEixoX(float max,JSONObject json) throws JSONException{
        Eixo eixo = new Eixo();
        eixo.setHabilitado(false);
        eixo.setTipoEixo(TipoEixo.EIXO_X);

        eixo.setExibirLabel(false);
        Descricao desc = new Descricao("Eixo X");
        desc.setTamanhoFonte(TipoTamanhoTexto.NORMAL.getValor());
        desc.setCor(Color.BLACK);
        eixo.setLabel(desc);

        eixo.setExibirGrade(false);
        eixo.setEspessuraGrade(TipoEspessura.FINO);
        eixo.setCorGrade(Color.BLACK);

        eixo.setTipoFormatacao(TipoFormatacao.MONETARIO);
        eixo.setFormatacao("");

        eixo.setValorMaximo(max);
        eixo.setValorMinimo(0);

       // eixo.setLinhaLimite(LinhaLimite.populaLinhaLimiteX(json));

        return eixo;
    }
    public static Eixo populaBarraEixoY(TipoEixo tipoEixo,float min,float max,JSONObject json)throws JSONException{
        Eixo eixo = new Eixo();
        eixo.setHabilitado(false);
        eixo.setTipoEixo(tipoEixo);

        eixo.setExibirLabel(false);

        Descricao desc = new Descricao(tipoEixo.toString());
        desc.setTamanhoFonte(TipoTamanhoTexto.NORMAL.getValor());
        desc.setCor(Color.BLACK);
        eixo.setLabel(desc);

        eixo.setExibirGrade(false);
        eixo.setEspessuraGrade(TipoEspessura.FINO);
        eixo.setCorGrade(Color.BLACK);

        eixo.setTipoFormatacao(TipoFormatacao.MONETARIO);
        eixo.setFormatacao("");

        eixo.setValorMaximo(max);
        eixo.setValorMinimo(min);


        //eixo.setLinhaLimite(LinhaLimite.populaLinhaLimiteY(json));

        return eixo;
    }

    public static Eixo populaEixoY(TipoEixo tipoEixo,float min,float max,JSONObject json)throws JSONException{
        Eixo eixo = new Eixo();
        eixo.setHabilitado(false);
        eixo.setTipoEixo(tipoEixo);

        eixo.setExibirLabel(true);

        Descricao desc = new Descricao(tipoEixo.toString());
        desc.setTamanhoFonte(TipoTamanhoTexto.NORMAL.getValor());
        desc.setCor(Color.BLACK);
        eixo.setLabel(desc);

        eixo.setExibirGrade(false);
        eixo.setEspessuraGrade(TipoEspessura.FINO);
        eixo.setCorGrade(Color.BLACK);

        eixo.setTipoFormatacao(TipoFormatacao.MONETARIO);
        eixo.setFormatacao("");

        eixo.setValorMaximo(max);
        eixo.setValorMinimo(min);


        eixo.setLinhaLimite(LinhaLimite.populaLinhaLimiteY(json));

        return eixo;
    }
}
