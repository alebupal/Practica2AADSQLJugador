package com.example.alejandro.practica2aadsqljugador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alejandro on 07/12/2014.
 */
public class Ayudante extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "futbol.sqlite";
    public static final int DATABASE_VERSION = 2;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override//Lo que ejecutara cuando el usuario instala la aplicacion y le mete la base de datos
    public void onCreate(SQLiteDatabase db) {

        String sql;
        sql="create table "+Contrato.TablaJugador.TABLA+
                " (" + Contrato.TablaJugador._ID +
                " integer primary key autoincrement, " +
                Contrato.TablaJugador.NOMBRE + " text, " +
                Contrato.TablaJugador.TELEFONO + " text, " +
                Contrato.TablaJugador.FNAC+" text)";
        Log.v("sql", sql);
        db.execSQL(sql);
        sql="create table "+Contrato.TablaPartido.TABLA+
                " ("+ Contrato.TablaPartido._ID+
                " integer PRIMARY KEY autoincrement, "+
                Contrato.TablaPartido.IDJUGADOR+" integer, "+
                Contrato.TablaPartido.VALORACION+" integer, "+
                Contrato.TablaPartido.CONTRINCANTE+" text, "+
                "unique ("+Contrato.TablaPartido.IDJUGADOR+" ,"+Contrato.TablaPartido.CONTRINCANTE+"))";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //transformar el esquema de la de la version old a la new
        //sin que se produzca perdida de datos

        //1º crear tablas de respaldo(identicas)
        //2º copio los datso de mis tablas en las de respaldo
        //3º borro las tablas originales
        //4º creo las tablas nuevas (onCreate)
        //5º copio en las nuevas tablas los datos de las tablas de respaldo
        //6º borro las tablas de respaldo

        if (oldVersion<2) {
            String sql = "CREATE TABLE JugadorRespaldo (id INTEGER, nombre TEXT, telefono TEXT, valoracion INTEGER,  fnac TEXT)";
            db.execSQL(sql);
            sql="INSERT INTO JugadorRespaldo  SELECT * FROM jugador";
            db.execSQL(sql);
            sql="drop table if exists "+ Contrato.TablaJugador.TABLA;
            db.execSQL(sql);
            onCreate(db);
            sql="INSERT INTO "+Contrato.TablaJugador.TABLA+" ("+Contrato.TablaJugador.NOMBRE +" , "+
                    Contrato.TablaJugador.TELEFONO+" , "+Contrato.TablaJugador.FNAC +") SELECT nombre, telefono, fnac FROM JugadorRespaldo";
            db.execSQL(sql);
            sql="INSERT INTO "+Contrato.TablaPartido.TABLA+" ("+Contrato.TablaPartido.VALORACION+" , "
                    +Contrato.TablaPartido.IDJUGADOR+
                    " ) SELECT valoracion, "+Contrato.TablaJugador._ID+" FROM JugadorRespaldo jugador1 INNER JOIN "+
                    Contrato.TablaJugador.TABLA+" jugador2 WHERE jugador1.nombre=jugador2."+Contrato.TablaJugador.NOMBRE+
                    " AND jugador1.telefono=jugador2."+Contrato.TablaJugador.TELEFONO+
                    " AND jugador1.fnac=jugador2."+Contrato.TablaJugador.FNAC;
            db.execSQL(sql);
            sql="UPDATE "+Contrato.TablaPartido.TABLA+" SET "+Contrato.TablaPartido.CONTRINCANTE+"='Media anterior'";
            db.execSQL(sql);
            sql="drop table JugadorRespaldo";
            db.execSQL(sql);
        }

    }
}
