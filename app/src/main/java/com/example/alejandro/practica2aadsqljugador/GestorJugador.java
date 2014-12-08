package com.example.alejandro.practica2aadsqljugador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 07/12/2014.
 */
public class GestorJugador {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorJugador(Context c) {
        abd = new Ayudante(c);
    }

    public void open() {
        bd = abd.getWritableDatabase();
    }

    public void openRead() {
        bd = abd.getReadableDatabase();
    }

    public void close() {
        abd.close();
    }

    public long insert(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugador.FNAC, objeto.getFnac());
        valores.put(Contrato.TablaJugador.NOMBRE,objeto.getNombre());
        valores.put(Contrato.TablaJugador.TELEFONO,objeto.getTelefono());
        long id = bd.insert(Contrato.TablaJugador.TABLA,null, valores);
        //id es el codigo autonumerico
        return id;
    }
    public int delete(Jugador objeto) {
        String condicion = Contrato.TablaJugador._ID + " = ? ";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.delete(
                Contrato.TablaJugador.TABLA, condicion,argumentos);
        return cuenta;
    }



    public int update(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugador.FNAC, objeto.getFnac());
        valores.put(Contrato.TablaJugador.NOMBRE, objeto.getNombre());
        valores.put(Contrato.TablaJugador.TELEFONO, objeto.getTelefono());
        String condicion = Contrato.TablaJugador._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.update(Contrato.TablaJugador.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public List<Jugador> select(){
        return select(null,null,null);

    }

    public List<Jugador> select(String condicion,String[] parametros, String orden) {
        List<Jugador> alj = new ArrayList<Jugador>();

        //un elemento que me permite recorrer el resultado de la consulta
        Cursor cursor = bd.query(Contrato.TablaJugador.TABLA, null,
                condicion, parametros, null, null, orden);//select *from jugador where condicion
        // bd.query(tabla, columnas,condicion, parametroCondicion, groupBy, having, orderBy);
        cursor.moveToFirst();
        Jugador objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            alj.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return alj;
    }

    //Obtiene todos los jugadores
    public static Jugador getRow(Cursor c) {
        Jugador objeto = new Jugador();
        objeto.setId(c.getLong(0));
        objeto.setNombre(c.getString(1));
        objeto.setTelefono(c.getString(2));
        objeto.setFnac(c.getString(3));
        return objeto;
    }

    public Jugador getRowID2(long id) {
        String[] proyeccion= { Contrato.TablaJugador._ID,
                Contrato.TablaJugador.NOMBRE,
                Contrato.TablaJugador.TELEFONO,
                Contrato.TablaJugador.FNAC};
        String where= Contrato.TablaJugador._ID + " = ?";
        String[] parametros= new String[] { id+"" };
        String groupby= null;
        String having= null;
        String orderby= Contrato.TablaJugador.NOMBRE+ " ASC";
        Cursor c = bd.query(Contrato.TablaJugador.TABLA, proyeccion,where, parametros, groupby, having, orderby);
        c.moveToFirst();
        Jugador j= getRow(c);
        c.close();
        return j;
    }

    //Consulta donde saca al jugador por id
    public Jugador getRowID(long id){
        List<Jugador> lj=select(Contrato.TablaJugador._ID + "= ?",new String[]{id + ""},null);
        if(!lj.isEmpty())
            return lj.get(0);
        return null;
    }

    //Nos devuelve el cursor de la consulta
    public Cursor getCursor(String condicion,String[] parametros, String orden) {
        Cursor cursor= bd.query(Contrato.TablaJugador.TABLA, null, condicion, parametros,null, null,orden);
        return cursor;
    }
}
