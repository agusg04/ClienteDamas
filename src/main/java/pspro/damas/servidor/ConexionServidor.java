package pspro.damas.servidor;

import pspro.damas.controlador.ControladorCliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionServidor {

    ControladorCliente controladorCliente;
    private final String HOST = "localhost";
    private final int PUERTO = 5000;
    private Socket socketCliente;
    private ObjectOutputStream flujoSalida;
    private ObjectInputStream flujoEntrada;

    public void iniciarConexionServidor() {
        try {
            socketCliente = new Socket(HOST, PUERTO);
        } catch (IOException ex) {
            Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearFlujos() {
        try {
            flujoSalida = new ObjectOutputStream(socketCliente.getOutputStream());
            flujoEntrada = new ObjectInputStream(socketCliente.getInputStream());

        } catch (IOException ex) {
            Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarTexto(String texto) {
        try {
            flujoSalida.writeUTF(texto);
            flujoSalida.flush();
            System.out.println(texto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String leerTexto() {
        try {
            return flujoEntrada.readUTF();
        } catch (IOException e) {
            System.err.println("Error al leer texto del servidor");
        }
        return null;
    }

    public int leerEntero() {
        try {
            return flujoEntrada.readInt();
        } catch (IOException e) {
            System.err.println("Error al leer enteros del servidor");
        }
        return 0;
    }

    public void leerPartidas(int opcion) {
        try {

        } catch (IOException e) {

        }

        switch (opcion) {
            case 1:
        }
    }

    public boolean enviarOrdenLogin(String nombre, String contrasenia) {
        boolean verificacion = false;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("L;").append(nombre.trim()).append(";").append(contrasenia.trim());
            enviarTexto(sb.toString());

            verificacion = flujoEntrada.readBoolean();

            System.out.println("BOOLEAN LEIDO");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return verificacion;
    }

    public boolean enviarOrdenRegistro(String nombre, String contrasenia) {
        boolean verificacion = false;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("R;").append(nombre.trim()).append(";").append(contrasenia.trim());
            enviarTexto(sb.toString());

            verificacion = flujoEntrada.readBoolean();

            System.out.println("BOOLEAN LEIDO");


        } catch (IOException e) {
            e.printStackTrace();
        }

        return verificacion;
    }

}
