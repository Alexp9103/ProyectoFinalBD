package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import logico.Comision;
import logico.Evento;
import logico.GestionEvento;
import logico.Recurso;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;

public class RegistrarEvento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnAgregar;
	private JButton btnCancelar;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtUbicacion;
	private JSpinner spnCupo;
	private JSpinner spnFechaInicio;
	private JSpinner spnFechaFin;
	private JTable tabledisponible;
	
	private static Object[] rows;
	private static DefaultTableModel model;
	private static DefaultTableModel model2;
	private static DefaultTableModel model3;
	
	private ArrayList<Recurso> disponible;
	private ArrayList<Recurso> agregados;
	private ArrayList<Comision> comisionesagregadas;
	
	private JTable tableagregados;
	private JButton btnAgregarComision;
	private JTable tablaComisiones;
	private JButton btnIsquierda;
	private JButton btnDerecha;
	
	private Recurso rec1;
	private Recurso rec2;
	private JButton btnEliminar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarEvento dialog = new RegistrarEvento();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarEvento() {
		disponible = new ArrayList<>();
		agregados = new ArrayList<>();
		disponible.addAll(GestionEvento.getInstance().getRecursos());
		
		setTitle("Registro de Eventos");
		setBounds(100, 100, 651, 671);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.controlShadow);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(12, 13, 609, 148);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("C\u00F3digo:");
		lblNewLabel.setBounds(12, 13, 56, 16);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(12, 58, 56, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cupo:");
		lblNewLabel_2.setBounds(290, 13, 56, 16);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Ubicaci\u00F3n:");
		lblNewLabel_3.setBounds(290, 58, 71, 16);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Fecha de inicio:");
		lblNewLabel_4.setBounds(12, 98, 99, 16);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Fecha de conclusi\u00F3n:");
		lblNewLabel_5.setBounds(290, 98, 128, 16);
		panel.add(lblNewLabel_5);
		
		txtCodigo = new JTextField();
		txtCodigo.setEnabled(false);
		txtCodigo.setBounds(71, 10, 116, 22);
		panel.add(txtCodigo);
		txtCodigo.setColumns(10);
		txtCodigo.setText("Event-"+GestionEvento.getInstance().getCodevento());
		
		txtNombre = new JTextField();
		txtNombre.setBounds(71, 55, 187, 22);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		spnCupo = new JSpinner();
	    spnCupo.setBounds(353, 10, 61, 22);
	    panel.add(spnCupo);


	    SpinnerNumberModel cupoModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
	    spnCupo.setModel(cupoModel);
	    

	    spnCupo.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {

	            int value = (int) spnCupo.getValue();
	            if (value < 0) {
	                spnCupo.setValue(0);  
	            }
	        }
	    });
		
		txtUbicacion = new JTextField();
		txtUbicacion.setBounds(353, 55, 244, 22);
		panel.add(txtUbicacion);
		txtUbicacion.setColumns(10);
		
		spnFechaInicio = new JSpinner();
		spnFechaInicio.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		spnFechaInicio.setBounds(106, 95, 140, 22);
		panel.add(spnFechaInicio);

		spnFechaFin = new JSpinner();
		spnFechaFin.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		spnFechaFin.setBounds(414, 95, 140, 22);
		panel.add(spnFechaFin);

		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(12, 387, 609, 200);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_6 = new JLabel("Recursos:");
		lblNewLabel_6.setBounds(3, 0, 71, 16);
		panel_1.add(lblNewLabel_6);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 29, 210, 162);
		panel_1.add(scrollPane);
		
		model = new DefaultTableModel();
		String[] columnas = {"Código","Tipo"};
		model.setColumnIdentifiers(columnas);
		tabledisponible = new JTable();
		tabledisponible.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int rowSelected = -1;
				rowSelected = tabledisponible.getSelectedRow();
				if(rowSelected>=0){
					btnDerecha.setEnabled(true);
					rec1 = GestionEvento.getInstance().buscarrecurso(tabledisponible.getValueAt(rowSelected, 0).toString());
				}
			}
		});
		scrollPane.setViewportView(tabledisponible);
		tabledisponible.setModel(model);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(372, 29, 210, 162);
		panel_1.add(scrollPane_1);
		
		model2 = new DefaultTableModel();
		model2.setColumnIdentifiers(columnas);
		tableagregados = new JTable();
		tableagregados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int rowSelected = -1;
				rowSelected = tableagregados.getSelectedRow();
				if(rowSelected>=0){
					btnIsquierda.setEnabled(true);
					rec2 = GestionEvento.getInstance().buscarrecurso(tableagregados.getValueAt(rowSelected, 0).toString());
				}
			}
		});
		scrollPane_1.setViewportView(tableagregados);
		tableagregados.setModel(model2);
		
		JLabel lblNewLabel_7 = new JLabel("Disponibles:");
		lblNewLabel_7.setBounds(104, 13, 84, 16);
		panel_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Agregados:");
		lblNewLabel_8.setBounds(435, 13, 84, 16);
		panel_1.add(lblNewLabel_8);
		
		btnDerecha = new JButton("Añadir");
		btnDerecha.setEnabled(false);
		btnDerecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregados.add(rec1);
				eliminardisponible(rec1);
				btnDerecha.setEnabled(false);
				cargardatos();
				
			}
		});
		btnDerecha.setBounds(268, 62, 85, 25);
		panel_1.add(btnDerecha);
		
		btnIsquierda = new JButton("Quitar");
		btnIsquierda.setEnabled(false);
		btnIsquierda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disponible.add(rec2);
				eliminaragregado(rec2);
				btnIsquierda.setEnabled(false);
				cargardatos();		
			}
		});
		btnIsquierda.setBounds(268, 122, 85, 25);
		panel_1.add(btnIsquierda);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(12, 174, 609, 200);
		contentPanel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_9 = new JLabel("Comisiones:");
		lblNewLabel_9.setBounds(3, 0, 80, 16);
		panel_2.add(lblNewLabel_9);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(247, 29, 334, 158);
		panel_2.add(scrollPane_2);
		
		model3 = new DefaultTableModel();
		String[] columnas1 = {"Código","Área","Presidente"};
		model3.setColumnIdentifiers(columnas1);
		tablaComisiones = new JTable();
		scrollPane_2.setViewportView(tablaComisiones);
		tablaComisiones.setModel(model3);
		
		btnAgregarComision = new JButton("Agregar");
		btnAgregarComision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegistrarComision aux = new RegistrarComision();
				GestionEvento.getInstance().guardarDatos("DatosEventosPUCMM.dat");
				aux.setModal(true);
				aux.setVisible(true);
				cargardatos();
			}
		});
		btnAgregarComision.setBounds(68, 55, 97, 25);
		panel_2.add(btnAgregarComision);
		
		JLabel lblNewLabel_10 = new JLabel("Seleccionados:");
		lblNewLabel_10.setBounds(366, 10, 97, 16);
		panel_2.add(lblNewLabel_10);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(68, 125, 97, 25);
		panel_2.add(btnEliminar);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.controlShadow);
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnAgregar = new JButton("Agregar");
				btnAgregar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						agregarevento();
						GestionEvento.getInstance().guardarDatos("DatosEventosPUCMM.dat");
					}
				});
				btnAgregar.setActionCommand("OK");
				buttonPane.add(btnAgregar);
				getRootPane().setDefaultButton(btnAgregar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						GestionEvento.getInstance().getcomisionesaux().clear();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
		cargardatos();
	}

	
	public void cargardatos() {

	    if (model == null || model2 == null || model3 == null) {
	        return; 
	    }

	    if (disponible == null) {
	        disponible = new ArrayList<>();
	    }
	    if (agregados == null) {
	        agregados = new ArrayList<>();
	    }

	    model.setRowCount(0);
	    model2.setRowCount(0);
	    model3.setRowCount(0);

	    rows = new Object[model.getColumnCount()];

	    if (disponible != null) {
	        for (Recurso rec : disponible) {
	            if (rec != null && rec.getdisponible() == true) {
	                rows[0] = rec.getCodigo();
	                rows[1] = rec.getTipo();
	                model.addRow(rows);    
	            }
	        }
	    }

	    if (agregados != null) {
	        for (Recurso rec : agregados) {
	            if (rec != null) {
	                rows[0] = rec.getCodigo();
	                rows[1] = rec.getTipo();
	                model2.addRow(rows);    
	            }
	        }
	    }

	    rows = new Object[model3.getColumnCount()];

	    if (GestionEvento.getInstance().getcomisionesaux() != null) {
	        for (Comision com : GestionEvento.getInstance().getcomisionesaux()) {
	            if (com != null) {
	                rows[0] = com.getIdcomision();
	                rows[1] = com.getArea();
	                rows[2] = com.getPresidente().getNombre();
	                model3.addRow(rows);
	            }
	        }
	    }
	}

	
	public void agregarevento(){
		
		if(!(txtNombre.getText().equals("")))
		{
			if(!(txtUbicacion.getText().equals("")))
			{
				if(GestionEvento.getInstance().getcomisionesaux().size() != 0)
				{
					Evento aux = new Evento(txtNombre.getText(), txtCodigo.getText(), txtUbicacion.getText()
							, spnFechaInicio.getValue().toString(), spnFechaFin.getValue().toString(), 
							Integer.parseInt(spnCupo.getValue().toString()));
					aux.setComisiones(GestionEvento.getInstance().getcomisionesaux());
					aux.setRecursos(agregados);
					GestionEvento.getInstance().agregarevento(aux);
					GestionEvento.getInstance().guardarDatos("DatosEventosPUCMM.dat");
					JOptionPane.showMessageDialog(null, "Evento creado!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
					
	
					for(Comision com : GestionEvento.getInstance().getcomisionesaux()) 
					{
						GestionEvento.getInstance().agregarcomisiones(com);
					}
					clear();
				}
				else
					JOptionPane.showMessageDialog(null, "Escoja al menos una comisión!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null, "Debe colocar una ubicación!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(null, "Debe colocar un nombre!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	
	public void clear(){
		txtCodigo.setText("Event-"+GestionEvento.getInstance().getCodevento());
		txtNombre.setText("");
		txtUbicacion.setText("");
		
		agregados.clear();
		GestionEvento.getInstance().getcomisionesaux().clear();
		spnCupo.setValue(Integer.valueOf(0));
		cargardatos();

	}
	
	public int inddisponible(Recurso rec) {
		int i = 0;
		boolean encontrado = false;
		
		while(i < disponible.size() && encontrado == false) {
			if(disponible.get(i).getCodigo().equals(rec.getCodigo()))
			{
				encontrado = true;
			}
			i++;
		}
		
		return i-1;
	}
	
	public void eliminardisponible(Recurso rec) {
		int ind = inddisponible(rec);
		disponible.remove(ind);
	}
	
	public int indagregado(Recurso rec) {
		int i = 0;
		boolean encontrado = false;
		
		while(i < agregados.size() && encontrado == false) {
			if(agregados.get(i).getCodigo().equals(rec.getCodigo()))
			{
				encontrado = true;
			}
			i++;
		}
		
		return i-1;
	}
	
	public void eliminaragregado(Recurso rec) {
		int ind = indagregado(rec);
		
		agregados.remove(ind);
	}


}



