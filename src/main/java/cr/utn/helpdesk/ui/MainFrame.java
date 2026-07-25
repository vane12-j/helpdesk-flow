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

//Aquí esta toda la parte visual del formulario, para verlo corriendo es en el archivo main.java
public class MainFrame extends JFrame {

    private final IncidenciaService service = new IncidenciaService();
    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JComboBox<Categoria> cbCategoria;
    private JComboBox<Impacto> cbImpacto;
    private JComboBox<Urgencia> cbUrgencia;
    private JButton btnRegistrar;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JTextField txtTituloSeleccionado;
    private JTextField txtEstadoSeleccionado;
    private JTextField txtFechaCreacion;
    private JTextArea txtSolucion;
    private JButton btnCerrarIncidencia;
    private Incidencia incidenciaSeleccionada;
    private JComboBox<cr.utn.helpdesk.enums.Estado> cbNuevoEstado;
    private JButton btnActualizarEstado;
    private JButton btnResolver;
    public MainFrame() {

        configurarVentana();
        inicializarComponentes();
        agregarComponentes();
        configurarEventos();
        setVisible(true);

    }

    private void configurarVentana() {

        setTitle("HelpDesk Flow");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void inicializarComponentes() {

        txtTitulo = new JTextField(25);

        txtDescripcion = new JTextArea(4,25);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

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

        //metodo para que la tabla del formulario no se pueda editar
        modeloTabla = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Estado");
        modeloTabla.addColumn("Prioridad");
        modeloTabla.addColumn("Fecha creación");
        modeloTabla.addColumn("Fecha cierre");

        tabla = new JTable(modeloTabla);

        // No permitir mover columnas
        tabla.getTableHeader().setReorderingAllowed(false);

        // Solo seleccionar una fila
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(120);

    }

    private void agregarComponentes() {

        // Panel Registrar Incidencia
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
        formulario.add(btnRegistrar, gbc);
        gbc.gridx = 2;
        formulario.add(btnResolver, gbc);

        add(formulario, BorderLayout.NORTH);

        // Tabla
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(
                BorderFactory.createTitledBorder("Incidencias registradas")
        );
        add(scrollTabla, BorderLayout.CENTER);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;

    }

    private void configurarEventos() {

        btnRegistrar.addActionListener(e -> registrarIncidencia());

        btnResolver.addActionListener(e -> abrirResolver());

    }

    private void registrarIncidencia() {

        try {

            Incidencia incidencia = service.registrarIncidencia(
                    txtTitulo.getText(),
                    txtDescripcion.getText(),
                    (Categoria) cbCategoria.getSelectedItem(),
                    (Impacto) cbImpacto.getSelectedItem(),
                    (Urgencia) cbUrgencia.getSelectedItem()
            );

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

    private void limpiarFormulario() {

        txtTitulo.setText("");
        txtDescripcion.setText("");

        cbCategoria.setSelectedIndex(0);
        cbImpacto.setSelectedIndex(0);
        cbUrgencia.setSelectedIndex(0);

        txtTitulo.requestFocus();

    }


    private void abrirResolver() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una incidencia."
            );

            return;

        }

        int id = (int) modeloTabla.getValueAt(fila,0);

        Incidencia incidencia = service.buscarPorId(id);

        try {

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