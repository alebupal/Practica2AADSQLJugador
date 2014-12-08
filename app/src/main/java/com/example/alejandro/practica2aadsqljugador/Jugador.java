package com.example.alejandro.practica2aadsqljugador;

import java.io.Serializable;

/**
 * Created by Alejandro on 07/12/2014.
 */
public class Jugador implements Serializable,Comparable<Jugador>{


    private long id;
    private String nombre;
    private String telefono;
    private String fnac;


    public Jugador() {
    }

    public Jugador(String nombre, String telefono, String fnac) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.fnac = fnac;
    }

    public Jugador(String nombre, long id, String telefono, String fnac) {
        this.nombre = nombre;
        this.id = id;
        this.telefono = telefono;
        this.fnac = fnac;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Jugador.class != o.getClass()) return false;

        Jugador jugador = (Jugador) o;

        if (id != jugador.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


    public String toString() {

        return this.getNombre();
    }

    @Override
    public int compareTo(Jugador jugador) {
        if(this.nombre.compareTo(jugador.nombre)!=0){
            return this.nombre.compareTo(jugador.nombre);
        }else{
            return this.fnac.compareTo(jugador.fnac);
        }

    }
}

