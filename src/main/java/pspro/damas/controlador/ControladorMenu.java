package pspro.damas.controlador;

import pspro.damas.servidor.ConexionServidor;
import pspro.damas.vista.VistaMenu;

import java.util.Random;

public class ControladorMenu implements Runnable{

    private final ConexionServidor conexionServidor;
    private final VistaMenu vistaMenu;

    public ControladorMenu(ConexionServidor conexionServidor) {
        this.conexionServidor = conexionServidor;
        this.vistaMenu = new VistaMenu(this);

    }

    public void mostrarVistaMenu() {
        vistaMenu.mostrarVentana();
    }


    @Override
    public void run() {
        while (true) {
            int orden = conexionServidor.leerEntero();
            if (orden != 0) {
                switch (orden) {
                    case 1:
                        //Recibir las partidas sin terminar que no son mi turno
                        //orden;
                        //Objeto;
                        conexionServidor.leerPartidas();
                        actualizarPartidas();
                        break;

                    case 2:
                        //Recibir las partidas sin terminar que son mi turno
                        //orden;
                        //Objeto;
                        conexionServidor.leerPartidas();
                        actualizarPartidas();
                        break;

                    case 2:
                        //Recibir las partidas terminadas
                        //orden;
                        //Objeto;
                        conexionServidor.leerPartidas();
                        actualizarPartidas();
                        break;

                    case 4:
                        //Recibir los usuarios
                        //orden;
                        //Objeto;
                        conexionServidor.leerUsuarios();
                        actualizarUsuarios();
                        break;


                    default:
                        throw new AssertionError();
                }

            }
        }
    }
}
