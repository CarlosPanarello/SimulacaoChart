package com.panarello.mpandroidchart;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
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
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.panarello.mpandroidchart.convert.Conversor;
import com.panarello.mpandroidchart.dominio.ConjutoDado;
import com.panarello.mpandroidchart.dominio.Dado;
import com.panarello.mpandroidchart.dominio.Eixo;
import com.panarello.mpandroidchart.dominio.Grafico;
import com.panarello.mpandroidchart.dominio.LinhaLimite;
import com.panarello.mpandroidchart.dominio.Parametros;
import com.panarello.mpandroidchart.dominio.TipoEixo;
import com.panarello.mpandroidchart.dominio.TipoFormatacao;
import com.panarello.mpandroidchart.dominio.TipoGrafico;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class GraficoDinamico extends Activity implements CompoundButton.OnCheckedChangeListener {
    private final String TAG = "GRF-DYM";
    private final String TAG2 = "JSON";

    private HorizontalBarChart barHChart;
    private BarChart barVChart;
    private PieChart pieChart;
    private LineChart lineChart;
    private SeekBar seekBar;
    private RadioGroup radio;

    private View gerarRadioButtonOpcao(int alt, List<Grafico> listaGraficos) {

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setId(View.generateViewId());
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, alt));
        //radioGroup.setBackgroundColor(Color.RED);
        for (Grafico g : listaGraficos) {
            RadioButton rButton = new RadioButton(this);
            rButton.setId(View.generateViewId());
            rButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.25f));
            rButton.setText(g.getDescricaoGrafico().getTexto());
            rButton.setTextSize(12f);
            rButton.setTag(g);

            rButton.setOnCheckedChangeListener(this);
            radioGroup.addView(rButton);
        }
        return radioGroup;
    }

    private View opcoesGrafico(int alt, List<Grafico> listaGraficos) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setId(View.generateViewId());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, alt));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        radio = (RadioGroup) gerarRadioButtonOpcao(alt, listaGraficos);
        linearLayout.addView(gerarRadioButtonOpcao(alt, listaGraficos));
        //linearLayout.setBackgroundColor(Color.YELLOW);
        return linearLayout;
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

    private View criarSlider(int alt) {
        SeekBar lSeekBar = new SeekBar(this);
        lSeekBar.setId(View.generateViewId());
        lSeekBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, alt));
        lSeekBar.setOnSeekBarChangeListener(new SeekBarListenner(barVChart, barHChart, lineChart, pieChart));
        lSeekBar.setProgress(0);
        lSeekBar.incrementProgressBy(10);
        lSeekBar.setMax(100);
        return lSeekBar;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked && buttonView.getTag() != null && buttonView.getTag() instanceof Grafico) {
            //Toast toast = ;
            //toast.show();
            Grafico grafico = (Grafico) buttonView.getTag();
            barVChart.setVisibility(View.GONE);
            barHChart.setVisibility(View.GONE);
            lineChart.setVisibility(View.GONE);
            pieChart.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), grafico.getDescricaoGrafico().getTexto(), Toast.LENGTH_SHORT).show();
            switch (grafico.getTipo()) {
                case BARRA_HORIZONTAL:
                    barHChart.setVisibility(View.VISIBLE);
                    break;
                case BARRA_VERTICAL:
                    barVChart.setVisibility(View.VISIBLE);
                    break;
                case PIZZA:
                    pieChart.setVisibility(View.VISIBLE);
                    break;
                case LINHA:
                    lineChart.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    // Conversor
    private void initEixo(TipoGrafico tipo, Eixo eixo, XAxis axis) {
        axis.setEnabled(eixo.isHabilitado());
        if (eixo.isHabilitado()) {
            if (!TipoFormatacao.SEM_FORMATACAO.equals(eixo.getTipoFormatacao())) {
                axis.setValueFormatter(new ValueAxisFormatter(eixo.getTipoFormatacao(), eixo.getFormatacao()));
            }

            axis.setDrawGridLines(eixo.isExibirGrade());
            if (eixo.isExibirGrade()) {
                axis.setGridLineWidth(eixo.getEspessuraGrade().getValor());
                axis.setGridColor(eixo.getCorGrade());
            }

            axis.setDrawLabels(eixo.isExibirLabel());
            if (eixo.isExibirLabel()) {
                //axis.setTextSize(eixo.getLabel().getTamanhoFonte());
                axis.setTextColor(eixo.getLabel().getCor());
                //xAxis.setTypeface(Typeface.create(Typeface.MONOSPACE,2));
            }
        }
        if (eixo.getLinhaLimite() != null && eixo.getLinhaLimite().isHabilitado()) {
            axis.addLimitLine(linhaLimite(eixo.getLinhaLimite()));
        }

        if (tipo.equals(TipoGrafico.LINHA)) {
            axis.setAxisMaximum(eixo.getValorMaximo());
            axis.setAxisMinimum(eixo.getValorMinimo());
        }


    }

    private void initEixo(TipoGrafico tipo,Eixo eixo, YAxis axis) {
        axis.setEnabled(eixo.isHabilitado());
        if (eixo.isHabilitado()) {
            if (!TipoFormatacao.SEM_FORMATACAO.equals(eixo.getTipoFormatacao())) {
                axis.setValueFormatter(new ValueAxisFormatter(eixo.getTipoFormatacao(), eixo.getFormatacao()));
            }


            axis.setDrawGridLines(eixo.isExibirGrade());
            if (eixo.isExibirGrade()) {
                axis.setGridLineWidth(eixo.getEspessuraGrade().getValor());
                axis.setGridColor(eixo.getCorGrade());
            }


            axis.setDrawLabels(eixo.isExibirLabel());
            if (eixo.isExibirLabel()) {
                //axis.setTextSize(eixo.getLabel().getTamanhoFonte());
                axis.setTextColor(eixo.getLabel().getCor());
                //xAxis.setTypeface(Typeface.create(Typeface.MONOSPACE,2));
            }
        }

        if (eixo.getLinhaLimite() != null && eixo.getLinhaLimite().isHabilitado()) {
            axis.addLimitLine(linhaLimite(eixo.getLinhaLimite()));
        }

        axis.setAxisMaximum(eixo.getValorMaximo());
        axis.setAxisMinimum(eixo.getValorMinimo());


    }

    private LimitLine linhaLimite(LinhaLimite linha) {
        LimitLine line = new LimitLine(linha.getValorLimite(), linha.getDescricao().getTexto());

        line.setTextSize(linha.getDescricao().getTamanhoFonte());
        line.setLineWidth(linha.getEspessuraLinha().getValor());
        line.setLineColor(linha.getCorLinha());

        switch (linha.getTipoPontilhado()) {
            case CURTO:
                line.enableDashedLine(5f, 10f, 0);
                break;
            case MEDIO:
                line.enableDashedLine(10f, 10f, 0);
                break;
            case LONGO:
                line.enableDashedLine(30f, 10f, 0);
                break;
            case NENHUM:
                line.enableDashedLine(100f, 0f, 0);
            default:
                break;
        }
        switch (linha.getTipoPosicaoDescricao()) {
            case LEFT_TOP:
                line.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
                break;
            case RIGHT_TOP:
                line.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                break;
            case LEFT_BOTTOM:
                line.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
                break;
            case RIGHT_BOTTOM:
                line.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                break;
            default:
                break;
        }

        return line;
    }

    private Description initDescricao(Grafico grafico) {
        Description description = new Description();
        if (grafico.getDescricaoGrafico().isHabilitar()) {
            description.setEnabled(true);
            description.setText(grafico.getDescricaoGrafico().getTexto());

            Paint.Align align;
            switch (grafico.getDescricaoGrafico().getAlinhamento()) {
                case RIGHT:
                    align = Paint.Align.RIGHT;
                    break;
                case LEFT:
                    align = Paint.Align.LEFT;
                    break;
                default:
                    align = Paint.Align.CENTER;
            }
            description.setTextAlign(align);

            description.setTextSize(grafico.getDescricaoGrafico().getTamanhoFonte());
            description.setTextColor(grafico.getDescricaoGrafico().getCor());
            description.setXOffset(grafico.getDescricaoGrafico().getPosicaoEixoX());
            description.setYOffset(grafico.getDescricaoGrafico().getPosicaoEixoY());
        } else {
            description.setEnabled(false);
        }
        return description;
    }

    private void ajustarGraficoBarraEixoY(BarChart barra) {
        float maxValor = Float.MIN_VALUE;
        if (barra.getData() != null &&
                barra.getData().getDataSetCount() > 0) {

            for (IBarDataSet ds : barra.getData().getDataSets()) {
                BarDataSet set = (BarDataSet) ds;
                for (BarEntry be : set.getValues()) {
                    if (maxValor < be.getY()) {
                        maxValor = be.getY();
                    }
                }
            }

            barra.getAxisLeft().setAxisMaximum(maxValor + maxValor * 0.2f);
            barra.getAxisLeft().setAxisMaximum(maxValor + maxValor * 0.2f);
        }
    }

    private void ajustarGraficoLinhaEixos(LineChart line) {
        float maxValorY = Float.MIN_VALUE;
        float maxValorX = Float.MIN_VALUE;
        if (line.getData() != null &&
                line.getData().getDataSetCount() > 0) {

            for (ILineDataSet ds : line.getData().getDataSets()) {
                LineDataSet set = (LineDataSet) ds;
                for (Entry be : set.getValues()) {
                    if (maxValorY < be.getY()) {
                        maxValorY = be.getY();
                    }
                    if (maxValorX < be.getX()) {
                        maxValorX = be.getX();
                    }
                }
            }
            line.getAxisLeft().setAxisMaximum(maxValorY + maxValorY * 0.2f);
            line.getAxisRight().setAxisMaximum(maxValorY + maxValorY * 0.2f);

            line.getXAxis().setAxisMaximum(maxValorX + 3);
        }
    }


    private LineData initDataLine(Grafico grafico) {
        List<ConjutoDado> dados = grafico.getConjutoDados();
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        int hashUltimo = 0;

        for (ConjutoDado conjutoDado : dados) {

            ArrayList<Entry> valores = new ArrayList<>();
            Set<Integer> coresItem = new HashSet<>();
            Set<Integer> coresTexto = new HashSet<>();

            for (Dado dado : conjutoDado.getListaValores()) {
                coresItem.add(dado.getCorItem());
                coresTexto.add(dado.getCorTexto());
                valores.add(new Entry(dado.getValorX(), dado.getValorY(), new Integer(dado.hashCode())));
                hashUltimo = dado.hashCode();
            }

            conjutoDado.getTipoFormatacao();

            LineDataSet lineDataSet = new LineDataSet(valores, conjutoDado.getDescricaoConjunto());
            lineDataSet.setColors(new ArrayList<Integer>(coresItem));
            lineDataSet.setValueTextColors(new ArrayList<Integer>(coresTexto));

            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            lineDataSet.setDrawValues(conjutoDado.isExibirValores());
            lineDataSet.setValueTextSize(conjutoDado.getTamanhoTextoItens());

            lineDataSet.setForm(Legend.LegendForm.CIRCLE);
            lineDataSet.setFormSize(grafico.getEstilo().getTamanhoTextoLegendas().getValor());

            lineDataSet.setValueFormatter(new ValueFormatter(hashUltimo, conjutoDado.getTipoFormatacao(), ""));

            lineDataSet.setDrawValues(true);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setDrawFilled(false);
            lineDataSet.setDrawVerticalHighlightIndicator(true);
            lineDataSet.setDrawCircles(false);


            dataSets.add(lineDataSet);
        }

        return new LineData(dataSets);
    }


    private BarData initDataBar(Grafico grafico) {
        List<ConjutoDado> dados = grafico.getConjutoDados();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        int hashUltimo = 0;
        for (ConjutoDado conjutoDado : dados) {

            ArrayList<BarEntry> valores = new ArrayList<>();
            Set<Integer> coresItem = new HashSet<>();
            Set<Integer> coresTexto = new HashSet<>();

            for (Dado dado : conjutoDado.getListaValores()) {
                coresItem.add(dado.getCorItem());
                coresTexto.add(dado.getCorTexto());
                valores.add(new BarEntry(dado.getValorX(), dado.getValorY(), new Integer(dado.hashCode())));
                hashUltimo = dado.hashCode();
            }

            conjutoDado.getTipoFormatacao();

            BarDataSet barDataSet = new BarDataSet(valores, conjutoDado.getDescricaoConjunto());
            barDataSet.setColors(new ArrayList<>(coresItem));
            barDataSet.setValueTextColors(new ArrayList<>(coresTexto));

            barDataSet.setDrawValues(true);
            barDataSet.setValueTextSize(conjutoDado.getTamanhoTextoItens());

            barDataSet.setForm(Legend.LegendForm.CIRCLE);
            barDataSet.setFormSize(grafico.getEstilo().getTamanhoTextoLegendas().getValor());

            barDataSet.setValueFormatter(new ValueFormatter(hashUltimo, conjutoDado.getTipoFormatacao(), ""));

            //barDataSet.setStackLabels(new String[]{"Testem","outrom" });

            dataSets.add(barDataSet);


        }

        BarData data = new BarData(dataSets);
        data.setBarWidth(grafico.getLarguraBarra());


        // Isso sobrepoe os valores dos DataSet
        //data.setValueTextSize(grafico.getDescricaoGrafico().getTamanhoFonte());
        //data.setValueTextColor(grafico.getDescricaoGrafico().getCor());
        //data.setValueTypeface(Typeface.DEFAULT);

        return data;
    }

    private void updateDataLine(LineChart line, int indiceLinha, List<Float> valores) {
        int indexValores = 0;
        if (line.getData() != null && line.getData().getDataSetCount() > 0) {
            LineDataSet linhaDataSet = (LineDataSet) line.getData().getDataSetByIndex(indiceLinha);

            for (Entry entry : linhaDataSet.getValues()) {
                if (indexValores < valores.size()) {
                    entry.setY(valores.get(indexValores));
                    indexValores++;
                }
            }

            if (indexValores > 0) {
                line.getData().notifyDataChanged();
                line.notifyDataSetChanged();
                ajustarGraficoLinhaEixos(line);
            }
            //bar.invalidate();

            line.animateY(2000);
        }
    }

    private void updateDataBar(BarChart bar, int indiceBarra, float[] valores) {
        int indexValores = 0;
        if (bar.getData() != null && bar.getData().getDataSetCount() > 0) {
            BarDataSet barDataSet = (BarDataSet) bar.getData().getDataSetByIndex(indiceBarra);

            for (Entry entry : barDataSet.getValues()) {
                if (indexValores < valores.length) {
                    entry.setY(valores[indexValores]);
                    indexValores++;
                }
            }

            if (indexValores > 0) {
                bar.getData().notifyDataChanged();
                bar.notifyDataSetChanged();
                ajustarGraficoBarraEixoY(bar);
            }
            //bar.invalidate();

            bar.animateY(2000);
        }
    }

    private BarChart criarGraficoBarraVertical(BarChart gBarra, int alt, Grafico grafico) {
        if(gBarra == null){
            gBarra = new BarChart(this);
        }

        gBarra.setId(View.generateViewId());
        gBarra.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, alt));

        gBarra.setDescription(initDescricao(grafico));

        //Habilitando interação com o grafico
        gBarra.setPinchZoom(grafico.isHabilitarZoom());
        gBarra.setDoubleTapToZoomEnabled(grafico.isHabilitarZoom());
        gBarra.setTouchEnabled(grafico.isHabilitarToque());
        gBarra.setDragEnabled(grafico.isHabilitarArrastar());

        //Inicializando os eixos do grafico
        initEixo(grafico.getTipo(), grafico.getEixoX(), gBarra.getXAxis());

        if (TipoEixo.EIXO_Y_DIREITA.equals(grafico.getEixoY().getTipoEixo())) {
            initEixo(grafico.getTipo(),grafico.getEixoY(), gBarra.getAxisRight());
            gBarra.getAxisLeft().setEnabled(false);
        } else {
            initEixo(grafico.getTipo(),grafico.getEixoY(), gBarra.getAxisLeft());
            gBarra.getAxisRight().setEnabled(false);
        }

        gBarra.setData(initDataBar(grafico));

        gBarra.setFitBars(true);
        if (grafico.getEstilo().isHabilitarLegendas()) {
            gBarra.getLegend().setEnabled(true);
            gBarra.getLegend().setTextSize(grafico.getEstilo().getTamanhoTextoLegendas().getValor());
            gBarra.getLegend().setTextColor(grafico.getEstilo().getCorLegendas());
        } else {
            gBarra.getLegend().setEnabled(false);
        }

        gBarra.setDrawBorders(grafico.getEstilo().isExibirBordas());
        //gBarra.setBorderColor(grafico.getEstilo().getCorDaBorda());
        //gBarra.setBorderWidth(grafico.getEstilo().getEspessuraBorda().getValor());

        //gBarra.setDrawGridBackground(grafico.getEstilo().isExibirGradeDeFundo());
        //gBarra.setGridBackgroundColor(grafico.getEstilo().getCorDeFundoGrade());
        //gBarra.setBackgroundColor(grafico.getEstilo().getCorDeFundo()) ;

        gBarra.animateX(grafico.getTipoAnimacao().getValor());
        gBarra.animateY(grafico.getTipoAnimacao().getValor());

        gBarra.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        return gBarra;
    }

    private LineChart criarGraficoLinha(int alt, Grafico grafico) {
        LineChart line = new LineChart(this);
        line.setId(View.generateViewId());
        line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, alt));

        line.setDescription(initDescricao(grafico));

        //Habilitando interação com o grafico
        line.setPinchZoom(grafico.isHabilitarZoom());
        line.setDoubleTapToZoomEnabled(grafico.isHabilitarZoom());
        line.setTouchEnabled(grafico.isHabilitarToque());
        line.setDragEnabled(grafico.isHabilitarArrastar());

        //Inicializando os eixos do grafico
        initEixo(grafico.getTipo(), grafico.getEixoX(), line.getXAxis());

        if (TipoEixo.EIXO_Y_DIREITA.equals(grafico.getEixoY().getTipoEixo())) {
            initEixo(grafico.getTipo(),grafico.getEixoY(), line.getAxisRight());
            line.getAxisLeft().setEnabled(false);
        } else {
            initEixo(grafico.getTipo(),grafico.getEixoY(), line.getAxisLeft());
            line.getAxisRight().setEnabled(false);
        }

        line.setData(initDataLine(grafico));

        if (grafico.getEstilo().isHabilitarLegendas()) {
            line.getLegend().setEnabled(true);
            line.getLegend().setTextSize(grafico.getEstilo().getTamanhoTextoLegendas().getValor());
            line.getLegend().setTextColor(grafico.getEstilo().getCorLegendas());
        } else {
            line.getLegend().setEnabled(false);
        }

        line.setDrawBorders(grafico.getEstilo().isExibirBordas());
        //gBarra.setBorderColor(grafico.getEstilo().getCorDaBorda());
        //gBarra.setBorderWidth(grafico.getEstilo().getEspessuraBorda().getValor());

        //gBarra.setDrawGridBackground(grafico.getEstilo().isExibirGradeDeFundo());
        //gBarra.setGridBackgroundColor(grafico.getEstilo().getCorDeFundoGrade());
        //gBarra.setBackgroundColor(grafico.getEstilo().getCorDeFundo()) ;

        line.animateX(grafico.getTipoAnimacao().getValor());
        line.animateY(grafico.getTipoAnimacao().getValor());
        line.setAutoScaleMinMaxEnabled(true);

        return line;
    }

    private HorizontalBarChart criarGraficoBarraHorizontal(int alt, Grafico grafico) {
        HorizontalBarChart gBarra = new HorizontalBarChart(this);
        gBarra.setId(View.generateViewId());
        gBarra.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, alt));

        gBarra.setDescription(initDescricao(grafico));

        //Habilitando interação com o grafico
        gBarra.setPinchZoom(grafico.isHabilitarZoom());
        gBarra.setDoubleTapToZoomEnabled(grafico.isHabilitarZoom());
        gBarra.setTouchEnabled(grafico.isHabilitarToque());
        gBarra.setDragEnabled(grafico.isHabilitarArrastar());

        //Inicializando os eixos do grafico
        initEixo(grafico.getTipo(), grafico.getEixoX(), gBarra.getXAxis());

        if (TipoEixo.EIXO_Y_DIREITA.equals(grafico.getEixoY().getTipoEixo())) {
            initEixo(grafico.getTipo(),grafico.getEixoY(), gBarra.getAxisRight());
            gBarra.getAxisLeft().setEnabled(false);
        } else {
            initEixo(grafico.getTipo(),grafico.getEixoY(), gBarra.getAxisLeft());
            gBarra.getAxisRight().setEnabled(false);
        }

        gBarra.setData(initDataBar(grafico));

        gBarra.setFitBars(true);
        if (grafico.getEstilo().isHabilitarLegendas()) {
            gBarra.getLegend().setEnabled(true);
            gBarra.getLegend().setTextSize(grafico.getEstilo().getTamanhoTextoLegendas().getValor());
            gBarra.getLegend().setTextColor(grafico.getEstilo().getCorLegendas());
        } else {
            gBarra.getLegend().setEnabled(false);
        }

        gBarra.setDrawBorders(grafico.getEstilo().isExibirBordas());
        //gBarra.setBorderColor(grafico.getEstilo().getCorDaBorda());
        //gBarra.setBorderWidth(grafico.getEstilo().getEspessuraBorda().getValor());

        //gBarra.setDrawGridBackground(grafico.getEstilo().isExibirGradeDeFundo());
        //gBarra.setGridBackgroundColor(grafico.getEstilo().getCorDeFundoGrade());
        //gBarra.setBackgroundColor(grafico.getEstilo().getCorDeFundo()) ;

        gBarra.animateX(grafico.getTipoAnimacao().getValor());
        gBarra.animateY(grafico.getTipoAnimacao().getValor());

        gBarra.setDrawValueAboveBar(false);
        return gBarra;
    }

    public PieData initDataPie(Grafico gp){
        PieData pieDados;
        ArrayList<PieEntry> entries = new ArrayList<>();

        List<Integer> cores = new ArrayList<>();
        for(ConjutoDado conjDado:gp.getConjutoDados()){
            for(Dado dado:conjDado.getListaValores()){
                entries.add(new PieEntry(dado.getValorX(),dado.getDescricaoValor()));
                cores.add(dado.getCorItem());
            }
        }

        PieDataSet item = new PieDataSet(entries,"");

        item.setColors(cores);

        item.setSelectionShift(gp.getTamanhoDestaquePorcaoSelecionadaPizza());

        pieDados = new PieData();
        pieDados.setDataSet(item);

        return pieDados;
    }

    private PieChart criarGraficoPizza(int alt, Grafico gp) {
        PieChart pie = new PieChart(this);
        pie.setId(View.generateViewId());
        pie.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (alt/2)+alt));

        pie.setDescription(initDescricao(gp));

        //dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        pie.animateY(gp.getTipoAnimacao().getValor());
        pie.setData(initDataPie(gp));

        // enable hole and configure
        pie.setDrawHoleEnabled(gp.isHabilitarBuracoCentroPizza());
        pie.setHoleColor(gp.getCorCentroPizza());
        pie.setHoleRadius(gp.getPorcentagemRaioCentroPizza());
        //pie.setTransparentCircleRadius(100);

        pie.setHighlightPerTapEnabled(gp.isHabilitarDestaqueAoToquePizza());
        //pie.setMaxHighlightDistance(100);

        //pie.setPadding
        //lPieChart.setOnChartValueSelectedListener(O);

        pie.setCenterText(gp.getTextoCentroPizza().getTexto());
        pie.setCenterTextColor(gp.getTextoCentroPizza().getCor());
        pie.setCenterTextSize(gp.getTextoCentroPizza().getTamanhoFonte());
        pie.setDrawCenterText(gp.getTextoCentroPizza().isHabilitar());
        pie.setCenterTextOffset(gp.getTextoCentroPizza().getPosicaoEixoX(),gp.getTextoCentroPizza().getPosicaoEixoY());
        //pie.setCenterTextTypeface();
        //pie.setCenterTextRadiusPercent();

        //lPieChart.setOnClickListener(new OnChartValueSelectedListener());
        //pie.setCenterTextRadiusPercent(40);
        pie.setDrawEntryLabels(gp.getConjutoDados().get(0).isExibirValores());
        //
        pie.setRotationEnabled(gp.isHabilitarGirarAoToque());
        // pie.setScaleX(10);
        pie.setBackgroundColor(gp.getEstilo().getCorDeFundo());
        //Exibe %
        pie.setUsePercentValues(gp.isHabilitarValoresPorPorcentagemPizza());
        return pie;
    }

    private void testeJson(){
        try {

            final Gson gson = new Gson();
// original object instantiation
            Grafico modeloBarraHorizontal = Conversor.montaGraficoBarraHorizontal(null);
            Grafico modeloBarraVertical = Conversor.montaGraficoBarraVertical(null);
            Grafico modeloLinha = Conversor.montaGraficoLinha(null);
            Grafico modeloPizza = Conversor.montaGraficoPizza(null);

            //Object to Json
            String jsonBH = gson.toJson(modeloBarraHorizontal);
            //Log.d(TAG2,"Converted jsonBH string is : " + jsonBH);
            //Object to Json
            String jsonBV = gson.toJson(modeloBarraVertical);
            //Log.d(TAG2,"Converted jsonBV string is : " + jsonBV);

            //Object to Json
            String jsonL = gson.toJson(modeloLinha);
            System.out.println("Converted Java object : " + jsonL);
            //Log.d(TAG2,"Converted jsonL string is : " + jsonL);

            //Object to Json
            String jsonP = gson.toJson(modeloPizza);
            //Log.d(TAG2,"Converted jsonP string is : " + jsonP);

            //Json to Object
            //ModelObject modelObject1 = gson.fromJson(json, ModelObject.class);
            //System.out.println("Converted Java object : " + modelObject1);
        } catch (Exception e ){
            e.printStackTrace();
        }
    }

    private void montarGrafico(){
        Log.d(TAG2,"montarGrafico");




    }
    private void atualizarGrafico(){}

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_dinamico);

        Parametros p = new Parametros(TipoGrafico.BARRA_HORIZONTAL,false);
        new ConexaoObterGrafico().execute(p);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_grafico_dinamico);

        testeJson();

        int alt = alturaTela() / 3;
        int altSlider = alturaTela() / 10;
        List<Grafico> lista = new ArrayList<>();
        try {
            Grafico gbv = Conversor.montaGraficoBarraVertical(new JSONObject());
            Grafico gbh = Conversor.montaGraficoBarraHorizontal(new JSONObject());
            Grafico gl = Conversor.montaGraficoLinha(new JSONObject());
            Grafico gp = Conversor.montaGraficoPizza(new JSONObject());

            barVChart = criarGraficoBarraVertical(null,alt, gbv);
            barHChart = criarGraficoBarraHorizontal(alt, gbh);
            lineChart = criarGraficoLinha(alt, gl);
            pieChart = criarGraficoPizza(alt, gp);

            barVChart.setVisibility(View.GONE);
            barHChart.setVisibility(View.GONE);
            lineChart.setVisibility(View.GONE);
            pieChart.setVisibility(View.GONE);

            lista.add(gbv);
            lista.add(gl);
            lista.add(gbh);
            lista.add(gp);

            seekBar = (SeekBar) criarSlider(altSlider);

            linearLayout.addView(opcoesGrafico(altSlider, lista));
            linearLayout.addView(barVChart);
            linearLayout.addView(lineChart);
            linearLayout.addView(barHChart);
            linearLayout.addView(pieChart);
           /* linearLayout.addView(barHChart);
            linearLayout.addView(lineChart);
            linearLayout.addView(pieChart);
*/
            linearLayout.addView(seekBar);
            //linearLayout.addView(criarGraficoBarraVertical(lista.get(0)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float calculoBarra(BigDecimal valorAporte, BigDecimal valorMensal, int anoSaida) {
        float retorno = 1000f + (10f * anoSaida);
        return retorno;
    }

    private List<Float> calculoLinha(BigDecimal valorAporte, BigDecimal valorMensal, int anoSaida) {
        List<Float> retorno = new ArrayList<>();

        retorno.add(1f);

        float p2 = 2.00f;
        for (int i = 2; i < 11; i++) {
            retorno.add(p2);
            p2 += p2 + (anoSaida / 10);
        }

        return retorno;
    }


    //Formatadores

    public class ValueAxisFormatter implements IAxisValueFormatter {
        private NumberFormat nf;
        private Locale local;
        private TipoFormatacao tipoFormatacao;
        private String formatacao;

        /**
         * @param local
         * @param tipoFormatacao -Enum com os tipos de formatacao aceitos
         * @param formatacao     - formatacao usada qdo o tipo de formatacao for REGEX a mesma usada no String.format
         */
        public ValueAxisFormatter(Locale local, TipoFormatacao tipoFormatacao, String formatacao) {
            this.local = local;
            this.nf = NumberFormat.getCurrencyInstance(this.local);
            this.tipoFormatacao = tipoFormatacao;
            this.formatacao = formatacao;
        }

        /**
         * @param tipoFormatacao -Enum com os tipos de formatacao aceitos
         * @param formatacao     - formatacao usada qdo o tipo de formatacao for REGEX a mesma usada no String.format
         */
        public ValueAxisFormatter(TipoFormatacao tipoFormatacao, String formatacao) {
            this.local = new Locale("pt", "BR");
            this.nf = NumberFormat.getCurrencyInstance(this.local);

            this.tipoFormatacao = tipoFormatacao;
            this.formatacao = formatacao;
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            switch (tipoFormatacao) {
                case NUMERICO_COM_DECIMAL:
                    return String.format(local, "%.2f", value);
                case NUMERICO_SEM_DECIMAL:
                    return String.format(local, "%d", value);
                case MONETARIO:
                    return nf.format(value);
                case REGEX:
                    return String.format(local, formatacao, value);
                case SEM_FORMATACAO:
                default:
                    return String.valueOf(value);
            }
        }
    }

    public class ValueFormatter implements IValueFormatter {

        private NumberFormat nf;
        private Locale local;
        private Integer hashEntryValorExibir;
        private TipoFormatacao tipoFormatacao;
        private String formatacao;

        /**
         * @param local
         * @param hashEntryValorExibir - hash para identificacao para exibir apenas esse Entry pelo hash
         * @param tipoFormatacao       -Enum com os tipos de formatacao aceitos
         * @param formatacao           - formatacao usada qdo o tipo de formatacao for REGEX a mesma usada no String.format
         */
        public ValueFormatter(Locale local, int hashEntryValorExibir, TipoFormatacao tipoFormatacao, String formatacao) {
            this.local = local;

            this.hashEntryValorExibir = hashEntryValorExibir;
            this.tipoFormatacao = tipoFormatacao;
            this.formatacao = formatacao;
        }

        /**
         * @param hashEntryValorExibir - hash para identificacao para exibir apenas esse Entry pelo hash
         * @param tipoFormatacao       -Enum com os tipos de formatacao aceitos
         * @param formatacao           - formatacao usada qdo o tipo de formatacao for REGEX a mesma usada no String.format
         */
        public ValueFormatter(int hashEntryValorExibir, TipoFormatacao tipoFormatacao, String formatacao) {
            this.local = new Locale("pt", "BR");
            this.nf = NumberFormat.getCurrencyInstance(this.local);
            this.hashEntryValorExibir = hashEntryValorExibir;
            this.tipoFormatacao = tipoFormatacao;
            this.formatacao = formatacao;
        }

        /**
         * @param local
         * @param tipoFormatacao -Enum com os tipos de formatacao aceitos
         * @param formatacao     - formatacao usada qdo o tipo de formatacao for REGEX a mesma usada no String.format
         */
        public ValueFormatter(Locale local, TipoFormatacao tipoFormatacao, String formatacao) {
            this.local = local;
            this.nf = NumberFormat.getCurrencyInstance(this.local);
            this.hashEntryValorExibir = null;
            this.tipoFormatacao = tipoFormatacao;
            this.formatacao = formatacao;
        }

        /**
         * @param tipoFormatacao -Enum com os tipos de formatacao aceitos
         * @param formatacao     - formatacao usada qdo o tipo de formatacao for REGEX a mesma usada no String.format
         */
        public ValueFormatter(TipoFormatacao tipoFormatacao, String formatacao) {
            this.local = new Locale("pt", "BR");
            this.nf = NumberFormat.getCurrencyInstance(this.local);
            this.hashEntryValorExibir = null;
            this.tipoFormatacao = tipoFormatacao;
            this.formatacao = formatacao;
        }


        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here

            //So vai exibir o valor indicado
            if (entry != null && entry.getData() != null && hashEntryValorExibir != null
                    && entry.getData() instanceof Integer
                    && !((Integer) entry.getData()).equals(hashEntryValorExibir)) {
                return "";
            }

            switch (tipoFormatacao) {
                case NUMERICO_COM_DECIMAL:
                    return String.format(local, "%.2f", value);
                case NUMERICO_SEM_DECIMAL:
                    return String.format(local, "%d", value);
                case MONETARIO:
                    return nf.format(value);
                case REGEX:
                    return String.format(local, formatacao, value);
                case SEM_FORMATACAO:
                default:
                    return String.valueOf(value);
            }
        }
    }


    //Listenner Seekbar
    public class SeekBarListenner implements SeekBar.OnSeekBarChangeListener {

        private BarChart lBbarChart;
        private HorizontalBarChart lBarHChart;
        private LineChart lLineChart;
        private PieChart lPieChart;


        public SeekBarListenner(BarChart barChart, HorizontalBarChart barHChart, LineChart lineChart, PieChart pieChart) {
            this.lBbarChart = barChart;
            this.lBarHChart = barHChart;
            this.lLineChart = lineChart;
            this.lPieChart = pieChart;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            int progress = progresValue;
            int stepSize = 10;
            progress = ((int) Math.round(progress / stepSize)) * stepSize;
            seekBar.setProgress(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //textView.setText("Covered: " + progress + "/" + seekBar.getMax());
            // TODO colocar uma chamada ao servico aqui

            if (lBbarChart != null && lBbarChart.getVisibility() == View.VISIBLE) {
                float valor = calculoBarra(BigDecimal.ZERO, BigDecimal.ZERO, seekBar.getProgress());
                float valores[] = {0f, valor};
                updateDataBar(lBbarChart, 1, valores);
            }
            if (lBarHChart != null && lBarHChart.getVisibility() == View.VISIBLE) {
                float valor = calculoBarra(BigDecimal.ZERO, BigDecimal.ZERO, seekBar.getProgress());
                float valores[] = {0f, valor};
                updateDataBar(lBarHChart, 1, valores);
            }

            if (lLineChart != null && lLineChart.getVisibility() == View.VISIBLE) {
                updateDataLine(lLineChart, 1, calculoLinha(BigDecimal.ZERO, BigDecimal.ZERO, seekBar.getProgress()));

            }
        }
    }

    public class ConexaoObterGrafico extends AsyncTask<Parametros, Void, Grafico> {
        @Override
        protected  Grafico doInBackground(Parametros... params) {
            try {
                final String url = params[0].urlRequisicao();
                // colocar a requisao HTTP aqui e a transf em json em obj
                Grafico g = new Grafico(TipoGrafico.BARRA_HORIZONTAL,"teste");

                JSONObject json = null;
                try {
                    json = getJSONObjectFromURL(url);

                    if(json != null){

                        Gson gson=new GsonBuilder().create();

                        g = gson.fromJson(json.toString(),Grafico.class);
                        Log.d(TAG2,"Texto Desc-->"+g.getDescricaoGrafico().getTexto());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return g;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Grafico grafico) {
            if(grafico != null ) {
                int alt = alturaTela() / 2;
                barVChart = criarGraficoBarraVertical(barVChart,alt,grafico);
                barVChart.setVisibility(View.VISIBLE);

                barVChart.getData().notifyDataChanged();
                barVChart.notifyDataSetChanged();
            }
        }
    }

    public class ConexaoCalculoGrafico extends AsyncTask<Parametros, Void, List<ConjutoDado>> {
        @Override
        protected  List<ConjutoDado> doInBackground(Parametros... params) {
            try {
                final String url = params[0].urlRequisicao();
                // colocar a requisao HTTP aqui e a transf em json em obj
                return new ArrayList<>();
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ConjutoDado> dados) {
            if(dados != null ) {
                atualizarGrafico();
            }
        }
    }

}