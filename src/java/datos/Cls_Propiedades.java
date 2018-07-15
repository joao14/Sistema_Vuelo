/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 *
 * @author Programacion
 */
public class Cls_Propiedades {

    static String servidor = "";
    static String puerto = "";
    static String esquema = "";
    static String nombrebase = "";
    static String password = "";

    public Cls_Propiedades() {

    }

    public Cls_Propiedades(String servidor, String puerto, String esquema, String nombrebase, String password) {

        this.servidor = servidor;
        this.puerto = puerto;
        this.esquema = esquema;
        this.nombrebase = nombrebase;
        this.password = password;
    }

    public static String getServidor() {
        return servidor;
    }

    public static void setServidor(String servidor) {
        Cls_Propiedades.servidor = servidor;
    }

    public static String getPuerto() {
        return puerto;
    }

    public static void setPuerto(String puerto) {
        Cls_Propiedades.puerto = puerto;
    }

    public static String getEsquema() {
        return esquema;
    }

    public static void setEsquema(String esquema) {
        Cls_Propiedades.esquema = esquema;
    }

    public static String getNombrebase() {
        return nombrebase;
    }

    public static void setNombrebase(String nombrebase) {
        Cls_Propiedades.nombrebase = nombrebase;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Cls_Propiedades.password = password;
    }

}
