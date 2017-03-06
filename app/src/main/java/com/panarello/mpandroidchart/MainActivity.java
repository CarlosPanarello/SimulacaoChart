package com.panarello.mpandroidchart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    //protected BarChart mChart;
    private BarChart lBarChart;
    private PieChart lPieChart;
    private LineChart lLineChart;
    private Button botao;
    private SeekBar seekBar;
    //BarDataSet dadosBarra;
    //private static final int REQUEST_CODE = 999;

    final String[] labels = new String[] { "XProjetado", "XDesejado"};

    final String[] xLabelsLinear = new String[] {"2000","2003","2006","2009","2012","2015",
                                                 "2018","2021","2024","2027","2030","2033","2036"};

    private ArrayList<BarDataSet> getDataSet(float valor) {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(0f, 0.00f,"Projetado");
        BarEntry v1e2 = new BarEntry(0f, 1400000.00f);

        valueSet1.add(v1e1);
        valueSet1.add(v1e2);


        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v2e1 = new BarEntry(1f, 0.00f,"Desejado");
        BarEntry v2e2 = new BarEntry(1f,valor);


        valueSet2.add(v2e1);
        valueSet2.add(v2e2);


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Plano atual");
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Simulação");

        //barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(Color.rgb(0, 139, 237));

        //barDataSet1.setFormSize(10f);
        //barDataSet1.setValueTextSize(1f);
        //barDataSet1.setValueTextColor(Color.RED);
        barDataSet1.setValueFormatter(new MyValueFormatter());

        barDataSet2.setColor(Color.rgb(0, 84, 175));
        barDataSet2.setValueFormatter(new MyValueFormatter());
        //ist<BarEntry> listaTeste = new ArrayList<>();
        //listaTeste.add( new BarEntry(0f,0.00f,"teste") );
        //listaTeste.add( new BarEntry(1f,0.00f,"teste2") );

        //barDataSet2.setValues(listaTeste);
        //barDataSet2.setDrawValues(false);
        //barDataSet2.setValueTextSize(1f);

        List<Integer> listaCores = new ArrayList<>();
        //Segue a ordem da inclusao do valueSet2
        listaCores.add(Color.rgb(255,255,255));
        listaCores.add(Color.rgb(0,0,0));

        barDataSet2.setValueTextColors(listaCores);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        return dataSets;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void slider(){
        seekBar = (SeekBar) findViewById(R.id.sliderBarra);

        seekBar.setProgress(0);
        seekBar.incrementProgressBy(10);
        seekBar.setMax(100);

        seekBar.setOnSeekBarChangeListener(this);

    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        int progress = progresValue;
        //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
        int stepSize = 10;
        progress = ((int)Math.round(progress/stepSize))*stepSize;
        seekBar.setProgress(progress);
        //textview.setText(progress + "");

        setDataBar(lBarChart,1200000f+(10000f*progress));
        lBarChart.invalidate();
       //lBarChart.animateY(5000);
        //
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //textView.setText("Covered: " + progress + "/" + seekBar.getMax());
        Toast.makeText(getApplicationContext(), "Changing seekbar's progress "+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slider();
        botao = (Button) findViewById(R.id.botao);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,GraficoDinamico.class);
                //i.putExtra("Chave",valor);
                startActivity(i);
            }
        });



        lBarChart = (BarChart) findViewById(R.id.barChartLayout);
        lBarChart.getLayoutParams().height =600;
        //lBarChart.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 250));
        //dadosBarra = new BarDataSet(carregarDadosBarra(), "# of Calls");

        Description description = new Description();
        description.setXOffset(0.5f);
        description.setYOffset(0.5f);
        description.setTextSize(100);
        description.setTextAlign(Paint.Align.RIGHT);
        description.setPosition(1,700);
        description.setEnabled(false);

        lBarChart.setPinchZoom(false);
        lBarChart.setDoubleTapToZoomEnabled(false);

        lBarChart.setDescription(description);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels[(int) value];
            }

            // we don't draw numbers, so no decimal digits needed
  //          @Override
//            public int getDecimalDigits() {  return 0; }
        };

        XAxis xAxis = lBarChart.getXAxis();

        xAxis.setGridLineWidth(1.5f);
        //xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(true);


        xAxis.setTypeface(Typeface.create(Typeface.MONOSPACE,2));

        YAxis left = lBarChart.getAxisLeft();
        left.setDrawLabels(true); // no axis labels
        left.setAxisMinimum(0f); // start at zero
        left.setAxisMaximum(2200000f); // the axis maximum is 100
        left.setDrawGridLines(true);
        left.setValueFormatter(new MyValueFormatterAxis());

        //left.setTypeface(Typeface.create(Typeface.MONOSPACE,3));
        //left.setTextSize(20f);
        //left.setDrawAxisLine(false); // no axis line
        //left.setDrawGridLines(false); // no grid lines
        //left.setDrawZeroLine(true); // draw a zero line
        lBarChart.getAxisRight().setEnabled(false); // no right axis
        lBarChart.getAxisLeft().setEnabled(true);
        lBarChart.getXAxis().setEnabled(true);

        //BarDataSet set1 = new BarDataSet(carregarDadosBarra(), "The year 2017");


        //dataSets.add(carregarDadosBarra());

        setDataBar(lBarChart,1800000.00f);

        lBarChart.setFitBars(true);

        //lBarChart.setBorderColor(Color.BLACK);
        //lBarChart.setBorderWidth(0.8f);
        lBarChart.setDrawBorders(false);
        lBarChart.setDrawGridBackground(false);
        //Cor de fundo
        //lBarChart.setGridBackgroundColor(Color.rgb(247,252,246));

        lBarChart.getLegend().setTextSize(14f);
        /*
        BarData data = new BarData(getDataSet());
        mChart.setData(data);
        mChart.setDescription("My Chart");
        mChart.animateXY(2000, 2000);
        mChart.invalidate();
        */
        lineChartInicializacao();
        pieChartInicializacao();
    }

    private void ajustarGrafico(BarChart c,float valor){
        float maxValor = Float.MIN_VALUE;
        if (c.getData() != null &&
                c.getData().getDataSetCount() > 0) {

            for(IBarDataSet ds : c.getData().getDataSets() ){
                BarDataSet set =  (BarDataSet) ds;
                for(BarEntry be : set.getValues()){
                    if( maxValor < be.getY() ){
                        maxValor = be.getY();
                    }
                }
            }

            c.getAxisLeft().setAxisMaximum(maxValor + maxValor * 0.2f);
        }
    }

    private void setDataBar(BarChart c,float valor){

        if (c.getData() != null &&
                c.getData().getDataSetCount() > 0) {
            BarDataSet set1 = (BarDataSet) c.getData().getDataSetByIndex(1);
            set1.getValues().get(1).setY(valor);
            c.getData().notifyDataChanged();
            c.notifyDataSetChanged();
            ajustarGrafico(c,valor);
        } else {
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.addAll(getDataSet(valor));
            BarData data = new BarData(dataSets);
            //data.setValueTextSize(10f);
            //data.setValueTypeface(Typeface.DEFAULT);

            data.setBarWidth(0.8f);
            data.setValueTextSize(13f);



            c.setData(data);
        }


    }

    private PieDataSet getPieDataSet(){
        ArrayList<PieEntry> valueSet1 = new ArrayList<>();
        PieEntry v1e1 = new PieEntry(40f,"valor40");
        PieEntry v1e2 = new PieEntry(80f);
        PieEntry v1e3 = new PieEntry(60f,"valor60");
        valueSet1.add(v1e1);
        valueSet1.add(v1e2);
        valueSet1.add(v1e3);


        PieDataSet item = new PieDataSet(valueSet1,"Conjunto1");
        item.setColors(Color.RED,Color.BLUE,Color.GREEN);

        return item;
    }

    private ArrayList<LineDataSet> getLineDataSet() {
        ArrayList<LineDataSet> dataSets = null;

        ArrayList<Entry> valueSet1 = new ArrayList<>();
        Entry v1e01 = new Entry(01f, 1.00f);
        Entry v1e02 = new Entry(02f, 2.00f);
        Entry v1e03 = new Entry(03f, 4.00f);
        Entry v1e04 = new Entry(04f, 8.00f);
        Entry v1e05 = new Entry(05f, 16.00f);
        Entry v1e06 = new Entry(06f, 32.00f);
        Entry v1e07 = new Entry(07f, 64.00f);
        Entry v1e08 = new Entry(08f, 128.00f);
        Entry v1e09 = new Entry(09f, 256.00f);
        Entry v1e10 = new Entry(10f, 512.00f);
        valueSet1.add(v1e01);
        valueSet1.add(v1e02);
        valueSet1.add(v1e03);
        valueSet1.add(v1e04);
        valueSet1.add(v1e05);
        valueSet1.add(v1e06);
        valueSet1.add(v1e07);
        valueSet1.add(v1e08);
        valueSet1.add(v1e09);
        valueSet1.add(v1e10);

        ArrayList<Entry> valueSet2 = new ArrayList<>();
       Entry v2e01 = new Entry(01f,001.00f);
        /*Entry v2e02 = new Entry(02f,002.05f);
        Entry v2e03 = new Entry(03f,004.20f);
        Entry v2e04 = new Entry(04f,008.61f);
        Entry v2e05 = new Entry(05f,017.66f);
        Entry v2e06 = new Entry(06f,036.20f);
        Entry v2e07 = new Entry(07f,074.22f);
        Entry v2e08 = new Entry(08f,152.15f);
        Entry v2e09 = new Entry(09f,311.91f);
        Entry v2e10 = new Entry(10f,639.41f);*/

        Entry v2e02 = new Entry(02f,0003f);
        Entry v2e03 = new Entry(03f,0009f);
        Entry v2e04 = new Entry(04f,0027f);
        Entry v2e05 = new Entry(05f,0081f);
        Entry v2e06 = new Entry(06f,0142f);
        Entry v2e07 = new Entry(07f,0350f);
        Entry v2e08 = new Entry(08f,0750f);
        Entry v2e09 = new Entry(09f,1500f);
        Entry v2e10 = new Entry(10f,3000f);

        valueSet2.add(v2e01);
        valueSet2.add(v2e02);
        valueSet2.add(v2e03);
        valueSet2.add(v2e04);
        valueSet2.add(v2e05);
        valueSet2.add(v2e06);
        valueSet2.add(v2e07);
        valueSet2.add(v2e08);
        valueSet2.add(v2e09);
        valueSet2.add(v2e10);


        ArrayList<Entry> valueSet3 = new ArrayList<>();
        Entry v3e01 = new Entry(01f,001.00f);
        valueSet3.add(v3e01);
        Entry v3e02 = new Entry(12f,100.00f);
        valueSet3.add(v3e02);

        LineDataSet lineDataSet1 = new LineDataSet(valueSet1, "Plano Atual");
        LineDataSet lineDataSet2 = new LineDataSet(valueSet2, "Simulação");

        LineDataSet lineDataSet3 = new LineDataSet(valueSet3, "");
        lineDataSet3.setFillAlpha(0);
        lineDataSet3.setDrawValues(false);
        lineDataSet3.setDrawCircleHole(false);
        lineDataSet3.setDrawCircles(false);

        //lineDataSet1.setColor(Color.rgb(0, 155, 0));
        lineDataSet1.setColor(Color.rgb(0, 139, 237));
        //lineDataSet1.setColor(Color.rgb(0, 155, 0));
        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        //lineDataSet1.setFormSize(10f);
        //lineDataSet1.setValueTextSize(1f);
        //lineDataSet1.setValueTextColor(Color.RED);
        lineDataSet1.setValueFormatter(new MyValueFormatter(10.0f));
        lineDataSet1.setDrawValues(true);
        lineDataSet1.setDrawCircleHole(false);
        lineDataSet1.setDrawFilled(false);
        lineDataSet1.setDrawVerticalHighlightIndicator(true);
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setValueTextSize(12f);

        lineDataSet2.setColor(Color.rgb(0, 84, 175));
        lineDataSet2.setValueFormatter(new MyValueFormatter(10.0f));
        lineDataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet2.setValueTextSize(12f);

        lineDataSet2.setDrawCircleHole(false);
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawValues(true);
        //lineDataSet2.setValueTextSize(1f);

        dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);
        //dataSets.add(lineDataSet3);

        return dataSets;
    }

    private void pieChartInicializacao(){
        lPieChart = (PieChart)  findViewById(R.id.pieChartLayout);
        lPieChart.getLayoutParams().height = 600;
        Description description = new Description();
        description.setEnabled(false);

        lPieChart.setDescription(description);

        PieDataSet dataset =getPieDataSet();
        //dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData dados = new PieData(dataset);
        lPieChart.animateY(5000);
        lPieChart.setData(dados);

        // enable hole and configure
        lPieChart.setDrawHoleEnabled(true);
        lPieChart.setHoleColor(Color.GRAY);
        lPieChart.setHoleRadius(50);
        lPieChart.setTransparentCircleRadius(10);

        lPieChart.setHighlightPerTapEnabled(true);
        //lPieChart.setOnChartValueSelectedListener(O);

        lPieChart.setCenterText("Centro");
        //lPieChart.setOnClickListener(new OnChartValueSelectedListener());

        //Exibe %
        lPieChart.setUsePercentValues(true);
    }

    private void lineChartInicializacao(){
        lLineChart = (LineChart) findViewById(R.id.lineChartLayout);
        lLineChart.getLayoutParams().height = 600;
        //lLineChart.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 250));
        lLineChart.animateY(5000);
        lLineChart.animateX(5000);
        Description description = new Description();
        description.setEnabled(false);

        lLineChart.setDescription(description);

        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.addAll(getLineDataSet());

        LineData lineData = new LineData(dataSets);

        lLineChart.setData(lineData);
        //lLineChart.setGridBackgroundColor(Color.rgb(247,252,246));
        lLineChart.setBorderWidth(0.8f);
        lLineChart.setBorderColor(Color.BLACK);
        lLineChart.setDrawBorders(false);
        lLineChart.setDrawGridBackground(false);
        //lLineChart.setGridBackgroundColor(Color.rgb(200,255,255));

        lLineChart.getLegend().setTextSize(14f);

        IAxisValueFormatter formatter2 = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabelsLinear[(int) value];
            }

            // we don't draw numbers, so no decimal digits needed
            //          @Override
//            public int getDecimalDigits() {  return 0; }
        };

        XAxis xAxis = lLineChart.getXAxis();
        //xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter2);
        //xAxis.setDrawLabels(false);
        //xAxis.setDrawGridLines(false);
        //Define o maximo de itens para exibr
        lLineChart.setVisibleXRangeMaximum(10);

        //Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        LimitLine ll1 = new LimitLine(10f, "2030");
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 10f, 0f);
        //ll1.setLineColor(Color.rgb(80,80,80));
        //ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll1.setTextSize(10f);
        //ll1.setTypeface(tf);


        xAxis.addLimitLine(ll1);
        xAxis.setAxisMaximum(12f);

        Log.d("ChartPana","label count" + xAxis.getLabelCount());
        //lLineChart.setViewPortOffsets(100,10,100,10);
        xAxis.setTypeface(Typeface.create(Typeface.MONOSPACE,2));

        YAxis left = lLineChart.getAxisLeft();
        YAxis right = lLineChart.getAxisRight();
        right.setDrawLabels(false); // no axis labels
        left.setDrawLabels(true); // no axis labels
        left.setValueFormatter(new MyValueFormatterAxis());
        left.setAxisMinimum(0f); // start at zero
        left.setAxisMaximum(5000f); // the axis maximum is 100

        right.setAxisMinimum(0f); // start at zero
        right.setAxisMaximum(5000f); // the axis maximum is 100

        //lLineChart.setScaleYEnabled(true);
        //lLineChart.setScaleY(0.5f);
        //lLineChart.setScaleMinima(100f,100f);
        lLineChart.setAutoScaleMinMaxEnabled(true);

        lLineChart.setPinchZoom(true);
        lLineChart.setDoubleTapToZoomEnabled(true);

        xAxis.setEnabled(false);
        right.setEnabled(false);
        left.setEnabled(false);
    }

    public class MyValueFormatterAxis implements IAxisValueFormatter{
        private NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return nf.format(value);
        }
    }

    public class MyValueFormatter implements IValueFormatter{

        private NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        private float ultimo;

        public MyValueFormatter(float ultimo) {
            this.ultimo = ultimo;
        }

        public MyValueFormatter() {
            ultimo =-1;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            Log.d("ChartPana",""+entry.getX()+  " - " + ultimo + " iguais - " +  (ultimo != entry.getX()));
            if(ultimo>0f && ultimo != entry.getX()){
                return "";
            }

            if(entry.getData()!= null){
                if(entry.getData() instanceof String){
                    Log.d("ChartPana","Data "+ entry.getData().toString());
                    return entry.getData().toString();
                }
            }

            return nf.format(value); // e.g. append a dollar-sign
        }
    }
}
