package pspro.damas.vista.tablero;

import modelo.ColorPieza;
import modelo.Partida;
import modelo.Pieza;
import pspro.damas.controlador.Controlador;
import pspro.damas.vista.menu.Panel2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TableroFrame extends JFrame {
    private final Controlador controlador;
    private Panel2 panel2;
    Partida partida;
    JButton[][] casillas;

    JLabel labelColor;
    JLabel labelTurno;
    JComboBox<Integer> comboBoxFilaOrigen;
    JComboBox<Integer> comboBoxColumnaOrigen;
    JComboBox<Integer> comboBoxFilaDestino;
    JComboBox<Integer> comboBoxColumnaDestino;
    JButton btnMover;
    JButton btnRendirse;

    public TableroFrame(Partida partida, ColorPieza tuColor, Panel2 panel2, Controlador controlador) {
        this.partida = partida;
        this.panel2 = panel2;
        this.controlador = controlador;

        int filas = partida.getTablero().getTamanio();
        int columnas = partida.getTablero().getTamanio();

        setTitle("Partida " + partida.getIdPartida());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear el panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        getContentPane().add(panelPrincipal);

        // Crear el panel para el tablero
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(filas + 1, columnas + 1)); // Añadimos 1 fila y 1 columna extra
        casillas = new JButton[filas][columnas];

        // Añadir números a la primera fila y primera columna
        for (int i = 0; i <= filas; i++) {
            for (int j = 0; j <= columnas; j++) {
                if (i == 0 && j == 0) {
                    JLabel label = new JLabel();
                    panelTablero.add(label); // Esquina superior izquierda vacía
                } else if (i == 0) {
                    JLabel label = new JLabel(String.valueOf(j - 1), SwingConstants.CENTER);
                    label.setForeground(Color.BLACK); // Color de fuente blanco
                    label.setFont(new Font("Arial", Font.BOLD, 14)); // Configurar fuente y tamaño
                    panelTablero.add(label); // Números en la primera fila
                } else if (j == 0) {
                    JLabel label = new JLabel(String.valueOf(i - 1), SwingConstants.CENTER);
                    label.setForeground(Color.BLACK); // Color de fuente blanco
                    label.setFont(new Font("Arial", Font.BOLD, 14)); // Configurar fuente y tamaño
                    panelTablero.add(label); // Números en la primera columna
                } else {
                    JButton boton = new JButton();
                    boton.setPreferredSize(new Dimension(50, 50));
                    boton.setBackground((i + j) % 2 == 0 ? new Color(0xD4AE54) : new Color(0x6B3919));

                    panelTablero.add(boton);
                    casillas[i - 1][j - 1] = boton;

                    // Cargar la ficha correspondiente del tablero
                    Pieza pieza = partida.getTablero().getPieza(i - 1, j - 1);
                    if (pieza != null) {
                        if (pieza.getColor() == ColorPieza.BLANCA) {
                            boton.setIcon(new ImageIcon("src/main/resources/white.png"));
                        } else {
                            boton.setIcon(new ImageIcon("src/main/resources/red.png"));
                        }
                    }
                }
            }
        }

        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new FlowLayout());

        labelColor = new JLabel("Color: " + (tuColor == ColorPieza.BLANCA ? "BLANCAS" : "ROJAS"));
        labelTurno = new JLabel("Turno: " + (partida.esTuTurno(controlador.getIdUsuario()) ? "Tuyo" : "Adversario"));
        btnRendirse = new JButton("Rendirse");
        btnRendirse.addActionListener(e -> controlador.rendirse(
                partida.getIdPartida(), partida.getIdAdversario(controlador.getIdUsuario())
        ));

        panelDatos.add(labelColor);
        panelDatos.add(labelTurno);
        panelDatos.add(btnRendirse);


        // Crear el panel para los campos de texto y el botón de mover
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new FlowLayout());

        // Inicializar ComboBoxes
        Integer[] opciones = new Integer[filas];
        for (int i = 0; i < filas; i++) {
            opciones[i] = i;
        }

        comboBoxFilaOrigen = new JComboBox<>(opciones);
        comboBoxColumnaOrigen = new JComboBox<>(opciones);
        comboBoxFilaDestino = new JComboBox<>(opciones);
        comboBoxColumnaDestino = new JComboBox<>(opciones);

        panelControles.add(new JLabel("Origen (fil, col):"));
        panelControles.add(comboBoxFilaOrigen);
        panelControles.add(comboBoxColumnaOrigen);
        panelControles.add(new JLabel("Destino (fil, col):"));
        panelControles.add(comboBoxFilaDestino);
        panelControles.add(comboBoxColumnaDestino);

        btnMover = new JButton("Mover");

        btnMover.addActionListener(e -> controlador.moverFicha(
                partida.getIdPartida(),
                partida.getIdAdversario(controlador.getIdUsuario()),
                comboBoxFilaOrigen.getItemAt(comboBoxFilaOrigen.getSelectedIndex()),
                comboBoxColumnaOrigen.getItemAt(comboBoxColumnaOrigen.getSelectedIndex()),
                comboBoxFilaDestino.getItemAt(comboBoxFilaDestino.getSelectedIndex()),
                comboBoxColumnaDestino.getItemAt(comboBoxColumnaDestino.getSelectedIndex())
        ));

        panelControles.add(btnMover);
        // Agregar los paneles al panel principal
        panelPrincipal.add(panelDatos, BorderLayout.NORTH);
        panelPrincipal.add(panelTablero, BorderLayout.CENTER);
        panelPrincipal.add(panelControles, BorderLayout.SOUTH);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panel2.cerrarTablero(partida);
            }
        });
    }

    public void cerrarTablero() {
        dispose();
    }
}

