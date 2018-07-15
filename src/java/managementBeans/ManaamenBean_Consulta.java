package managementBeans;

import conexionbasedatos.conexion;
import datos.Cls_Socilicitud;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@ManagedBean(name = "consulta")
@ViewScoped
public class ManaamenBean_Consulta implements Serializable {

    private Date fechaI;
    private Date fechaf;
    private String destino;
    private Date fecha_min;
    private Map<String, String> MenuDestino = new HashMap<String, String>();
    conexion con = new conexion();
    List<Cls_Socilicitud> lista = null;   

    @PostConstruct
    public void init() {
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

    public void Consultar() {
        Llenar_Tabla();
    }

    public List<Cls_Socilicitud> Llenar_Tabla() {

        if (con.getConexBDD().conectar()) {
            String SQL = "select s.id,s.nombre,s.fecha_viaje,s.fecha_solicitud,s.hora_solicitud,d.id_destino,d.nombre_destino,s.estado \n"
                    + "from tbl_solicitante s,tbl_destino d \n"
                    + "where d.id_destino=s.id_destino and d.id_destino='" + getDestino() + "' order by s.fecha_viaje asc";
            List<Object> list = con.getConexBDD().consultar(SQL);
            lista = new ArrayList<Cls_Socilicitud>();
            String palabra = "";
            for (int i = 1; i < list.size(); i++) {
                Object[] obje = (Object[]) list.get(i);
                if (obje[7].equals("0") || Integer.parseInt(String.valueOf(obje[7]).replace("                   ", "")) == 0) {
                    palabra = "Nuevo";
                } else {
                    palabra = "Reservada";
                }
                lista.add(new Cls_Socilicitud(obje[0] + "", obje[1] + "", DeStringADate(obje[2] + ""), obje[6] + "", DeStringADate(obje[3] + ""),
                        obje[4] + "", palabra));

            }

        } else {
            System.out.println("No se pierde llenar la tabla");
        }

        return lista;
    }

    public void actualizarR(String id) {
        if (con.getConexBDD().conectar()) {
            con.getConexBDD().actualizarRegistro("tbl_solicitante", "estado='1'", "id=" + id);
            con.getConexBDD().desconectar();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Vuelo asignado al pasajero satisfactoriamente"));
        } else {
            System.out.println("No puede establecer conexion");
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

    public void cleanData() {
        this.fechaI = null;
        this.fechaf = null;
        this.destino = "";
    }

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public Date getFechaf() {
        return fechaf;
    }

    public void setFechaf(Date fechaf) {
        this.fechaf = fechaf;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Map<String, String> getMenuDestino() {
        return MenuDestino;
    }

    public void setMenuDestino(Map<String, String> MenuDestino) {
        this.MenuDestino = MenuDestino;
    }

    public Date getFecha_min() {
        return fecha_min;
    }

    public void setFecha_min(Date fecha_min) {
        this.fecha_min = fecha_min;
    }

    public List<Cls_Socilicitud> getLista() {
        return lista;
    }

    public void setLista(List<Cls_Socilicitud> lista) {
        this.lista = lista;
    }
 

}
