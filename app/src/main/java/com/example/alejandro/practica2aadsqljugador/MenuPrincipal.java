package com.example.alejandro.practica2aadsqljugador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MenuPrincipal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void verPartido(View v){
        Intent i = new Intent(this, VerPartido.class);
        startActivity(i);



    }
    public void verJugador(View v){
        Intent i = new Intent(this, VerJugador.class);
        startActivity(i);
    }


}
