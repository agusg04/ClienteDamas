package pspro.damas;

import pspro.damas.controlador.Controlador;
import pspro.damas.servidor.EIS;
import pspro.damas.servidor.RIS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;

    public static void main(String[] args) {

        try (Socket socketCliente = new Socket(HOST, PUERTO)){
            ObjectOutputStream flujoSalida = new ObjectOutputStream(socketCliente.getOutputStream());
            ObjectInputStream flujoEntrada = new ObjectInputStream(socketCliente.getInputStream());

            EIS eis = new EIS(flujoSalida);
            Controlador controlador = new Controlador(eis);
            RIS ris = new RIS(flujoEntrada, controlador);

            ris.start();
            controlador.start();

        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
