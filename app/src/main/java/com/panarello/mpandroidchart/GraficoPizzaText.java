package com.panarello.mpandroidchart;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.panarello.mpandroidchart.dominio.Fundos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GraficoPizzaText extends AppCompatActivity  implements OnChartValueSelectedListener {

    private final String TAG = "GRF-PIE-T";

    PieChart lPieChart;
    Spinner lSpinnerFundos;
    Spinner lSpinnerPorcentagem;
    List<Fundos> listaFundos;
    int tempoAnimacao = 2500;
    List<String>  listaPorcentagem;



    ImageButton btnAdicionar;
    ImageButton btnRemover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_pizza_text);

        inicializarListaFundos();
        atualizarListaPorcentagem();
        inicializarSpinner();
        inicializarPizza(listaFundos);

        btnAdicionar = (ImageButton) findViewById(R.id.adicionarItemSpinner);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarItemPie();
            }
        });
        btnRemover = (ImageButton) findViewById(R.id.removerItemSpiner);
        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerItemPie();
            }
        });
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if(e.getData()!= null && e.getData() instanceof Fundos){
            Fundos f = (Fundos) e.getData();
            lPieChart.setCenterText(textoCentral(f.getDescricao(),BigDecimal.ZERO,BigDecimal.ZERO));
            lPieChart.setCenterTextColor(Color.BLACK);
            lPieChart.setCenterTextSize(16f);

            setSpinnerFundos(f);
            selecionarPorcentagemSpinner(f.getValor());
        }
    }

    @Override
    public void onNothingSelected() {

    }

    private void adicionarItemPie(){
        if(!ultrapassou100()) {
            atualizarListaPorcentagem();
            atualizarListaSpinnerPorcentagem();
        } else {
            Toast.makeText(this,"Não é possivel adicionar mais fundos",Toast.LENGTH_LONG).show();
        }
    }

    private void removerItemPie(){

    }

    public PieData atualizarDadosPizza(List<Fundos> lista){
        PieData pieDados;
        ArrayList<PieEntry> entries = new ArrayList<>();

        List<Integer> cores = new ArrayList<>();

        for(Fundos fundo : lista){
            if(fundo.isSelecionado()) {
                entries.add(new PieEntry(Float.valueOf(fundo.getValor().toString()), fundo.getDescricao(), fundo));
                cores.add(fundo.getColor());
            }
        }

        PieDataSet item = new PieDataSet(entries,"");

        item.setColors(cores);

        item.setSelectionShift(15f);

        pieDados = new PieData();
        pieDados.setDataSet(item);
        pieDados.setValueFormatter(new PercentFormatter());
        pieDados.setValueTextSize(16f);
        pieDados.setDrawValues(false);
        return pieDados;
    }

    private void inicializarPizza(List<Fundos> listaInicial){
        lPieChart = (PieChart) findViewById(R.id.pieFundosText);
        //lPieChart.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, alturaTela()/3));
        lPieChart.getLayoutParams().height = alturaTela()/3;
        Description desc = new Description();
        desc.setText("Distribuicao dos Fundos");
        desc.setEnabled(false);

        lPieChart.setDescription(desc);

        lPieChart.animateY(tempoAnimacao);
        lPieChart.setData(atualizarDadosPizza(listaInicial));

        // enable hole and configure
        lPieChart.setDrawHoleEnabled(true);
        lPieChart.setHoleColor(Color.TRANSPARENT);
        lPieChart.setHoleRadius(60);
        //pie.setTransparentCircleRadius(100);

        lPieChart.setHighlightPerTapEnabled(true);
        //pie.setMaxHighlightDistance(100);

        //pie.setPadding
        //lPieChart.setOnChartValueSelectedListener(O);

        lPieChart.setCenterText("Fundos");
        lPieChart.setCenterTextColor(Color.BLACK);
        lPieChart.setCenterTextSize(10l);
        lPieChart.setDrawCenterText(true);
        //lPieChart.setCenterTextOffset(gp.getTextoCentroPizza().getPosicaoEixoX(),gp.getTextoCentroPizza().getPosicaoEixoY());
        //pie.setCenterTextTypeface();
        //pie.setCenterTextRadiusPercent();

        //lPieChart.setOnClickListener(new OnChartValueSelectedListener());
        //pie.setCenterTextRadiusPercent(40);
        lPieChart.setDrawEntryLabels(false);
        //
        lPieChart.setRotationEnabled(true);
        // pie.setScaleX(10);
        lPieChart.setBackgroundColor(Color.TRANSPARENT);
        //Exibe %
        lPieChart.setUsePercentValues(true);
        lPieChart.getData().setValueTextSize(20f);
        lPieChart.setOnChartValueSelectedListener(this);

        Legend l = lPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setXEntrySpace(0f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setXOffset(0f);
        l.setTextSize(12f);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setEnabled(false);
        //lPieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        lPieChart.setExtraRightOffset(0f);
    }


    private void inicializarListaFundos(){
        listaFundos = new ArrayList<>();
        boolean selecionado = true;
        listaFundos.add(new Fundos(0,"Selecione ...", new BigDecimal(25.00),false, Color.TRANSPARENT));
        listaFundos.add(new Fundos(1,"Fundo FIX V",new BigDecimal(0.00),!selecionado,Color.rgb(254, 149, 7)));
        listaFundos.add(new Fundos(2,"Fundo FIX VI",new BigDecimal(0.00),!selecionado,Color.rgb(106, 167, 134)));
        listaFundos.add(new Fundos(3,"Fundo FIX VII",new BigDecimal(10.00),selecionado,Color.rgb(53, 194, 209)));
        listaFundos.add(new Fundos(4,"Fundo FIX VIII",new BigDecimal(15.00),selecionado,Color.rgb(193, 37, 82)));
        listaFundos.add(new Fundos(5,"Fundo FIX IX",new BigDecimal(10.00),selecionado,Color.rgb(255, 102, 0)));
        listaFundos.add(new Fundos(6,"Fundo Composto 10D",new BigDecimal(10.00),selecionado,Color.rgb(106, 150, 31)));
        listaFundos.add(new Fundos(7,"Fundo Composto 20D",new BigDecimal(10.00),selecionado,Color.rgb(179, 100, 53)));
        listaFundos.add(new Fundos(8,"Fundo Composto 30D",new BigDecimal(10.00),selecionado,Color.rgb(254, 247, 120)));
        listaFundos.add(new Fundos(9,"Fundo Composto 40D",new BigDecimal(10.00),selecionado,Color.rgb(217, 80, 138)));
    }

    private BigDecimal valorTotalItemSelecionados(List<Fundos> lista){
        BigDecimal valor = BigDecimal.ZERO;

        for(Fundos f : lista){
            if(f.isSelecionado()) {
                valor = valor.add(f.getValor());
            }
        }
        return valor;
    }

    private void atualizarListaPorcentagem(){
        if(listaPorcentagem == null){
            listaPorcentagem = new ArrayList<>();
        }

        int fator = 5;
        Integer limite = Integer.valueOf(valorTotalItemSelecionados(listaFundos).toString());

        listaPorcentagem.add("Selecione...");

        for(int i =fator ; i <= limite ; i+=fator){
            listaPorcentagem.add(i + " %");
        }
    }


    private void setlSpinnerPorcentagem(String valorSelecionado){
        if(lSpinnerPorcentagem != null && lSpinnerPorcentagem.getCount() <=0){
            return;
        }

        for(int i=0; i< lSpinnerPorcentagem.getCount(); i++){
            Object item =lSpinnerPorcentagem.getItemAtPosition(i);
            if(item != null && valorSelecionado.equalsIgnoreCase(((String) item).replace("%","").trim()) ){
                lSpinnerPorcentagem.setSelection(i);
                return;
            }
        }
    }

    private void setSpinnerFundos(Fundos fundo){
        if(lSpinnerFundos != null &&lSpinnerFundos.getCount() <=0){
            return;
        }

        for(int i=0; i< lSpinnerFundos.getCount(); i++){
            Object item =lSpinnerFundos.getItemAtPosition(i);
            if(item != null && ((Fundos) item).getId() == fundo.getId()){
                lSpinnerFundos.setSelection(i);
                return;
            }
        }
    }

    private BigDecimal selecionarFundoNoGrafico(Fundos fundo){
        int size = lPieChart.getData().getDataSet().getEntryCount();
        int index = -1;
        BigDecimal valor = BigDecimal.ZERO;

        for(int i=0 ; i< size; i++){
            if(lPieChart.getData().getDataSet().getEntryForIndex(i) != null){
                Entry e = lPieChart.getData().getDataSet().getEntryForIndex(i);
                if(e.getData() instanceof Fundos){
                    Fundos f = (Fundos) e.getData();
                    if(f.getId() == fundo.getId()){
                        index = i;
                        valor = f.getValor();
                    }
                }
            }
        }
        if(size > 0 && index >= 0) {
            Highlight highlight = new Highlight(index, 0, 0);
            lPieChart.highlightValue(highlight, false);
            lPieChart.setCenterText(textoCentral(fundo.getDescricao(),BigDecimal.ZERO,BigDecimal.ZERO));
            lPieChart.setCenterTextColor(Color.BLACK);
        }

        return valor;
    }

    private boolean ultrapassou100(){
        if(valorTotalItemSelecionados(listaFundos).compareTo(new BigDecimal(100)) >= 0){
            lSpinnerPorcentagem.setEnabled(false);
            return true;
        } else {
            lSpinnerPorcentagem.setEnabled(true);
            return false;
        }
    }

    private void selecionarPorcentagemSpinner(BigDecimal valor){
        if(valor != null) {
            setlSpinnerPorcentagem(valor.toString());
        }
        ultrapassou100();


    }

    private void atualizarListaSpinnerPorcentagem(){
        ArrayAdapter<String> dataAdapterPorcentagem = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listaPorcentagem);
        lSpinnerPorcentagem.setAdapter(dataAdapterPorcentagem);

    }

    private void inicializarSpinner(){
        lSpinnerFundos = (Spinner) findViewById(R.id.spinnerFundosText);
        lSpinnerPorcentagem = (Spinner) findViewById(R.id.spinnerPorcentagemText);

        ArrayAdapter<Fundos> dataAdapter = new ArrayAdapter<Fundos>(this,
                android.R.layout.simple_spinner_item, listaFundos);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lSpinnerFundos.setAdapter(dataAdapter);
        lSpinnerFundos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position) instanceof Fundos){
                    Fundos fundo = (Fundos) parent.getItemAtPosition(position);
                    BigDecimal valor = selecionarFundoNoGrafico(fundo);
                    selecionarPorcentagemSpinner(valor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        atualizarListaSpinnerPorcentagem();
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        lSpinnerPorcentagem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private SpannableString textoCentral(String fundo, BigDecimal valorItem, BigDecimal valorTotal){
        String valorPorcento;
        SpannableString s;

        if(valorItem!= null && valorItem.compareTo(BigDecimal.ZERO)>0
                && valorTotal!=null && valorTotal.compareTo(BigDecimal.ZERO)>0){
            valorPorcento = valorItem.multiply(new BigDecimal(100)).divide(valorTotal,2,BigDecimal.ROUND_HALF_UP).setScale(2).toString() + "%";

            s = new SpannableString(fundo + "\n" + valorPorcento);
            s.setSpan(new RelativeSizeSpan(1f), 0, fundo.length(), 0);
            s.setSpan(new StyleSpan(Typeface.BOLD), 0, fundo.length(), 0);
            s.setSpan(new RelativeSizeSpan(1.5f), fundo.length(), s.length(), 0);
        } else {
            s = new SpannableString(fundo);
        }
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;

    }

    private int alturaTela() {
        Display display = getWindowManager().getDefaultDisplay();
        String displayName = display.getName();  // minSdkVersion=17+
        Log.i(TAG, "displayName  = " + displayName);

        // display size in pixels
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.i(TAG, "width        = " + width);
        Log.i(TAG, "height       = " + height);

        // pixels, dpi
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        int densityDpi = metrics.densityDpi;
        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;
        Log.i(TAG, "widthPixels  = " + widthPixels);
        Log.i(TAG, "heightPixels = " + heightPixels);
        Log.i(TAG, "densityDpi   = " + densityDpi);
        Log.i(TAG, "xdpi         = " + xdpi);
        Log.i(TAG, "ydpi         = " + ydpi);
        return height;
    }
}
