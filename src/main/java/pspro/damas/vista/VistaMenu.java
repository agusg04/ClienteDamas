package pspro.damas.vista;

import pspro.damas.controlador.ControladorLogin;
import pspro.damas.controlador.ControladorMenu;
import pspro.damas.vista.menu.Panel1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaMenu extends JFrame{
    private final ControladorMenu controladorMenu;

    JPanel panelContenido = new JPanel();

    private Panel1 panel1;
    //private Panel2 panel2;
    //private Panel3 panel3;


    public VistaMenu(ControladorMenu controladorMenu) {
        SwingUtilities.invokeLater(this::inicializarComponentes);
        this.controladorMenu = controladorMenu;
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal que contiene todos los elementos
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);

        // Panel superior con tres botones
        JPanel panelSuperior = new JPanel(new GridLayout(1, 3));
        JButton btnPanel1 = new JButton("Nueva Partida");
        btnPanel1.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
        JButton btnPanel2 = new JButton("Partidas en Curso");
        btnPanel2.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));
        JButton btnPanel3 = new JButton("Partidas Pasadas");
        btnPanel3.setFont(new Font("Source Sans Pro Black", Font.PLAIN, 15));

        panelSuperior.add(btnPanel1);
        panelSuperior.add(btnPanel2);
        panelSuperior.add(btnPanel3);

        // Manejador de eventos para los botones
        btnPanel1.addActionListener(e -> mostrarPanel(panel1));

        //btnPanel2.addActionListener(e -> mostrarPanel(panel2));
        // Puedes agregar lógica para el Panel2 aquí

        //btnPanel3.addActionListener(e -> mostrarPanel(panel3));
            // Puedes agregar lógica para el Panel3 aquí

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Panel de contenido
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        panelContenido.setLayout(new BorderLayout(0,0));
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
}
