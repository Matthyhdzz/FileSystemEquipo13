    public class Main {
        public static void main(String[] args) {
            FileSystem fs = new FileSystem();

            // Se crean los usuarios
            Usuario user1 = new Usuario("Mati");
            Usuario user2 = new Usuario("Fabri");
            Usuario user3 = new Usuario("Mati");

            System.out.println("\nSe agregan los usuarios al sistema");
            fs.agregarUsuario(user1);
            fs.agregarUsuario(user2);
            fs.agregarUsuario(user3); // Nombre repetido a propósito para verificar que ya existe el usuario

            System.out.println("\nSe crean los archivos");
            fs.crearArchivo("Documento.txt", "Hola mundo", user1, "r");
            fs.crearArchivo("Script.sh", "echo hola ", user2, "rw");

            System.out.println("\nListado de archivos actuales:");
            fs.listarArchivos();

            System.out.println("\nPrueba de lectura de los archivos");
            fs.leerArchivo("Documento.txt", user1); // Debería leer el contenido
            fs.leerArchivo("Script.sh", user1);     // Debería dar acceso denegado
            fs.leerArchivo("Script.sh", user2);     // Debería leer el contenido
            fs.leerArchivo("NoExiste", user1);      // Debe mostrar mensaje de archivo no encontrado


            System.out.println("\n---- Prueba de modificación de los archivos ----");
            fs.modificarArchivo("Documento.txt", user1, "Este doc fue modificado!");
            fs.modificarArchivo("Script.sh", user2, "Este script fue modificado");

            System.out.println("\nLectura luego de la modificación");
            fs.leerArchivo("Documento.txt", user1);
            fs.leerArchivo("Script.sh", user2);
        }
    }
