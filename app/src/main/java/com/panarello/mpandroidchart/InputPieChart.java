package com.panarello.mpandroidchart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.panarello.mpandroidchart.R;
import com.panarello.mpandroidchart.dominio.ConjutoDado;
import com.panarello.mpandroidchart.dominio.Dado;
import com.panarello.mpandroidchart.dominio.Grafico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InputPieChart extends AppCompatActivity implements AdapterView.OnItemSelectedListener,OnChartValueSelectedListener {
    private final String TAG = "GRF-PIE";
    PieChart lPieChart;
    Spinner lSpinnerFundos;
    EditText lEditTextValorFundos;
    List<Fundos> listaFundos;
    List<Fundos> listaFundosSelecionados;
    Button botaoInserir;
    Fundos fundoSelecionado;

    public PieData atualizarDadosPizza(List<Fundos> lista){
        PieData pieDados;
        ArrayList<PieEntry> entries = new ArrayList<>();

        List<Integer> cores = new ArrayList<>();
        cores.add(Color.rgb(217, 80, 138));
        cores.add(Color.rgb(254, 149, 7));
        cores.add(Color.rgb(106, 167, 134));
        cores.add(Color.rgb(53, 194, 209));
        cores.add(Color.rgb(193, 37, 82));
        cores.add(Color.rgb(255, 102, 0));
        cores.add(Color.rgb(245, 199, 0));
        cores.add(Color.rgb(106, 150, 31));
        cores.add(Color.rgb(179, 100, 53));
        cores.add(Color.rgb(254, 247, 120));


        for(Fundos fundo : lista){
            if(fundo.getValor().compareTo(BigDecimal.ZERO) > 0) {
                entries.add(new PieEntry(Float.valueOf(fundo.getValor().toString()), fundo.getDescricao(), fundo));
            }
        }

        PieDataSet item = new PieDataSet(entries,"");

        item.setColors(cores);

        item.setSelectionShift(15f);

        pieDados = new PieData();
        pieDados.setDataSet(item);

        pieDados.setValueTextSize(20f);
        return pieDados;
    }

    private void inicializarPizza(List<Fundos> listaInicial){
        lPieChart = (PieChart) findViewById(R.id.pieFundos);
        //lPieChart.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, alturaTela()/3));
        lPieChart.getLayoutParams().height = alturaTela()/3;
        Description desc = new Description();
        desc.setText("Distribuicao dos Fundos");
        desc.setEnabled(true);

        lPieChart.setDescription(desc);

        lPieChart.animateY(5000);
        lPieChart.setData(atualizarDadosPizza(listaInicial));

        // enable hole and configure
        lPieChart.setDrawHoleEnabled(true);
        lPieChart.setHoleColor(Color.TRANSPARENT);
        //lPieChart.setHoleRadius(50);
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
    }

    private void inicializarSpinner(){
        lSpinnerFundos = (Spinner) findViewById(R.id.spinnerFundos);

        ArrayAdapter<Fundos> dataAdapter = new ArrayAdapter<Fundos>(this,
                android.R.layout.simple_spinner_item, listaFundos);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lSpinnerFundos.setAdapter(dataAdapter);
        lSpinnerFundos.setOnItemSelectedListener(this);
    }

    private void inicializarListaFundos(){
        listaFundos = new ArrayList<>();
        listaFundos.add(new Fundos(1,"Fundo FIX V",BigDecimal.ZERO));
        listaFundos.add(new Fundos(2,"Fundo FIX VI",BigDecimal.ZERO));
        listaFundos.add(new Fundos(3,"Fundo FIX VII",BigDecimal.ZERO));
        listaFundos.add(new Fundos(4,"Fundo FIX VIII",BigDecimal.ZERO));
        listaFundos.add(new Fundos(5,"Fundo FIX IX",BigDecimal.ZERO));
        listaFundos.add(new Fundos(6,"Fundo Composto 10D",BigDecimal.ZERO));
        listaFundos.add(new Fundos(7,"Fundo Composto 20D",BigDecimal.ZERO));
        listaFundos.add(new Fundos(8,"Fundo Composto 30D",BigDecimal.ZERO));
        listaFundos.add(new Fundos(9,"Fundo Composto 40D",BigDecimal.ZERO));

        listaFundosSelecionados = new ArrayList<>();
        listaFundosSelecionados.add(new Fundos(1,"Fundo FIX V",new BigDecimal(100.00)));
    }

    private void adicionarItemPie(){
        boolean encontrou = false;
        for(Fundos f : listaFundosSelecionados){
            if(f.getId() == fundoSelecionado.getId()){
                encontrou = true;
                try {
                    BigDecimal valor = new BigDecimal(lEditTextValorFundos.getText().toString());
                    f.setValor(valor);
                } catch (Exception e){
                    Toast.makeText(this,"ERRO NO VALOR2",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

        if(encontrou == false) {
            listaFundosSelecionados.add(fundoSelecionado);
        }

        lPieChart.setData(atualizarDadosPizza(listaFundosSelecionados));
        lPieChart.animateY(3000);
        lPieChart.notifyDataSetChanged();
        lPieChart.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pie_chart);

        botaoInserir = (Button) findViewById(R.id.botaoInserir);
        botaoInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarItemPie();
            }
        });

        inicializarListaFundos();

        inicializarPizza(listaFundosSelecionados);
        inicializarSpinner();

        lEditTextValorFundos = (EditText) findViewById(R.id.etValor);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        if(parent.getItemAtPosition(pos) instanceof Fundos){
            fundoSelecionado = (Fundos) parent.getItemAtPosition(pos);

            BigDecimal valor = BigDecimal.ZERO;
            try {
                valor = new BigDecimal(lEditTextValorFundos.getText().toString());
                Toast.makeText(this,"VALOR-->" + valor.toString(),Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(this,"ERRO NO VALOR",Toast.LENGTH_SHORT).show();
            }

            lPieChart.getHighlighted();

            Highlight highlight = new Highlight(0,Float.valueOf(valor.toString()),0);

            lPieChart.highlightValue(highlight, false);
            //lPieChart.invalidate();
            fundoSelecionado.setValor(valor);

        }


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if(e.getData()!= null && e.getData() instanceof Fundos){
            Fundos f = (Fundos) e.getData();
            lPieChart.setCenterText(f.getDescricao());
            lPieChart.setCenterTextColor(Color.BLACK);
            lEditTextValorFundos.setText(f.getValor().toString());
            //TODO rever esse modo de selecionar um item
            lSpinnerFundos.setSelection(f.getId()-1);
            h.getDataSetIndex();
            h.getDataIndex();
            h.getXPx();
        }
    }

    @Override
    public void onNothingSelected() {
        lPieChart.setCenterText("");
        Toast.makeText(this,"PieChart nothing seletecd",Toast.LENGTH_LONG ).show();
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
    public class Fundos{
        private int id;
        private String descricao;
        private BigDecimal valor;

        public Fundos(int id, String descricao, BigDecimal valor) {
            this.id = id;
            this.descricao = descricao;
            this.valor = valor;
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

        @Override
        public String toString() {
            return descricao;
        }
    }
}
