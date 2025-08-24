package datos;


import dominio.Cliente;
import zona_fit.conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteDAO implements IClienteDAO{


    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes =new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con= Conexion.getConection();
        var sql = "SELECT * FROM cliente ORDER BY id";
        try {
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()){
                var cliente=new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membrecia"));
                clientes.add(cliente);
            }
        }catch (Exception e){
            System.out.println("Error al listar Clientes: "+e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error pe "+e.getMessage());
            }
        }
        clientes.forEach(System.out::println );
        return clientes;  // <-- devuelve la lista que llenaste

    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {


        PreparedStatement ps;
        ResultSet rs;
        var con= Conexion.getConection();
        var sql= "SELECT * FROM cliente WHERE id = ?";
                try{
                    ps =con.prepareStatement(sql);
                    ps.setInt(1,cliente.getId());
                    rs= ps.executeQuery();
                    if (rs.next()){
                        cliente.setNombre(rs.getString("nombre"));
                        cliente.setApellido(rs.getString("apellido"));
                        cliente.setMembresia(rs.getInt("membrecia"));
                        return true;

                    }
                } catch (Exception e) {
                    System.out.println("Error al recuperar cliente por id: "+e.getMessage());
                }
                finally {
                    try {
                        con.close();
                    }catch (Exception e){
                        System.out.println("Error al cerrar conexion: "+e.getMessage());
                    }
                }

        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement  ps;
        ResultSet rs;
        var con= Conexion.getConection();
        var sql = "INSERT INTO cliente (nombre, apellido, membrecia) VALUES (?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
            return true;


        } catch (Exception e) {
            System.out.println("Error al insertar cliente a bd: "+e.getMessage());
        }finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion: "+e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {

        PreparedStatement ps;
        ResultSet rs;
        Connection con=Conexion.getConection();
        var sql="Update cliente SET nombre=?, apellido=?, membrecia=? WHERE id=?";
        try {
            ps=con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error al modificar Cliente: "+e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion: "+e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con= Conexion.getConection();
        String sql="DELETE FROM cliente WHERE id=?";
        try {
            ps=con.prepareStatement(sql);
            ps.setInt(1,cliente.getId());
            ps.execute();
            return true;

        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: "+e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexion"+e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {
        //Listar Clientes

          IClienteDAO clienteDAO= new ClienteDAO();


        //Buscar por id:
//        var cliente1=new Cliente(1);
//        System.out.println("Cliente antes de la busqueda: "+cliente1);
//        var encontrado= clienteDAO.buscarClientePorId(cliente1);
//        if (encontrado){
//            System.out.println("Cliente encontrado: "+cliente1);
//        }else
//            System.out.println("No se encontro registro de "+cliente1.getId());

        //agregarcliente
//        var nuevoCliente=new Cliente("Daniel","Ortiz",203);
//        var agregado = clienteDAO.agregarCliente(nuevoCliente);
//        if (agregado){
//            System.out.println("Cliente agregado: "+nuevoCliente);
//        }else
//            System.out.println("Error al agregar cliente: "+nuevoCliente);


        //modificar cliente:
//        var modificarCliente=new Cliente(1, "Gabriel de Jesus","Ortiz",150);
//        var modificado= clienteDAO.modificarCliente(modificarCliente);
//        if (modificado){
//            System.out.println("Cliente modificado: "+modificarCliente);
//        }else
//            System.out.println("No se modifico cliente: "+modificarCliente);


        //Eliminar un cliente
//        var eliminarCliente=new Cliente(343);
//        var eliminar=clienteDAO.eliminarCliente(eliminarCliente);
//        if (eliminar){
//            System.out.println("Cliente eliminado "+eliminarCliente);
//        }else
//            System.out.println("Error al eliminar cliente: "+eliminarCliente);
//
//        System.out.println("***Listar clientesx***");
//        var clientes =clienteDAO.listarClientes();
//        clientes.forEach(System.out::println );




        var entrada=new Scanner(System.in);
        var salir=false;
        do{
            try {
                System.out.print("""
                    ***Zona fit (GYM)
                    1.Listar Clientes
                    2.Buscar Clientes
                    3.Agregar Cliente
                    4.Modificar cliente
                    5.Eliminar Cliente
                    6.Salir
                    Elije una opcion:\s""");
                var opcion=Integer.parseInt(entrada.nextLine());
                switch (opcion){
                    case 1-> clienteDAO.listarClientes();
                    case 2->{
                        System.out.print("Ingresa el id a buscar: ");
                        var id=Integer.parseInt(entrada.nextLine());
                        var cliente1=new Cliente(id);
                        var encontrado=clienteDAO.buscarClientePorId(cliente1);
                        if (encontrado){
                            System.out.println("cliente encontrado: "+cliente1);
                        }else
                            System.out.println("cliente no encontrado: "+cliente1);
                    }
                    case 3->{try {
                        System.out.print("Ingresa el nombre de el cliente: ");
                        var nombre=entrada.nextLine();
                        System.out.print("Ingresa el apellido");
                        var apellido=entrada.nextLine();
                        System.out.print("Introduce la membresia: ");
                        var membresia=Integer.parseInt(entrada.nextLine());
                        var nuevoCliente=new Cliente(nombre,apellido,membresia);
                        var agregado = clienteDAO.agregarCliente(nuevoCliente);
                        if (agregado ){
                            System.out.println("Cliente agregado: "+nuevoCliente);
                        }else
                            System.out.println("Error al agregar cliente: "+nuevoCliente);

                    } catch (Exception e) {
                        System.out.println("Error: "+e.getMessage());
                    }
                    }
                    case 4->{
                        try {
                            System.out.println("Ingresa los sifuientes datos a modificar");
                            System.out.print("Ingresa el id a modificar: ");
                            var id=Integer.parseInt(entrada.nextLine());
                            System.out.print("ingresa el nombre");
                            var nombre=entrada.nextLine();
                            System.out.println("ingresa el apellido");
                            var apellido=entrada.nextLine();
                            System.out.print("Ingresa la membresia: ");
                            var membresia=Integer.parseInt(entrada.nextLine());
                            var nuevoCliente=new Cliente(id,nombre,apellido,membresia);
                            var modificar=clienteDAO.modificarCliente(nuevoCliente);
                            if (modificar){
                                System.out.println("Modificacion correcta: "+nuevoCliente);
                            }else
                                System.out.println("Error al modificar"+nuevoCliente);
                        } catch (Exception e) {
                            System.out.println("Error: "+e.getMessage());
                        }
                    }
                    case 5->{
                        try {
                            System.out.println("Ingresa el id a Eliminar: ");
                            var id=Integer.parseInt(entrada.nextLine());
                            var eliminarCliente=new Cliente(id);
                            var eliminar=clienteDAO.eliminarCliente(eliminarCliente);
                            if (eliminar){
                                System.out.println("Cliente eliminado: "+eliminarCliente);
                            }else
                                System.out.println("Error");
                        } catch (Exception e) {
                            System.out.println("Error: "+e.getMessage());
                        }

                    }
                    case 6->salir=true;
                    default -> System.out.println("Opcion no valida intentao de nuevo: ");
                }
            } catch (Exception e) {
                System.out.println("Error: "+e.getMessage()+", Intentalo de nuevo:");
            }

        }while (!salir);
    }
}
