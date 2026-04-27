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
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import logico.GestionEvento;
import logico.Recurso;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class RegistrarRecurso extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnAgregar;
	private JButton btnCancelar;
	private JTextField txtCodigo;
	private JTextField txtUbicacion;
	private JComboBox BoxTipo;
	private JLabel label4;
	private JEditorPane ptxtDescripcion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarRecurso dialog = new RegistrarRecurso();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarRecurso() {
		setTitle("Agregar recurso");
		setBounds(100, 100, 334, 317);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.controlShadow);
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("C\u00F3digo:");
		lblNewLabel.setBounds(12, 19, 56, 16);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Ubicaci\u00F3n:");
		lblNewLabel_1.setBounds(12, 62, 79, 16);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Tipo:");
		lblNewLabel_2.setBounds(12, 106, 39, 16);
		contentPanel.add(lblNewLabel_2);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(10, 34, 298, 22);
		contentPanel.add(txtCodigo);
		txtCodigo.setColumns(10);
		txtCodigo.setText("Recur-"+GestionEvento.getInstance().getCodrecurso());
		
		txtUbicacion = new JTextField();
		txtUbicacion.setBounds(12, 84, 296, 22);
		contentPanel.add(txtUbicacion);
		txtUbicacion.setColumns(10);
		
		BoxTipo = new JComboBox();
		BoxTipo.setModel(new DefaultComboBoxModel(new String[] {"<Seleccionar>", "Microfono", "Bocina", "Pantalla", "Cable ETHERNET", "Cable fibra", "Power Cord", "Cable HDMI a HDMI", "Cable tipo C a HDMI", "Cable VGA a HDMI", "Modem", "Switch", "Proyector", "Pizarra", "Marcadores"}));
		BoxTipo.setToolTipText("");
		BoxTipo.setBounds(12, 129, 296, 22);
		contentPanel.add(BoxTipo);
		
		label4 = new JLabel("Descripci\u00F3n:");
		label4.setBounds(10, 162, 99, 16);
		contentPanel.add(label4);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 180, 292, 50);
		contentPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		ptxtDescripcion = new JEditorPane();
		ptxtDescripcion.setBounds(12, 180, 106, 27);
		contentPanel.add(ptxtDescripcion);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.activeCaption);
			buttonPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnAgregar = new JButton("Agregar");
				btnAgregar.setBackground(Color.WHITE);
				btnAgregar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						registrarrecurso();
						GestionEvento.getInstance().guardarDatos("DatosEventosPUCMM.dat");
					}
				});
				btnAgregar.setActionCommand("OK");
				buttonPane.add(btnAgregar);
				getRootPane().setDefaultButton(btnAgregar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.setBackground(Color.WHITE);
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
	
	public void registrarrecurso() {
		
		if(!(txtUbicacion.getText().equals("")))
		{
			if(BoxTipo.getSelectedIndex() != 0) 
			{
				Recurso rec = new Recurso(txtCodigo.getText(),true, txtUbicacion.getText(), BoxTipo.getSelectedItem().toString(),ptxtDescripcion.getText());
				GestionEvento.getInstance().agregarrecurso(rec);
				GestionEvento.getInstance().guardarDatos("DatosEventosPUCMM.dat");
				JOptionPane.showMessageDialog(null, "Recurso agregado!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				clear();
			}
			else
				JOptionPane.showMessageDialog(null, "Debe seleccionar un tipo!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(null, "Debe colocar una ubicación!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public void clear() {
		txtCodigo.setText("Recur-"+GestionEvento.getInstance().getCodrecurso());
		txtUbicacion.setText("");
		BoxTipo.setSelectedIndex(0);
		ptxtDescripcion.setText("");
	}
}
