package com.oracle.dgnmovil.app.model;

/**
 * Created by osvaldo on 10/18/14.
 */
public class Rae {
    private long id;
    private String nombre;
    private String img;
    private long numNormas;

    public Rae() {}

    public Rae(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getNumNormas() {
        return numNormas;
    }

    public void setNumNormas(long numNormas) {
        this.numNormas = numNormas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
