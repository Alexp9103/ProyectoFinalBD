package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Comision;
import logico.GestionEvento;
import logico.Jurado;
import logico.Participante;
import logico.Persona;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.awt.SystemColor;

public class RegistrarComision extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtcodigo;
	private JTextField txtpresidente;
	private JTable tableselect;
	private JTable tableadd;
	private JButton btnagregarp;
	private DefaultTableModel modeltable,modeltableadd;
	private Object[] rowsadd,rowsselect;
	private int seleccionado=-1;
	private JComboBox cmbarea;
	private JButton btnenviar;
	private JButton btneliminar;
	private Jurado presidenteJurado=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarComision dialog = new RegistrarComision();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarComision() {

		setTitle("Registrar Comision");
		setBounds(100, 100, 544, 458);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.controlShadow);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Codigo:");
		lblNewLabel.setBounds(64, 30, 52, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Área de trabajo:");
		lblNewLabel_1.setBounds(19, 75, 97, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtcodigo = new JTextField();
		txtcodigo.setText("Com-"+GestionEvento.getInstance().getCodcomision());
		txtcodigo.setEditable(false);
		txtcodigo.setBounds(116, 27, 125, 20);
		contentPanel.add(txtcodigo);
		txtcodigo.setColumns(10);
		
		cmbarea = new JComboBox();
		cmbarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadtable();
				modeltableadd.setRowCount(0);
				txtpresidente.setText("");
			}
		});
		cmbarea.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Mec\u00E1nica Newtoniana", "Biolog\u00EDa avanzada", "Física cu\u00E1ntica", "Ingenier\u00EDa Industrial", "Ingenier\u00EDa Telem\u00E1tica", "Algebra Lineal"}));
		cmbarea.setBounds(116, 72, 125, 20);
		contentPanel.add(cmbarea);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBorder(new TitledBorder(null, "Jurados:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 126, 505, 252);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBorder(new TitledBorder(null, "Jurados disponibles:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 38, 210, 172);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
			}
		});
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		modeltable=new DefaultTableModel();
		String[] columnas= {"Codigo","Nombre"};
		modeltable.setColumnIdentifiers(columnas);
		tableselect = new JTable();
		tableselect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				seleccionado=tableselect.getSelectedRow();
				if(seleccionado>=0) {
					btneliminar.setEnabled(false);
					btnenviar.setEnabled(true);
				}
			}
		});
		tableselect.setModel(modeltable);
		scrollPane.setViewportView(tableselect);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new TitledBorder(null, "Jurados generados:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(285, 38, 210, 172);
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		
		tableadd = new JTable();
		tableadd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				seleccionado=tableadd.getSelectedRow();
				if(seleccionado>=0) {
					btneliminar.setEnabled(true);
					btnenviar.setEnabled(false);	
					btnagregarp.setEnabled(true);
				}
			}
		});
		modeltableadd=new DefaultTableModel();
		String[] columnasadd= {"Codigo","Nombre"};
		modeltableadd.setColumnIdentifiers(columnasadd);
		tableadd.setModel(modeltableadd);
		scrollPane_1.setViewportView(tableadd);
		
		JLabel lblNewLabel_3 = new JLabel("Presidente:");
		lblNewLabel_3.setBounds(20, 227, 70, 14);
		panel.add(lblNewLabel_3);
		
		txtpresidente = new JTextField();
		txtpresidente.setEditable(false);
		txtpresidente.setBounds(89, 224, 131, 20);
		panel.add(txtpresidente);
		txtpresidente.setColumns(10);
		
		btnagregarp = new JButton("Agregar presidente");
		btnagregarp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seleccionado>=0) {
					presidenteJurado = GestionEvento.getInstance().buscarJurado(modeltableadd.getValueAt(seleccionado, 0).toString());
					if(presidenteJurado!=null ) {
						txtpresidente.setText(presidenteJurado.getNombre());
					}else {
						JOptionPane.showMessageDialog(null, "Presidente anteriormente asignado a la misma comision", "Error", JOptionPane.OK_OPTION);
					}
				}
				btnagregarp.setEnabled(false);
			}
		});
		btnagregarp.setEnabled(false);
		btnagregarp.setBounds(340, 218, 155, 23);
		panel.add(btnagregarp);
		
		btnenviar = new JButton(">>");
		btnenviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seleccionado>=0) {
					rowsadd=new Object[modeltableadd.getColumnCount()];
					rowsadd[0]=modeltable.getValueAt(seleccionado, 0).toString();
					rowsadd[1]=modeltable.getValueAt(seleccionado, 1).toString();
					modeltableadd.addRow(rowsadd);
					modeltable.removeRow(seleccionado);
					btnenviar.setEnabled(false);
				}
			}
		});
		btnenviar.setEnabled(false);
		btnenviar.setBounds(224, 79, 55, 23);
		panel.add(btnenviar);
		
		btneliminar = new JButton("<<");
		btneliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(seleccionado>=0) {
					rowsselect[0]=modeltableadd.getValueAt(seleccionado, 0).toString();
					rowsselect[1]=modeltableadd.getValueAt(seleccionado, 1).toString();
					modeltable.addRow(rowsselect);
					modeltableadd.removeRow(seleccionado);
					btneliminar.setEnabled(false);
				}
			}
		});
		btneliminar.setEnabled(false);
		btneliminar.setBounds(224, 131, 55, 23);
		panel.add(btneliminar);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.controlShadow);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnagregar = new JButton("Agregar");
				btnagregar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
				            
				            if (presidenteJurado == null) {
				                JOptionPane.showMessageDialog(null, 
				                    "No se puede crear una comisión sin al menos un presidente", 
				                    "Error", 
				                    JOptionPane.ERROR_MESSAGE);
				                return;
				            }
				        
				            if (txtcodigo.getText().trim().isEmpty() || cmbarea.getSelectedItem() == null) {
				                JOptionPane.showMessageDialog(null, 
				                    "Debe llenar todos los campos obligatorios", 
				                    "Error", 
				                    JOptionPane.ERROR_MESSAGE);
				                return;
				            }

				            Comision comision = new Comision(txtcodigo.getText().trim(), 
				                                             cmbarea.getSelectedItem().toString(), 
				                                             presidenteJurado);

				            if (modeltableadd.getRowCount() == 0) {
				                JOptionPane.showMessageDialog(null, 
				                    "Debe agregar al menos un jurado a la comisión", 
				                    "Error", 
				                    JOptionPane.ERROR_MESSAGE);
				                return;
				            }

				            for (int i = 0; i < modeltableadd.getRowCount(); i++) {
				                String codigoJurado = modeltableadd.getValueAt(i, 0).toString();
				                Jurado juradoaux = GestionEvento.getInstance().buscarJurado(codigoJurado);
				                if (juradoaux != null && juradoaux != presidenteJurado) {
				                    comision.agregarjurados(juradoaux);
				                }
				            }

				            if (comision.getJurados().size() == 0) {
				                JOptionPane.showMessageDialog(null, 
				                    "Ingrese más de un jurado para cada comisión", 
				                    "Aviso", 
				                    JOptionPane.INFORMATION_MESSAGE);
				                return;
				            }

				            GestionEvento.getInstance().agregarcomisionesaux(comision);
				            GestionEvento.getInstance().guardarDatos("DatosEventosPUCMM.dat");


				            if (GestionEvento.getInstance().getcomisionesaux().contains(comision)) {
				                JOptionPane.showMessageDialog(null, 
				                    "Comisión registrada correctamente", 
				                    "Aviso", 
				                    JOptionPane.INFORMATION_MESSAGE);
				            } else {
				                JOptionPane.showMessageDialog(null, 
				                    "Hubo un error al registrar la comisión", 
				                    "Error", 
				                    JOptionPane.ERROR_MESSAGE);
				                return;
				            }
				            clean();
				            presidenteJurado = null;

				        } catch (Exception ex) {
				            JOptionPane.showMessageDialog(null, 
				                "Ocurrió un error: " + ex.getMessage(), 
				                "Error", 
				                JOptionPane.ERROR_MESSAGE);
				            ex.printStackTrace(); 
				        }
					}
				});
				btnagregar.setActionCommand("OK");
				buttonPane.add(btnagregar);
				getRootPane().setDefaultButton(btnagregar);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void loadtable() {
		modeltable.setRowCount(0);
		rowsselect =new Object[modeltable.getColumnCount()];
		for (int i = 0; i < GestionEvento.getInstance().getPersonas().size(); i++) {
			if(GestionEvento.getInstance().getPersonas().get(i) instanceof Jurado) {
				if(((Jurado)GestionEvento.getInstance().getPersonas().get(i)).getAreaespecializado().equalsIgnoreCase(String.valueOf(cmbarea.getSelectedItem()))) {
					rowsselect[0]=((Jurado)GestionEvento.getInstance().getPersonas().get(i)).getCodjurado();
					rowsselect[1]=((Jurado)GestionEvento.getInstance().getPersonas().get(i)).getNombre();
					modeltable.addRow(rowsselect);
				}	
			}	
		}
	}
	private void clean() {
		txtcodigo.setText("Com-"+GestionEvento.getInstance().getCodcomision());
		modeltableadd.setRowCount(0);
		txtpresidente.setText("");
		cmbarea.setSelectedItem("<Seleccione>");
	}

	

}
