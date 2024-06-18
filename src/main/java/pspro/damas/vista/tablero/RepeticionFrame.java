package pspro.damas.vista.tablero;

import modelo.*;
import pspro.damas.vista.menu.Panel3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class RepeticionFrame extends JFrame {

    private Panel3 panel3;
    PartidaTerminada partidaTerminada;

    private JButton[][] casillas;
    private JButton btnSiguienteAccion;
    private JButton btnMovimientoAnterior;
    private int movimientoActual = 0;

    public RepeticionFrame(PartidaTerminada partidaTerminada, Panel3 panel3) {
        this.partidaTerminada = partidaTerminada;
        this.panel3 = panel3;

        int filas = partidaTerminada.getTableros().get(0).getTamanio();
        int columnas = partidaTerminada.getTableros().get(0).getTamanio();

        setTitle("Repeticion " + partidaTerminada.getIdPartida());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        // Crear el panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        getContentPane().add(panelPrincipal);

        // Crear el panel para el tablero
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(filas, columnas));
        casillas = new JButton[filas][columnas];

        // Crear y agregar los botones al panel del tablero
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(50, 50));
                boton.setBackground((i + j) % 2 == 0 ? new Color(0xD4AE54) : new Color(0x6B3919));
                panelTablero.add(boton);
                casillas[i][j] = boton;
            }
        }

        // Crear el panel para los campos de texto y el botón de mover
        JPanel panelControles = new JPanel();
        panelControles.setLayout(new FlowLayout());

        btnMovimientoAnterior = new JButton("Movimiento Anterior");
        btnMovimientoAnterior.addActionListener(e -> movimientoAnterior());
        panelControles.add(btnMovimientoAnterior);

        btnSiguienteAccion = new JButton("Siguiente Movimiento");
        btnSiguienteAccion.addActionListener(e -> siguienteAccion());
        panelControles.add(btnSiguienteAccion);

        // Agregar los paneles al panel principal
        panelPrincipal.add(panelTablero, BorderLayout.CENTER);
        panelPrincipal.add(panelControles, BorderLayout.SOUTH);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panel3.cerrarTablero(partidaTerminada);
            }
        });

        mostrarMovimiento(0);

    }

    public void siguienteAccion() {
        if (movimientoActual < partidaTerminada.getTableros().size() - 1) {
            movimientoActual++;
            mostrarMovimiento(movimientoActual);
        } else {
            mostrarMensaje("No hay más movimientos.");
        }
    }

    public void movimientoAnterior() {
        if (movimientoActual > 0) {
            movimientoActual--;
            mostrarMovimiento(movimientoActual);
        } else {
            mostrarMensaje("Ya estás en el primer movimiento.");
        }
    }

    private void mostrarMovimiento(int indice) {
        ArrayList<Tablero> movimientos = (ArrayList<Tablero>) partidaTerminada.getTableros();
        Tablero tablero = movimientos.get(indice);

        for (int i = 0; i < tablero.getTamanio(); i++) {
            for (int j = 0; j < tablero.getTamanio(); j++) {
                Pieza pieza = tablero.getPieza(i, j);
                JButton boton = casillas[i][j];
                if (pieza != null) {
                    pintarPieza(boton, pieza);
                } else {
                    boton.setIcon(null);
                }
            }
        }
    }

    private void pintarPieza(JButton boton, Pieza pieza) {
        if (pieza.getColor() == ColorPieza.BLANCA) {
            if (pieza.isEsDama()) {
                boton.setIcon(new ImageIcon("src/main/resources/whiteKing.png"));
            } else {
                boton.setIcon(new ImageIcon("src/main/resources/white.png"));
            }
        } else if (pieza.getColor() == ColorPieza.NEGRA){
            if (pieza.isEsDama()) {
                boton.setIcon(new ImageIcon("src/main/resources/redKing.png"));
            } else {
                boton.setIcon(new ImageIcon("src/main/resources/red.png"));
            }
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

}
