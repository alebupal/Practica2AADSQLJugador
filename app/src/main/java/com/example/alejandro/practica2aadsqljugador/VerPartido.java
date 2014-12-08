package com.example.alejandro.practica2aadsqljugador;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


public class VerPartido extends Activity {
    private GestorPartido gp;
    private GestorJugador gj;
    private AdaptadorCursorPartido acp;
    private ListView lv;
    private long idJugador = 0;
    private int posicionSpinner;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        if (id == R.id.action_borrar) {
            borrar(index);
        } else if (id == R.id.action_editar) {
            editar(index);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_partido);
        gp = new GestorPartido(this);
        gj = new GestorJugador(this);
        lv = (ListView) findViewById(R.id.lvPartido);
        registerForContextMenu(lv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuAnadir) {
            return anadir();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onPause() {
        super.onPause();
        gp.close();
    }

    @Override
    protected void onResume() {
        gp.open();
        super.onResume();
        final ListView lv = (ListView) findViewById(R.id.lvPartido);
        Cursor c = gp.getCursor(null, null, null);
        acp = new AdaptadorCursorPartido(this, c);
        lv.setAdapter(acp);
    }

    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean anadir() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.menuAnadir));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.anadir_partido, null);
        alert.setView(vista);

        final Spinner spinnerNombre = (Spinner)vista.findViewById(R.id.spinnerAnadirNombre);
        List<Jugador> alj;
        gj.open();
        alj = gj.select();
        ArrayAdapter<Jugador> dataAdapter = new ArrayAdapter<Jugador>(this, android.R.layout.simple_spinner_item, alj);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNombre.setAdapter(dataAdapter);

        spinnerNombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                idJugador = ((Jugador)parent.getItemAtPosition(pos)).getId();
                tostada(idJugador+"");

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int whichButton) {
                final EditText etAnadirValoracion, etAnadirContrincante;
                final String valoracion,contrincante;

                etAnadirValoracion=(EditText)vista.findViewById(R.id.etAnadirValoracion);
                etAnadirContrincante=(EditText)vista.findViewById(R.id.etAnadirContrincante);

                valoracion=etAnadirValoracion.getText().toString();
                contrincante=etAnadirContrincante.getText().toString();

                if(etAnadirValoracion.getText().toString().equals("")==true || etAnadirContrincante.getText().toString().equals("")==true){
                    tostada(getString(R.string.vacio));
                    Log.v("id",idJugador+"");
                }else{

                    Partido j=new Partido(Integer.parseInt(valoracion),contrincante,idJugador);
                    if(comprobar(j)==true){
                        long id=gp.insert(j);
                        acp.getCursor().close();
                        acp.changeCursor(gp.getCursor(null,null,null));
                        tostada(getString(R.string.jugadorInsertado));
                    }else{
                        tostada(getString(R.string.repetido));
                    }

                }
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }

    private boolean borrar(final int pos) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.menuEliminar));
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Cursor c=(Cursor)lv.getItemAtPosition(pos); //El elemento del cursor que estas haciendo click
                Partido p=GestorPartido.getRow(c);
                gp.delete(p);
                acp.changeCursor(gp.getCursor(null,null,null));
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }

    private boolean editar(final int index) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.menuEditar));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.editor_partido, null);
        alert.setView(vista);

        Cursor c=(Cursor)lv.getItemAtPosition(index);
        final Partido p=GestorPartido.getRow(c);

        final EditText etEditarValoracion, etEditarContrincante;

        etEditarValoracion=(EditText)vista.findViewById(R.id.etEditarValoracion);
        etEditarContrincante=(EditText)vista.findViewById(R.id.etEditarContrincante);




        final Spinner spinnerNombre = (Spinner)vista.findViewById(R.id.spinnerEditarPartido);
        List<Jugador> alj;
        gj.open();
        alj = gj.select();
        ArrayAdapter<Jugador> dataAdapter = new ArrayAdapter<Jugador>(this, android.R.layout.simple_spinner_item, alj);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNombre.setAdapter(dataAdapter);



       for (int i = 0; i <alj.size() ; i++) {
            if(gp.getRow(p.getId()).getIdJugador()==alj.get(i).getId()){
                posicionSpinner=i;
            }
        }
        spinnerNombre.setSelection(posicionSpinner);

        spinnerNombre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                idJugador = ((Jugador) parent.getItemAtPosition(pos)).getId();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        etEditarValoracion.setText(gp.getRow(p.getId()).getValoracion()+"");
        etEditarContrincante.setText(gp.getRow(p.getId()).getContrincante());

        alert.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Partido p2 = new Partido();
                p2.setValoracion(Integer.parseInt(etEditarValoracion.getText().toString()));
                p2.setContrincante(etEditarContrincante.getText().toString());
                p2.setId(p.getId());
                p2.setIdJugador(idJugador);

                if(etEditarValoracion.getText().toString().equals("")==true || etEditarContrincante.getText().toString().equals("")==true ){
                    tostada(getString(R.string.vacio));
                }else{
                    if(comprobar(p2)==true){
                        gp.update(p2);
                        acp.changeCursor(gp.getCursor(null, null, null));
                        tostada(getString(R.string.jugadorEditado));
                    }else{
                        tostada(getString(R.string.repetido));
                    }

                }
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }

    public boolean comprobar(Partido p2){
        Partido p1;
        List<Partido> list =gp.select();
        for (int i=0;i<list.size();i++){
            p1=list.get(i);
            if(p1.getContrincante().compareToIgnoreCase(p2.getContrincante())==0 && p1.getIdJugador()==p2.getIdJugador()){
                return false;
            }
        }
        return true;
    }
}




