/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionbasedatos;

import datos.Cls_Propiedades;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Programacion
 */
public class validacion {

    //Metodo para obtener datos del archivo de propiedades
    public void Archivo_Propiedades() {
        Cls_Propiedades clase = null;
        Properties propiedades = new Properties();
        InputStream entrada = null;
        try {
            entrada = new FileInputStream("C:\\configuracion_sistema.properties");
            propiedades.load(entrada);
            //directorioLogs  nombreempresalogo
            clase = new Cls_Propiedades(propiedades.getProperty("servidor"),
                    propiedades.getProperty("puerto"),
                    propiedades.getProperty("esquema"),
                    propiedades.getProperty("nombrebase"),
                    propiedades.getProperty("password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }
}
