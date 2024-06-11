package pspro.damas.vista;

import pspro.damas.controlador.ControladorMenu;
import pspro.damas.vista.menu.Panel1;
import pspro.damas.vista.menu.Panel2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VistaMenu extends JFrame{
    private final ControladorMenu controladorMenu;

    JPanel panelContenido = new JPanel();
    private Panel1 panel1;
    private Panel2 panel2;
    //private Panel3 panel3;


    public VistaMenu(ControladorMenu controladorMenu) {
        this.controladorMenu = controladorMenu;
        this.panel1 = new Panel1(controladorMenu);
        SwingUtilities.invokeLater(this::inicializarComponentes);

    }

    public void inicializarComponentes() {
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal que contiene todos los elementos
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        // Panel superior con tres botones
        JPanel panelSuperior = new JPanel(new GridLayout(1, 3));
        JButton btnNuevaPartida = new JButton("Nueva Partida");
        btnNuevaPartida.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
        JButton btnPartidasEnCurso = new JButton("Partidas en Curso");
        btnPartidasEnCurso.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
        JButton btnPartidasPasadas = new JButton("Partidas Pasadas");
        btnPartidasPasadas.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));

        panelSuperior.add(btnNuevaPartida);
        panelSuperior.add(btnPartidasEnCurso);
        panelSuperior.add(btnPartidasPasadas);

        // Manejador de eventos para los botones
        btnNuevaPartida.addActionListener(e -> {
            mostrarPanel(panel1);
            actualizarUsuarios();
        });

        //actualizar panel por primera vez?
        //actualizarUsuarios();

        btnPartidasEnCurso.addActionListener(e -> {
            mostrarPanel(panel2);
            //actualizarPartidas();
        });

        //btnPartidasEnCurso.addActionListener(e -> mostrarPanel(panel2));
        //panel2.actualizarPartidas();

        //btnPartidasPasadas.addActionListener(e -> mostrarPanel(panel3);
        //panel3.actualizarPartidas();

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Panel de contenido
        panelContenido.setLayout(new BorderLayout(0,0));

        panelPrincipal.add(panelContenido, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controladorMenu.finalizarPrograma();
            }
        });
    }

    private void mostrarPanel(JPanel panel) {
        // Limpiar el contenido actual
        panelContenido.removeAll();

        panel.setPreferredSize(new Dimension(900,500));

        // Agregar el nuevo panel al contenido
        panelContenido.add(panel, BorderLayout.CENTER);

        // Revalidar y repintar el contenido
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    public void mostrarVentana() {
        setVisible(true);
    }

    public void cerrarVentana() {
        dispose();
    }

    public void actualizarUsuarios() {
        if (panel1 != null) {
            panel1.actualizarUsuarios();
        }
    }
}
