/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deber6filesstreams;

/**
 *
 * @author dannibrito
 */
public class Laptop {
    String descripcion;
    String disco;
    int estrellas;
    String marca;
    int memoria;
    double precio;

    public Laptop() {
    }

    public Laptop(String descripcion, String disco, int estrellas, String marca, int memoria, double precio) {
        this.descripcion = descripcion;
        this.disco = disco;
        this.estrellas = estrellas;
        this.marca = marca;
        this.memoria = memoria;
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDisco() {
        return disco;
    }

    public void setDisco(String disco) {
        this.disco = disco;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getMemoria() {
        return memoria;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
    
}
