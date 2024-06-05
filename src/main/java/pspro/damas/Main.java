package pspro.damas;

import pspro.damas.controlador.ControladorCliente;
import pspro.damas.servidor.ConexionServidor;

public class Main {

    public static void main(String[] args) {
        ConexionServidor conexionServidor = new ConexionServidor();
        ControladorCliente controladorCliente = new ControladorCliente(conexionServidor);
        controladorCliente.iniciar();
    }

}
