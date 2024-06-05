package pspro.damas.controlador;

import pspro.damas.servidor.ConexionServidor;

public class ControladorCliente {
    private final ConexionServidor conexionServidor;
    private final ControladorLogin controladorLogin;

    public ControladorCliente(ConexionServidor conexionServidor) {
        this.conexionServidor = conexionServidor;
        this.controladorLogin = new ControladorLogin(conexionServidor);
    }

    public void iniciar() {
        conexionServidor.iniciarConexionServidor();
        conexionServidor.crearFlujos();
        controladorLogin.mostrarVistaLogin();
    }
}
