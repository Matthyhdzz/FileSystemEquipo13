import java.util.ArrayList;

public class FileSystem {
    private ArrayList<Usuario> usuarios;    // Lista de usuarios
    private ArrayList<Archivo> archivos;    // Lista de archivos

    public FileSystem() {
        usuarios = new ArrayList<>();
        archivos = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        if (buscarUsuario(usuario.getNombre()) == null){
            usuarios.add(usuario);
            System.out.println("Usuario agregado: " + usuario.getNombre());
        } else {
            System.out.println("Error: El usuario ya existe!");
        }
    }

    public Usuario buscarUsuario(String nombre) {
        for (Usuario usuario : usuarios){
            if (usuario.getNombre().equals((nombre))){
                return usuario;
            }
        }
        return null;
    }

    public void crearArchivo(String nombre, String contenido, Usuario propietario, String permisos) {
        if(buscarArchivo(nombre) != null){
            System.out.println("Error: Ya existe un archivo con ese nombre");
            return;
        }
        Archivo nuevoArchivo = new Archivo(nombre, contenido, propietario, permisos);
        archivos.add(nuevoArchivo);
        System.out.println("Archivo creado: " + nombre);
    }

    public Archivo buscarArchivo(String nombre) {
        for (Archivo archivo : archivos) {
            if (archivo.getNombre().equals(nombre)) {
                return archivo;
            }
        }
        return null;
    }

    public void leerArchivo(String nombreArchivo, Usuario usuario) {
        Archivo archivo = buscarArchivo(nombreArchivo);
        if (archivo == null) {
            System.out.println("Archivo no encontrado.");
            return;
        }

        boolean esPropietario = archivo.getPropietario().getNombre().equals(usuario.getNombre());
        if  (esPropietario || archivo.puedeLeer()) {
            System.out.println("Contenido: " + archivo.getContenido());
        } else {
            System.out.println("Acceso denegado.");
        }
    }

    public void modificarArchivo(String nombreArchivo,Usuario usuario, String nuevoContenido){
        Archivo archivo = buscarArchivo(nombreArchivo);
        if (archivo == null){
            System.out.println("Archivo no encontrado");
            return;

        }
        boolean esPropietario = archivo.getPropietario().getNombre().equals(usuario.getNombre());
        if (esPropietario || archivo.puedeEscribir()){
            archivo.setContenido(nuevoContenido);
            System.out.println("Se modificó el archivo " + archivo.getNombre() + " correctamente");

        }else{
            System.out.println("Acceso al archivo " + archivo.getNombre() +"denegado, no tienes los permisos requeridos");
        }
    }

    public void listarArchivos() {
        for (Archivo archivo : archivos) {
            System.out.println("Archivo: " + archivo.getNombre() + ", Propietario: " + archivo.getPropietario().getNombre() + ", Permisos: " + archivo.getPermisos());
        }
    }
}
