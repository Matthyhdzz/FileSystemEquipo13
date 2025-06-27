public class Archivo {      //Seteamos cada uno de los atributos de un archivo
    private String nombre;
    private String contenido;
    private Usuario propietario;
    private String permisos;

    public Archivo(String nombre, String contenido, Usuario propietario, String permisos){
        this.nombre = nombre;
        this.contenido = contenido;
        this.propietario = propietario;
        this.permisos = permisos;

    }
    public String getNombre(){
        return nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public String getPermisos() {
        return permisos;
    }

    public Usuario getPropietario() {
        return propietario;
    }
    public void setContenido() {
        this.contenido = contenido;

    }
    public void setPermisos(){
        this.permisos = permisos;
    }
    public boolean puedeLeer() {
        return permisos.contains("r");
    }

    public boolean puedeEscribir() {
        return permisos.contains("w");
    }

    public boolean puedeEjecutar() {
        return permisos.contains("x");
    }
}
