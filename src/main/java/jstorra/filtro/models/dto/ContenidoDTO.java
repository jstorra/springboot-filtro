package jstorra.filtro.models.dto;

import jstorra.filtro.models.TipoContenido;

public class ContenidoDTO {
    private Integer id;
    private String nombre;
    private TipoContenido tipoContenido;
    private Integer genero1_id;
    private Integer genero2_id;
    private String estado;
    private Integer plataforma_id;
    private double calificacion;
    private String comentario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoContenido getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(TipoContenido tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public Integer getGenero1_id() {
        return genero1_id;
    }

    public void setGenero1_id(Integer genero1_id) {
        this.genero1_id = genero1_id;
    }

    public Integer getGenero2_id() {
        return genero2_id;
    }

    public void setGenero2_id(Integer genero2_id) {
        this.genero2_id = genero2_id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPlataforma_id() {
        return plataforma_id;
    }

    public void setPlataforma_id(Integer plataforma_id) {
        this.plataforma_id = plataforma_id;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
