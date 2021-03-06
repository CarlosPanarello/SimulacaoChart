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

public class InputPieChart extends AppCompatActivity implements AdapterView.OnItemSelectedListener,OnChartValueSelectedListener {
    private final String TAG = "GRF-PIE";
    PieChart lPieChart;
    Spinner lSpinnerFundos;
    EditText lEditTextValorFundos;
    List<Fundos> listaFundos;

    int tempoAnimacao = 2500;

    Button botaoInserir;
    Fundos fundoSelecionado;
    TextView textValor;

    ImageButton btnAdicionar;
    ImageButton btnRemover;

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
        lPieChart = (PieChart) findViewById(R.id.pieFundos);
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
        boolean selecionado = true;
        listaFundos.add(new Fundos(0,"Selecione ...",BigDecimal.ZERO,false,Color.TRANSPARENT));
        listaFundos.add(new Fundos(1,"Fundo FIX V",new BigDecimal(50.00),selecionado,Color.rgb(254, 149, 7)));
        listaFundos.add(new Fundos(2,"Fundo FIX VI",new BigDecimal(500.00),selecionado,Color.rgb(106, 167, 134)));
        listaFundos.add(new Fundos(3,"Fundo FIX VII",new BigDecimal(700.00),selecionado,Color.rgb(53, 194, 209)));
        listaFundos.add(new Fundos(4,"Fundo FIX VIII",new BigDecimal(300.00),selecionado,Color.rgb(193, 37, 82)));
        listaFundos.add(new Fundos(5,"Fundo FIX IX",new BigDecimal(400.00),selecionado,Color.rgb(255, 102, 0)));
        listaFundos.add(new Fundos(6,"Fundo Composto 10D",new BigDecimal(345.00),selecionado,Color.rgb(106, 150, 31)));
        listaFundos.add(new Fundos(7,"Fundo Composto 20D",new BigDecimal(145.00),selecionado,Color.rgb(179, 100, 53)));
        listaFundos.add(new Fundos(8,"Fundo Composto 30D",new BigDecimal(100.00),selecionado,Color.rgb(254, 247, 120)));
        listaFundos.add(new Fundos(9,"Fundo Composto 40D",new BigDecimal(154.00),selecionado,Color.rgb(217, 80, 138)));
        atualizarValorTotalTextView();
    }

    private BigDecimal valorTotal(List<Fundos> lista){
        BigDecimal valor = BigDecimal.ZERO;

        for(Fundos f : lista){
            if(f.isSelecionado()) {
                valor = valor.add(f.getValor());
            }
        }
       return valor;
    }

    private void atualizarValorTotalTextView(){
        textValor.setText("R$ " +valorTotal(listaFundos).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    private void removerItemPie(){
        for(Fundos f : listaFundos){
            if(f.getId() == fundoSelecionado.getId() && f.isSelecionado()){
                f.setValor(BigDecimal.ZERO);
                f.setSelecionado(false);
                lPieChart.setData(atualizarDadosPizza(listaFundos));
                lPieChart.animateY(tempoAnimacao);
                lPieChart.notifyDataSetChanged();
                lPieChart.invalidate();
                atualizarValorTotalTextView();
                lEditTextValorFundos.setText("");
                lPieChart.setCenterText("");
                lPieChart.highlightValues(null);
                fundoSelecionado = listaFundos.get(0);
                selecionarFundo();
                setItemNoSpinner(fundoSelecionado);
                break;
            }
        }
    }

    private void adicionarItemPie(){
        BigDecimal valor = BigDecimal.ZERO;
        try {
            valor = new BigDecimal(lEditTextValorFundos.getText().toString());
        } catch (Exception e){}

        if(lEditTextValorFundos.getText().toString().trim().isEmpty() || BigDecimal.ZERO.compareTo(valor) >= 0 ){
            Toast.makeText(this,"Favor informe um valor maior que zero",Toast.LENGTH_LONG).show();
            return;
        }

        for(Fundos f : listaFundos){
            if(f.getId() == fundoSelecionado.getId()){
                f.setSelecionado(true);
                f.setValor(valor);
                break;
            }
        }

        lPieChart.setData(atualizarDadosPizza(listaFundos));
        lPieChart.animateY(tempoAnimacao);
        lPieChart.notifyDataSetChanged();
        lPieChart.invalidate();
        atualizarValorTotalTextView();
        selecionarItemNoGrafico();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pie_chart);

        textValor = (TextView) findViewById((R.id.valorTotal));

        botaoInserir = (Button) findViewById(R.id.botaoInserir);
        botaoInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarItemPie();
            }
        });

        btnAdicionar = (ImageButton) findViewById(R.id.adicionarItem);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarItemPie();
            }
        });
        btnRemover = (ImageButton) findViewById(R.id.removerItem);
        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerItemPie();
            }
        });


        inicializarListaFundos();

        inicializarPizza(listaFundos);
        inicializarSpinner();

        lEditTextValorFundos = (EditText) findViewById(R.id.etValor);
    }

    private BigDecimal selecionarItemNoGrafico(){
        int size = lPieChart.getData().getDataSet().getEntryCount();
        int index = -1;
        BigDecimal valor = BigDecimal.ZERO;

        for(int i=0 ; i< size; i++){
            if(lPieChart.getData().getDataSet().getEntryForIndex(i) != null){
                Entry e = lPieChart.getData().getDataSet().getEntryForIndex(i);
                if(e.getData() instanceof Fundos){
                    Fundos f = (Fundos) e.getData();
                    if(f.getId() == fundoSelecionado.getId()){
                        index = i;
                        valor = f.getValor();
                    }
                }
            }
        }
        if(size > 0 && index >= 0) {
            Highlight highlight = new Highlight(index, 0, 0);
            lPieChart.highlightValue(highlight, false);
            lPieChart.setCenterText(textoCentral(fundoSelecionado.getDescricao(), valor, valorTotal(listaFundos)));
            lPieChart.setCenterTextColor(Color.BLACK);
        }

        return valor;
    }

    public void selecionarFundo(){
        if(fundoSelecionado.getId() == 0){
            lEditTextValorFundos.setText("");
            lEditTextValorFundos.setEnabled(false);
        } else {
            lEditTextValorFundos.setEnabled(true);
            BigDecimal valor = selecionarItemNoGrafico();
            fundoSelecionado.setValor(valor);
            lEditTextValorFundos.setText(valor.toString());
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        if(parent.getItemAtPosition(pos) instanceof Fundos){
            fundoSelecionado = (Fundos) parent.getItemAtPosition(pos);
            selecionarFundo();
            selecionarItemNoGrafico();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void setItemNoSpinner(Fundos fundoEscolhido){
        if(lSpinnerFundos != null &&lSpinnerFundos.getCount() <=0){
            return;
        }

        for(int i=0; i< lSpinnerFundos.getCount(); i++){
            Object item =lSpinnerFundos.getItemAtPosition(i);
            if(item != null && ((Fundos) item).getId() == fundoEscolhido.getId()){
                lSpinnerFundos.setSelection(i);
                return;
            }
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if(e.getData()!= null && e.getData() instanceof Fundos){
            Fundos f = (Fundos) e.getData();
            lPieChart.setCenterText(textoCentral(f.getDescricao(),f.getValor(),valorTotal(listaFundos)));
            lPieChart.setCenterTextColor(Color.BLACK);
            lPieChart.setCenterTextSize(16f);
            lEditTextValorFundos.setText(f.getValor().toString());
            setItemNoSpinner(f);
        }
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

}
