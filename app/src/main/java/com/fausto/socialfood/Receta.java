package com.fausto.socialfood;

public class Receta {

public enum Categorias{

    ENSALADAS,SALSAS,PIZZAS,HAMBURGUESAS,SOPAS,ARROZ;

}
    private String Titulo;
    private String Ingredientes;
    private String Procedimiento;
    private String Descripcion;
    private String imagen;
    private String Categoria;

    public Receta() {

    }

    public Receta(String titulo, String descripcion, String Ingredientes, String procedimiento, String imagen, String categoria) {
        this.Titulo = titulo;
        this.Ingredientes = Ingredientes;
        this.Procedimiento = procedimiento;
        this.imagen = imagen;
        this.Descripcion = descripcion;
        this.Categoria = categoria;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        this.Categoria = categoria;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getIngredientes() {
        return Ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.Ingredientes = ingredientes;
    }

    public void setTitulo(String titulo) {
        this.Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }


    public String getProcedimiento() {
        return Procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.Procedimiento = procedimiento;
    }
}
