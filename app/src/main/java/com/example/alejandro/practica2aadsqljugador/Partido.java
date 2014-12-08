package com.example.alejandro.practica2aadsqljugador;

/**
 * Created by Alejandro on 07/12/2014.
 */
public class Partido {

    private long id;
    private long idJugador;
    private int valoracion;
    private String contrincante;

    public Partido() {
    }

    public Partido(long id, long idJugador, int valoracion, String contrincante) {
        this.id = id;
        this.idJugador = idJugador;
        this.valoracion = valoracion;
        this.contrincante = contrincante;
    }

     public Partido(int valoracion, String contrincante, long idJugador) {
        this.idJugador = idJugador;
        this.valoracion = valoracion;
        this.contrincante = contrincante;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(long idJugador) {
        this.idJugador = idJugador;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getContrincante() {
        return contrincante;
    }

    public void setContrincante(String contrincante) {
        this.contrincante = contrincante;
    }


    public String toString() {
        return this.getIdJugador()+"";
    }
}
