package pspro.damas.vista.tablero.componentestablero;

import modelo.Partida;
import pspro.damas.controlador.Controlador;

import javax.swing.*;
import java.awt.*;

public class PanelControles extends JPanel {
    JComboBox<Integer> comboBoxFilaOrigen;
    JComboBox<Integer> comboBoxColumnaOrigen;
    JComboBox<Integer> comboBoxFilaDestino;
    JComboBox<Integer> comboBoxColumnaDestino;
    JButton btnMover;
    JButton btnRendirse;

    public PanelControles(Partida partida, Controlador controlador) {
        setLayout(new FlowLayout());

        // Inicializar ComboBoxes
        Integer[] opciones = new Integer[partida.getTablero().getTamanio()];
        for (int i = 0; i < partida.getTablero().getTamanio(); i++) {
            opciones[i] = i;
        }

        comboBoxFilaOrigen = new JComboBox<>(opciones);
        comboBoxColumnaOrigen = new JComboBox<>(opciones);
        comboBoxFilaDestino = new JComboBox<>(opciones);
        comboBoxColumnaDestino = new JComboBox<>(opciones);

        btnRendirse = new JButton("Rendirse");
        btnRendirse.addActionListener(e -> controlador.rendirse(
                partida.getIdPartida(), partida.getIdAdversario(controlador.getIdUsuario())
        ));

        add(btnRendirse);
        add(new JLabel("Origen (fil, col):"));
        add(comboBoxFilaOrigen);
        add(comboBoxColumnaOrigen);
        add(new JLabel("Destino (fil, col):"));
        add(comboBoxFilaDestino);
        add(comboBoxColumnaDestino);

        btnMover = new JButton("Mover");

        btnMover.addActionListener(e -> controlador.moverFicha(
                partida.getIdPartida(),
                partida.getIdAdversario(controlador.getIdUsuario()),
                comboBoxFilaOrigen.getItemAt(comboBoxFilaOrigen.getSelectedIndex()),
                comboBoxColumnaOrigen.getItemAt(comboBoxColumnaOrigen.getSelectedIndex()),
                comboBoxFilaDestino.getItemAt(comboBoxFilaDestino.getSelectedIndex()),
                comboBoxColumnaDestino.getItemAt(comboBoxColumnaDestino.getSelectedIndex())
        ));

        add(btnMover);
    }
}
