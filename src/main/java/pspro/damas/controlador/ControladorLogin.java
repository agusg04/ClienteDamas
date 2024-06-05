package pspro.damas.controlador;

import pspro.damas.servidor.ConexionServidor;
import pspro.damas.vista.VistaLogin;

public class ControladorLogin {

    private final VistaLogin vistaLogin;
    private final ConexionServidor conexionServidor;
    private final ControladorMenu controladorMenu;

    public ControladorLogin(ConexionServidor conexionServidor) {
        this.conexionServidor 	= conexionServidor;
        this.vistaLogin 		= new VistaLogin(this);
        this.controladorMenu    = new ControladorMenu(conexionServidor);
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
            accionFallido("Error: Los campos no pueden estar vacíos");
            return;
        }

        exitoso = conexionServidor.enviarOrdenLogin(nombreUsuario, contrasenia);

        if (exitoso) {
            inicioSesionExitoso();
            controladorMenu.mostrarVistaMenu();
        } else {
            accionFallido("Error: El usuario o la contraseña son incorrectos");
        }
    }

    private void inicioSesionExitoso(){
        System.out.println("Inicio de sesión exitoso");
        vistaLogin.mostrarDialogoLoginExitoso();
        this.cerrarVistaLogin();
    }

    private void registroExitoso(){
        System.out.println("Registro exitoso");
        vistaLogin.vaciarCampos();
        vistaLogin.mostrarDialogoRegistroExitoso();
    }

    private void accionFallido(String mensaje){
        System.out.println(mensaje);
        vistaLogin.vaciarCampos();
        vistaLogin.mostrarDialogoFallido(mensaje);
    }


    public void registrarse() {
        String nombreUsuario = vistaLogin.getNombreUsuario();
        String contrasenia 	 = vistaLogin.getContrasenia();

        boolean exitoso;

        if(nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
            accionFallido("Error: Los campos no pueden estar vacíos");
            return;
        }

        exitoso = conexionServidor.enviarOrdenRegistro(nombreUsuario, contrasenia);

        if (exitoso) {
            registroExitoso();
            //vistaMenu.mostrar();
        } else {
            accionFallido("Error: Este nombre ya está en uso");
        }
    }

}
