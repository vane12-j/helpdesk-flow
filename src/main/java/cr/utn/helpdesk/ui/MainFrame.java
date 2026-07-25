package cr.utn.helpdesk.ui;

import cr.utn.helpdesk.enums.Categoria;
import cr.utn.helpdesk.enums.Impacto;
import cr.utn.helpdesk.enums.Urgencia;
import cr.utn.helpdesk.model.Incidencia;
import cr.utn.helpdesk.service.IncidenciaService;

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

    }

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
        formulario.add(btnRegistrar, gbc);

        add(formulario, BorderLayout.NORTH);

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(
                BorderFactory.createTitledBorder("Incidencias registradas")
        );

        add(scrollTabla, BorderLayout.CENTER);

    }

    private void configurarEventos() {

        btnRegistrar.addActionListener(e -> registrarIncidencia());

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
                    incidencia.getPrioridad()
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

}