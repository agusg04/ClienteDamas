package pspro.damas.vista.tablero.componentestablero;

import modelo.Partida;
import modelo.Pieza;
import modelo.Tablero;
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

    public boolean esCaptursssa(Partida partida, int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {

        int sentidoX = filaDestino > filaOrigen ? 1 : -1;
        int sentidoY = columnaDestino > columnaOrigen ? 1 : -1;

        int filaActual = filaOrigen + sentidoX;
        int columnaActual = columnaOrigen + sentidoY;

        while (filaActual != filaDestino || columnaActual != columnaDestino) {
            Pieza pieza = partida.getTablero().getPieza(filaActual, columnaActual);
            if (pieza != null && pieza.getColor() != partida.getTablero().getPieza(filaOrigen, columnaOrigen).getColor()) {
                return true;
            }
            filaActual += sentidoX;
            columnaActual += sentidoY;
        }

        return false;
    }

    public boolean esCaptura(Partida partida, int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
        Tablero tablero = partida.getTablero();
        Pieza piezaMovida = tablero.getPieza(filaOrigen, columnaOrigen);

        int sentidoX = Integer.compare(filaDestino, filaOrigen); // 1 si filaDestino > filaOrigen, -1 si filaDestino < filaOrigen
        int sentidoY = Integer.compare(columnaDestino, columnaOrigen); // 1 si columnaDestino > columnaOrigen, -1 si columnaDestino < columnaOrigen

        int distanciaX = Math.abs(filaDestino - filaOrigen);
        int distanciaY = Math.abs(columnaDestino - columnaOrigen);

        // Verificar que el movimiento sea diagonal
        if (distanciaX != distanciaY) {
            return false;
        }

        if (!piezaMovida.isEsDama()) {
            // Verificar captura para piezas normales
            if (distanciaX == 2) { // Movimiento de captura normal debe ser exactamente 2 casillas en diagonal
                int filaIntermedia = filaOrigen + sentidoX;
                int columnaIntermedia = columnaOrigen + sentidoY;
                Pieza piezaIntermedia = tablero.getPieza(filaIntermedia, columnaIntermedia);
                return piezaIntermedia != null && piezaIntermedia.getColor() != piezaMovida.getColor();
            }
        } else {
            // Verificar captura para damas
            boolean piezaEncontrada = false;

            for (int i = 1; i < distanciaX; i++) {
                int filaIntermedia = filaOrigen + i * sentidoX;
                int columnaIntermedia = columnaOrigen + i * sentidoY;
                Pieza piezaIntermedia = tablero.getPieza(filaIntermedia, columnaIntermedia);

                if (piezaIntermedia != null) {
                    if (piezaIntermedia.getColor() == piezaMovida.getColor() || piezaEncontrada) {
                        return false; // Hay más de una pieza en el camino o la pieza encontrada es del mismo color
                    }
                    piezaEncontrada = true; // Se encontró la pieza del adversario
                }
            }

            // Verificar que la siguiente casilla después de la pieza capturada está vacía
            int filaDespuesCaptura = filaDestino + sentidoX;
            int columnaDespuesCaptura = columnaDestino + sentidoY;
            return piezaEncontrada && tablero.getPieza(filaDestino, columnaDestino) == null;
        }

        return false;
    }


}
