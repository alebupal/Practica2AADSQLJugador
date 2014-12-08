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
public class AdaptadorCursorPartido extends CursorAdapter{

    private TextView tvNombre, tvValoracion, tvContrincante;

    public AdaptadorCursorPartido(Context context, Cursor cursor) {
        super(context, cursor ,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup vg) {
        LayoutInflater i = LayoutInflater.from(vg.getContext());
        View v = i.inflate(R.layout.lista_detalle_partido, vg, false);
        return v;
    }




    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        GestorPartido gp=new GestorPartido(context);
        Partido p = gp.getRow(cursor);


        GestorJugador gj=new GestorJugador(context);
        gj.open();

        tvNombre=(TextView) view.findViewById(R.id.tvNombre);
        tvValoracion=(TextView) view.findViewById(R.id.tvFnac);
        tvContrincante=(TextView) view.findViewById(R.id.tvContrincante);

        tvNombre.setText(gj.getRowID(p.getIdJugador()).getNombre());
        tvValoracion.setText(p.getValoracion()+"");
        tvContrincante.setText(p.getContrincante());
    }


}
