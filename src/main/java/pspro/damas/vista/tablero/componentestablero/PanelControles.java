package pspro.damas.vista.tablero.componentestablero;

import modelo.Partida;
import modelo.Pieza;
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

        btnMover.addActionListener(e -> {
            // Obtener las posiciones de origen y destino desde los ComboBoxes
            int filaOrigen = comboBoxFilaOrigen.getItemAt(comboBoxFilaOrigen.getSelectedIndex());
            int columnaOrigen = comboBoxColumnaOrigen.getItemAt(comboBoxColumnaOrigen.getSelectedIndex());
            int filaDestino = comboBoxFilaDestino.getItemAt(comboBoxFilaDestino.getSelectedIndex());
            int columnaDestino = comboBoxColumnaDestino.getItemAt(comboBoxColumnaDestino.getSelectedIndex());

            // Comprobar si el movimiento implica capturar una ficha
            if (esCaptura(
                    partida,
                    filaOrigen, columnaOrigen,
                    filaDestino, columnaDestino)) {
                // Si es una captura, enviar la captura
                controlador.capturarFicha(
                        partida.getIdPartida(),
                        partida.getIdAdversario(controlador.getIdUsuario()),
                        filaOrigen, columnaOrigen,
                        filaDestino, columnaDestino);
            } else {
                // Si no es una captura, mover la ficha normalmente
                controlador.moverFicha(
                        partida.getIdPartida(),
                        partida.getIdAdversario(controlador.getIdUsuario()),
                        filaOrigen, columnaOrigen,
                        filaDestino, columnaDestino);
            }
        });


        add(btnMover);
    }

    public boolean esCaptura(Partida partida, int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
        // Verificar si el movimiento implica pasar por encima de una ficha
        int filaIntermedia = (filaOrigen + filaDestino) / 2;
        int columnaIntermedia = (columnaOrigen + columnaDestino) / 2;
        Pieza piezaIntermedia = partida.getTablero().getPieza(filaIntermedia, columnaIntermedia);

        // Si hay una pieza intermedia de un color opuesto, es una captura
        return piezaIntermedia != null && piezaIntermedia.getColor() != partida.getTablero().getPieza(filaOrigen, columnaOrigen).getColor();
    }

}
