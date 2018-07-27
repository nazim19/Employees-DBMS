import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.JTable;
import java.awt.Scrollbar;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;


public class Employee extends JFrame {
	Connection conn=Login.conn;//DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
	private JPanel contentPane;
	private JTextField idFeild;
	private JTextField nameFeild;
	private JTextField noFeild;
	private JTable table_2;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee frame = new Employee();
					frame.setVisible(true);
					frame.setTitle("Employee");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	void refresh() throws SQLException
	{

		PreparedStatement st=conn.prepareStatement("select *from employees");
		ResultSet r=st.executeQuery();
		table_2.setModel(DbUtils.resultSetToTableModel(r));
	}
	public Employee() throws ClassNotFoundException, SQLException {
	
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 965, 744);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 278, 927, 415);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String i=(String) table_2.getValueAt(table_2.getSelectedRow(), 0);
				String s=(String) table_2.getValueAt(table_2.getSelectedRow(), 1);
				String n=(String) table_2.getValueAt(table_2.getSelectedRow(), 2);
				
				idFeild.setText(i);
				nameFeild.setText(s);
				noFeild.setText(n);

			}
		});
		scrollPane.setBounds(10, 22, 905, 431);
		panel.add(scrollPane);
		
		table_2 = new JTable();
		scrollPane.setViewportView(table_2);
		
		JLabel lblDatabase = new JLabel("DATABASE");
		lblDatabase.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatabase.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblDatabase.setBounds(391, 0, 144, 22);
		panel.add(lblDatabase);
		
		
		JLabel lblIncorrectInput = new JLabel("Incorrect input");
		lblIncorrectInput.setForeground(Color.RED);
		lblIncorrectInput.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblIncorrectInput.setBounds(331, 115, 142, 14);
		contentPane.add(lblIncorrectInput);
		lblIncorrectInput.setVisible(false);
		
		refresh();
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String s=noFeild.getText();
					if(s.length()==10)
					{	int flag=0;
							try {
								PreparedStatement stm=conn.prepareStatement("select *from emp");
								ResultSet r=stm.executeQuery();
									while(r.next())
									{
									if(r.getInt(1)==(Integer.parseInt(idFeild.getText()))||r.getLong(3)==Long.parseLong(noFeild.getText()))
									{
										JOptionPane.showMessageDialog(null, "ID or number already exists.");
										flag=1;
									}
									
									}
								} catch (Exception e1) {}
								// TODO Auto-generated catch block				
						if(flag==0)
						{
							try
							{	
								PreparedStatement stmt=conn.prepareStatement("insert into emp values(?,?,?)");					
								stmt.setInt(1, Integer.valueOf(idFeild.getText()));
								stmt.setString(2,nameFeild.getText());
								stmt.setString(3,noFeild.getText());
								ResultSet rs1=stmt.executeQuery();
								refresh();
								JOptionPane.showMessageDialog(null, "New Employee record added.");
								idFeild.setText("");
								nameFeild.setText("");
							}catch(Exception ec){}
						}
					}
				else
				{
					JOptionPane.showMessageDialog(null,"Incorrect details." );
				}
			}	
		});
		
		
		
		btnAdd.setBounds(257, 227, 73, 39);
		contentPane.add(btnAdd);
		
		idFeild = new JTextField();
		idFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c=e.getKeyChar();
				if(!(Character.isDigit(c)))
				{
					e.consume();
				}
			}
		});
		idFeild.setBounds(331, 37, 162, 20);
		contentPane.add(idFeild);
		idFeild.setColumns(10);
		
		nameFeild = new JTextField();
		nameFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!(Character.isAlphabetic(e.getKeyChar())||e.getKeyChar()==KeyEvent.VK_SPACE))
						{
							e.consume();
						}
			}
		});
		nameFeild.setBounds(331, 69, 162, 20);
		contentPane.add(nameFeild);
		nameFeild.setColumns(10);
		
		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(noFeild.getText().length()==10)
				{
				try {
					PreparedStatement stmt=conn.prepareStatement("update emp set name=?,mob_no.=? where ID =?");
					stmt.setString(1,nameFeild.getText());
					stmt.setString(2, noFeild.getText());
					stmt.setInt(3, Integer.parseInt(idFeild.getText()));
					ResultSet rs=stmt.executeQuery();
					refresh();
					JOptionPane.showMessageDialog(null, "Employee record updated.");
					} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Incorrect details.");
				}
			}
		});
		
		JButton button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a=Integer.parseInt(table_2.getValueAt(table_2.getSelectedRow(),0).toString());
				int action=JOptionPane.showConfirmDialog(null, "Do you really want to delete ?","Delete",JOptionPane.YES_NO_OPTION);
				if(action==0)
					{	
					try {
							
							PreparedStatement stmt=conn.prepareStatement("delete from emp where id=?");
							stmt.setInt(1, a);
							ResultSet rs=stmt.executeQuery();	
							refresh();
						} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					}
				}
			
			}
		});
		
		button.setBounds(164, 227, 81, 39);
		contentPane.add(button);
		
		JLabel lblId = new JLabel("Employee Id");
		lblId.setBounds(173, 46, 104, 14);
		contentPane.add(lblId);
		
		JLabel lblName = new JLabel("First Name");
		lblName.setBounds(173, 72, 104, 14);
		contentPane.add(lblName);
		
		JButton Logout = new JButton("LOGOUT");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login =new Login();
				login.setVisible(true);
				dispose();
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Logout.setBounds(10, 213, 142, 53);
		contentPane.add(Logout);
		
		JLabel empIcon = new JLabel("");
		empIcon.setIcon(new ImageIcon("M:\\java\\Emoloyee\\images\\employee.png"));
		empIcon.setBounds(10, 11, 142, 118);
		contentPane.add(empIcon);
		
		JLabel lable = new JLabel("Mob No.");
		lable.setBounds(173, 103, 46, 14);
		contentPane.add(lable);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane_1);
		btnUpdate.setBounds(338, 227, 81, 39);
		contentPane.add(btnUpdate);
		
		noFeild = new JTextField();
		noFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				lblIncorrectInput.setVisible(true);
				String s=noFeild.getText();
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
				if(s.length()==10)
				{	
					lblIncorrectInput.setVisible(false);
				}
			}
		});
	
		noFeild.setBounds(331, 97, 162, 20);
		contentPane.add(noFeild);
		noFeild.setColumns(10);
		
		JLabel label = new JLabel("+91");
		label.setFont(new Font("Rockwell", Font.PLAIN, 11));
		label.setBounds(307, 101, 27, 14);
		contentPane.add(label);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(637, 69, 81, 14);
		contentPane.add(lblLastName);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(758, 66, 162, 20);
		contentPane.add(textField);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(637, 100, 45, 14);
		contentPane.add(lblEmail);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(758, 97, 162, 20);
		contentPane.add(textField_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(758, 129, 89, 20);
		contentPane.add(dateChooser);
		
		JLabel lblNewLabel = new JLabel("Hire Date");
		lblNewLabel.setBounds(637, 133, 55, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setBounds(478, 167, 45, 14);
		contentPane.add(lblSalary);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(555, 161, 162, 20);
		contentPane.add(textField_2);
		
		JLabel lblCommissionPct = new JLabel("Commission PCT");
		lblCommissionPct.setBounds(488, 199, 45, 14);
		contentPane.add(lblCommissionPct);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(565, 193, 162, 20);
		contentPane.add(textField_3);
		
		JLabel lblManagerId = new JLabel("Manager ID");
		lblManagerId.setBounds(478, 220, 45, 14);
		contentPane.add(lblManagerId);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(555, 214, 162, 20);
		contentPane.add(textField_4);
		
		JLabel lblDepartmentId = new JLabel("Department ID");
		lblDepartmentId.setBounds(466, 252, 45, 14);
		contentPane.add(lblDepartmentId);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(543, 246, 162, 20);
		contentPane.add(textField_5);
		
		
		
		
		
		
		
	}
}
