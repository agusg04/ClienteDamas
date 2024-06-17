package pspro.damas.vista.tablero.componentestablero;

import modelo.ColorPieza;
import modelo.Partida;
import modelo.Pieza;

import javax.swing.*;
import java.awt.*;

public class PanelTablero extends JPanel {
    int tamanio;
    private final Partida partida;
    private JButton[][] casillas;

    public PanelTablero(Partida partida) {
        this.tamanio = partida.getTablero().getTamanio();
        this.partida = partida;
        setLayout(new GridLayout(tamanio, tamanio));
        casillas = new JButton[tamanio][tamanio];
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        int filas = partida.getTablero().getTamanio();
        int columnas = partida.getTablero().getTamanio();

        setLayout(new GridLayout(filas + 1, columnas + 1)); // Añadimos 1 fila y 1 columna extra
        casillas = new JButton[filas][columnas];

        // Añadir números a la primera fila y primera columna
        for (int i = 0; i <= filas; i++) {
            for (int j = 0; j <= columnas; j++) {
                if (i == 0 && j == 0) {
                    JLabel label = new JLabel();
                    add(label); // Esquina superior izquierda vacía
                } else if (i == 0) {
                    JLabel label = new JLabel(String.valueOf(j - 1), SwingConstants.CENTER);
                    label.setForeground(Color.BLACK); // Color de fuente blanco
                    label.setFont(new Font("Arial", Font.BOLD, 14)); // Configurar fuente y tamaño
                    add(label); // Números en la primera fila
                } else if (j == 0) {
                    JLabel label = new JLabel(String.valueOf(i - 1), SwingConstants.CENTER);
                    label.setForeground(Color.BLACK); // Color de fuente blanco
                    label.setFont(new Font("Arial", Font.BOLD, 14)); // Configurar fuente y tamaño
                    add(label); // Números en la primera columna
                } else {
                    JButton boton = new JButton();
                    boton.setPreferredSize(new Dimension(50, 50));
                    boton.setBackground((i + j) % 2 == 0 ? new Color(0xD4AE54) : new Color(0x6B3919));

                    add(boton);
                    casillas[i - 1][j - 1] = boton;

                    // Cargar la ficha correspondiente del tablero
                    Pieza pieza = partida.getTablero().getPieza(i - 1, j - 1);
                    if (pieza != null) {
                        if (pieza.getColor() == ColorPieza.BLANCA) {
                            if (pieza.isEsDama()) {
                                boton.setIcon(new ImageIcon("src/main/resources/whiteKing.png"));
                            }
                            boton.setIcon(new ImageIcon("src/main/resources/white.png"));
                        } else {
                            if (pieza.isEsDama()) {
                                boton.setIcon(new ImageIcon("src/main/resources/redKing.png"));
                            }
                            boton.setIcon(new ImageIcon("src/main/resources/red.png"));
                        }
                    }
                }
            }
        }
    }

    public void actualizarPartida(Partida nuevaPartida) {
        // Actualizar la vista del tablero
        for (int i = 0; i < tamanio; i++) {
            for (int j = 0; j < tamanio; j++) {
                Pieza pieza = nuevaPartida.getTablero().getPieza(i, j);
                JButton boton = casillas[i][j];
                if (pieza != null) {
                    boton.setIcon(new ImageIcon("src/main/resources/" + (pieza.getColor() == ColorPieza.BLANCA ? "white.png" : "red.png")));
                } else {
                    boton.setIcon(null);
                }
            }
        }
        repaint();
    }
}
