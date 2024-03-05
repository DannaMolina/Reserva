/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reserva;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;



public class Reserva {

  
    public static void main(String[] args) throws SQLException {
        
         String usuario = "root";
        String password = "123456789";
        String url = "jdbc:mysql://localhost:3308/condominio";


        String Dia;
        String HoraInicio;
        String HoraFin;
        int IdEventos = 0;
        
        String[] opciones = {"Realizar Reserva", "Consultar Reserva", "Actualizar Reserva", "Eliminar Reserva"};

        int opcionSeleccionada = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Menú", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        try (Connection conexion = DriverManager.getConnection(url, usuario, password)) {
            switch (opcionSeleccionada) {
                case 0:

        JOptionPane.showMessageDialog(null, "A continuación se le pedirán los datos para realizar la reserva");

                    Dia = JOptionPane.showInputDialog("Ingrese el día de la reserva ejemplo(2024-12-31)");
                    HoraInicio = JOptionPane.showInputDialog("Ingrese la hora de inicio de su reserva ejemplo(12:60:60)");
                    HoraFin = JOptionPane.showInputDialog("Ingrese la hora de finalización de su reserva ejemplo(2024-12-31 12:60:60)");
                    IdEventos = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del evento"));
        
       
            String sqlreserva = "INSERT INTO reservas (reservas_Dia, reservas_hora_inicio, reservas_hora_fin, eventos_eventos_id) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statementReserva = conexion.prepareStatement(sqlreserva)) {
                        statementReserva.setString(1, Dia);
                        statementReserva.setString(2, HoraInicio);
                        statementReserva.setString(3, HoraFin);
                        statementReserva.setInt(4,IdEventos);
                    
                        

                    int filasInsertadasReserva = statementReserva.executeUpdate();
                    if (filasInsertadasReserva > 0) {
                         JOptionPane.showMessageDialog(null, "La reserva fue registrada correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo realizar la reserva. Por favor, inténtelo de nuevo más tarde.");
                    }
                }
                 break;
                case 1:
                    //Consultar Reserva
                    ConsultarReserva(conexion);
                    break;
                    
                case 2:
                    //Actualizar Reserva
                    int idReservaActualizar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la Reserva a actualizar"));
                    String nuevoDia = JOptionPane.showInputDialog("Ingrese el nuevo dia:");
                    String nuevaHora = JOptionPane.showInputDialog("Ingrese la nueva hora");
                    ActualizarReserva(conexion, idReservaActualizar, nuevoDia, nuevaHora);
                    break;
                case 3:
                    //Eliminar
                    int idReservaEliminar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la Persona a eliminar"));
                    EliminarReserva(conexion, idReservaEliminar);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida");
                    break;
            }

            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
     }
    

    // Método para consultar todos los guardias en la base de datos
   public static void ConsultarReserva(Connection conexion) throws SQLException {
    String sqlConsulta = "SELECT r.reservas_id, r.reservas_dia, r.reservas_hora_inicio, r.reservas_hora_fin, r.eventos_eventos_id " +
                         "FROM reservas r " +
                         "JOIN eventos e ON e.eventos_id = r.eventos_eventos_id";
    PreparedStatement statementConsulta = conexion.prepareStatement(sqlConsulta);
    ResultSet resultSet = statementConsulta.executeQuery();
    while (resultSet.next()) {
        System.out.println("ID: " + resultSet.getString("reservas_id"));
        System.out.println("Día: " + resultSet.getString("reservas_dia"));
        System.out.println("Hora de inicio: " + resultSet.getString("reservas_hora_inicio"));
        System.out.println("Hora fin: " + resultSet.getString("reservas_hora_fin"));
        System.out.println("ID Evento: " + resultSet.getString("eventos_eventos_id"));
    }
}
  // Método para actualizar
    public static void ActualizarReserva(Connection connection, int idReserva, String Dia, String HoraInicio) throws SQLException {
        String sqlUpdate = "UPDATE reservas SET reservas_dia = ?, reservas_hora_inicio = ? WHERE reservas_id = ?";
        PreparedStatement statementUpdate = connection.prepareStatement(sqlUpdate);
        statementUpdate.setString(1, Dia);
        statementUpdate.setString(2, HoraInicio);
        statementUpdate.setInt(3, idReserva);
        statementUpdate.executeUpdate();
    }
public static void EliminarReserva(Connection conexion, int idReserva) throws SQLException {
    try {
        // Eliminar las filas en la tabla reservas 
        String sqlDeleteReserva = "DELETE FROM reservas WHERE reservas_id = ?";
        PreparedStatement statementDeleteReserva = conexion.prepareStatement(sqlDeleteReserva);
        statementDeleteReserva.setInt(1, idReserva);
        statementDeleteReserva.executeUpdate();

        System.out.println("Reserva eliminada exitosamente");
    } catch (SQLException e) {
        System.out.println("Error al intentar eliminar la Reserva: " + e.getMessage());
    }
}

   

   
    
}


