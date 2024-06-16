package pspro.damas.controlador;

import datos.DatosUsuario;
import modelo.Partida;
import modelo.PartidaTerminada;
import pspro.damas.servidor.EIS;
import pspro.damas.vista.VistaLogin;
import pspro.damas.vista.VistaMenu;

import java.util.*;

public class Controlador extends Thread {
    private final VistaLogin vistaLogin;
    private final VistaMenu vistaMenu;
    private final EIS flujoSalida;
    private DatosUsuario datosUsuario;
    private boolean running = true;

    public Controlador(EIS flujoSalida) {
        this.flujoSalida = flujoSalida;
        this.vistaLogin = new VistaLogin(this);
        this.vistaMenu = new VistaMenu(this);
    }

    public void setDatosUsuario(DatosUsuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }

    public int getIdUsuario() {
        return datosUsuario.getIdUsuario();
    }

    public DatosUsuario getDatosUsuario() {
        return datosUsuario;
    }

    @Override
    public void start() {
        mostrarVistaLogin();
        while (running){

        }
    }

    public void pararControlador() {
        running = false;
        System.exit(0);
    }

    public void finalizarPrograma() {
        flujoSalida.enviarTexto("0;");
        vistaMenu.cerrarVentana();
        pararControlador();
    }

    public void mostrarVistaLogin() {
        vistaLogin.mostrarVentana();
    }

    public void cerrarVistaLogin() {
        vistaLogin.cerrarVentana();
    }

    public void mostrarVistaMenu() {
        vistaMenu.mostrarVentana();
    }

    public void cerrarVistaMenu() {
        vistaMenu.cerrarVentana();
    }

    public void iniciarSesion() {
        String nombreUsuario = vistaLogin.getNombreUsuario();
        String contrasenia 	 = vistaLogin.getContrasenia();

        if(nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
            mostrarAccionFallido("Error: Los campos no pueden estar vacíos");
            return;
        }

        flujoSalida.enviarTexto("1;" + nombreUsuario + ";" + contrasenia + ";");

    }

    public void registrarse() {
        String nombreUsuario = vistaLogin.getNombreUsuario();
        String contrasenia 	 = vistaLogin.getContrasenia();

        if(nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
            mostrarAccionFallido("Error: Los campos no pueden estar vacíos");
            return;
        }

        flujoSalida.enviarTexto("2;" + nombreUsuario + ";" + contrasenia);
    }


    public void inicioSesionExitoso(){
        System.out.println("Inicio de sesión exitoso");
        vistaLogin.mostrarDialogoLoginExitoso();
        cerrarVistaLogin();
        mostrarVistaMenu();
    }

    public void mostrarRegistroExitoso(){
        System.out.println("Registro exitoso");
        vistaLogin.vaciarCampos();
        vistaLogin.mostrarDialogoRegistroExitoso();
    }

    public void mostrarAccionFallido(String mensaje){
        System.out.println(mensaje);
        vistaLogin.vaciarCampos();
        vistaLogin.mostrarDialogoFallido(mensaje);
    }

    public void actualiazrUsuarios(Map<Integer, String> usuariosConectados){
        usuariosConectados.remove(getIdUsuario());
        vistaMenu.actualizarUsuarios(usuariosConectados);
    }

    public void actualizarPartidasEnCurso(Map<Integer, Partida> partidas, boolean online) {
        vistaMenu.actualizarPartidasEnCurso(partidas, online);
    }

    public void actualizarPartidasTerminadas(Map<Integer, PartidaTerminada> partidasTerminadas) {
        vistaMenu.cargarPartidasTerminadas(partidasTerminadas);
    }


    public void iniciarPartida(int idSeleccionado) {
        flujoSalida.enviarTexto("1;" + idSeleccionado);
    }

    public void moverFicha(int idPartida, int idAdversario, int iniX, int iniY, int finX, int finY) {
        flujoSalida.enviarTexto("3;" + idPartida + ";" + idAdversario + ";" + iniX + ";" + iniY + ";" + finX + ";" + finY);
    }

    public void rendirse(int idPartida, int idAdversario) {
        flujoSalida.enviarTexto("2;" + idPartida + ";" + idAdversario);
    }

    public void mostrarMensajeConexion(String nombreUsuario, boolean conectado) {
        vistaMenu.mostrarMensaje("El usuario " + nombreUsuario + " se ha " + (conectado ? "conectado" : "desconectado"));
    }

    public void mostrarMensajeRendicion() {
        vistaMenu.mostrarMensaje("!Te rendiste en la partida!");
    }

    public void mostrarMensajeRendicionAdversario(int idTablero) {
        vistaMenu.mostrarMensaje("El adversario se rindió en la partida " + idTablero);
    }

    public void cerrarTablero(int idTablero) {
        vistaMenu.cerrarTablero(idTablero);
    }

    public void actualizarTablero(int idTableroActualizar) {
        vistaMenu.actualizarTablero(idTableroActualizar);
    }

    public void mostrarMensajeMovimientoInvalido(int idPartida) {
        vistaMenu.mostrarMensajeMovimientoInvalido("Tu movimiento no es válido", idPartida);
    }
}
