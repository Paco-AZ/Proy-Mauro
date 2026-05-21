package modelo.seguridad;

public class SesionActual {

    // 1. La única "instancia" estática y privada de la clase
    private static SesionActual instancia;

    // 2. Las variables de tu sesión (ya no son estáticas, pertenecen a la instancia)
    private int idUsuario;
    private String nombreUsuario;
    private String rol;

    // 3. Constructor PRIVADO (Evita que alguien haga "new SesionActual()" desde otro lado)
    private SesionActual() {
        this.idUsuario = -1;
        this.nombreUsuario = "";
        this.rol = "";
    }

    // 4. El "Portal" de acceso: Si no existe la sesión, la crea. Si ya existe, te la devuelve.
    public static SesionActual getInstance() {
        if (instancia == null) {
            instancia = new SesionActual();
        }
        return instancia;
    }

    // --- MÉTODOS DE NEGOCIO ---

    // Método para llenar los datos cuando el login es exitoso
    public void iniciarSesion(int idUsuario, String nombreUsuario, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    // Método para borrar los datos cuando cierra sesión
    public void cerrarSesion() {
        this.idUsuario = -1;
        this.nombreUsuario = "";
        this.rol = "";
    }

    // --- GETTERS (No hacemos Setters para que no modifiquen la sesión a medias) ---
    public int getIdUsuario() { return idUsuario; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getRol() { return rol; }
    
    // Método de utilidad para saber si hay alguien logueado
    public boolean haySesionActiva() {
        return idUsuario != -1;
    }
}