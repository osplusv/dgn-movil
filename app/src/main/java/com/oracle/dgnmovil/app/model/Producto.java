package com.oracle.dgnmovil.app.model;

import java.util.List;

/**
 * Created by osvaldo on 10/18/14.
 */
public class Producto {
    private String nombre;
    private long numNormas;
    private List<String> img;

    public Producto() {}

    public Producto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getNumNormas() {
        return numNormas;
    }

    public void setNumNormas(long numNormas) {
        this.numNormas = numNormas;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
