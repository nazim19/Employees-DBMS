import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
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
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class Login extends JFrame {
	int mx,my;
	public String username;
	public String password;
	public static Connection conn;
	private JPanel contentPane;
	private JTextField usernameFeild;
	private JPasswordField passwordFeild;

	/**hr
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
		setBounds(100, 100, 500, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setUndecorated(true);
		
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mx=e.getX();
				my=e.getY();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x=e.getXOnScreen();
				int y=e.getYOnScreen();
				setLocation(x-mx,y-my);
			}
		});
		panel.setBounds(0, 0, 500, 165);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblClose = new JLabel("close");
		lblClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		lblClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblClose.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClose.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblClose.setForeground(Color.RED);
		lblClose.setBounds(437, 11, 53, 14);
		panel.add(lblClose);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/images/sonic_dock_icons_by_nded.png")));
		lblNewLabel_1.setBounds(150, 0, 200, 165);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 500, 367);
		contentPane.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(Login.class.getResource("/images/f6a469c7ca57d44ff0e2801736b3cd4f.jpg")));
		
		JButton loginBtn = new JButton("");
		loginBtn.setRolloverEnabled(false);
		loginBtn.setRequestFocusEnabled(false);
		loginBtn.setOpaque(false);
		loginBtn.setIgnoreRepaint(true);
		loginBtn.setFocusable(false);
		loginBtn.setFocusTraversalKeysEnabled(false);
		loginBtn.setFocusPainted(false);
		loginBtn.setBorderPainted(false);
		loginBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		loginBtn.setBounds(303, 264, 64, 28);
		loginBtn.setBackground(new Color(51, 153, 0));
		contentPane.add(loginBtn);
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username=usernameFeild.getText();
				password=passwordFeild.getText();
				if(username.equals("hr")&&password.equals("hr"))
				{
				try {
					conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE",username,password);
					dispose();
					Employee emp=new Employee();
					emp.frame.setVisible(true);
				} catch (Exception e1) {}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Incorrect username or password");
				}
			}
		});
		loginBtn.setForeground(Color.WHITE);
		
		usernameFeild = new JTextField();
		usernameFeild.setBorder(new EmptyBorder(0, 0, 0, 0));
		usernameFeild.setBackground(Color.WHITE);
		usernameFeild.setBounds(193, 188, 174, 20);
		contentPane.add(usernameFeild);
		usernameFeild.setColumns(10);
		
		passwordFeild = new JPasswordField();
		passwordFeild.setBorder(new EmptyBorder(0, 0, 0, 0));
		passwordFeild.setBounds(192, 229, 175, 20);
		contentPane.add(passwordFeild);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(0, 0, 40, 32);
		contentPane.add(label_1);
	}
}
