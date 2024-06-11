package pspro.damas.controlador;

import datos.DatosUsuario;
import pspro.damas.servidor.ConexionServidor;
import pspro.damas.vista.VistaLogin;

public class ControladorLogin {

    private final VistaLogin vistaLogin;
    private final ConexionServidor conexionServidor;

    public ControladorLogin(ConexionServidor conexionServidor) {
        this.conexionServidor 	= conexionServidor;
        this.vistaLogin 		= new VistaLogin(this);
    }

    public void mostrarVistaLogin() {
        vistaLogin.mostrarVentana();
    }

    public void cerrarVistaLogin() {
        vistaLogin.cerrarVentana();
    }

    public void iniciarSesion() {
        String nombreUsuario = vistaLogin.getNombreUsuario();
        String contrasenia 	 = vistaLogin.getContrasenia();

        boolean exitoso;

        if(nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
            mostrarAccionFallido("Error: Los campos no pueden estar vacíos");
            return;
        }

        exitoso = conexionServidor.enviarOrdenLogin(nombreUsuario, contrasenia);

        if (exitoso) {
            mostrarInicioSesionExitoso();
            DatosUsuario datosUsuario = conexionServidor.leerDatosUsuario();
            ControladorMenu controladorMenu = new ControladorMenu(conexionServidor, datosUsuario);
            controladorMenu.mostrarVistaMenu();
        } else {
            mostrarAccionFallido("Error: El usuario o la contraseña son incorrectos");
        }
    }

    private void mostrarInicioSesionExitoso(){
        System.out.println("Inicio de sesión exitoso");
        vistaLogin.mostrarDialogoLoginExitoso();
        this.cerrarVistaLogin();
    }

    private void mostrarRegistroExitoso(){
        System.out.println("Registro exitoso");
        vistaLogin.vaciarCampos();
        vistaLogin.mostrarDialogoRegistroExitoso();
    }

    private void mostrarAccionFallido(String mensaje){
        System.out.println(mensaje);
        vistaLogin.vaciarCampos();
        vistaLogin.mostrarDialogoFallido(mensaje);
    }


    public void registrarse() {
        String nombreUsuario = vistaLogin.getNombreUsuario();
        String contrasenia 	 = vistaLogin.getContrasenia();

        boolean exitoso;

        if(nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
            mostrarAccionFallido("Error: Los campos no pueden estar vacíos");
            return;
        }

        exitoso = conexionServidor.enviarOrdenRegistro(nombreUsuario, contrasenia);

        if (exitoso) {
            mostrarRegistroExitoso();
        } else {
            mostrarAccionFallido("Error: Este nombre ya está en uso");
        }
    }

}
