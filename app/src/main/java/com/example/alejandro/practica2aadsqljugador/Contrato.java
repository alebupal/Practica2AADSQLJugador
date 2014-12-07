package com.example.alejandro.practica2aadsqljugador;

import android.provider.BaseColumns;

/**
 * Created by Alejandro on 07/12/2014.
 */
public class Contrato {

    private  Contrato(){}

    public static abstract class TablaJugador implements BaseColumns {
        public static final String TABLA="jugador";
        public static final String NOMBRE="nombre";
        public static final String TELEFONO="telefono";
        public static final String VALORACION="valoracion";
        public static final String FNAC="fnac";
    }
    public static abstract class TablaPartido implements BaseColumns{
        public static final String TABLA ="partido";
        public static final String IDJUGADOR ="idjugador";
        public static final String CONTRINCANTE ="contrincante";
        public static final String VALORACION ="valoracion";

    }
}
