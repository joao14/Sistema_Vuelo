/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionbasedatos;


import gestionbdd.*;
import java.io.Serializable;

import java.sql.Connection;
import java.sql.ResultSet;

import java.util.List;

/**
 *
 * @author byron
 */
public class ClsGenericaBaseDatos implements Serializable {

    private static final long serialVersionUID = 1L;
    private ProveedorBDD proveedorBDD;
    private ConexionOracle conOra;
    private ConexionDB2 conDB2;
    private ConexionPostgres conPost;
    

    public enum ProveedorBDD {

        POSTGRES,
        SQL_SERVER,
        ORACLE,
        DB2;

        public String value() {
            return name();
        }
    }

    public ClsGenericaBaseDatos(ProveedorBDD proveedorBDD, String servidor, String puerto, String bdd, String usua, String pass) {
        this.proveedorBDD = proveedorBDD;
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
            conOra = new ConexionOracle(servidor, puerto, bdd, usua, pass);
        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
            conPost = new ConexionPostgres(servidor, puerto, bdd, usua, pass);
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {

        }

    }

    public ClsGenericaBaseDatos(ProveedorBDD proveedorBDD, String pool) {
        this.proveedorBDD = proveedorBDD;
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
            conOra = new ConexionOracle(pool);
        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {
            conDB2 = new ConexionDB2(pool);
        }
    }

    public boolean conectar() {
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
            if (conOra != null) {
                return conOra.conectar();
            }
        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
            if (conPost != null) {
                return conPost.conectar();
            }
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {

        }
        return false;
    }

    public boolean conectarPool() {
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {

            if (conOra != null) {
                return conOra.conectarPool();
            }
        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {

            if (conDB2 != null) {

                return conDB2.conectarPool();
            }
        }
        return false;
    }

    public List<Object> consultar(String sql) {
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
            return conOra.consultar(sql);
        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
            return conPost.consultar(sql);
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {
            return conDB2.consultar(sql);
        }
        return null;
    }

    public String insertarRegistro(String nombreTabla, String clavePrimariaTabla, String nombreColumnas, String valoresColumnas) {
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
            return conOra.insertarRegistro(nombreTabla, clavePrimariaTabla, nombreColumnas, valoresColumnas);
        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
            return conPost.insertarRegistro(nombreTabla, clavePrimariaTabla, nombreColumnas, valoresColumnas);
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {
            return conDB2.insertarRegistro(nombreTabla, clavePrimariaTabla, nombreColumnas, valoresColumnas);
        }
        return "No se encontro proveedor de BDD...";
    }

    public String actualizarRegistro(String nombreTabla, String setColumnas, String condicion) {
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
            return conOra.actualizarRegistro(nombreTabla, setColumnas, condicion);
        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
            return conPost.actualizarRegistro(nombreTabla, setColumnas, condicion);
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {
            return conDB2.actualizarRegistro(nombreTabla, setColumnas, condicion);
        }
        return "No se encontro proveedor de BDD...";
    }

//    public String eliminarRegistro(String nombreTabla, String condicion) {
//
//        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
//            return conOra.eliminarRegistro(nombreTabla, condicion);
//        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
//        }
//        return "No se encontro proveedor de BDD...";
//    }
    public ResultSet consultarResulset(String sql) {
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
            return conOra.consultarR(sql);
        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
            return conPost.consultarR(sql);
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {

            return conDB2.consultarR(sql);
        }
        return null;
    }
//    public void ejecutarProcedimiento(String nombreProcedimiento, String fechaTransaccion) throws SQLException, Exception {
//         if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
//            conOra.ejecutarProcedimiento(nombreProcedimiento,fechaTransaccion);
//        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
//            conPost.ejecutarProcedimiento(nombreProcedimiento,fechaTransaccion);
//        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {
//            conDB2.ejecutarProcedimiento(nombreProcedimiento,fechaTransaccion);
//        }
//    }

    public void desconectar() {
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {
            conOra.desconectar();
        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
            conPost.desconectar();
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {
            conDB2.desconectar();
        }
    }

    public Connection getConnection() {
        if (proveedorBDD.equals(proveedorBDD.ORACLE)) {

        } else if (proveedorBDD.equals(proveedorBDD.POSTGRES)) {
            return conPost.getConexion();
        } else if (proveedorBDD.equals(proveedorBDD.DB2)) {

        }
        return null;
    }
}
