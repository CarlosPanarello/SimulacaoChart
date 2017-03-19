package com.panarello.mpandroidchart.dominio;

import java.math.BigDecimal;

/**
 * Created by carlospanarello on 14/03/17.
 */

public class Parametros {

    public static final String BARRAVERTICAL = "https://grafico.herokuapp.com/graficoBarraVertical";
    public static final String BARRAHORIZONTAL = "https://grafico.herokuapp.com/graficoBarraHorizontal";
    public static final String LINHA = "https://grafico.herokuapp.com/graficoLinha";
    public static final String PIZZA = "https://grafico.herokuapp.com/graficoPizza";

    public static final String CALCULOBARRA = "https://grafico.herokuapp.com/calculoBarra";
    public static final String CALCULOLINHA = "https://grafico.herokuapp.com/calculoLinha";
    public static final String CALCULOPIZZA = "https://grafico.herokuapp.com/calculoPizza";


    private TipoGrafico tipo;
    private boolean realizarCalculo = false;

    private BigDecimal  montanteInicial,contribuicaoExtra,contribuicaoMensalAtual,contribuicaoMensalSimulado;
    private Integer anoSaidaAtual,anoSaidaSimulado;

    public Parametros(TipoGrafico tipo, boolean realizarCalculo) {
        this.tipo = tipo;
        this.realizarCalculo = realizarCalculo;
        montanteInicial = BigDecimal.ZERO;
        contribuicaoExtra = BigDecimal.ZERO;
        contribuicaoMensalAtual = BigDecimal.ZERO;
        contribuicaoMensalSimulado = BigDecimal.ZERO;
        anoSaidaAtual = 0;
        anoSaidaSimulado = 0;
    }

    public TipoGrafico getTipo() {
        return tipo;
    }

    public void setTipo(TipoGrafico tipo) {
        this.tipo = tipo;
    }

    public boolean isRealizarCalculo() {
        return realizarCalculo;
    }

    public void setRealizarCalculo(boolean realizarCalculo) {
        this.realizarCalculo = realizarCalculo;
    }

    public BigDecimal getMontanteInicial() {
        return montanteInicial;
    }

    public void setMontanteInicial(BigDecimal montanteInicial) {
        this.montanteInicial = montanteInicial;
    }

    public BigDecimal getContribuicaoExtra() {
        return contribuicaoExtra;
    }

    public void setContribuicaoExtra(BigDecimal contribuicaoExtra) {
        this.contribuicaoExtra = contribuicaoExtra;
    }

    public BigDecimal getContribuicaoMensalAtual() {
        return contribuicaoMensalAtual;
    }

    public void setContribuicaoMensalAtual(BigDecimal contribuicaoMensalAtual) {
        this.contribuicaoMensalAtual = contribuicaoMensalAtual;
    }

    public BigDecimal getContribuicaoMensalSimulado() {
        return contribuicaoMensalSimulado;
    }

    public void setContribuicaoMensalSimulado(BigDecimal contribuicaoMensalSimulado) {
        this.contribuicaoMensalSimulado = contribuicaoMensalSimulado;
    }

    public Integer getAnoSaidaAtual() {
        return anoSaidaAtual;
    }

    public void setAnoSaidaAtual(Integer anoSaidaAtual) {
        this.anoSaidaAtual = anoSaidaAtual;
    }

    public Integer getAnoSaidaSimulado() {
        return anoSaidaSimulado;
    }

    public void setAnoSaidaSimulado(Integer anoSaidaSimulado) {
        this.anoSaidaSimulado = anoSaidaSimulado;
    }

    public String urlRequisicao(){
        String retorno = "";
        switch (tipo) {
            case BARRA_HORIZONTAL:
                if(isRealizarCalculo()){
                    return CALCULOBARRA+montarQueryString();
                } else {
                   return BARRAHORIZONTAL;
                }

            case BARRA_VERTICAL:
                if(isRealizarCalculo()){
                    return CALCULOBARRA+montarQueryString();
                } else {
                    return BARRAVERTICAL;
                }
            case LINHA:
                if(isRealizarCalculo()){
                    return CALCULOLINHA+montarQueryString();
                } else {
                    return LINHA;
                }
            case PIZZA:
                if(isRealizarCalculo()){
                    return CALCULOPIZZA+montarQueryString();
                } else {
                    return PIZZA;
                }
            default:
                return "";
        }
    }

    private String montarQueryString(){
        StringBuilder s = new StringBuilder();

        s.append("?montanteInicial="+montanteInicial.setScale(2).toString())
        .append("&contribuicaoExtra="+contribuicaoExtra.setScale(2).toString())
        .append("&contribuicaoMensalAtual="+contribuicaoMensalAtual.setScale(2).toString())
        .append("&contribuicaoMensalSimulado="+contribuicaoMensalSimulado.setScale(2).toString())
        .append("&anoSaidaAtual="+anoSaidaAtual.toString())
        .append("&anoSaidaSimulado="+anoSaidaSimulado.toString());

        return s.toString();
    }
}
