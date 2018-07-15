/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managementBeans;

import conexionbasedatos.conexion;
import conexionbasedatos.validacion;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Alex
 */
@ManagedBean(name = "solicitud")
@ViewScoped
public class ManagameBean implements Serializable {

    private String nombre;
    private Date fecha;
    private String destino;
    private Date fecha_min;
    private Map<String, String> MenuDestino = new HashMap<String, String>();
    conexion con = new conexion();

    @PostConstruct
    public void init() {
        validacion val = new validacion();
        val.Archivo_Propiedades();
        Calendar fecha = Calendar.getInstance();
        fecha.add(Calendar.DATE, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strFechaAyer = sdf.format(fecha.getTime());
        Date fecha1 = DeStringADate(strFechaAyer);
        setFecha_min(fecha1);
        llenarSelectOneMenu();

    }

    public void llenarSelectOneMenu() {
        String SQL = "select * from tbl_destino";
        if (con.getConexBDD().conectar()) {
            List<Object> lista = con.getConexBDD().consultar(SQL);
            for (int i = 1; i < lista.size(); i++) {
                Object[] obj = (Object[]) lista.get(i);
                MenuDestino.put(obj[1] + "", obj[0] + "");
            }
            con.getConexBDD().desconectar();
        } else {
            System.out.println("No se puede llenar el componente Select");
        }
    }

    public Date DeStringADate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String strFecha = fecha;
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(strFecha);
            return fechaDate;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return fechaDate;
        }
    }

    //Metodo para transformar la fecha  
    public String convertirDate_String(Date fecha) {
        String Date_String = "";
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        Date_String = simple.format(fecha);
        return Date_String;
    }

    public void actionEvent() {
        //Obtiene los datos segun el erchivo de propiedades
        Date fecha = new Date();
        GuardarRegistros(getNombre(), "0", "2015-08-16", "2015-08-17", "12:45:13", getDestino());

    }

    public void GuardarRegistros(String nombre, String estado, String fecha_viaje, String fecha_solicitud, String hora_solicitud, String id_destino) {
        String SQlGuardar = "";
        if (con.getConexBDD().conectar()) {
            String sms = con.getConexBDD().insertarRegistro("tbl_solicitante", null, "id_destino,nombre,estado,fecha_viaje,fecha_solicitud,hora_solicitud", "" + id_destino + ",'" + nombre + "'," + estado + ",'" + fecha_viaje + "','" + fecha_solicitud + "','" + hora_solicitud + "'");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registro ingresado correctamente"));
            con.getConexBDD().desconectar();
            cleanData();
        }
    }

    public void cleanData() {
        this.nombre = "";
        this.fecha = null;
        this.destino = "";
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

    public Date getFecha_min() {
        return fecha_min;
    }

    public void setFecha_min(Date fecha_min) {
        this.fecha_min = fecha_min;
    }

    public Map<String, String> getMenuDestino() {
        return MenuDestino;
    }

    public void setMenuDestino(Map<String, String> MenuDestino) {
        this.MenuDestino = MenuDestino;
    }
}
