package zona_fit.conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    public static Connection getConection(){
        Connection conexion =null;
        var basedatos="zona_fit_bd";
        var url = "jdbc:mysql://localhost:3306/"+basedatos;
        var usuario ="root";
        var password="admin";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion= DriverManager.getConnection(url,usuario,password);
        }catch (Exception e){
            System.out.println("Error al conectarnos a la base datos: "+e.getMessage());
        }


        return conexion;
    }

    public static void main(String[] args) {
        var conexion =Conexion.getConection();
        if (conexion!=null){
            System.out.println("Conexion exitosa: "+conexion);
        }else
            System.out.println("Error al conectarse: ");
    }
}
