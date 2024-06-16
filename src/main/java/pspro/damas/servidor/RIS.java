package pspro.damas.servidor;

import datos.DatosUsuario;
import modelo.Partida;
import modelo.PartidaTerminada;
import modelo.Tablero;
import pspro.damas.controlador.Controlador;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RIS extends Thread {
    private final ObjectInputStream flujoEntrada;
    private final Controlador controlador;
    private boolean running = true;

    public RIS(ObjectInputStream flujoEntrada, Controlador controlador) {
        this.flujoEntrada = flujoEntrada;
        this.controlador = controlador;
    }

    public int leerEntero() {
        try {
            return flujoEntrada.readInt();
        } catch (IOException e) {
            System.err.println("Error al leer enteros del servidor");
        }
        return -1;
    }

    public String leerTexto() {
        try {
            return flujoEntrada.readUTF();
        } catch (IOException e) {
            System.err.println("Error al leer texto del servidor");
        }
        return null;
    }

    public boolean leerBoolean() {
        try {
            return flujoEntrada.readBoolean();
        } catch (IOException e) {
            System.err.println("Error al leer boolean del servidor");
        }
        return false;
    }

    public DatosUsuario leerDatosUsuario() {
        try {
            return (DatosUsuario) flujoEntrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Map<Integer, String> leerUsuarios() {
        try {
            return (Map<Integer, String>) flujoEntrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public List<Tablero> leerPartidasSinTerminar() {
        try {
            return (ArrayList<Tablero>) flujoEntrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Map<Integer, Partida> leerPartidas() {
        try {
            return (Map<Integer, Partida>) flujoEntrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    public Map<Integer, PartidaTerminada> leerPartidasTerminadas() {
        try {
            return (Map<Integer, PartidaTerminada>) flujoEntrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }

    @Override
    public void run() {
        try {
            while (running) {
                int orden = leerEntero();
                if (orden == -1) {
                    // El flujo se cerró desde el servidor
                    running = false;
                    break;
                }
                switch (orden) {
                    case 0:
                        flujoEntrada.close();
                        running = false;
                        break;
                    case 1:
                        DatosUsuario datosUsuario = leerDatosUsuario();
                        controlador.setDatosUsuario(datosUsuario);
                        controlador.inicioSesionExitoso();
                        break;

                    case 2:
                        controlador.mostrarAccionFallido("Error: El usuario o la contraseña son incorrectos");
                        break;

                    case 3:
                        controlador.mostrarRegistroExitoso();
                        break;

                    case 4:
                        controlador.mostrarAccionFallido("Error: Este nombre ya está en uso");
                        break;

                    case 5:
                        Map<Integer, String> usuariosConectados = leerUsuarios();
                        controlador.actualiazrUsuarios(usuariosConectados);
                        break;

                    case 6:
                        Map<Integer, Partida> partidasOnline = leerPartidas();
                        Map<Integer, Partida> partidasOffline = leerPartidas();
                        controlador.actualizarPartidasEnCurso(partidasOnline, true);
                        controlador.actualizarPartidasEnCurso(partidasOffline, false);
                        break;

                    case 7:
                        Map<Integer, PartidaTerminada> partidasTerminadas = leerPartidasTerminadas();
                        controlador.actualizarPartidasTerminadas(partidasTerminadas);
                        break;

                    case 8:
                        String nombreUsuario = leerTexto();
                        boolean conectado = leerBoolean();
                        controlador.mostrarMensajeConexion(nombreUsuario, conectado);
                        break;

                    case 9:
                        controlador.mostrarMensajeRendicion();
                        int idTablero = leerEntero();
                        controlador.cerrarTablero(idTablero);
                        break;

                    case 10:
                        int idTableroGane = leerEntero();
                        controlador.mostrarMensajeRendicionAdversario(idTableroGane);
                        controlador.cerrarTablero(idTableroGane);
                        break;

                    case 11:
                        int idTableroActualizar = leerEntero();
                        controlador.actualizarTablero(idTableroActualizar);
                        break;

                    case 12:
                        int idPartida = leerEntero();
                        controlador.mostrarMensajeMovimientoInvalido(idPartida);
                        break;


                    default:
                        // Manejar otros casos según sea necesario
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer desde el flujo de entrada: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                flujoEntrada.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el flujo de entrada: " + e.getMessage());
            }
        }
    }
}
