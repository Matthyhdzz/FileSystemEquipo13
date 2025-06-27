public class Main {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();

        Usuario user1 = new Usuario("Mati");
        Usuario user2 = new Usuario("Fabri");

        fs.agregarUsuario(user1);
        fs.agregarUsuario(user2);

        fs.crearArchivo("Documento.txt", "Hola mundo", user1,"r");
        fs.crearArchivo("Script.sh", "echo hola ", user2    ,"rw");
        fs.listarArchivos();


    }
}
