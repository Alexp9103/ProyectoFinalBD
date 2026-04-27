package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import logico.GestionEvento;
import visual.MenuPrincipal;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;

public class Login extends JFrame {

	private JPanel contentPane;
	private JFormattedTextField ftxtUsuario;
	private JFormattedTextField ftxtContrasena;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		logico.GestionEvento.cargarDatos("DatosEventosPUCMM.dat");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 349, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Gestion Evento PUCMM");
		lblNewLabel.setFont(new Font("Candara", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 5, 297, 51);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setFont(new Font("Candara", Font.BOLD, 14));
		lblNewLabel_1.setBounds(128, 77, 56, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a");
		lblNewLabel_2.setFont(new Font("Candara", Font.BOLD, 14));
		lblNewLabel_2.setBounds(119, 130, 75, 16);
		panel.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Iniciar seci\u00F3n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(GestionEvento.getInstance().confirmLogin(ftxtUsuario.getText(),ftxtContrasena.getText())){
					MenuPrincipal frame = new MenuPrincipal();
					dispose();
					frame.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(null, "Usuario o contrase�a incorrecta", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton.setBounds(91, 205, 131, 25);
		panel.add(btnNewButton);
		ftxtUsuario = new JFormattedTextField();
		ftxtUsuario.setToolTipText("");
		ftxtUsuario.setBounds(76, 100, 161, 19);
		panel.add(ftxtUsuario);
		
		ftxtContrasena = new JFormattedTextField();
		ftxtContrasena.setBounds(76, 159, 161, 19);
		panel.add(ftxtContrasena);
		setLocationRelativeTo(null);
	}
}

