public class Usuario {
    private String nombre;          //Como atributo principal, el nombre para su identificación

    public Usuario(String nombre) {
        this.nombre = nombre;        //El constructor recibe el nombre lo guarda

    }

    public String getNombre () {     //Este metodo nos permite acceder al nombre del usuario
        return nombre;
    }
}
