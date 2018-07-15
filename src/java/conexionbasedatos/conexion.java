/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionbasedatos;

import datos.Cls_Propiedades;
import java.io.Serializable;

public class conexion implements Serializable {

    private static final long serialVersionUID = 1L;
    Cls_Propiedades propiedades = new Cls_Propiedades();

    ClsGenericaBaseDatos conexBDD = new ClsGenericaBaseDatos(
            ClsGenericaBaseDatos.ProveedorBDD.POSTGRES,
            Cls_Propiedades.getServidor(),
            Cls_Propiedades.getPuerto(),
            Cls_Propiedades.getNombrebase(),
            Cls_Propiedades.getEsquema(),  
            Cls_Propiedades.getPassword()
    );

    public ClsGenericaBaseDatos getConexBDD() {
        return conexBDD;
    }

    public void setConexBDD(ClsGenericaBaseDatos conexBDD) {
        this.conexBDD = conexBDD;
    }
}
