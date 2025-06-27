import java.util.ArrayList;

public class FileSystem {
    private ArrayList<Usuario> usuarios;    // Lista de usuarios
    private ArrayList<Archivo> archivos;    // Lista de archivos

    public FileSystem() {
        usuarios = new ArrayList<>();
        archivos = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void crearArchivo(String nombre, String contenido, Usuario propietario, String permisos) {
        Archivo nuevoArchivo = new Archivo(nombre, contenido, propietario, permisos);
        archivos.add(nuevoArchivo);
    }

    public Archivo buscarArchivo(String nombre) {
        for (Archivo archivo : archivos) {
            if (archivo.getNombre().equals(nombre)) {
                return archivo;
            }
        }
        return null;
    }

    public void listarArchivos() {
        for (Archivo archivo : archivos) {
            System.out.println("Archivo: " + archivo.getNombre() +
                    ", Propietario: " + archivo.getPropietario().getNombre() +
                    ", Permisos: " + archivo.getPermisos());
        }
    }
}
