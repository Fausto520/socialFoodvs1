package com.fausto.socialfood;

public class recetaPojo {

    private String Titulo;
    private String Descripcion;
    private String Procedimiento;
    private String Ingredientes;
    private String imagen;
    private String Categoria;

    public recetaPojo() {

    }

    public recetaPojo(String titulo, String descripcion, String procedimiento, String ingredientes, String imagen, String categoria) {
        Titulo = titulo;
        Descripcion = descripcion;
        Procedimiento = procedimiento;
        Ingredientes = ingredientes;
        this.imagen = imagen;
        Categoria = categoria;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getProcedimiento() {
        return Procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        Procedimiento = procedimiento;
    }

    public String getIngredientes() {
        return Ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        Ingredientes = ingredientes;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }
}
