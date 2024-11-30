package dad.Model;

import javafx.beans.property.*;

public class tutorEmpresa {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty contacto = new SimpleStringProperty();
    private final IntegerProperty Id_Empresa = new SimpleIntegerProperty();
    private final StringProperty nombreEmpresa = new SimpleStringProperty();

    public tutorEmpresa() {
    }


    public tutorEmpresa(int id, String nombre, String contacto, int idEmpresa, String nombreEmpresa) {
        this.id.set(id);
        this.nombre.set(nombre);
        this.contacto.set(contacto);
        this.Id_Empresa.set(idEmpresa);
        this.nombreEmpresa.set(nombreEmpresa);
    }


    public int getId() {
        return id.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getContacto() {
        return contacto.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public void setContacto(String contacto) {
        this.contacto.set(contacto);
    }

    public void setNombreEmpresa(String nombreEmpresa){
        this.nombreEmpresa.set(nombreEmpresa);
    }
    public int getId_Empresa() {
        return Id_Empresa.get();
    }

    public IntegerProperty id_EmpresaProperty() {
        return Id_Empresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa.get();
    }

    public StringProperty nombreEmpresaProperty() {
        return nombreEmpresa;
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty contactoProperty() {
        return contacto;
    }
}
