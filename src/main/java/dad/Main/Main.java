package dad.Main;

import dad.Conexion.DatabaseInitializer;
import javafx.application.*;

public class Main {
    public static void main(String[] args) {

        try {
            // Verificar y crear la base de datos (si no existe) primero
            DatabaseInitializer.initializeDatabase();
            System.out.println("Base de datos inicializada correctamente.");

        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

        Application.launch(ProyectoFTP.class,args);
    }
}
