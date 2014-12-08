package com.example.alejandro.practica2aadsqljugador;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by Alejandro on 07/12/2014.
 */
public class AdaptadorCursorJugador extends CursorAdapter {

    private TextView tvNombre, tvTelefono, tvMedia, tvFnac;


    public AdaptadorCursorJugador(Context context, Cursor c) {
        super(context, c,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.lista_detalle_jugador, vg, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        GestorJugador gj=new GestorJugador(context);
        GestorPartido gp=new GestorPartido(context);
        Jugador j;
        j=gj.getRow(cursor);

        tvNombre=(TextView) view.findViewById(R.id.tvNombre);
        tvTelefono=(TextView) view.findViewById(R.id.tvTelefono);
        tvFnac=(TextView) view.findViewById(R.id.tvFnac);
        tvMedia=(TextView) view.findViewById(R.id.tvMedia);

        tvNombre.setText(j.getNombre());
        tvTelefono.setText(j.getTelefono());
        tvFnac.setText(j.getFnac());

        gp.open();
        tvMedia.setText(gp.getMediaID(j.getId()).toString());
    }

}
