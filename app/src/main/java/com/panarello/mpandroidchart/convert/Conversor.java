package com.panarello.mpandroidchart.convert;

import android.graphics.Color;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.panarello.mpandroidchart.dominio.ConjutoDado;
import com.panarello.mpandroidchart.dominio.Dado;
import com.panarello.mpandroidchart.dominio.Descricao;
import com.panarello.mpandroidchart.dominio.Eixo;
import com.panarello.mpandroidchart.dominio.EstiloDoGrafico;
import com.panarello.mpandroidchart.dominio.Grafico;
import com.panarello.mpandroidchart.dominio.TipoAlinhamento;
import com.panarello.mpandroidchart.dominio.TipoAnimacao;
import com.panarello.mpandroidchart.dominio.TipoEixo;
import com.panarello.mpandroidchart.dominio.TipoFormatacao;
import com.panarello.mpandroidchart.dominio.TipoGrafico;
import com.panarello.mpandroidchart.dominio.TipoLarguraBarra;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panar on 19/02/2017.
 */

public class Conversor {


    public static Grafico montaGrafico(String url){


        return null;
    }

    public static Grafico montaGraficoLinha(JSONObject json)throws JSONException {
        Grafico g1 = new Grafico(TipoGrafico.LINHA,"Linha");

        g1.setIncluirDescricaoDentroDoGrafico(false);
        g1.setHabilitarArrastar(false);
        g1.setHabilitarToque(false);
        g1.setHabilitarZoom(false);
        g1.setTipoAnimacao(TipoAnimacao.NORMAL);
        g1.setLarguraBarra(TipoLarguraBarra.GRANDE.getValor());

        List<ConjutoDado> lista = populaConjuntoDadosLinha(json);
        g1.setEstilo(EstiloDoGrafico.populaEstilo(json));
        //g1.setConjutoDados(lista);

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
        g1.setEstilo(EstiloDoGrafico.populaEstilo(json));

        g1.setIncluirDescricaoDentroDoGrafico(false);
        g1.setHabilitarArrastar(false);
        g1.setHabilitarToque(false);
        g1.setHabilitarZoom(false);

        g1.setTipoAnimacao(TipoAnimacao.NORMAL);
        //g1.setLarguraBarra(TipoLarguraBarra.GRANDE.getValor());

        g1.getEstilo().setCorDeFundo(Color.BLUE);
        g1.setHabilitarDestaqueAoToquePizza(true);
        g1.setHabilitarBuracoCentroPizza(true);
        g1.setHabilitarValoresPorPorcentagemPizza(true);
        g1.setTamanhoDestaquePorcaoSelecionadaPizza(18f);
        g1.setCorCentroPizza(Color.RED);
        g1.setPorcentagemRaioCentroPizza(50f);
        g1.setPorcentagemTransparenciaCentroPizza(0);
        g1.setHabilitarGirarAoToque(false);


        ConjutoDado lista = populaConjuntoDadosPizza(json);

        g1.getConjutoDados().add(lista);

        Descricao descCentro = g1.getDescricaoGrafico();
        descCentro.setAlinhamento(TipoAlinhamento.CENTER);
        descCentro.setCor(Color.BLACK);
        descCentro.setHabilitar(false);
        descCentro.setPosicaoEixoX(10);
        descCentro.setPosicaoEixoY(20);
        descCentro.setTamanhoFonte(15);
        descCentro.setTexto("DESC CENTRO");

        Descricao desc = g1.getDescricaoGrafico();
        desc.setAlinhamento(TipoAlinhamento.CENTER);
        desc.setCor(Color.BLACK);
        desc.setHabilitar(false);
        desc.setPosicaoEixoX(10);
        desc.setPosicaoEixoY(20);
        desc.setTamanhoFonte(15);
        desc.setTexto("DESC PIZZA");


        g1.setDescricaoGrafico(desc);
        g1.setTextoCentroPizza(descCentro);
        //g1.setEixoX(Eixo.populaEixoX(valorMaximoX(lista),json));
        //g1.setEixoY(Eixo.populaEixoY(TipoEixo.EIXO_Y_ESQUERDA,valorMinimoY(lista),valorMaximoY(lista),json));

        return g1;
    }

    public static Grafico montaGraficoBarraVertical(JSONObject json)throws JSONException{

        Grafico g1 = new Grafico(TipoGrafico.BARRA_VERTICAL,"Vertical-Local");

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

        ConjutoDado conjutoDado1 = new ConjutoDado(true,14,"Descricao 1a", TipoFormatacao.MONETARIO);

        conjutoDado1.getListaValores().add(new Dado(conjutoDado1.hashCode(),Color.BLACK,Color.GREEN,0f,0f));
        conjutoDado1.getListaValores().add(new Dado(conjutoDado1.hashCode(),Color.BLACK,Color.GREEN,0f,1200f));

        ConjutoDado conjutoDado2 = new ConjutoDado(true,14,"Descricao 2a",TipoFormatacao.MONETARIO);
        conjutoDado2.getListaValores().add(new Dado(conjutoDado2.hashCode(),Color.BLACK,Color.YELLOW,1f,0f));
        conjutoDado2.getListaValores().add(new Dado(conjutoDado2.hashCode(),Color.BLACK,Color.YELLOW,1f,1400f));

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

    public static ConjutoDado populaConjuntoDadosPizza(JSONObject json){

        ConjutoDado conjutoDado = new ConjutoDado(true,15,"",TipoFormatacao.SEM_FORMATACAO);

        List<Integer> cores = new ArrayList<Integer>();
        cores.add(ColorTemplate.MATERIAL_COLORS[0]);
        cores.add(ColorTemplate.MATERIAL_COLORS[1]);
        cores.add(ColorTemplate.MATERIAL_COLORS[2]);
        cores.add(ColorTemplate.MATERIAL_COLORS[3]);
        cores.add(ColorTemplate.PASTEL_COLORS[0]);
        cores.add(ColorTemplate.PASTEL_COLORS[1]);
        cores.add(ColorTemplate.PASTEL_COLORS[2]);
        cores.add(ColorTemplate.PASTEL_COLORS[3]);
        cores.add(ColorTemplate.PASTEL_COLORS[4]);
        cores.add(ColorTemplate.JOYFUL_COLORS[0]);

        for(int i =0 ; i< 10 ;i++){
            conjutoDado.getListaValores().add(new Dado(conjutoDado.hashCode(),
                    Color.GREEN,cores.get(i),10f,0f,"item-"+i));
        }

        return conjutoDado;
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
