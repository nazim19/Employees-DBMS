import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class EmpInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmpInfo frame = new EmpInfo();
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
	public EmpInfo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(36, 35, 46, 14);
		contentPane.add(lblName);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(36, 61, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(120, 35, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(120, 61, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblPhoto = new JLabel("Photo");
		lblPhoto.setBounds(324, 35, 80, 77);
		contentPane.add(lblPhoto);
	}
}
