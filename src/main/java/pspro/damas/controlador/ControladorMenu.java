package pspro.damas.controlador;

import datos.DatosUsuario;
import pspro.damas.servidor.ConexionServidor;
import pspro.damas.vista.VistaMenu;

import java.util.Map;

public class ControladorMenu implements Runnable{

    private final ConexionServidor conexionServidor;
    private final VistaMenu vistaMenu;
    private DatosUsuario datosUsuario;

    public ControladorMenu(ConexionServidor conexionServidor, DatosUsuario datosUsuario) {
        this.conexionServidor = conexionServidor;
        this.datosUsuario = datosUsuario;
        this.vistaMenu = new VistaMenu(this);
        //notificarDemasJugadores();
    }

    public void mostrarVistaMenu() {
        vistaMenu.mostrarVentana();
    }

    public void finalizarPrograma() {
        conexionServidor.enviarTexto("0;");
        System.out.println("Orden 0; enviada");
        vistaMenu.cerrarVentana();
    }

    public Map<Integer, String> devolverJugadoresDisponibles() {
        conexionServidor.enviarTexto("7;" + datosUsuario.getIdUsuario() + ";");
        return conexionServidor.leerJugadoreDisponibles();
    }

    public void actualizarUsuarios() {
        vistaMenu.actualizarUsuarios();
    }

    public void iniciarPartida(int idSeleccionado) {
        conexionServidor.enviarTexto("1;" + idSeleccionado + ";");
        System.out.println(conexionServidor.leerEntero());
    }
    
    public void notificarDemasJugadores() {
    	conexionServidor.enviarTexto("8;" + datosUsuario.getIdUsuario() + ";");
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
                        /////////////conexionServidor.leerPartidas();
                        /////////////actualizarPartidas();
                        break;

                    case 2:
                        //Recibir las partidas sin terminar que son mi turno
                        //orden;
                        //Objeto;
                        /////////////conexionServidor.leerPartidas();
                        /////////////actualizarPartidas();
                        break;

                    case 3:
                        //Recibir las partidas terminadas
                        //orden;
                        //Objeto;
                        /////////////conexionServidor.leerPartidas();
                        /////////////actualizarPartidas();
                        break;

                    case 4:
                        //Recibir los usuarios cuando un jugador se conecte
                        //orden;
                        //Objeto;
                        actualizarUsuarios();
                        break;


                    default:
                        throw new AssertionError();
                }

            }
        }
    }
}
