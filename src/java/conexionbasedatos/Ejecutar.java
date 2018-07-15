/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionbasedatos;

/**
 *
 * @author Alex
 */
public class Ejecutar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        conexion con=new conexion();
        con.getConexBDD().conectar();
        con.getConexBDD().desconectar();
    }
}
