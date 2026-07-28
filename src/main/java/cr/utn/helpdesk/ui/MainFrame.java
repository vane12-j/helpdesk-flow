package cr.utn.helpdesk.ui;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.Incidencia;
import cr.utn.helpdesk.service.IncidenciaService;
import cr.utn.helpdesk.enums.Estado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Desde aquí el usuario registra incidencias y puede seleccionar una para resolverla.
public class MainFrame extends JFrame {

    // Servicio que administra toda la lógica de las incidencias.
    private final IncidenciaService service = new IncidenciaService();

    // Componentes del formulario de registro.
    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JComboBox<Categoria> cbCategoria;
    private JComboBox<Impacto> cbImpacto;
    private JComboBox<Urgencia> cbUrgencia;

    private JButton btnRegistrar;

    // Tabla donde se muestran todas las incidencias registradas.
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Componentes utilizados para trabajar con una incidencia seleccionada.
    private JTextField txtTituloSeleccionado;
    private JTextField txtEstadoSeleccionado;
    private JTextField txtFechaCreacion;
    private JTextArea txtSolucion;
    private JButton btnCerrarIncidencia;
    private Incidencia incidenciaSeleccionada;

    private JComboBox<Estado> cbNuevoEstado;
    private JButton btnActualizarEstado;

    // Botón que abre la ventana ResolverIncidenciaFrame.
    private JButton btnResolver;

    public MainFrame() {

        // Configura la ventana.
        configurarVentana();

        // Crea todos los componentes gráficos.
        inicializarComponentes();

        // Agrega los componentes a la interfaz.
        agregarComponentes();

        // Asocia los eventos de los botones.
        configurarEventos();

        setVisible(true);

    }

    // Configuración general de la ventana principal.
    private void configurarVentana() {

        setTitle("HelpDesk Flow");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    // Inicializa todos los controles del formulario.
    private void inicializarComponentes() {

        txtTitulo = new JTextField(25);

        txtDescripcion = new JTextArea(4,25);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        // Los ComboBox cargan automáticamente todos los valores de sus respectivos enums.
        cbCategoria = new JComboBox<>(Categoria.values());
        cbImpacto = new JComboBox<>(Impacto.values());
        cbUrgencia = new JComboBox<>(Urgencia.values());

        btnRegistrar = new JButton("Registrar incidencia");

        txtTituloSeleccionado = new JTextField(25);
        txtTituloSeleccionado.setEditable(false);

        btnResolver = new JButton("Resolver incidencia");

        txtEstadoSeleccionado = new JTextField(20);
        txtEstadoSeleccionado.setEditable(false);

        txtFechaCreacion = new JTextField(15);
        txtFechaCreacion.setEditable(false);

        txtSolucion = new JTextArea(4,25);
        txtSolucion.setLineWrap(true);
        txtSolucion.setWrapStyleWord(true);

        btnCerrarIncidencia = new JButton("Cerrar incidencia");

        cbNuevoEstado = new JComboBox<>();
        btnActualizarEstado = new JButton("Actualizar estado");

        // Modelo de la tabla.
        // Se sobrescribe isCellEditable() para impedir que el usuario edite las celdas.
        modeloTabla = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Se definen las columnas de la tabla.
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Prioridad");
        modeloTabla.addColumn("Fecha creación");
        modeloTabla.addColumn("Fecha cierre");

        tabla = new JTable(modeloTabla);

        // No permitir mover columnas.
        tabla.getTableHeader().setReorderingAllowed(false);

        // Solo se puede seleccionar una incidencia a la vez.
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Se ajusta el ancho de las columnas para mejorar la visualización.
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(120);

    }

    // Construye la interfaz gráfica agregando todos los componentes.
    private void agregarComponentes() {

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Registrar incidencia"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formulario.add(new JLabel("Título:"), gbc);

        gbc.gridx = 1;
        formulario.add(txtTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formulario.add(new JLabel("Descripción:"), gbc);

        gbc.gridx = 1;
        formulario.add(new JScrollPane(txtDescripcion), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formulario.add(new JLabel("Categoría:"), gbc);

        gbc.gridx = 1;
        formulario.add(cbCategoria, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formulario.add(new JLabel("Impacto:"), gbc);

        gbc.gridx = 1;
        formulario.add(cbImpacto, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formulario.add(new JLabel("Urgencia:"), gbc);

        gbc.gridx = 1;
        formulario.add(cbUrgencia, gbc);

        gbc.gridx = 1;
        gbc.gridy++;

        // Botón para registrar una nueva incidencia.
        formulario.add(btnRegistrar, gbc);

        gbc.gridx = 2;

        // Botón para abrir la ventana donde se resuelve una incidencia.
        formulario.add(btnResolver, gbc);

        add(formulario, BorderLayout.NORTH);

        // Tabla donde se muestran todas las incidencias registradas.
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(
                BorderFactory.createTitledBorder("Incidencias registradas")
        );
        add(scrollTabla, BorderLayout.CENTER);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;

    }

    // Asocia cada botón con la acción correspondiente.
    private void configurarEventos() {

        btnRegistrar.addActionListener(e -> registrarIncidencia());

        btnResolver.addActionListener(e -> abrirResolver());

    }

    // Registra una nueva incidencia utilizando la información del formulario.
    private void registrarIncidencia() {

        try {

            Incidencia incidencia = service.registrarIncidencia(
                    txtTitulo.getText(),
                    txtDescripcion.getText(),
                    (Categoria) cbCategoria.getSelectedItem(),
                    (Impacto) cbImpacto.getSelectedItem(),
                    (Urgencia) cbUrgencia.getSelectedItem()
            );

            // Agrega la nueva incidencia a la tabla.
            modeloTabla.addRow(new Object[]{
                    incidencia.getId(),
                    incidencia.getTitulo(),
                    incidencia.getCategoria(),
                    incidencia.getEstado(),
                    incidencia.getPrioridad(),
                    incidencia.getFechaCreacion(),
                    ""
            });

            limpiarFormulario();

            JOptionPane.showMessageDialog(
                    this,
                    "Incidencia #" + incidencia.getId() + " registrada correctamente."
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }

    // Limpia los controles para permitir registrar una nueva incidencia.
    private void limpiarFormulario() {

        txtTitulo.setText("");
        txtDescripcion.setText("");

        cbCategoria.setSelectedIndex(0);
        cbImpacto.setSelectedIndex(0);
        cbUrgencia.setSelectedIndex(0);

        // Devuelve el foco al campo título para facilitar el siguiente registro.
        txtTitulo.requestFocus();

    }

    // Abre la ventana para resolver la incidencia seleccionada.
    private void abrirResolver() {

        int fila = tabla.getSelectedRow();

        // Verifica que el usuario haya seleccionado una fila.
        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una incidencia."
            );

            return;

        }

        // Obtiene el ID de la incidencia seleccionada.
        int id = (int) modeloTabla.getValueAt(fila,0);

        // Busca la incidencia completa en el servicio.
        Incidencia incidencia = service.buscarPorId(id);

        try {

            // Abre la ventana ResolverIncidenciaFrame enviando la incidencia seleccionada.
            new ResolverIncidenciaFrame(
                    this,
                    service,
                    incidencia,
                    modeloTabla,
                    fila
            );

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    ex.toString()
            );

        }

    }

}