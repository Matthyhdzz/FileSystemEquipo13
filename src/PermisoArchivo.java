public class PermisoArchivo {
    private String nombreArchivo;
    private String nombreUsuario;
    private String permisos; // Formato: rwx, r--, rw-, etc.

    public PermisoArchivo(String nombreArchivo, String nombreUsuario, String permisos) {
        this.nombreArchivo = nombreArchivo;
        this.nombreUsuario = nombreUsuario;
        this.permisos = permisos;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    public boolean puedeLeer() {
        return permisos.length() > 0 && permisos.charAt(0) == 'r';
    }

    public boolean puedeEscribir() {
        return permisos.length() > 1 && permisos.charAt(1) == 'w';
    }

    public boolean puedeEjecutar() {
        return permisos.length() > 2 && permisos.charAt(2) == 'x';
    }

    @Override
    public String toString() {
        return "Usuario: " + nombreUsuario + " - Archivo: " + nombreArchivo + " - Permisos: " + permisos;
    }

    public static boolean validarFormatoPermisos(String permisos) {
        if (permisos == null || permisos.length() != 3) {
            return false;
        }
        
        char[] chars = permisos.toCharArray();
        
        // Primer carácter: r o -
        if (chars[0] != 'r' && chars[0] != '-') return false;
        
        // Segundo carácter: w o -
        if (chars[1] != 'w' && chars[1] != '-') return false;
        
        // Tercer carácter: x o -
        if (chars[2] != 'x' && chars[2] != '-') return false;
        
        return true;
    }

    public static String crearPermisos(boolean lectura, boolean escritura, boolean ejecucion) {
        StringBuilder sb = new StringBuilder();
        sb.append(lectura ? 'r' : '-');
        sb.append(escritura ? 'w' : '-');
        sb.append(ejecucion ? 'x' : '-');
        return sb.toString();
    }
}