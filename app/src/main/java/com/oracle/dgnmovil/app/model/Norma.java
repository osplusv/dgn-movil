package com.oracle.dgnmovil.app.model;

/**
 * Created by osvaldo on 10/18/14.
 */
public class Norma {

    private long id;
    private String clave;
    private String titulo;
    private String fecha;
    private String img;
    private String publicacion;
    private String activa;
    private String tipo;
    private String norma_internacional;
    private String concordancia;
    private String documento;
    private int favorito;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(String publicacion) {
        this.publicacion = publicacion;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNorma_internacional() {
        return norma_internacional;
    }

    public void setNorma_internacional(String norma_internacional) {
        this.norma_internacional = norma_internacional;
    }

    public String getConcordancia() {
        return concordancia;
    }

    public void setConcordancia(String concordancia) {
        this.concordancia = concordancia;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public String[] getStringAttributes() {
        String attributes[] = new String[10];
        attributes[0] = getClave();
        attributes[1] = getTitulo();
        attributes[2] = getFecha();
        attributes[3] = getImg();
        attributes[4] = getPublicacion();
        attributes[5] = getNorma_internacional();
        attributes[6] = getConcordancia();
        attributes[7] = getDocumento();
        return attributes;
    }
}
