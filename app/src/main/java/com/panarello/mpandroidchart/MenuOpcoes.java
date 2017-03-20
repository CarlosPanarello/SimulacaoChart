package com.panarello.mpandroidchart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuOpcoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opcoes);
/*
        botao2 = (Button) findViewById(R.id.botao2);
        botao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,InputPieChart.class);
                //i.putExtra("Chave",valor);
                startActivity(i);
            }
        });*/
    }

    public void irGraficoFixo(View view){
        Intent i = new Intent(MenuOpcoes.this,GraficoFixoActivity.class);
        startActivity(i);
    }

    public void irGraficoDinamico(View view){
        Intent i = new Intent(MenuOpcoes.this,GraficoDinamico.class);
        startActivity(i);
    }

    public void irGraficoDinamicoRest(View view){
        Intent i = new Intent(MenuOpcoes.this,GraficoDinamico.class);
        startActivity(i);
    }

    public void irGraficoPizzaInputSpinner(View view){
        Intent i = new Intent(MenuOpcoes.this,InputPieChart.class);
        startActivity(i);
    }

    public void irGraficoPizzaEditText(View view){
        Intent i = new Intent(MenuOpcoes.this,GraficoPizzaText.class);
        startActivity(i);
    }
}
