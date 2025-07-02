import java.util.Scanner;

public class Main {
    private static FileSystem fs = new FileSystem();
    private static Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        System.out.println("=== SIMULADOR DE FILE SYSTEM ===");
        
        // Crear algunos usuarios por defecto
        fs.agregarUsuario(new Usuario("admin"));
        fs.agregarUsuario(new Usuario("user1"));
        fs.agregarUsuario(new Usuario("user2"));
        
        // Crear algunos archivos de ejemplo
        Usuario admin = fs.buscarUsuario("admin");
        Usuario user1 = fs.buscarUsuario("user1");
        
        fs.crearArchivo("sistema.txt", "Archivo del sistema", admin, "r--");
        fs.crearArchivo("publico.txt", "Archivo público", admin, "rw-");
        fs.crearArchivo("privado.txt", "Archivo privado", user1, "---");
        
        // Agregar permisos específicos
        fs.asignarPermiso("sistema.txt", user1, "r--");
        fs.asignarPermiso("publico.txt", user1, "rw-");
        
        mostrarMenu();
    }
    
    private static void mostrarMenu() {
        while (true) {
            System.out.println("\n==========================================");
            if (usuarioActual != null) {
                System.out.println("Usuario actual: " + usuarioActual.getNombre());
            } else {
                System.out.println("No hay usuario logueado");
            }
            System.out.println("==========================================");
            System.out.println("1. Login");
            System.out.println("2. Crear usuario");
            System.out.println("3. Listar archivos");
            System.out.println("4. Crear archivo");
            System.out.println("5. Leer archivo");
            System.out.println("6. Modificar archivo");
            System.out.println("7. Eliminar archivo");
            System.out.println("8. Cambiar permisos de archivo");
            System.out.println("9. Asignar permiso a usuario");
            System.out.println("10. Revocar permiso a usuario");
            System.out.println("11. Ver permisos de archivo");
            System.out.println("12. Listar usuarios");
            System.out.println("13. Logout");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            
            switch (opcion) {
                case 1: login(); break;
                case 2: crearUsuario(); break;
                case 3: listarArchivos(); break;
                case 4: crearArchivo(); break;
                case 5: leerArchivo(); break;
                case 6: modificarArchivo(); break;
                case 7: eliminarArchivo(); break;
                case 8: cambiarPermisos(); break;
                case 9: asignarPermiso(); break;
                case 10: revocarPermiso(); break;
                case 11: verPermisos(); break;
                case 12: listarUsuarios(); break;
                case 13: logout(); break;
                case 0: 
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
    
    private static void login() {
        System.out.print("Ingresa tu nombre de usuario: ");
        String nombre = scanner.nextLine();
        Usuario usuario = fs.buscarUsuario(nombre);
        
        if (usuario != null) {
            usuarioActual = usuario;
            System.out.println("Login exitoso. Bienvenido " + nombre + "!");
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
    
    private static void crearUsuario() {
        System.out.print("Nombre del nuevo usuario: ");
        String nombre = scanner.nextLine();
        Usuario nuevoUsuario = new Usuario(nombre);
        fs.agregarUsuario(nuevoUsuario);
    }
    
    private static void listarArchivos() {
        fs.listarArchivos();
    }
    
    private static void crearArchivo() {
        if (usuarioActual == null) {
            System.out.println("Debes estar logueado para crear archivos.");
            return;
        }
        
        System.out.print("Nombre del archivo: ");
        String nombre = scanner.nextLine();
        System.out.print("Contenido inicial: ");
        String contenido = scanner.nextLine();
        System.out.print("Permisos (ej: rwx, r--, rw-): ");
        String permisos = scanner.nextLine();
        
        fs.crearArchivo(nombre, contenido, usuarioActual, permisos);
    }
    
    private static void leerArchivo() {
        if (usuarioActual == null) {
            System.out.println("Debes estar logueado para leer archivos.");
            return;
        }
        
        System.out.print("Nombre del archivo a leer: ");
        String nombre = scanner.nextLine();
        fs.leerArchivo(nombre, usuarioActual);
    }
    
    private static void modificarArchivo() {
        if (usuarioActual == null) {
            System.out.println("Debes estar logueado para modificar archivos.");
            return;
        }
        
        System.out.print("Nombre del archivo a modificar: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo contenido: ");
        String contenido = scanner.nextLine();
        fs.modificarArchivo(nombre, usuarioActual, contenido);
    }
    
    private static void eliminarArchivo() {
        if (usuarioActual == null) {
            System.out.println("Debes estar logueado para eliminar archivos.");
            return;
        }
        
        System.out.print("Nombre del archivo a eliminar: ");
        String nombre = scanner.nextLine();
        fs.eliminarArchivo(nombre, usuarioActual);
    }
    
    private static void cambiarPermisos() {
        if (usuarioActual == null) {
            System.out.println("Debes estar logueado para cambiar permisos.");
            return;
        }
        
        System.out.print("Nombre del archivo: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevos permisos (ej: rwx, r--, rw-): ");
        String permisos = scanner.nextLine();
        fs.cambiarPermisos(nombre, usuarioActual, permisos);
    }
    
    private static void asignarPermiso() {
        if (usuarioActual == null) {
            System.out.println("Debes estar logueado.");
            return;
        }
        
        System.out.print("Nombre del archivo: ");
        String nombreArchivo = scanner.nextLine();
        System.out.print("Usuario al que asignar permiso: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Permisos a asignar (ej: r--, rw-, rwx): ");
        String permisos = scanner.nextLine();
        
        Usuario usuario = fs.buscarUsuario(nombreUsuario);
        if (usuario != null) {
            fs.asignarPermiso(nombreArchivo, usuario, permisos);
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
    
    private static void revocarPermiso() {
        if (usuarioActual == null) {
            System.out.println("Debes estar logueado.");
            return;
        }
        
        System.out.print("Nombre del archivo: ");
        String nombreArchivo = scanner.nextLine();
        System.out.print("Usuario al que revocar permiso: ");
        String nombreUsuario = scanner.nextLine();
        
        Usuario usuario = fs.buscarUsuario(nombreUsuario);
        if (usuario != null) {
            fs.revocarPermiso(nombreArchivo, usuario);
        } else {
            System.out.println("Usuario no encontrado.");
        }
    }
    
    private static void verPermisos() {
        System.out.print("Nombre del archivo: ");
        String nombre = scanner.nextLine();
        fs.mostrarPermisosArchivo(nombre);
    }
    
    private static void listarUsuarios() {
        fs.listarUsuarios();
    }
    
    private static void logout() {
        if (usuarioActual != null) {
            System.out.println("Logout exitoso. Hasta luego " + usuarioActual.getNombre() + "!");
            usuarioActual = null;
        } else {
            System.out.println("No hay ningún usuario logueado.");
        }
    }
}