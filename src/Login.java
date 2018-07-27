import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Font;

public class Login extends JFrame {
	public String username;
	public String password;
	public static Connection conn;

	private JPanel contentPane;
	private JTextField usernameFeild;
	private JPasswordField passwordFeild;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setTitle("Login into Database");
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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserName = new JLabel("User name");
		lblUserName.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 13));
		lblUserName.setBounds(200, 58, 88, 14);
		contentPane.add(lblUserName);
		
		usernameFeild = new JTextField();
		usernameFeild.setBounds(288, 52, 136, 20);
		contentPane.add(usernameFeild);
		usernameFeild.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 13));
		lblPassword.setBounds(200, 95, 88, 14);
		contentPane.add(lblPassword);
		
		passwordFeild = new JPasswordField();
		passwordFeild.setBounds(288, 93, 136, 20);
		contentPane.add(passwordFeild);
		
		JButton loginBtn = new JButton("");
		loginBtn.setIcon(new ImageIcon("M:\\java\\Emoloyee\\images\\login-button.png"));
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username=usernameFeild.getText();
				password=passwordFeild.getText();
				try {
					conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE",username,password);
					dispose();
					Employee emp=new Employee();
					emp.setVisible(true);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Incorrect username or password");
				}
			}
		});
		loginBtn.setForeground(SystemColor.desktop);
		loginBtn.setBounds(234, 162, 123, 35);
		contentPane.add(loginBtn);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("M:\\java\\Emoloyee\\images\\img_311846.png"));
		label.setBounds(10, 11, 170, 170);
		contentPane.add(label);
	}
}
