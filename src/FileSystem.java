import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileSystem {
    private ArrayList<Usuario> usuarios;
    private ArrayList<Archivo> archivos;
    private Map<String, Map<String, String>> permisosEspecificos; // archivo -> (usuario -> permisos)

    public FileSystem() {
        usuarios = new ArrayList<>();
        archivos = new ArrayList<>();
        permisosEspecificos = new HashMap<>();
    }

    public void agregarUsuario(Usuario usuario) {
        if (buscarUsuario(usuario.getNombre()) == null) {
            usuarios.add(usuario);
            System.out.println("Usuario agregado: " + usuario.getNombre());
        } else {
            System.out.println("Error: El usuario ya existe!");
        }
    }

    public Usuario buscarUsuario(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        return null;
    }

    public void crearArchivo(String nombre, String contenido, Usuario propietario, String permisos) {
        if (buscarArchivo(nombre) != null) {
            System.out.println("Error: Ya existe un archivo con ese nombre");
            return;
        }
        
        if (!validarPermisos(permisos)) {
            System.out.println("Error: Formato de permisos inválido. Usa formato como 'rwx', 'r--', 'rw-', etc.");
            return;
        }
        
        Archivo nuevoArchivo = new Archivo(nombre, contenido, propietario, permisos);
        archivos.add(nuevoArchivo);
        permisosEspecificos.put(nombre, new HashMap<>());
        System.out.println("Archivo creado: " + nombre + " con permisos: " + permisos);
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
            System.out.println("Archivo no encontrado: " + nombreArchivo);
            return;
        }

        if (puedeUsuarioLeer(archivo, usuario)) {
            System.out.println("=== Contenido de " + nombreArchivo + " ===");
            System.out.println(archivo.getContenido());
            System.out.println("=====================================");
        } else {
            System.out.println("Acceso denegado: No tienes permisos de lectura para " + nombreArchivo);
        }
    }

    public void modificarArchivo(String nombreArchivo, Usuario usuario, String nuevoContenido) {
        Archivo archivo = buscarArchivo(nombreArchivo);
        if (archivo == null) {
            System.out.println("Archivo no encontrado: " + nombreArchivo);
            return;
        }

        if (puedeUsuarioEscribir(archivo, usuario)) {
            archivo.setContenido(nuevoContenido);
            System.out.println("Archivo " + nombreArchivo + " modificado correctamente");
        } else {
            System.out.println("Acceso denegado: No tienes permisos de escritura para " + nombreArchivo);
        }
    }

    public void eliminarArchivo(String nombreArchivo, Usuario usuario) {
        Archivo archivo = buscarArchivo(nombreArchivo);
        if (archivo == null) {
            System.out.println("Archivo no encontrado: " + nombreArchivo);
            return;
        }

        boolean esPropietario = archivo.getPropietario().getNombre().equals(usuario.getNombre());
        if (esPropietario) {
            archivos.remove(archivo);
            permisosEspecificos.remove(nombreArchivo);
            System.out.println("Archivo " + nombreArchivo + " eliminado correctamente");
        } else {
            System.out.println("Acceso denegado: Solo el propietario puede eliminar el archivo");
        }
    }

    public void cambiarPermisos(String nombreArchivo, Usuario usuario, String nuevosPermisos) {
        Archivo archivo = buscarArchivo(nombreArchivo);
        if (archivo == null) {
            System.out.println("Archivo no encontrado: " + nombreArchivo);
            return;
        }

        if (!validarPermisos(nuevosPermisos)) {
            System.out.println("Error: Formato de permisos inválido. Usa formato como 'rwx', 'r--', 'rw-', etc.");
            return;
        }

        boolean esPropietario = archivo.getPropietario().getNombre().equals(usuario.getNombre());
        if (esPropietario) {
            archivo.setPermisos(nuevosPermisos);
            System.out.println("Permisos de " + nombreArchivo + " cambiados a: " + nuevosPermisos);
        } else {
            System.out.println("Acceso denegado: Solo el propietario puede cambiar los permisos del archivo");
        }
    }

    public void asignarPermiso(String nombreArchivo, Usuario usuario, String permisos) {
        Archivo archivo = buscarArchivo(nombreArchivo);
        if (archivo == null) {
            System.out.println("Archivo no encontrado: " + nombreArchivo);
            return;
        }

        if (!validarPermisos(permisos)) {
            System.out.println("Error: Formato de permisos inválido. Usa formato como 'rwx', 'r--', 'rw-', etc.");
            return;
        }

        Map<String, String> permisosArchivo = permisosEspecificos.get(nombreArchivo);
        permisosArchivo.put(usuario.getNombre(), permisos);
        System.out.println("Permisos '" + permisos + "' asignados a " + usuario.getNombre() + " para el archivo " + nombreArchivo);
    }

    public void revocarPermiso(String nombreArchivo, Usuario usuario) {
        Archivo archivo = buscarArchivo(nombreArchivo);
        if (archivo == null) {
            System.out.println("Archivo no encontrado: " + nombreArchivo);
            return;
        }

        Map<String, String> permisosArchivo = permisosEspecificos.get(nombreArchivo);
        if (permisosArchivo.containsKey(usuario.getNombre())) {
            permisosArchivo.remove(usuario.getNombre());
            System.out.println("Permisos revocados para " + usuario.getNombre() + " en el archivo " + nombreArchivo);
        } else {
            System.out.println("El usuario " + usuario.getNombre() + " no tiene permisos específicos para " + nombreArchivo);
        }
    }

    public void mostrarPermisosArchivo(String nombreArchivo) {
        Archivo archivo = buscarArchivo(nombreArchivo);
        if (archivo == null) {
            System.out.println("Archivo no encontrado: " + nombreArchivo);
            return;
        }

        System.out.println("=== Permisos de " + nombreArchivo + " ===");
        System.out.println("Propietario: " + archivo.getPropietario().getNombre() + " - Permisos: " + archivo.getPermisos());
        
        Map<String, String> permisosArchivo = permisosEspecificos.get(nombreArchivo);
        if (permisosArchivo != null && !permisosArchivo.isEmpty()) {
            System.out.println("Permisos específicos:");
            for (Map.Entry<String, String> entry : permisosArchivo.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue());
            }
        } else {
            System.out.println("No hay permisos específicos asignados");
        }
        System.out.println("=====================================");
    }

    public void listarArchivos() {
        if (archivos.isEmpty()) {
            System.out.println("No hay archivos en el sistema");
            return;
        }
        
        System.out.println("=== ARCHIVOS EN EL SISTEMA ===");
        for (Archivo archivo : archivos) {
            System.out.println("Archivo: " + archivo.getNombre() + 
                             " | Propietario: " + archivo.getPropietario().getNombre() + 
                             " | Permisos: " + archivo.getPermisos() +
                             " | Tamaño: " + archivo.getContenido().length() + " caracteres");
        }
        System.out.println("===============================");
    }

    public void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios en el sistema");
            return;
        }
        
        System.out.println("=== USUARIOS DEL SISTEMA ===");
        for (Usuario usuario : usuarios) {
            System.out.println("- " + usuario.getNombre());
        }
        System.out.println("=============================");
    }

    private boolean puedeUsuarioLeer(Archivo archivo, Usuario usuario) {
        // Si es el propietario, usar sus permisos
        if (archivo.getPropietario().getNombre().equals(usuario.getNombre())) {
            return archivo.puedeLeer();
        }
        
        // Verificar permisos específicos
        Map<String, String> permisosArchivo = permisosEspecificos.get(archivo.getNombre());
        if (permisosArchivo != null && permisosArchivo.containsKey(usuario.getNombre())) {
            String permisos = permisosArchivo.get(usuario.getNombre());
            return permisos.charAt(0) == 'r';
        }
        
        // Sin permisos específicos, no puede leer
        return false;
    }

    private boolean puedeUsuarioEscribir(Archivo archivo, Usuario usuario) {
        // Si es el propietario, usar sus permisos
        if (archivo.getPropietario().getNombre().equals(usuario.getNombre())) {
            return archivo.puedeEscribir();
        }
        
        // Verificar permisos específicos
        Map<String, String> permisosArchivo = permisosEspecificos.get(archivo.getNombre());
        if (permisosArchivo != null && permisosArchivo.containsKey(usuario.getNombre())) {
            String permisos = permisosArchivo.get(usuario.getNombre());
            return permisos.length() > 1 && permisos.charAt(1) == 'w';
        }
        
        // Sin permisos específicos, no puede escribir
        return false;
    }

    private boolean puedeUsuarioEjecutar(Archivo archivo, Usuario usuario) {
        // Si es el propietario, usar sus permisos
        if (archivo.getPropietario().getNombre().equals(usuario.getNombre())) {
            return archivo.puedeEjecutar();
        }
        
        // Verificar permisos específicos
        Map<String, String> permisosArchivo = permisosEspecificos.get(archivo.getNombre());
        if (permisosArchivo != null && permisosArchivo.containsKey(usuario.getNombre())) {
            String permisos = permisosArchivo.get(usuario.getNombre());
            return permisos.length() > 2 && permisos.charAt(2) == 'x';
        }
        
        // Sin permisos específicos, no puede ejecutar
        return false;
    }

    private boolean validarPermisos(String permisos) {
        if (permisos == null || permisos.length() != 3) {
            return false;
        }
        
        for (int i = 0; i < 3; i++) {
            char c = permisos.charAt(i);
            if (i == 0 && c != 'r' && c != '-') return false;
            if (i == 1 && c != 'w' && c != '-') return false;
            if (i == 2 && c != 'x' && c != '-') return false;
        }
        
        return true;
    }
}