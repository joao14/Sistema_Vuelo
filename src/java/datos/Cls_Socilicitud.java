/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.Date;

/**
 *
 * @author Alex
 */
public class Cls_Socilicitud {

    private String id;
    private String nombre;
    private Date fecha;
    private String destino;
    private Date fechas;
    private String horas;
    private String estado;

    public Cls_Socilicitud() {
    }

    public Cls_Socilicitud(String id, String nombre, Date fecha, String destino, Date fechas, String horas, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.destino = destino;
        this.fechas = fechas;
        this.horas = horas;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFechas() {
        return fechas;
    }

    public void setFechas(Date fechas) {
        this.fechas = fechas;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
