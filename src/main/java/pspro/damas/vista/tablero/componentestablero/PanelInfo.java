package pspro.damas.vista.tablero.componentestablero;

import modelo.ColorPieza;
import modelo.Partida;
import pspro.damas.controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class PanelInfo extends JPanel {
    JLabel labelColor;
    JLabel labelTurno;

    public PanelInfo(Partida partida, Controlador controlador) {
        setLayout(new FlowLayout());
        labelColor = new JLabel("Color: " + (partida.getColorJugador(controlador.getIdUsuario()) == ColorPieza.BLANCA ? "BLANCAS" : "ROJAS"));
        labelTurno = new JLabel("Turno: " + (partida.esTuTurno(controlador.getIdUsuario()) ? "Tuyo" : "Adversario"));

        add(labelColor);
        add(labelTurno);
    }

    public void actualizarInfo(Partida partida, Controlador controlador) {
        // Actualizar la información de la partida
        labelTurno.setText("Turno: " + (partida.esTuTurno(controlador.getIdUsuario()) ? "Tuyo" : "Adversario"));
        repaint();
    }
}
