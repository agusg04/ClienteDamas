package pspro.damas.vista.menu;

import modelo.Partida;
import pspro.damas.controlador.Controlador;
import pspro.damas.vista.tablero.TableroFrame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Panel2 extends JPanel {

    HashMap<Integer, Partida> partidasOnline;
    HashMap<Integer, Partida> partidasOffline;
    HashMap<Integer, TableroFrame> tablerosCreados = new HashMap<>();
    private final Controlador controlador;
    private JList<String> listaPartidasOffline;
    private JList<String> listaPartidasOnLine;

    public Panel2(Controlador controlador) {
        this.controlador = controlador;
        SwingUtilities.invokeLater(this::inicializarComponentes);
        setVisible(true);
    }

    private void inicializarComponentes() {

        // Inicializar las listas
        listaPartidasOffline = new JList<>();
        listaPartidasOnLine = new JList<>();

        // Scroll para partidas
        JScrollPane scrollPartidasOnline = crearScrollPane(listaPartidasOnLine);
        JScrollPane scrollPartidasOffline = crearScrollPane(listaPartidasOffline);

        // Etiquetas para las listas
        JLabel labelPartidasOnline = crearEtiqueta("Partidas Online");
        JLabel labelPartidasOffline = crearEtiqueta("Partidas Offline");

        // Panel para listas con etiquetas
        JPanel panelListas = new JPanel(new GridLayout(2, 1, 10, 10));// 2 filas, 1 columna, espacio entre componentes
        panelListas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelListas.add(crearPanelConEtiqueta(labelPartidasOnline, scrollPartidasOnline));
        panelListas.add(crearPanelConEtiqueta(labelPartidasOffline, scrollPartidasOffline));

        // Botones para cargar partidas
        JButton btnCargarPartidaOnline = crearBoton("Cargar Partida Online", e -> cargarTableroOnlineSeleccionado());
        JButton btnCargarPartidaOffline = crearBoton("Cargar Partida Offline", e -> cargarTableroOfflineSeleccionado());

        // Panel para los botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10)); // 2 filas, 1 columna, espacio entre botones
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotones.add(btnCargarPartidaOnline);
        panelBotones.add(btnCargarPartidaOffline);

        // Agregar los paneles al Panel2
        add(panelListas, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.EAST);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setPreferredSize(new Dimension(200, 30));
        return etiqueta;
    }

    private JScrollPane crearScrollPane(JList<String> lista) {
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setPreferredSize(new Dimension(300, 250));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    private JPanel crearPanelConEtiqueta(JLabel etiqueta, JScrollPane scrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JButton crearBoton(String texto, java.awt.event.ActionListener listener) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(new Dimension(200, 30));
        boton.addActionListener(listener);
        return boton;
    }

    public void actualizarPartidas(Map<Integer, Partida> partidas, boolean online) {
        if (online) {
            partidasOnline = (HashMap<Integer, Partida>) partidas;
            listaPartidasOnLine.setModel(crearModeloLista(partidasOnline));
            listaPartidasOnLine.setCellRenderer(crearListCellRenderer(partidasOnline));
        } else {
            partidasOffline = (HashMap<Integer, Partida>) partidas;
            listaPartidasOffline.setModel(crearModeloLista(partidasOffline));
            listaPartidasOffline.setCellRenderer(crearListCellRenderer(partidasOffline));
        }
    }

    private DefaultListModel<String> crearModeloLista(HashMap<Integer, Partida> partidas) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Partida partida : partidas.values()) {
            model.addElement("Partida " + partida.getIdPartida());
        }
        return model;
    }

    private DefaultListCellRenderer crearListCellRenderer(Map<Integer, Partida> partidas) {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                String partidaLabel = (String) value;
                int idPartida = Integer.parseInt(partidaLabel.replace("Partida ", "").trim());

                Partida partida = partidas.get(idPartida);

                if (partida == null) {
                    return c;
                }

                if (isSelected) {
                    setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    setForeground(Color.WHITE);
                } else {
                    setBorder(null);
                }

                if (partida.esTuTurno(controlador.getIdUsuario())) {
                    c.setBackground(Color.GREEN);
                } else {
                    c.setBackground(Color.RED);
                }

                return c;
            }
        };
    }

    private void cargarTableroOnlineSeleccionado() {
        cargarTablero(listaPartidasOnLine.getSelectedValue(), true);
    }

    private void cargarTableroOfflineSeleccionado() {
        cargarTablero(listaPartidasOffline.getSelectedValue(), false);
    }

    private void cargarTablero(String nombreSeleccionado, boolean online) {

        if (nombreSeleccionado == null) {
            mostrarMensajeError("Seleccione una partida.");
            return;
        }

        Integer idPartida = extraerIdTablero(nombreSeleccionado);
        if (idPartida == null) {
            mostrarMensajeError("El nombre seleccionado no tiene el formato correcto.");
            return;
        }

        if (estaYaCreado(idPartida)) {
            mostrarMensajeError("El tablero ya está abierto");
            return;
        }

        Partida partidaACargar = encontrarPartidaPorId(idPartida, online);
        if (partidaACargar == null) {
            mostrarMensajeError("No se pudo cargar la partida.");
            return;
        }

        abrirPartida(partidaACargar);
    }

    private Integer extraerIdTablero(String nombreSeleccionado) {
        String[] partes = nombreSeleccionado.split(" ");
        if (partes.length > 1) {
            try {
                return Integer.parseInt(partes[1]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private Partida encontrarPartidaPorId(int idPartida, boolean online) {
        Map<Integer, Partida> partidas = online ? partidasOnline : partidasOffline;
        return partidas.get(idPartida);
    }

    private boolean estaYaCreado(int idPartida) {
        return tablerosCreados.containsKey(idPartida);
    }

    private TableroFrame abrirPartida(Partida partida) {
        TableroFrame tableroFrame = new TableroFrame(partida, this, controlador);
        tablerosCreados.put(partida.getIdPartida(), tableroFrame);
        if (tableroFrame != null) {
            tableroFrame.setVisible(true);
        } else {
            mostrarMensajeError("No se pudo cargar la partida.");
        }
        return null;
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void cerrarTableroInterno(Partida partidaAbierta) {
        if (partidaAbierta == null) {
            mostrarMensajeError("Partida nula, no se puede cerrar el tablero.");
            return;
        }

        Integer idPartida = partidaAbierta.getIdPartida();

        if (idPartida == null) {
            mostrarMensajeError("ID de partida no válido.");
            return;
        }

        tablerosCreados.remove(idPartida);
    }

    public void cerrarTableroExterno(int idTablero) {
        if (tablerosCreados.containsKey(idTablero)) {
            tablerosCreados.get(idTablero).cerrarTablero();
            tablerosCreados.remove(idTablero);
        }
    }

    public void actualizarTablero(int idTableroActualizar) {
        // Obtener el TableroFrame existente
        TableroFrame tableroFrame = tablerosCreados.get(idTableroActualizar);
        if (tableroFrame != null) {
            // Actualizar la partida en el TableroFrame existente
            Partida nuevaPartida = partidasOffline.getOrDefault(idTableroActualizar, partidasOnline.get(idTableroActualizar));
            tableroFrame.setPartida(nuevaPartida);

            // Actualizar los componentes gráficos del TableroFrame
            tableroFrame.actualizarComponentes(nuevaPartida);
            tableroFrame.repaint(); // Asegurar que se repinte correctamente

        }
    }


    public void mostrarMensajeMovimientoInvalido(String mensaje, int idPartida) {
        tablerosCreados.get(idPartida).mostrarMensaje(mensaje);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

}
