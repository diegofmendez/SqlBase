/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Mysql_prueba2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Inserccion {
    private final String tabla = "tabla1";

    public void guardar(Connection conexion, String nombre,int cedula,int edad){
        try {
            String instruccion = "INSERT INTO "+this.tabla+"(nombre, cedula, edad) VALUES(?, ?, ?)";    
            PreparedStatement consulta = conexion.prepareStatement(instruccion);
            consulta.setString(1, nombre);
            consulta.setInt(2, cedula);
            consulta.setInt(3, edad);

            System.out.println("Se efectuo la operacion de escritura");
            consulta.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al guardar datos"+ex);
            
        }

    }
    
    public void recuperarPorId(Connection conexion, int id) {
        try {
            String instruccion = "SELECT nombre, cedula, edad FROM " + this.tabla + " WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(instruccion);
            consulta.setInt(1, id);

            System.out.println("Ejecutando consulta: " + consulta.toString()); // Verifica la consulta generada

            ResultSet resultado = consulta.executeQuery();

            if (!resultado.isBeforeFirst()) { // Verifica si hay resultados antes de iterar
                System.out.println("No se encontraron registros con ID: " + id);
                return;
            }

            while (resultado.next()) {  // Recorre los resultados
                System.out.println("ID: " + id);
                System.out.println("Nombre: " + resultado.getString("nombre"));
                System.out.println("Cédula: " + resultado.getInt("cedula"));
                System.out.println("Edad: " + resultado.getInt("edad"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al recuperar datos: " + ex);
        }
    }
    
    public void borrarPorId(Connection conexion, int id) {
        try {
            String instruccion = "DELETE FROM " + this.tabla + " WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(instruccion);
            consulta.setInt(1, id);

            int filasAfectadas = consulta.executeUpdate(); // Ejecuta la eliminación

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún registro con ID: " + id, "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void editarPorId(Connection conexion, int id, String nombre, int cedula, int edad) {
        try {
            String instruccion = "UPDATE " + this.tabla + " SET nombre = ?, cedula = ?, edad = ? WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(instruccion);
            consulta.setString(1, nombre);
            consulta.setInt(2, cedula);
            consulta.setInt(3, edad);
            consulta.setInt(4, id);

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Registro actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún registro con ID: " + id, "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
