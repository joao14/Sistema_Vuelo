/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionbdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
public class ConexionPostgres {

    private Connection con;
    private String driver;
    private String cadena;
    private String usuario;
    private String clave;

    public Connection getConexion() {
        return this.con;
    }

    public ConexionPostgres(String servidor, String puerto, String bdd, String usuario, String clave) {
        driver = "org.postgresql.Driver";
        cadena = "jdbc:postgresql://" + servidor + ":" + puerto + "/" + bdd + "";
        this.usuario = usuario;
        this.clave = clave;
    }

    public boolean conectar() {
        try {
            Class.forName(driver);
            System.out.println("Conectado....");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Postgres JDBC Driver?");
            return false;
        }
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(cadena, usuario, clave);
            //System.out.println("conectado...");
        } catch (Exception ee) {
            System.out.println("Error: " + ee.getMessage());
            return false;
        }
        return true;
    }

    public String ejecutarSql(String sql) {
        String mensaje = "";
        Statement sta_sentencia = null;
        try {
            con.setAutoCommit(false);
            sta_sentencia = con.createStatement();
            sta_sentencia.executeUpdate(sql);
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (Exception ex) {
            }
            System.out.println("ERROR ejecutar() : ".concat(sql).concat(" : ").concat(e.getMessage()));
            mensaje = e.getMessage();
        } finally {
            if (con != null) {
                try {
                    sta_sentencia.close();
                } catch (Exception e) {
                }
            }
        }
        return mensaje;
    }

    public void desconectar() {
        try {
            con.close();
            System.out.println("desconectado..");
        } catch (Exception ex) {
        }
    }

    public List<Object> consultar(String sql) {
        ResultSet reg = null;

        List<Object> lisResul = new ArrayList();
        Object[] obj;

        String res = "";
        int numColumnas;
        try {
            reg = consultarR(sql);
            numColumnas = reg.getMetaData().getColumnCount();
            obj = new Object[numColumnas];
            for (int n = 1; n <= reg.getMetaData().getColumnCount(); n++) {
                res = res + reg.getMetaData().getColumnName(n) + ",";
                obj[n - 1] = reg.getMetaData().getColumnName(n);
            }
            lisResul.add(obj);
            while (reg.next()) {
                obj = new Object[numColumnas];
                for (int c = 1; c <= reg.getMetaData().getColumnCount(); c++) {
                    obj[c - 1] = reg.getString(c);
                }
                lisResul.add(obj);
            }

            // SOLO IMPRESION EN PANTALLA
//            System.out.println();
//            for (int i = 0; i < lisResul.size(); i++) {
//                obj = (Object[]) lisResul.get(i);
//                if (i == 0) {
//                    System.out.print("label: ");
//                } else {
//                    System.out.print("fila " + i + ": ");
//                }
//                for (int j = 0; j < obj.length; j++) {
//                    System.out.print(obj[j] + ", ");
//                }
//                if (i == 0) {
//                    System.out.println();
//                }
//                System.out.println();
//            }
        } catch (Exception e) {
            System.out.println("error capturado " + e.getMessage());
        }

        return lisResul;
    }

    public ResultSet consultarR(String sql) {

        ResultSet reg = null;
        Statement sta_sentencia = null;
        try {
            sta_sentencia = getConexion().createStatement();
            reg = sta_sentencia.executeQuery(sql);
        } catch (Exception ee) {
            System.out.println("FALLO: ".concat(ee.getMessage()));
        }
        return (reg);
    }

    public String actualizarRegistro(String nombreTabla, String setColumnas, String condicion) {
        String mensaje = "";
        String strSqlUpdate = "update " + nombreTabla + " set " + setColumnas + " where " + condicion;

        mensaje = ejecutarSql(strSqlUpdate);
        return mensaje;
    }

    public String insertarRegistro(String nombreTabla, String clavePrimariaTabla, String nombreColumnas, String valoresColumnas) {
        String mensaje = "";
        if (clavePrimariaTabla == null) {
            String strSqlInsert = "insert into " + nombreTabla + " (" + nombreColumnas + ") values (" + valoresColumnas + ")";
            mensaje = ejecutarSql(strSqlInsert);
//            if (mensaje.equals("No Se afecto ningun registro")) {
//                mensaje = "";
//            }
            return mensaje;
        }
        // obtengo el maximo valor de clave primaria de la tabla
        long num_max = obtenerMaximoPrimario(nombreTabla, clavePrimariaTabla) + 1;

        // creo la sequencia de la clave primaria de la tabla
        mensaje = ejecutarSql("create sequence SEQ_" + clavePrimariaTabla + " "
                + "start with " + num_max + " "
                //          + "nocache "
                + "increment by 1 ");

        if (mensaje.isEmpty()) {
            // si la secuencia se creo correctamente por primera ves
            String strSqlInsert = "insert into " + nombreTabla + " (" + clavePrimariaTabla + "," + nombreColumnas + ") values (NEXTVAL('SEQ_" + clavePrimariaTabla + "')," + valoresColumnas + ")";
            mensaje = ejecutarSql(strSqlInsert);
        } else {
            // la secuencia ya se encuentra creada  
            String strSqlInsert = "insert into " + nombreTabla + " (" + clavePrimariaTabla + "," + nombreColumnas + ") values (NEXTVAL('SEQ_" + clavePrimariaTabla + "')," + valoresColumnas + ")";
            mensaje = ejecutarSql(strSqlInsert);
        }

        return mensaje;
    }

    public long obtenerMaximoPrimario(String nombreTabla, String clavePrimaria) {
        // obtengo el maximo valor de clave primaria de la tabla
        List<Object> lisSqlMax = consultar("select MAX(" + clavePrimaria + ") from " + nombreTabla);
        long num_max = 0;
        if (lisSqlMax != null) {
            try {
                Object[] obj = (Object[]) lisSqlMax.get(1);

                num_max = Long.parseLong(obj[0] + "");
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
        }
        return num_max;
    }
}
