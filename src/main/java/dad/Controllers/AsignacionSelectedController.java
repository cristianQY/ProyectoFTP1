package dad.Controllers;

import dad.Conexion.*;
import dad.Model.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.net.*;
import java.sql.*;
import java.time.*;
import java.util.*;

public class AsignacionSelectedController implements Initializable {

    @FXML
    private Button Añadir;

    @FXML
    private Button Cancelar;

    @FXML
    private Button Eliminar;

    @FXML
    private DatePicker FechaFin;

    @FXML
    private DatePicker FechaInicio;

    @FXML
    private ChoiceBox<Integer> IdAlumno;

    @FXML
    private ChoiceBox<Integer> IdEmpresa;

    @FXML
    private ChoiceBox<Integer> IdTutorDocente;

    @FXML
    private ChoiceBox<Integer> IdTutorEmpresa;

    @FXML
    private BorderPane root;

    private Asignacion asignacionSeleccionado;

    @FXML
    void onAnadirAction(ActionEvent event) {
        String fechaInicio = FechaInicio.getValue().toString();
        String fechaFin = FechaFin.getValue().toString();
        Integer idAlumno = IdAlumno.getValue();
        Integer idEmpresa = IdEmpresa.getValue();
        Integer idTutorDocente = IdTutorDocente.getValue();
        Integer idTutorEmpresa = IdTutorEmpresa.getValue();


        String sql = "UPDATE alumnos_empresas_rel SET Id_Alumno=?, Id_Empresa=?, Id_Tutor_Empresa=?, Id_Tutor_Docente=?, Fecha_Inicio=?, Fecha_Fin=? WHERE Id_Asignacion=?";

        try (Connection con = Conectar.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idEmpresa);
            stmt.setInt(3, idTutorEmpresa);
            stmt.setInt(4, idTutorDocente);
            stmt.setString(5, fechaInicio);
            stmt.setString(6, fechaFin);
            stmt.setInt(7, asignacionSeleccionado.getIdAsignacion());

            // Ejecutar la actualización
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("La asignación ha sido actualizada con éxito.");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Base de datos actualizada");
            } else {
                System.err.println("No se encontró una asignación con ese ID.");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar la asignación: " + e.getMessage());
        }
    }

    @FXML
    void onCancelarAction(ActionEvent event) {
        cerrar();
    }


    private void cerrar(){
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onEliminarAction(ActionEvent event) {
            IdAlumno.setValue(null);
            IdEmpresa.setValue(null);
            IdTutorEmpresa.setValue(null);
            IdTutorDocente.setValue(null);
            FechaInicio.setValue(null);
            FechaFin.setValue(null);
    }

    public void obtenerAsignacion(Asignacion asignacion) {
        if (asignacion != null){
            this.asignacionSeleccionado = asignacion;
            IdAlumno.valueProperty().bindBidirectional(asignacion.idAlumnoProperty().asObject());
            IdEmpresa.valueProperty().bindBidirectional(asignacion.idEmpresaProperty().asObject());
            IdTutorDocente.valueProperty().bindBidirectional(asignacion.idTutorGrupoProperty().asObject());
            IdTutorEmpresa.valueProperty().bindBidirectional(asignacion.idTutorEmpresaProperty().asObject());
            FechaInicio.valueProperty().bindBidirectional(asignacion.fechaInicioProperty());
            FechaFin.valueProperty().bindBidirectional(asignacion.fechaFinProperty());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatosChoiceBoxes();
    }

    private void cargarDatosChoiceBoxes() {
        List<Integer> alumnos = obtenerAlumnos();
        List<Integer> empresas = obtenerEmpresas();
        List<Integer> tutoresDocente = obtenerTutoresDocente();
        List<Integer> tutoresEmpresa = obtenerTutoresEmpresa();
        IdAlumno.getItems().setAll(alumnos);
        IdEmpresa.getItems().setAll(empresas);
        IdTutorDocente.getItems().setAll(tutoresDocente);
        IdTutorEmpresa.getItems().setAll(tutoresEmpresa);
    }


    private List<Integer> obtenerAlumnos() {
        List<Integer> alumnos = new ArrayList<>();
        try (Connection con = Conectar.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Id_Alumno FROM alumno")) {

            while (rs.next()) {
                alumnos.add(rs.getInt("Id_Alumno"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    private List<Integer> obtenerEmpresas() {
        List<Integer> empresas = new ArrayList<>();
        try (Connection con = Conectar.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Id_Empresa FROM empresas")) {

            while (rs.next()) {
                empresas.add(rs.getInt("Id_Empresa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresas;
    }

    private List<Integer> obtenerTutoresDocente() {
        List<Integer> tutoresDocente = new ArrayList<>();
        try (Connection con = Conectar.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Id_Tutor FROM tutor_grupo")) {

            while (rs.next()) {
                tutoresDocente.add(rs.getInt("Id_Tutor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutoresDocente;
    }

    private List<Integer> obtenerTutoresEmpresa() {
        List<Integer> tutoresEmpresa = new ArrayList<>();
        try (Connection con = Conectar.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Id_Tutor FROM tutor_empresa")) {

            while (rs.next()) {
                tutoresEmpresa.add(rs.getInt("Id_Tutor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutoresEmpresa;
    }



    public Button getAñadir() {
        return Añadir;
    }

    public void setAñadir(Button añadir) {
        Añadir = añadir;
    }

    public Button getCancelar() {
        return Cancelar;
    }

    public void setCancelar(Button cancelar) {
        Cancelar = cancelar;
    }

    public Button getEliminar() {
        return Eliminar;
    }

    public void setEliminar(Button eliminar) {
        Eliminar = eliminar;
    }

    public DatePicker getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(DatePicker fechaFin) {
        FechaFin = fechaFin;
    }

    public DatePicker getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(DatePicker fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public ChoiceBox<Integer> getIdAlumno() {
        return IdAlumno;
    }

    public void setIdAlumno(ChoiceBox<Integer> idAlumno) {
        IdAlumno = idAlumno;
    }

    public ChoiceBox<Integer> getIdEmpresa() {
        return IdEmpresa;
    }

    public void setIdEmpresa(ChoiceBox<Integer> idEmpresa) {
        IdEmpresa = idEmpresa;
    }

    public ChoiceBox<Integer> getIdTutorDocente() {
        return IdTutorDocente;
    }

    public void setIdTutorDocente(ChoiceBox<Integer> idTutorDocente) {
        IdTutorDocente = idTutorDocente;
    }

    public ChoiceBox<Integer> getIdTutorEmpresa() {
        return IdTutorEmpresa;
    }

    public void setIdTutorEmpresa(ChoiceBox<Integer> idTutorEmpresa) {
        IdTutorEmpresa = idTutorEmpresa;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
}
