package com.example.alejandro.practica2aadsqljugador;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class VerJugador extends Activity {

    private GestorJugador gj;
    private GestorPartido gp;
    private AdaptadorCursorJugador acj;
    private ListView lv;
    private long idJugador=0;


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
        setContentView(R.layout.activity_ver_jugador);
        gj=new GestorJugador(this);
        gp=new GestorPartido(this);
        lv=(ListView)findViewById(R.id.lvJugador);
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
        gj.close();
    }

    @Override
    protected void onResume() {
        gj.open();
        super.onResume();
        final ListView lv= (ListView) findViewById(R.id.lvJugador);
        Cursor c = gj.getCursor(null,null,null);
        acj = new AdaptadorCursorJugador(this, c);
        lv.setAdapter(acj);
    }

    private void tostada(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean anadir() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.menuAnadir));
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.anadir_jugador, null);
        alert.setView(vista);
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int whichButton) {
                EditText etAnadirNombre, etAnadirTelefono, etAnadirFnac;

                etAnadirNombre=(EditText)vista.findViewById(R.id.etAnadirNombre);
                etAnadirTelefono=(EditText)vista.findViewById(R.id.etAnadirTelefono);
                etAnadirFnac=(EditText)vista.findViewById(R.id.etAnadirFnac);

                String nombre,telefono,fecha;

                nombre=etAnadirNombre.getText().toString();
                telefono=etAnadirTelefono.getText().toString();
                fecha=etAnadirFnac.getText().toString();

                if(etAnadirNombre.getText().toString().equals("")==true || etAnadirTelefono.getText().toString().equals("")==true || etAnadirFnac.getText().toString().equals("")==true){
                    tostada(getString(R.string.vacio));
                }else{
                    Jugador j=new Jugador(nombre,telefono,fecha);

                    long id=gj.insert(j);
                    acj.getCursor().close();
                    acj.changeCursor(gj.getCursor(null,null,null));
                    tostada(getString(R.string.jugadorInsertado));
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
                Jugador j=GestorJugador.getRow(c);
                gp.open();
                gp.deleteIDJugador(j);
                gj.delete(j);
                acj.changeCursor(gj.getCursor(null,null,null));
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
        final View vista = inflater.inflate(R.layout.editar_jugador, null);
        alert.setView(vista);

        Cursor c=(Cursor)lv.getItemAtPosition(index);
        final Jugador j=GestorJugador.getRow(c);

        final EditText etEditarNombre, etEditarTelefono, etEditarFnac;

        etEditarNombre=(EditText)vista.findViewById(R.id.etEditarNombre);
        etEditarTelefono=(EditText)vista.findViewById(R.id.etEditarTelefono);
        etEditarFnac=(EditText)vista.findViewById(R.id.etEditarFnac);



        etEditarNombre.setText(gj.getRowID(j.getId()).getNombre());
        etEditarTelefono.setText(gj.getRowID(j.getId()).getTelefono());
        etEditarFnac.setText(gj.getRowID(j.getId()).getFnac());

        alert.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Jugador j2 = new Jugador();
                if(etEditarNombre.getText().toString().equals("")==true || etEditarTelefono.getText().toString().equals("")==true || etEditarFnac.getText().toString().equals("")==true){
                    tostada(getString(R.string.vacio));
                }else{
                    j2.setNombre(etEditarNombre.getText().toString());
                    j2.setTelefono(etEditarTelefono.getText().toString());
                    j2.setFnac(etEditarFnac.getText().toString());
                    j2.setId(j.getId());
                    gj.update(j2);
                    acj.changeCursor(gj.getCursor(null, null, null));
                    tostada(getString(R.string.jugadorEditado));
                }
            }
        });
        alert.setNegativeButton(android.R.string.no, null);
        alert.show();
        return true;
    }
}
