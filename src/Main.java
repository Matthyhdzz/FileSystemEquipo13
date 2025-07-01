public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        Usuario user1 = new Usuario("Mati");
        Usuario user2 = new Usuario("Fabri");
        Usuario user3 = new Usuario("Mati");

        System.out.println(" ");

        fs.agregarUsuario(user1);
        fs.agregarUsuario(user2);
        fs.agregarUsuario(user3);

        System.out.println(" ");

        fs.crearArchivo("Documento.txt", "Hola mundo", user1,"r");
        fs.crearArchivo("Script.sh", "echo hola ", user2    ,"rw");

        System.out.println(" ");

        fs.listarArchivos();

        System.out.println(" ");

        System.out.println(("----Prueba de lectura de los archivos----"));
        fs.leerArchivo("Documento.txt", user1);
        fs.leerArchivo("Script.sh", user1); //Debería dar acceso denegado
        fs.leerArchivo("Script.sh", user2);
        fs.leerArchivo("NoExiste", user1);





    }
}
