package com.example.alejandro.practica2aadsqljugador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 07/12/2014.
 */
public class GestorPartido {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorPartido(Context c) {
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

    public long insert(Partido objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.IDJUGADOR, objeto.getIdJugador());
        valores.put(Contrato.TablaPartido.VALORACION,objeto.getValoracion());
        valores.put(Contrato.TablaPartido.CONTRINCANTE,objeto.getContrincante());
        long id = bd.insert(Contrato.TablaPartido.TABLA,null, valores);
        //id es el codigo autonumerico
        return id;
    }
    public int delete(Partido objeto) {
        String condicion = Contrato.TablaPartido._ID + " = ? ";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.delete(
                Contrato.TablaPartido.TABLA, condicion,argumentos);
        return cuenta;
    }
    public int deleteIDJugador(Jugador objeto2) {
        String condicion = Contrato.TablaPartido.IDJUGADOR + " = ?  ";
        String[] argumentos = {objeto2.getId() + ""};
        int cuenta = bd.delete(
                Contrato.TablaPartido.TABLA, condicion,argumentos);
        return cuenta;
    }



    public int update(Partido objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.IDJUGADOR, objeto.getIdJugador());
        valores.put(Contrato.TablaPartido.VALORACION, objeto.getValoracion());
        valores.put(Contrato.TablaPartido.CONTRINCANTE, objeto.getContrincante());
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.update(Contrato.TablaPartido.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public List<Partido> select(){
        return select(null,null,null);

    }

    public List<Partido> select(String condicion,String[] parametros, String orden) {
        List<Partido> alj = new ArrayList<Partido>();

        //un elemento que me permite recorrer el resultado de la consulta
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null,condicion, parametros, null, null, orden);//select *from Partido where condicion
        // bd.query(tabla, columnas,condicion, parametroCondicion, groupBy, having, orderBy);
        cursor.moveToFirst();
        Partido objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            alj.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return alj;
    }


    public Long getMediaID(long id){
        String[] proyeccion= {" AVG("+Contrato.TablaPartido.VALORACION+")"};
        String where= Contrato.TablaPartido.IDJUGADOR+ " = ?";
        String[] parametros= new String[] { id+"" };
        String groupby= null;
        String having= null;
        String orderby= null;
        Cursor c = bd.query(Contrato.TablaPartido.TABLA, proyeccion,where, parametros, groupby, having, orderby);
        c.moveToFirst();
        Long a= c.getLong(0);
        return a;
    }


    //Obtiene todos los Partidoes
    public static Partido getRow(Cursor c) {
        Partido objeto = new Partido();
        objeto.setId(c.getLong(0));
        objeto.setIdJugador(c.getLong(1));
        objeto.setValoracion(Integer.parseInt(c.getString(2)));
        objeto.setContrincante(c.getString(3));
        return objeto;
    }

    public Partido getRowID(long id) {
        String[] proyeccion= { Contrato.TablaPartido._ID,
                Contrato.TablaPartido.IDJUGADOR,
                Contrato.TablaPartido.VALORACION,
                Contrato.TablaPartido.CONTRINCANTE};
        String where= Contrato.TablaPartido._ID+ " = ?";
        String[] parametros= new String[] { id+"" };
        String groupby= null; String having= null;
        String orderby= Contrato.TablaPartido.IDJUGADOR+ " ASC";
        Cursor c = bd.query(Contrato.TablaPartido.TABLA, proyeccion, where, parametros, groupby, having, orderby);
        c.moveToFirst();
        Partido j= getRow(c);
        c.close();
        return j;
    }

    //Consulta donde saca al Partido por id
    public Partido getRow(long id){
        List<Partido> lj=select(Contrato.TablaPartido._ID + "= ?",new String[]{id + ""},null);
        if(!lj.isEmpty())
            return lj.get(0);
        return null;
    }

    //Nos devuelve el cursor de la consulta
    public Cursor getCursor(String condicion,String[] parametros, String orden) {
        Cursor cursor= bd.query(Contrato.TablaPartido.TABLA, null, condicion, parametros,null, null,orden);
        return cursor;
    }



}
