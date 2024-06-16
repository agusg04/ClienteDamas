package pspro.damas.vista.tablero;

import modelo.Partida;
import pspro.damas.controlador.Controlador;
import pspro.damas.vista.menu.Panel2;
import pspro.damas.vista.tablero.componentestablero.PanelControles;
import pspro.damas.vista.tablero.componentestablero.PanelInfo;
import pspro.damas.vista.tablero.componentestablero.PanelTablero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TableroFrame extends JFrame {
    private final Controlador controlador;
    private Partida partida;

    private PanelTablero panelTablero;
    private PanelInfo paenlInfo;
    private PanelControles panelControles;


    public TableroFrame(Partida partida, Panel2 panel2, Controlador controlador) {
        this.partida = partida;
        this.controlador = controlador;

        setTitle("Partida " + partida.getIdPartida());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        inicializarComponentes();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panel2.cerrarTableroInterno(partida);
            }
        });
    }

    public void cerrarTablero() {
        dispose();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        add(panelPrincipal);

        paenlInfo = new PanelInfo(partida, controlador);
        panelTablero = new PanelTablero(partida);
        panelControles = new PanelControles(partida, controlador);

        panelPrincipal.add(paenlInfo, BorderLayout.NORTH);
        panelPrincipal.add(panelTablero, BorderLayout.CENTER);
        panelPrincipal.add(panelControles, BorderLayout.SOUTH);
    }

    public void actualizarComponentes(Partida nuevaPartida) {
        this.partida = nuevaPartida;
        paenlInfo.actualizarInfo(nuevaPartida, controlador);
        panelTablero.actualizarPartida(nuevaPartida);
        repaint();
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
        actualizarComponentes(partida);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}

