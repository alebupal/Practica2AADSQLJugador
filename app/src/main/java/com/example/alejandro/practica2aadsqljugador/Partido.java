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
        this.valoracion = valoracion;
        this.contrincante = contrincante;
        this.idJugador = idJugador;
    }
    /* public Partido(int valoracion, String contrincante, long id_jugador) {
        this.idJugador = idJugador;
        this.valoracion = valoracion;
        this.contrincante = contrincante;
    }*/

    /*public Partido(String contrincante, long id, long id_jugador, String valoracion) {
        this.contrincante = contrincante;
        this.id = id;
        this.idJugador = idJugador;
        try {
            this.valoracion = Integer.parseInt(valoracion);
        } catch (NumberFormatException e) {
            this.valoracion = 0;
        }
    }*/

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
