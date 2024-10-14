package co.edu.unipiloto.persistenciaconsqlite;

public class Proyecto {
    private int id;
    private String Nproyecto;
    private String nombre;
    private String email;
    private String direccion;
    private String localidad;
    private String tipodeproyecto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNproyecto() {
        return Nproyecto;
    }

    public void setNproyecto(String nproyecto) {
        Nproyecto = nproyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTipodeproyecto() {
        return tipodeproyecto;
    }

    public void setTipodeproyecto(String tipodeproyecto) {
        this.tipodeproyecto = tipodeproyecto;
    }

    @Override
    public String toString() {
        return Nproyecto + " - " + nombre;
    }
}

