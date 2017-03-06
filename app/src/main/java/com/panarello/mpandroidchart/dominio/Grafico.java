package com.panarello.mpandroidchart.dominio;

import android.graphics.Color;

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

    public float getLarguraBarra() {
        return larguraBarra;
    }

    public void setLarguraBarra(float larguraBarra) {
        this.larguraBarra = larguraBarra;
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

    public static Grafico montaGraficoLinha(JSONObject json)throws JSONException{
        Grafico g1 = new Grafico(TipoGrafico.LINHA,"Linha");

        g1.setIncluirDescricaoDentroDoGrafico(false);
        g1.setHabilitarArrastar(false);
        g1.setHabilitarToque(false);
        g1.setHabilitarZoom(false);
        g1.setTipoAnimacao(TipoAnimacao.NORMAL);
        g1.setLarguraBarra(TipoLarguraBarra.GRANDE.getValor());

        List<ConjutoDado> lista = populaConjuntoDadosLinha(json);
        g1.setEstilo(EstiloDoGrafico.populaEstilo(json));
        g1.setConjutoDados(lista);

        Descricao desc = g1.getDescricaoGrafico();
        desc.setAlinhamento(TipoAlinhamento.CENTER);
        desc.setCor(Color.BLACK);
        desc.setHabilitar(false);
        desc.setPosicaoEixoX(10);
        desc.setPosicaoEixoY(20);
        desc.setTamanhoFonte(15);

        g1.setDescricaoGrafico(desc);
        g1.setEixoX(Eixo.populaEixoX(valorMaximoX(lista),json));
        g1.setEixoY(Eixo.populaEixoY(TipoEixo.EIXO_Y_ESQUERDA,valorMinimoY(lista),valorMaximoY(lista),json));

        return g1;
    }

    public static Grafico montaGraficoBarraHorizontal(JSONObject json)throws JSONException{
        Grafico g1 = new Grafico(TipoGrafico.BARRA_HORIZONTAL,"Horizontal");

        g1.setIncluirDescricaoDentroDoGrafico(false);
        g1.setHabilitarArrastar(false);
        g1.setHabilitarToque(false);
        g1.setHabilitarZoom(false);
        g1.setTipoAnimacao(TipoAnimacao.NORMAL);
        g1.setLarguraBarra(TipoLarguraBarra.GRANDE.getValor());

        List<ConjutoDado> lista = populaConjuntoDadosBarraHorizontal(json);
        g1.setEstilo(EstiloDoGrafico.populaEstilo(json));
        g1.setConjutoDados(lista);

        Descricao desc = g1.getDescricaoGrafico();
        desc.setAlinhamento(TipoAlinhamento.CENTER);
        desc.setCor(Color.BLACK);
        desc.setHabilitar(false);
        desc.setPosicaoEixoX(10);
        desc.setPosicaoEixoY(20);
        desc.setTamanhoFonte(15);

        g1.setDescricaoGrafico(desc);

        g1.setEixoX(Eixo.populaBarraEixoX(valorMaximoX(lista),json));
        g1.setEixoY(Eixo.populaBarraEixoY(TipoEixo.EIXO_Y_ESQUERDA,valorMinimoY(lista),valorMaximoY(lista),json));

        return g1;
    }

    public static Grafico montaGraficoPizza(JSONObject json)throws JSONException{
        Grafico g1 = new Grafico(TipoGrafico.PIZZA,"Pizza");
        return g1;
    }

    public static Grafico montaGraficoBarraVertical(JSONObject json)throws JSONException{

        Grafico g1 = new Grafico(TipoGrafico.BARRA_VERTICAL,"Vertical");

        g1.setIncluirDescricaoDentroDoGrafico(false);
        g1.setHabilitarArrastar(false);
        g1.setHabilitarToque(false);
        g1.setHabilitarZoom(false);
        g1.setTipoAnimacao(TipoAnimacao.NORMAL);
        g1.setLarguraBarra(TipoLarguraBarra.GRANDE.getValor());
        g1.setTipo(TipoGrafico.BARRA_VERTICAL);

        List<ConjutoDado> lista = populaConjuntoDadosBarraVertical(json);
        g1.setEstilo(EstiloDoGrafico.populaEstilo(json));
        g1.setConjutoDados(lista);
        g1.setDescricaoGrafico(Descricao.populaDescricao(json));
        g1.setEixoX(Eixo.populaBarraEixoX(valorMaximoX(lista),json));
        g1.setEixoY(Eixo.populaBarraEixoY(TipoEixo.EIXO_Y_ESQUERDA,valorMinimoY(lista),valorMaximoY(lista),json));

        return g1;
    }

    public static List<ConjutoDado> populaConjuntoDadosBarraVertical(JSONObject json){
        List<ConjutoDado> lista = new ArrayList<>();

        ConjutoDado conjutoDado1 = new ConjutoDado(true,14,"Descricao 1a",TipoFormatacao.MONETARIO);

        conjutoDado1.getListaValores().add(new Dado(conjutoDado1.hashCode(),Color.BLACK,Color.RED,0f,0f));
        conjutoDado1.getListaValores().add(new Dado(conjutoDado1.hashCode(),Color.BLACK,Color.RED,0f,1200f));

        ConjutoDado conjutoDado2 = new ConjutoDado(true,14,"Descricao 2a",TipoFormatacao.MONETARIO);
        conjutoDado2.getListaValores().add(new Dado(conjutoDado2.hashCode(),Color.BLACK,Color.BLUE,1f,0f));
        conjutoDado2.getListaValores().add(new Dado(conjutoDado2.hashCode(),Color.BLACK,Color.BLUE,1f,1400f));

        lista.add(conjutoDado1);
        lista.add(conjutoDado2);

        return lista;
    }

    public static List<ConjutoDado> populaConjuntoDadosBarraHorizontal(JSONObject json){
        List<ConjutoDado> lista = new ArrayList<>();

        ConjutoDado conjutoDado1 = new ConjutoDado(true,14,"Descricao 1h",TipoFormatacao.MONETARIO);

        conjutoDado1.getListaValores().add(new Dado(conjutoDado1.hashCode(),Color.BLACK,Color.RED,0f,0f));
        conjutoDado1.getListaValores().add(new Dado(conjutoDado1.hashCode(),Color.BLACK,Color.RED,0f,1200f));

        ConjutoDado conjutoDado2 = new ConjutoDado(true,14,"Descricao 2h",TipoFormatacao.MONETARIO);
        conjutoDado2.getListaValores().add(new Dado(conjutoDado2.hashCode(),Color.BLACK,Color.BLUE,1f,0f));
        conjutoDado2.getListaValores().add(new Dado(conjutoDado2.hashCode(),Color.BLACK,Color.BLUE,1f,1400f));

        lista.add(conjutoDado1);
        lista.add(conjutoDado2);

        return lista;
    }

    public static List<ConjutoDado> populaConjuntoDadosLinha(JSONObject json){
        List<ConjutoDado> lista = new ArrayList<>();

        ConjutoDado conjutoDado1 = new ConjutoDado(true,14,"Descricao 1a",TipoFormatacao.MONETARIO);
        ConjutoDado conjutoDado2 = new ConjutoDado(true,14,"Descricao 2a",TipoFormatacao.MONETARIO);

        conjutoDado1.getListaValores().add(new Dado(conjutoDado1.hashCode(),Color.BLACK,Color.RED,1f,1.00f));
        conjutoDado2.getListaValores().add(new Dado(conjutoDado2.hashCode(),Color.BLACK,Color.BLUE,1f,1.00f));

        float p1 =2.05f;
        float p2 =2.00f;
        for(int i = 2 ; i<11 ;i++){
            conjutoDado1.getListaValores().add(new Dado(conjutoDado1.hashCode(),Color.BLACK,Color.RED,i,p1));
            conjutoDado2.getListaValores().add(new Dado(conjutoDado2.hashCode(),Color.BLACK,Color.BLUE,i,p2));

            p1 = p1*2;
            p2 += p2 + 0.5f;
        }

        lista.add(conjutoDado1);
        lista.add(conjutoDado2);

        return lista;
    }

    private static  float valorMaximoX(List<ConjutoDado> dados){
        float max = Float.MIN_VALUE;
        if(dados == null){
            return  0;
        }
        for(ConjutoDado conjDado : dados){
            for(Dado dado :conjDado.getListaValores()){
                if(dado.getValorX()>max){
                    max = dado.getValorX();
                }
            }
        }

        return max+2;
    }

    private static float valorMaximoY(List<ConjutoDado> dados){
        float max = Float.MIN_VALUE;
        if(dados == null){
            return  0;
        }
        for(ConjutoDado conjDado : dados){
            for(Dado dado :conjDado.getListaValores()){
                if(dado.getValorY()>max){
                    max = dado.getValorY();
                }
            }
        }

        return max + max*0.2f;
    }

    private static float valorMinimoY(List<ConjutoDado> dados){
        float min = Float.MAX_VALUE;
        if(dados == null){
            return  0;
        }
        for(ConjutoDado conjDado : dados){
            for(Dado dado :conjDado.getListaValores()){
                if(dado.getValorY() < min){
                    min = dado.getValorY();
                }
            }
        }

        return min;
    }
}
