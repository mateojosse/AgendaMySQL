package agendamysql;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mateo
 */
public class ConexionDAO {
    Connection conexion = null;
    List<DatosDTO> listaDatos = new ArrayList<>();
private void conecta(){
    String user="root";
    String password="admin";
    String Url="jdbc:mysql://localhost:3306/4to20201?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection(Url, user, password);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(ConexionDAO.class.getName()).log(Level.SEVERE, null, ex);
    }   catch (SQLException ex) {
            Logger.getLogger(ConexionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
}    
public boolean inserta(DatosDTO datos){
    boolean estado = true;
    try{
        conecta();
        PreparedStatement ps = conexion.prepareStatement("insert in to datos(Nombre,Edad,Sexo) value (?,?,?)");
       ps.setString(1, datos.getNombre());
       ps.setString(2, datos.getEdad());
       ps.setString(3, datos.getSexo());
       ps.execute();
    }catch(SQLException ex){
        estado = false;
    }finally{
        cerrar();
    }return estado;
}
public boolean cargar(){
    boolean estado = true;
    DatosDTO datos;
    try{
        PreparedStatement ps = conexion.prepareStatement("select*from datos");
        ResultSet resultado = ps.executeQuery();
        while(resultado.next()){
            datos = new DatosDTO();
            datos.setId(resultado.getInt("id"));
            datos.setNombre(resultado.getString("Nombre"));
            datos.setEdad(resultado.getString("Edad"));
            datos.setSexo(resultado.getString("Sexo"));
            listaDatos.add(datos);
        }
    }catch(SQLException ex){
        estado = false;
        
    }finally{
        cerrar();
    }
    return estado;
}
public boolean actuliza(DatosDTO datos){
    boolean estado = true;
    try{
        PreparedStatement ps = conexion.prepareStatement("update datos set Nombre = ?, Edad=?, Sexo=? where id=?");
    ps.setString(1, datos.getNombre());
    ps.setString(2, datos.getEdad());
    ps.setString(3, datos.getSexo());
    ps.setInt(4, datos.getId());
    ps.execute();
    }catch(SQLException ex){
        estado=false;
    }finally{
        cerrar();
    }
    return estado;
}
public boolean eliminar(DatosDTO datos){
    boolean estado = true;
    try{
        PreparedStatement ps= conexion.prepareStatement("delete from datos where id=?");
        ps.setInt(1, datos.getId());
        ps.execute();
    }catch(SQLException ex){
        estado = false;
    }finally{
        cerrar();
    }return estado;
}
public List<DatosDTO> getDatos(){
    return listaDatos;
}
private void cerrar(){
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
   } 
}
