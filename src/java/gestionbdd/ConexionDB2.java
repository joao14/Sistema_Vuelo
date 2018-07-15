/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionbdd;

/**
 *
 * @author CHR
 */
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConexionDB2 {

    private Connection con;
    private List<String> sqlPantalla = new ArrayList<String>();
    private String driver;
    private String cadena;
    private String usuario;
    private String clave;
    private String pool;

    public Connection getConexion() {
        //System.out.println("Esta es la con: "+con);
        return this.con;
    }
//Hay q cambiar Este metodo para DB2
//    public ConexionDB2(String servidor, String puerto, String sid, String usua, String cla) {
//        driver = "oracle.jdbc.driver.OracleDriver";
//        cadena = "jdbc:oracle:thin:@" + servidor + ":" + puerto + ":" + sid;
//        usuario = usua;
//        clave = cla;
//    }

    public ConexionDB2(String poolC) {
        System.out.println(poolC);
        pool = poolC;
    }
//Hay q cambiar Este metodo para DB2
//    public boolean conectar() {
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException e) {
//            System.out.println("Where is your Oracle JDBC Driver?");
//            return false;
//        }
//        try {
//            Class.forName(driver).newInstance();
//            con = DriverManager.getConnection(cadena, usuario, clave);
//            //System.out.println("Conectado...");
//        } catch (Exception ee) {
//            System.out.println("Error: " + ee.getMessage());
//            return false;
//        }
//        return true;
//    }

    public boolean conectarPool() {
        try {

            Context ctx = new InitialContext();
            //The JDBC Data source that we just created
            DataSource ds = (DataSource) ctx.lookup(pool);
            con = ds.getConnection();
            if (con == null) {
                throw new SQLException("Error estableciendo la conexion!");
            } else {
                //System.out.println("Conectado...");
            }
        } catch (Exception ee) {
            System.out.println("Error: " + ee.getMessage());
            return false;
        }
        return true;
    }
//VErificar en DB2 CAmpo incremental automatico

    public long obtenerMaximoPrimario(String nombreTabla, String clavePrimaria) {
        // obtengo el maximo valor de clave primaria de la tabla
        List<Object> lisSqlMax = consultar("select MAX(" + clavePrimaria + ") from " + nombreTabla);
        long num_max = 0;
        if (lisSqlMax != null) {
            Object[] obj = (Object[]) lisSqlMax.get(1);
            try {
                num_max = Long.parseLong(obj[0] + "");
            } catch (Exception e) {
            }
        }
        return num_max;
    }

    public String insertarRegistro(String nombreTabla, String clavePrimariaTabla, String nombreColumnas, String valoresColumnas) {
        String mensaje = "";
        if (clavePrimariaTabla == null) {
            String strSqlInsert = "insert into " + nombreTabla + " (" + nombreColumnas + ") values (" + valoresColumnas + ")";
            mensaje = ejecutarSql(strSqlInsert);
            return mensaje;
        }
//        // obtengo el maximo valor de clave primaria de la tabla
//        long num_max = obtenerMaximoPrimario(nombreTabla, clavePrimariaTabla) + 1;
//
//        // creo la sequencia de la clave primaria de la tabla
//        mensaje = ejecutarSql("create sequence SEQ_" + clavePrimariaTabla + " "
//                + "start with " + num_max + " "
//                + "nocache "
//                + "increment by 1 ");
//
//        if (mensaje.isEmpty()) {
//            // si la secuencia se creo correctamente por primera ves
//            String strSqlInsert = "insert into " + nombreTabla + " (" + clavePrimariaTabla + "," + nombreColumnas + ") values (SEQ_" + clavePrimariaTabla + ".NEXTVAL," + valoresColumnas + ")";
//            mensaje = ejecutarSql(strSqlInsert);
//        } else {
//            // la secuencia ya se encuentra creada  
//            String strSqlInsert = "insert into " + nombreTabla + " (" + clavePrimariaTabla + "," + nombreColumnas + ") values (SEQ_" + clavePrimariaTabla + ".NEXTVAL," + valoresColumnas + ")";
//            mensaje = ejecutarSql(strSqlInsert);
//        }

        return mensaje;
    }

    public String actualizarRegistro(String nombreTabla, String setColumnas, String condicion) {
        String mensaje = "";
        String strSqlUpdate = "update " + nombreTabla + " set " + setColumnas + " where " + condicion;

        mensaje = ejecutarSql(strSqlUpdate);
        return mensaje;
    }

    public String eliminarRegistro(String nombreTabla, String condicion) {
        String mensaje = "";
        String strSqlDelete = "";
        if (condicion.endsWith("'null'")) {
            System.out.println("Falta Condicion Para elminar vacia");
            //strSqlDelete = "delete from " + nombreTabla;
        } else {
            strSqlDelete = "Delete from " + nombreTabla + " where " + condicion;
        }
        mensaje = ejecutarSql(strSqlDelete);
        return mensaje;
    }
//    public String eliminarRegistro(String nombreTabla, String condicion) {
//        String mensaje = "";
//        String strSqlDelete = "";
//        if (condicion.endsWith("'null'")) {
//            System.out.println("Condicion Para elminar vacia");
//            //strSqlDelete = "delete from " + nombreTabla;
//        } else {
//            strSqlDelete = "Delete from " + nombreTabla + " where " + condicion;
//        }
//        mensaje = ejecutarSql(strSqlDelete);
//        return mensaje;
//    }

//    public String eliminarTodosRegistro(String nombreTabla) {
//        String mensaje = "";
//        String strSqlDelete = "";
//        strSqlDelete = "delete from " + nombreTabla;
//        mensaje = ejecutarSql(strSqlDelete);
//        return mensaje;
//    }
    public String ejecutarSql(String sql) {
        String mensaje = "";
        Statement sta_sentencia = null;
        int intFilasAFectadas = 0;
        try {

            con.setAutoCommit(false);
            sta_sentencia = con.createStatement();
            //sta_sentencia.executeUpdate(sql);
            intFilasAFectadas = sta_sentencia.executeUpdate(sql);
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (Exception ex) {
            }
            //System.out.println("ERROR ejecutar() : ".concat(sql).concat(" : ").concat(e.getMessage()));
            mensaje = "ERROR ejecutar() : ".concat(sql).concat(" : ").concat(e.getMessage());
        } finally {
            if (con != null) {
                try {
                    sta_sentencia.close();
                } catch (Exception e) {
                }
            }
        }
        if (intFilasAFectadas == 0 && mensaje.equals("")) {
            mensaje = "No Se afecto ningun registro";
        }
        return mensaje;
    }

    public void agregarSql(String sql) {
        if (!sqlPantalla.contains(sql)) {
            sqlPantalla.add(sql);
        }
    }

    public String ejecutarListaSql() {
        String mensaje = "";
        if (!sqlPantalla.isEmpty()) {
            String sql = "";
            Statement sta_sentencia = null;
            try {
                con.setAutoCommit(false);
                sta_sentencia = con.createStatement();
                for (int i = 0; i < sqlPantalla.size(); i++) {
                    sql = sqlPantalla.get(i);
                    sta_sentencia.executeUpdate(sql);
                }
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                } catch (Exception ex) {
                }
                System.out.println("FALLO: ".concat(e.getMessage()));
                mensaje = e.getMessage();

            } finally {
                if (con != null) {
                    try {
                        if (sta_sentencia != null) {
                            sta_sentencia.close();
                        }
                    } catch (Exception e) {
                    }
                }
            }
            sqlPantalla.clear();
        }
        return mensaje;
    }

    public void desconectar() {
        try {
            con.close();
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

    public void ejecutarProcedimiento(String nombreProcedimiento, String fechaTransaccion) throws SQLException, Exception {
        if (nombreProcedimiento == null || nombreProcedimiento.isEmpty()) {
            System.err.println("NO SE PUEDE EJECUTAR PROCEDIMIENTO");
            return;
        }
        if (fechaTransaccion == null || fechaTransaccion.isEmpty()) {
            System.err.println("NO SE PUEDE EJECUTAR PROCEDIMIENTO");
            return;
        }

        CallableStatement callableStmt = null;
        try {

            // Llamada al procedimiento almacenado
            String getProcedimiento = "{ call " + nombreProcedimiento + "(?)}";
//            System.out.println("valor de getprocedimiento antes del try:  " + getProcedimiento);
            callableStmt = con.prepareCall(getProcedimiento);
            callableStmt.setString("VAR_CMP_FECHA_TRANSACCION", fechaTransaccion);
            //ejecuta procedimiento
            callableStmt.execute();
            System.out.println("se ejecuto correctamente el procedimiento");

        } catch (SQLException ex) {
            System.out.println("Error de SQL: " + ex.getMessage());
        } finally {
            if (callableStmt != null) {
                callableStmt.close();
            }
//            if (dbConnection != null) {
//                dbConnection.close();
//            }
        }
    } 

    public List<String> getListaSql() {
        return sqlPantalla;
    }
}
