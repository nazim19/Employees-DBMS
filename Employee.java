import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Properties;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.GridBagLayout;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLayeredPane;
import java.awt.SystemColor;
import javax.swing.border.CompoundBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Cursor;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JProgressBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DebugGraphics;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Component;
import java.awt.event.MouseMotionAdapter;
import java.awt.print.PrinterException;
import java.io.IOException;

import demo.BarChartDemo1;
import javax.swing.JInternalFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Panel;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
public class Employee extends JFrame {
	int mx,my;

	JFrame frame;
	private JTable table;
	private JTextField fNameFeild;
	private JTextField managerIdFeild;
	private JTextField emailIdFeild;
	private JTextField departmentIdFeild;
	private JTextField commissionPctFeild;
	private JTextField empIdFeild;
	private JTextField phoneNoFeild;
	private JTextField jobIdFeild;
	private JTextField lNameFeild;
	private JTextField salaryFeild;
	Connection conn=Login.conn;
	private JTextField empId;
	private JTextField phoneNo;
	private JTextField emailId;
	private JTextField departmentId;
	private JTextField managerId;
	private JTextField salary;
	private static JTextField id;
	private final JLabel lblNewLabel_6 = new JLabel("New label");
	private JTextField textField;
	private JTextField stopFeild;
	private JTextField routeFeild;
	private JTextField feeFeild;
	private JTable table_1;
	private JTable table_2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee window = new Employee();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static int getEmpId()
	{
		return(Integer.parseInt(id.getText()));
	}
	
	
	void refresh(JComboBox comboBox_order, JComboBox comboBox_year) throws SQLException
	{	
		PreparedStatement st = null;
		String order_by=comboBox_order.getSelectedItem().toString();
		int year=Integer.parseInt(Year.now().toString());
		try{
			if(comboBox_year.getSelectedIndex()==0)
			{
				st=conn.prepareStatement("select *from employees order by "+order_by);
			}
		else if(comboBox_year.getSelectedIndex()==1)
		{
			st=conn.prepareStatement("select *from employees where extract(year from hire_date)= ? order by "+order_by);
			st.setInt(1, year);
		}
		else if(comboBox_year.getSelectedIndex()==2)
		{
			year=year-1;
			st=conn.prepareStatement("select *from employees where extract(year from hire_date)= ? order by "+order_by);
		}
		else if(comboBox_year.getSelectedIndex()==3)
		{	
			year=year-5;
			st=conn.prepareStatement("select *from employees where extract(year from hire_date)>? order by "+order_by);
		}
		else if(comboBox_year.getSelectedIndex()==4)
		{
			year=2000;
			st=conn.prepareStatement("select *from employees where extract(year from hire_date)>? order by "+order_by);
			st.setInt(1, year);
		}
		ResultSet r=st.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(r));
		}catch(Exception e) {}
	}
	
	void refreshBus() throws SQLException
	{
		PreparedStatement stmt=conn.prepareStatement("select busfare.route_no , busfare.stop, employees.employee_id, employees.first_name from employees,busfare,busroute where employees.employee_id=busroute.id and busroute.stop=busfare.stop  order by busfare.route_no");
		ResultSet rs=stmt.executeQuery();
		table_1.setModel(DbUtils.resultSetToTableModel(rs));
	}
	
	void feeTable() throws SQLException
	{
		PreparedStatement stmt=conn.prepareStatement("select * from busfare order by stop");
		ResultSet rs=stmt.executeQuery();
		table_2.setModel(DbUtils.resultSetToTableModel(rs));
	}
	void sendMail(String sender,String mail)
	{
		Properties properties=new Properties();											// send mail on success
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator()
				{
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication("khannazim875875875@gmail.com","shakebanananazim");
					}
				});
		try
		{
			Message message=new MimeMessage(session);
			message.setFrom(new InternetAddress("khannazim875875875@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sender));
			message.setSubject("Registration successful");
			message.setText(mail);
			
			Transport.send(message);
		}catch(Exception e6){ }
	}
	
	void register(JDateChooser dateChooser, JProgressBar progressBar, JLabel lblIncorrectInput)
	{
		int flag=1;
		if(phoneNoFeild.getText().length()!=10)										//condition check for proper details
		{	lblIncorrectInput.setVisible(true);
		flag=0;
		}
		else if(fNameFeild.getText().length()==0||emailIdFeild.getText().length()==0||empIdFeild.getText().length()==0||managerIdFeild.getText().length()==0||departmentIdFeild.getText().length()==0||salaryFeild.getText().length()==0||dateChooser.getDate()==null)
			{
		JOptionPane.showMessageDialog(null, "Feild cannot be empty");
		flag=0;
		}
		else
		{ 
				try
				{
					PreparedStatement stmt=conn.prepareStatement("select * from employees");
					ResultSet rs=stmt.executeQuery();
					while(rs.next())
					{	
						if(empIdFeild.getText().equals(String.valueOf(rs.getInt(1))))
						{
							JOptionPane.showMessageDialog(null, "Employee Id already exists");
							flag=0;
							progressBar.setIndeterminate(false);
						}
						if(emailIdFeild.getText().equals(rs.getString(4)))
						{
							JOptionPane.showMessageDialog(null, "Email already exists");
							flag=0;
							progressBar.setIndeterminate(false);

						}
						if(phoneNoFeild.getText().equals(String.valueOf(rs.getLong(5))))
						{
						JOptionPane.showMessageDialog(null, "Phone number already exists");
						flag=0;
						progressBar.setIndeterminate(false);

						}
						
						
					}
				}catch(Exception e4){}
		}
				
			if(flag==1)									// insert data into database
			{	
				try {
				PreparedStatement stmt=conn.prepareStatement("insert into employees values(?,?,?,?,?,?,?,?,?,?)");
				stmt.setInt(1, Integer.parseInt(empIdFeild.getText()));
				stmt.setString(2, fNameFeild.getText());
				stmt.setString(3, lNameFeild.getText());
				stmt.setString(4, emailIdFeild.getText());
				stmt.setLong(5, Long.parseLong(phoneNoFeild.getText()));
				SimpleDateFormat dateformat=new SimpleDateFormat("dd-MM-yyyy");
				stmt.setString(6,dateformat.format(dateChooser.getDate()));
				stmt.setFloat(7, Float.parseFloat(salaryFeild.getText()));
				stmt.setFloat(8, Float.parseFloat(commissionPctFeild.getText()));
				stmt.setInt(9, Integer.parseInt(managerIdFeild.getText()));
				stmt.setInt(10, Integer.parseInt(departmentIdFeild.getText()));
				ResultSet rs=stmt.executeQuery();
				sendMail("khannazim875875@gmail.com","Hello "+fNameFeild.getText()+" "+lNameFeild.getText()+", u are successfully registered as an employee in our company.");
				progressBar.setIndeterminate(false);
				progressBar.setValue(100);
				JOptionPane.showMessageDialog(null, "Employee registered");

				} catch (Exception e1) {}

			}
	}
	/**
	 * Create the application.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Employee() throws SQLException, ClassNotFoundException {
		setUndecorated(true);
		Class.forName("oracle.jdbc.driver.OracleDriver");
		frame = new JFrame();
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Employee.class.getResource("/images/Hardest-Game-Ever-2-icon.ico")));
		frame.setBounds(100, 100, 1188, 667);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Database");
		frame.setUndecorated(true);
		
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
				frame.setLocation(x-mx, y-my);
			}
		});
		panel.setLocation(-396, -49);
		panel.setBackground(UIManager.getColor("CheckBox.foreground"));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("ToolBar.darkShadow"));
		panel_1.setBounds(10, 354, 1152, 302);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JComboBox comboBox_year = new JComboBox();
		
		JComboBox comboBox_order = new JComboBox();
		
		
		comboBox_year.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					refresh(comboBox_order,comboBox_year);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		comboBox_year.setBounds(262, 11, 120, 20);
		panel_1.add(comboBox_year);
		comboBox_year.setModel(new DefaultComboBoxModel(new String[] {"all", "this year", "last year", "last 5 years","after 2000"}));

		
		
		comboBox_order.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try {
					refresh(comboBox_order, comboBox_year);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		comboBox_order.setModel(new DefaultComboBoxModel(new String[] {"employee_id", "first_name", "salary", "hire_date"}));
		comboBox_order.setSelectedIndex(0);
		comboBox_order.setBounds(64, 11, 120, 20);
		panel_1.add(comboBox_order);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 33, 1132, 285);
		panel_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		refresh(comboBox_order,comboBox_year);
		
		JLabel label = new JLabel("DATABASE");
		label.setBounds(504, 7, 144, 22);
		panel_1.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		
		JLabel lblOrderBy = new JLabel("Order by :");
		lblOrderBy.setForeground(Color.WHITE);
		lblOrderBy.setBounds(10, 11, 62, 20);
		panel_1.add(lblOrderBy);
		
		JLabel lblYear = new JLabel("Year :");
		lblYear.setForeground(Color.WHITE);
		lblYear.setBounds(228, 8, 54, 23);
		panel_1.add(lblYear);
		
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBackground(new Color(102, 0, 204));
		tabbedPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		tabbedPane.setBounds(10, 77, 1152, 272);
		panel.add(tabbedPane);
		
		JPanel panel_7 = new JPanel();												//Home
		panel_7.setBackground(new Color(0, 153, 102));
		tabbedPane.addTab("Home", new ImageIcon(Employee.class.getResource("/images/home.png")), panel_7, null);
		tabbedPane.setForegroundAt(0, new Color(255, 255, 255));
		tabbedPane.setBackgroundAt(0, new Color(255, 105, 180));
		panel_7.setLayout(null);

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(SystemColor.controlHighlight);
		panel_8.setBounds(690, 11, 346, 245);
		panel_7.add(panel_8);
		panel_8.setLayout(null);
		
		JLabel lblNumberOfEmployees = new JLabel("Number of employees");
		lblNumberOfEmployees.setBounds(10, 42, 134, 14);
		panel_8.add(lblNumberOfEmployees);
		lblNumberOfEmployees.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel label_2 = new JLabel("Highest paid salary");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setBounds(10, 80, 134, 14);
		panel_8.add(label_2);
		
		JLabel label_3 = new JLabel("Employee of the week");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setBounds(10, 114, 141, 14);
		panel_8.add(label_3);
		
		JLabel label_4 = new JLabel("Total ");
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setBounds(10, 160, 134, 14);
		panel_8.add(label_4);
		
		JInternalFrame internalFrame = new JInternalFrame("Year vs Employee count");
		internalFrame.getContentPane().setBackground(SystemColor.textInactiveText);
		internalFrame.setBorder(new EmptyBorder(0, 0, 0, 0));
		internalFrame.setBounds(10, 11, 528, 245);
		panel_7.add(internalFrame);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(SystemColor.windowBorder);
		internalFrame.getContentPane().add(panel_9, BorderLayout.CENTER);
		internalFrame.setVisible(true);
		
		DefaultCategoryDataset dataset=new DefaultCategoryDataset();
		PreparedStatement stmt=conn.prepareStatement("select distinct extract (year from hire_date), count(extract (year from hire_date)) from employees group by extract(year from hire_date)");
		ResultSet rs=stmt.executeQuery();
		while(rs.next())
		{
			dataset.setValue(rs.getInt(2), "", rs.getString(1));
		}
		
		JFreeChart	chart=ChartFactory.createLineChart(null, null,null, dataset,PlotOrientation.VERTICAL,false,false,false);
		CategoryPlot catplot=chart.getCategoryPlot();
		catplot.setRangeGridlinePaint(Color.BLACK);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		ChartPanel chartPanel=new ChartPanel(chart);
		chartPanel.setBackground(SystemColor.textInactiveText);
		chartPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_9.add(chartPanel);
		chartPanel.setLayout(new BorderLayout(0, 0));
		
		panel_9.validate();
		
		JPanel panel_2 = new JPanel();													//Registration
		panel_2.setBackground(new Color(255, 99, 71));
		tabbedPane.addTab("Register", new ImageIcon(Employee.class.getResource("/images/bajsb.png")), panel_2, null);
		tabbedPane.setForegroundAt(1, new Color(255, 255, 255));
		tabbedPane.setBackgroundAt(1, new Color(138, 43, 226));
		panel_2.setLayout(null);
		
		JLabel label_6 = new JLabel("Phone no.");
		label_6.setBounds(290, 89, 92, 14);
		panel_2.add(label_6);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0, 255, 0));
		progressBar.setBounds(857, 243, 161, 20);
		panel_2.add(progressBar);
		
		
		JLabel lblIncorrectInput = new JLabel("Incorrect input");
		lblIncorrectInput.setBounds(426, 107, 139, 14);
		lblIncorrectInput.setForeground(Color.RED);
		panel_2.add(lblIncorrectInput);
		lblIncorrectInput.setVisible(false);
		
		JLabel lblNewLabel = new JLabel("Employee ID");
		lblNewLabel.setBounds(290, 126, 92, 14);
		panel_2.add(lblNewLabel);
		
		fNameFeild = new JTextField();
		fNameFeild.setBounds(425, 48, 152, 20);
		fNameFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isAlphabetic(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		panel_2.add(fNameFeild);
		fNameFeild.setColumns(10);
		
		managerIdFeild = new JTextField();
		managerIdFeild.setBounds(425, 151, 152, 20);
		managerIdFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		managerIdFeild.setColumns(10);
		panel_2.add(managerIdFeild);
		
		JLabel lblDepartmentId_1 = new JLabel("Department ID");
		lblDepartmentId_1.setBounds(290, 185, 92, 14);
		panel_2.add(lblDepartmentId_1);
		
		emailIdFeild = new JTextField();
		emailIdFeild.setBounds(835, 89, 152, 20);
		emailIdFeild.setColumns(10);
		panel_2.add(emailIdFeild);
		
		JLabel lblEmailId = new JLabel("Email ID");
		lblEmailId.setBounds(717, 89, 84, 14);
		panel_2.add(lblEmailId);
		
		departmentIdFeild = new JTextField();
		departmentIdFeild.setBounds(425, 182, 152, 20);
		departmentIdFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		departmentIdFeild.setColumns(10);
		panel_2.add(departmentIdFeild);
		
		JLabel lblManagerId = new JLabel("Salary");
		lblManagerId.setBounds(717, 123, 46, 14);
		panel_2.add(lblManagerId);
		
		commissionPctFeild = new JTextField();
		commissionPctFeild.setBounds(835, 182, 152, 20);
		commissionPctFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{	
					if(!((e.getKeyChar())=='.'))		
					{e.consume();
					}
				}
					
			}
		});
		commissionPctFeild.setColumns(10);
		panel_2.add(commissionPctFeild);
		
		JLabel lblCommissionPct = new JLabel("Commission PCT");
		lblCommissionPct.setBounds(717, 185, 108, 14);
		panel_2.add(lblCommissionPct);
		
		empIdFeild = new JTextField();
		empIdFeild.setBounds(425, 120, 152, 20);
		empIdFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit((e.getKeyChar())))
				{
					e.consume();
				}
			}
		});
		empIdFeild.setColumns(10);
		panel_2.add(empIdFeild);
		
		
		phoneNoFeild = new JTextField();
		phoneNoFeild.setBounds(425, 89, 152, 20);
		phoneNoFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit((e.getKeyChar())))
				{
					e.consume();
				}
				if(phoneNoFeild.getText().length()==10)
				{
					lblIncorrectInput.setVisible(false);
				}
			}
		});
		phoneNoFeild.setColumns(10);
		panel_2.add(phoneNoFeild);
		
		
		JLabel lblManagerId_1 = new JLabel("Manager ID");
		lblManagerId_1.setBounds(290, 154, 92, 14);
		panel_2.add(lblManagerId_1);
		
		lNameFeild = new JTextField();
		lNameFeild.setBounds(835, 48, 152, 20);
		lNameFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isAlphabetic((e.getKeyChar())))
				{
					e.consume();
				}
			}
		});
		lNameFeild.setColumns(10);
		panel_2.add(lNameFeild);
		
		JLabel lblJobId = new JLabel("Last Name");
		lblJobId.setBounds(717, 48, 71, 14);
		panel_2.add(lblJobId);
		
		JLabel lblHiringDate = new JLabel("Hiring date");
		lblHiringDate.setBounds(717, 154, 84, 14);
		panel_2.add(lblHiringDate);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(835, 145, 152, 20);
		panel_2.add(dateChooser);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(290, 51, 92, 14);
		panel_2.add(lblFirstName);
		
		salaryFeild = new JTextField();
		salaryFeild.setBounds(835, 123, 152, 20);
		salaryFeild.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		panel_2.add(salaryFeild);
		salaryFeild.setColumns(10);
		
		JButton btnAddEmployee = new JButton("Register Employee");
		btnAddEmployee.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAddEmployee.setIcon(new ImageIcon("M:\\java\\New1\\bin\\images\\user_man_add-512.png"));
		btnAddEmployee.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAddEmployee.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				progressBar.setIndeterminate(true);
			}
		});
		btnAddEmployee.setBounds(403, 227, 191, 35);
		btnAddEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register(dateChooser,progressBar,lblIncorrectInput);
				progressBar.setIndeterminate(false);
				progressBar.setValue(100);
				try {
					refresh(comboBox_order,comboBox_year);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_2.add(btnAddEmployee);
		
		JLabel pic = new JLabel("");
		pic.setBounds(42, 49, 158, 164);
		pic.setIcon(new ImageIcon("M:\\java\\Emoloyee\\images\\employee.png"));
		panel_2.add(pic);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(260, 35, 750, 181);
		panel_6.setBorder(new CompoundBorder(new CompoundBorder(), new CompoundBorder()));
		panel_6.setBackground(SystemColor.controlHighlight);
		panel_2.add(panel_6);
		
		JLabel lblEnterDetailsTo = new JLabel("Enter details to register employee:");
		lblEnterDetailsTo.setBounds(250, -9, 327, 52);
		lblEnterDetailsTo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEnterDetailsTo.setForeground(new Color(204, 255, 255));
		lblEnterDetailsTo.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblEnterDetailsTo);
		
		JLabel Progress = new JLabel("Progress :");
		Progress.setFont(new Font("Tahoma", Font.BOLD, 12));
		Progress.setForeground(UIManager.getColor("ToggleButton.light"));
		Progress.setBounds(857, 229, 85, 14);
		panel_2.add(Progress);
		
		JPanel panel_3 = new JPanel();												//Update emp
		panel_3.setForeground(new Color(0, 0, 0));
		panel_3.setBackground(new Color(70, 130, 180));
		tabbedPane.addTab("Alter", new ImageIcon(Employee.class.getResource("/images/update.png")), panel_3, null);
		tabbedPane.setForegroundAt(2, new Color(255, 255, 255));
		tabbedPane.setBackgroundAt(2, new Color(50, 205, 50));
		panel_3.setLayout(null);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setForeground(Color.GREEN);
		progressBar_1.setBounds(851, 243, 161, 20);
		panel_3.add(progressBar_1);
		
		
		JLabel lblIncorrectInput_1 = new JLabel("Incorrect Input");
		lblIncorrectInput_1.setForeground(Color.RED);
		lblIncorrectInput_1.setBounds(250, 107, 152, 14);
		panel_3.add(lblIncorrectInput_1);
		lblIncorrectInput_1.setVisible(false);
		
		JLabel nameLable = new JLabel("New label");
		nameLable.setHorizontalAlignment(SwingConstants.CENTER);
		nameLable.setFont(new Font("Tahoma", Font.BOLD, 20));
		nameLable.setForeground(new Color(50, 205, 50));
		nameLable.setBounds(269, 22, 463, 59);
		panel_3.add(nameLable);
		nameLable.setVisible(false);
		
		
		JLabel lblEmployeeId = new JLabel("Employee ID:");
		lblEmployeeId.setForeground(UIManager.getColor("ToggleButton.light"));
		lblEmployeeId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmployeeId.setBounds(10, 22, 96, 20);
		panel_3.add(lblEmployeeId);
		
		empId = new JTextField();
		empId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		empId.setBounds(117, 24, 142, 20);
		panel_3.add(empId);
		empId.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Phone no.");
		lblNewLabel_1.setForeground(new Color(255, 255, 204));
		lblNewLabel_1.setBounds(117, 96, 93, 14);
		panel_3.add(lblNewLabel_1);
		
		phoneNo = new JTextField();
		phoneNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		phoneNo.setBounds(250, 90, 152, 20);
		panel_3.add(phoneNo);
		phoneNo.setColumns(10);
		phoneNo.setEditable(false);
		
		emailId = new JTextField();
		emailId.setColumns(10);
		emailId.setBounds(250, 132, 152, 20);
		panel_3.add(emailId);
		emailId.setEditable(false);
		
		JLabel lblEmail = new JLabel("Email Id");
		lblEmail.setForeground(new Color(255, 255, 204));
		lblEmail.setBounds(117, 135, 93, 14);
		panel_3.add(lblEmail);
		
		departmentId = new JTextField();
		departmentId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		departmentId.setColumns(10);
		departmentId.setBounds(250, 169, 152, 20);
		panel_3.add(departmentId);
		departmentId.setEditable(false);
		
		JLabel lblDepartmentId_2 = new JLabel("Department Id");
		lblDepartmentId_2.setForeground(new Color(255, 255, 204));
		lblDepartmentId_2.setBounds(117, 175, 93, 14);
		panel_3.add(lblDepartmentId_2);
		
		managerId = new JTextField();
		managerId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		managerId.setColumns(10);
		managerId.setBounds(646, 96, 152, 20);
		panel_3.add(managerId);
		managerId.setEditable(false);
		
		JLabel lblManagerId_2 = new JLabel("Manager Id");
		lblManagerId_2.setForeground(new Color(255, 255, 204));
		lblManagerId_2.setBounds(543, 93, 93, 14);
		panel_3.add(lblManagerId_2);
		
		salary = new JTextField();
		salary.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		salary.setColumns(10);
		salary.setBounds(646, 138, 152, 20);
		panel_3.add(salary);
		salary.setEditable(false);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setForeground(new Color(255, 255, 204));
		lblSalary.setBounds(543, 135, 93, 14);
		panel_3.add(lblSalary);
		
		JButton btnNewButton = new JButton("Update record");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setIcon(new ImageIcon("M:\\java\\New1\\src\\images\\user-partner.png"));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				progressBar_1.setIndeterminate(true);
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int flag=1;
				if(((phoneNo.getText().length()==10))||!(emailId.getText().length()==0)||!(managerId.getText().length()==0)||!(departmentId.getText().length()==0))
				{		if(phoneNo.getText().length()!=10)
						{
							lblIncorrectInput.setVisible(true);
							progressBar_1.setIndeterminate(false);
							flag=0;
						}
						else
						{
						try
						{
							PreparedStatement stmt=conn.prepareStatement("select *from employees");
							ResultSet rs=stmt.executeQuery();
							while(rs.next())
							{
								if(emailId.getText().equals(rs.getString(4)))
								{	if(rs.getInt(1)==Integer.parseInt(empId.getText()))
									{
									flag=1;
									}
									JOptionPane.showMessageDialog(null, "Email already exists");
									progressBar_1.setIndeterminate(false);

									flag=0;
									progressBar_1.setIndeterminate(false);
								}
								else if((Long.parseLong(phoneNo.getText()))==rs.getLong(5))
								{	if(rs.getInt(1)==Integer.parseInt(empId.getText()))
									{
									flag=1;
									}								
								JOptionPane.showMessageDialog(null, "Phone number already exists");
								flag=0;
								progressBar_1.setIndeterminate(false);
								}
							}
						}catch(Exception e4){}
						}
						if(flag==1)
						{	
							try {
					PreparedStatement stmt=conn.prepareStatement("update employees set email= ?, phone_number=?, salary=?,manager_id=?,department_id=? where employee_id=?");
					stmt.setString(1, emailId.getText());
					stmt.setLong(2, Long.parseLong(phoneNo.getText()));
					stmt.setInt(3, Integer.parseInt(salary.getText()));
					stmt.setInt(4, Integer.parseInt(managerId.getText()));
					stmt.setInt(5, Integer.parseInt(departmentId.getText()));
					stmt.setInt(6, Integer.parseInt(empId.getText()));
					ResultSet rs=stmt.executeQuery();
					PreparedStatement s=conn.prepareStatement("select * from employees where employee_id= ?");
					s.setInt(1,Integer.parseInt(empId.getText()));
					ResultSet r=s.executeQuery();
					String mail = null;
					while(r.next())
					{
					mail="Hello "+r.getString(2)+" "+r.getString(3)+", Your record has been updated.";
					}
					sendMail("khannazim875875@gmail.com",mail);

					refresh(comboBox_order,comboBox_year);
					progressBar_1.setIndeterminate(false);
					JOptionPane.showMessageDialog(null, "Record updated");
					} catch (SQLException e1) {e1.printStackTrace();}
				}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Feild cannot be empty");
				}
			}
			
				
		});
		btnNewButton.setBounds(278, 223, 172, 40);
		panel_3.add(btnNewButton);
		
		JButton btnGo = new JButton("Go");
		btnGo.setForeground(Color.DARK_GRAY);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PreparedStatement s=conn.prepareStatement("select *from employees where employee_id= ?");
					s.setInt(1, Integer.parseInt(empId.getText()));
					ResultSet r=s.executeQuery();
					if(rs.next())
					{
					while(r.next())
					{	
						phoneNo.setEditable(true);
						emailId.setEditable(true);
						departmentId.setEditable(true);
						managerId.setEditable(true);
						salary.setEditable(true);
						nameLable.setText("Welcome Mr./Ms. "+r.getString(2)+" "+r.getString(3));
						nameLable.setVisible(true);
						phoneNo.setText(r.getString(5));
						emailId.setText(r.getString(4));
						salary.setText(String.valueOf(r.getInt(7)));
						managerId.setText(String.valueOf(r.getInt(9)));
						departmentId.setText(String.valueOf(r.getInt(10)));
					}	
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Employee not found!");
					}
				} catch (SQLException e1) { 	}
				
			
			}
		});
		
		
		JLabel label_1 = new JLabel("+91");	
		label_1.setForeground(new Color(255, 255, 204));
		label_1.setBounds(220, 86, 31, 28);
		panel_3.add(label_1);
		btnGo.setBounds(116, 55, 58, 23);
		panel_3.add(btnGo);			
		
		JLabel lblPhoto_1 = new JLabel("photo");
		lblPhoto_1.setForeground(new Color(255, 255, 204));
		lblPhoto_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhoto_1.setBounds(860, 22, 152, 153);
		panel_3.add(lblPhoto_1);
		
		JButton btnUploadPhoto = new JButton("Upload photo");
		btnUploadPhoto.setBounds(881, 181, 115, 28);
		panel_3.add(btnUploadPhoto);
		
		JLabel label_5 = new JLabel("Progress :");
		label_5.setForeground(new Color(255, 255, 204));
		label_5.setBounds(851, 229, 85, 14);
		panel_3.add(label_5);
		
		
		
		JPanel panel_4 = new JPanel();										//panel 4 search	
		panel_4.setBackground(new Color(255, 102, 51));
		panel_4.setForeground(Color.BLACK);
		tabbedPane.addTab("Search", new ImageIcon(Employee.class.getResource("/images/11-2-home.png")), panel_4, null);
		tabbedPane.setForegroundAt(3, new Color(255, 255, 255));
		tabbedPane.setBackgroundAt(3, new Color(47, 79, 79));
		panel_4.setLayout(null);
		
		
		JLabel lblEmployeeId_1 = new JLabel("Employee Id :");
		lblEmployeeId_1.setForeground(UIManager.getColor("ToggleButton.light"));
		lblEmployeeId_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmployeeId_1.setBounds(10, 73, 102, 20);
		panel_4.add(lblEmployeeId_1);
		
		id = new JTextField();
		id.setBounds(134, 75, 86, 20);
		panel_4.add(id);
		id.setColumns(10);
		
		JLabel lblPhoto = new JLabel("Photo");
		lblPhoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhoto.setBounds(876, 35, 137, 139);
		panel_4.add(lblPhoto);
		
		JLabel name = new JLabel("Name");
		name.setHorizontalAlignment(SwingConstants.CENTER);
		name.setBounds(876, 185, 137, 14);
		panel_4.add(name);
		name.setVisible(false);
		
		JLabel lblEmployeeId_2 = new JLabel("Employee Id :");
		lblEmployeeId_2.setForeground(Color.DARK_GRAY);
		lblEmployeeId_2.setBounds(285, 63, 86, 14);
		panel_4.add(lblEmployeeId_2);
		
		JLabel lblManagerId_3 = new JLabel("Manager Id :");
		lblManagerId_3.setForeground(Color.DARK_GRAY);
		lblManagerId_3.setBounds(285, 92, 86, 14);
		panel_4.add(lblManagerId_3);
		
		JLabel lblDepartmentId_3 = new JLabel("Department Id :");
		lblDepartmentId_3.setForeground(Color.DARK_GRAY);
		lblDepartmentId_3.setBounds(285, 117, 86, 14);
		panel_4.add(lblDepartmentId_3);
		
		JLabel lblPhoneNo = new JLabel("Phone No. :");
		lblPhoneNo.setForeground(Color.DARK_GRAY);
		lblPhoneNo.setBounds(570, 63, 108, 14);
		panel_4.add(lblPhoneNo);
		
		JLabel lblEmail_1 = new JLabel("Email :");
		lblEmail_1.setForeground(Color.DARK_GRAY);
		lblEmail_1.setBounds(570, 92, 108, 14);
		panel_4.add(lblEmail_1);
		
		JLabel lblSalary_1 = new JLabel("Salary :");
		lblSalary_1.setForeground(Color.DARK_GRAY);
		lblSalary_1.setBounds(285, 177, 86, 14);
		panel_4.add(lblSalary_1);
		
		JLabel lblHireDate = new JLabel("Hire date :");
		lblHireDate.setForeground(Color.DARK_GRAY);
		lblHireDate.setBounds(285, 202, 86, 14);
		panel_4.add(lblHireDate);
		
		JLabel lblNewLabel_2 = new JLabel("Commission PCT :");
		lblNewLabel_2.setForeground(Color.DARK_GRAY);
		lblNewLabel_2.setBounds(570, 177, 108, 14);
		panel_4.add(lblNewLabel_2);
		
		JLabel eId = new JLabel("");
		eId.setFont(new Font("Tahoma", Font.BOLD, 11));
		eId.setBounds(381, 63, 179, 14);
		panel_4.add(eId);
		eId.setVisible(false);
		
		JLabel mId = new JLabel("New label");
		mId.setFont(new Font("Tahoma", Font.BOLD, 11));
		mId.setBounds(381, 92, 179, 14);
		panel_4.add(mId);
		mId.setVisible(false);
		
		JLabel hDate = new JLabel("New label");
		hDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		hDate.setBounds(381, 202, 179, 14);
		panel_4.add(hDate);
		hDate.setVisible(false);
		
		JLabel sal = new JLabel("New label");
		sal.setFont(new Font("Tahoma", Font.BOLD, 11));
		sal.setBounds(381, 173, 179, 14);
		panel_4.add(sal);
		sal.setVisible(false);
		
		JLabel email = new JLabel("New label");
		email.setFont(new Font("Tahoma", Font.BOLD, 11));
		email.setBounds(688, 92, 122, 14);
		panel_4.add(email);
		email.setVisible(false);
		
		JLabel pNo = new JLabel("New label");
		pNo.setFont(new Font("Tahoma", Font.BOLD, 11));
		pNo.setBounds(688, 63, 122, 14);
		panel_4.add(pNo);
		pNo.setVisible(false);
		
		JLabel dId = new JLabel("New label");
		dId.setFont(new Font("Tahoma", Font.BOLD, 11));
		dId.setBounds(381, 117, 179, 14);
		panel_4.add(dId);
		dId.setVisible(false);
		
		JLabel cPct = new JLabel("New label");
		cPct.setFont(new Font("Tahoma", Font.BOLD, 11));
		cPct.setBounds(688, 177, 122, 14);
		panel_4.add(cPct);
		cPct.setVisible(false);
		
		
		
		JButton delete = new JButton("Remove ");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreparedStatement stmt;
				try {
					progressBar_1.setIndeterminate(true);
					stmt = conn.prepareStatement("delete employees where employee_id =?");
					stmt.setInt(1, Integer.parseInt(eId.getText()));
					ResultSet rs=stmt.executeQuery();
					int a=JOptionPane.showConfirmDialog(delete, "Are you sure");
					if(a==0) {JOptionPane.showMessageDialog(null,"Record has been removed");}
					refresh(comboBox_order,comboBox_year);
					progressBar_1.setIndeterminate(false);
					progressBar_1.setValue(100);
				} catch (SQLException e1) {JOptionPane.showMessageDialog(null,"Invalid action");}
	
			}
		});
		delete.setIcon(new ImageIcon(Employee.class.getResource("/images/remove_delete_file.png")));
		delete.setFont(new Font("Tahoma", Font.BOLD, 9));
		delete.setBounds(124, 117, 96, 34);
		panel_4.add(delete);
		
		
		
		
		JButton btnGo_1 = new JButton("Search");
		btnGo_1.setFont(new Font("Tahoma", Font.BOLD, 9));
		btnGo_1.setIcon(new ImageIcon(Employee.class.getResource("/images/search.png")));
		btnGo_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnGo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{	int flag=1;
					PreparedStatement stmt=conn.prepareStatement("select *from employees where employee_id=?");
					stmt.setInt(1, Integer.parseInt(id.getText()));
					ResultSet rs=stmt.executeQuery();				
					while(rs.next())
					{
						name.setVisible(true);
						eId.setVisible(true);
						mId.setVisible(true);
						dId.setVisible(true);
						pNo.setVisible(true);
						hDate.setVisible(true);
						sal.setVisible(true);
						email.setVisible(true);
						cPct.setVisible(true);
					eId.setText(String.valueOf(rs.getInt(1)));
					email.setText(rs.getString(4));
					pNo.setText(rs.getString(5));
					SimpleDateFormat dateformat=new SimpleDateFormat("dd-MM-yyyy");
					hDate.setText(dateformat.format(rs.getDate(6)));
					sal.setText(String.valueOf(rs.getInt(7)));
					cPct.setText(String.valueOf(rs.getFloat(8)));
					mId.setText(String.valueOf(rs.getInt(9)));
					dId.setText(String.valueOf(rs.getInt(10)));
					name.setText(rs.getString(2)+" "+rs.getString(3));
					flag=0;
					}
					if(!rs.next()&&flag==1)
					{
						JOptionPane.showMessageDialog(null, "Employee not found");
					}
					}catch(SQLException e4){}
			}
		});
		btnGo_1.setBounds(10, 117, 102, 34);
		panel_4.add(btnGo_1);
		
		JPanel panel_5 = new JPanel();
		panel_5.setForeground(Color.DARK_GRAY);
		panel_5.setBackground(SystemColor.controlHighlight);
		panel_5.setBounds(230, 23, 807, 227);
		panel_4.add(panel_5);
		
		JButton btnGetIcard = new JButton("Employee Icard");
		btnGetIcard.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGetIcard.setIcon(new ImageIcon(Employee.class.getResource("/images/066-512.png")));
		btnGetIcard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(id.getText().length()==0)
				{
					JOptionPane.showMessageDialog(null,"Enter id first");
				}
				else
				{
					
				
					int a=Integer.parseInt(id.getText());
					IcardEmp i=new IcardEmp();
					i.frame.setVisible(true);
				}
			}
		});
		btnGetIcard.setBounds(42, 181, 145, 23);
		panel_4.add(btnGetIcard);
		
		JButton btnBusIcard = new JButton("Bus Icard");
		btnBusIcard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(id.getText().length()==0)
				{
					JOptionPane.showMessageDialog(null,"Enter id first");
				}
				else
				{
					
				
					int a=Integer.parseInt(id.getText());
					IcardBus i;
					try {
						i = new IcardBus();
						i.setVisible(true);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnBusIcard.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBusIcard.setBounds(42, 215, 145, 23);
		panel_4.add(btnBusIcard);
		
		JPanel panel_10 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_10, null);
		panel_10.setLayout(null);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(224, 11, 599, 245);
		panel_10.add(tabbedPane_1);
		
		JPanel panel_11 = new JPanel();
		tabbedPane_1.addTab("New tab", null, panel_11, null);
		panel_11.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Enter employee id :");
		lblNewLabel_3.setBounds(125, 31, 110, 14);
		panel_11.add(lblNewLabel_3);
		
		JLabel lblNewLabel_fee = new JLabel("New label");
		lblNewLabel_fee.setBounds(348, 96, 133, 14);
		panel_11.add(lblNewLabel_fee);
		

		JLabel label_7 = new JLabel("");
		label_7.setBounds(348, 118, 116, 14);
		panel_11.add(label_7);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!Character.isDigit(e.getKeyChar()))
				{
					e.consume();
				}
			}
		});
		textField.setBounds(348, 31, 133, 20);
		panel_11.add(textField);
		textField.setColumns(10);
		
		JLabel lblSelectStop = new JLabel("Select stop :");
		lblSelectStop.setBounds(125, 62, 110, 20);
		panel_11.add(lblSelectStop);
		
		JComboBox comboBox_Stop = new JComboBox();
		comboBox_Stop.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				try
				{
				PreparedStatement smt=conn.prepareStatement("select fee,route_no from busfare where stop=?");
				smt.setString(1, comboBox_Stop.getSelectedItem().toString());
				ResultSet r=smt.executeQuery();
				while(r.next())
				{
				lblNewLabel_fee.setText(String.valueOf(r.getInt(1)));
				label_7.setText(String.valueOf(r.getInt(2)));		
				}
				}catch(Exception SQLException) {}
			}	
		});
		comboBox_Stop.setBounds(348, 62, 133, 20);
		panel_11.add(comboBox_Stop);
		comboBox_Stop.setSelectedIndex(-1);

		
		PreparedStatement smt=conn.prepareStatement("select stop from busfare order by stop");
		ResultSet r=smt.executeQuery();
		while(r.next())
		{
			comboBox_Stop.addItem(r.getString(1));

		}
		
		JLabel lblFeeyear = new JLabel("Fee/year :");
		lblFeeyear.setBounds(125, 93, 103, 14);
		panel_11.add(lblFeeyear);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					PreparedStatement stmt=conn.prepareStatement("insert into busroute values(?,?)");
					stmt.setInt(1, Integer.parseInt(textField.getText()));
					stmt.setString(2, comboBox_Stop.getSelectedItem().toString());
					ResultSet rs=stmt.executeQuery();
					refreshBus();
				}catch(SQLException e1) {}
			}
		});
		btnRegister.setBounds(252, 154, 89, 23);
		panel_11.add(btnRegister);
		
		JLabel lblRouteNo = new JLabel("Route No. :");
		lblRouteNo.setBounds(125, 118, 116, 14);
		panel_11.add(lblRouteNo);
		
		JPanel panel_12 = new JPanel();
		tabbedPane_1.addTab("New tab", null, panel_12, null);
		panel_12.setLayout(null);
		
		JLabel lblEnterNewStop = new JLabel("Enter new Stop :");
		lblEnterNewStop.setBounds(151, 54, 118, 14);
		panel_12.add(lblEnterNewStop);
		
		stopFeild = new JTextField();
		stopFeild.setBounds(336, 51, 118, 17);
		panel_12.add(stopFeild);
		stopFeild.setColumns(10);
		
		JLabel lblEnterRouteNo = new JLabel("Enter Route No. :");
		lblEnterRouteNo.setBounds(151, 79, 118, 17);
		panel_12.add(lblEnterRouteNo);
		
		routeFeild = new JTextField();
		routeFeild.setColumns(10);
		routeFeild.setBounds(336, 77, 118, 17);
		panel_12.add(routeFeild);
		
		JLabel lblEnterFeeyear = new JLabel("Enter Fee/year :");
		lblEnterFeeyear.setBounds(151, 109, 118, 17);
		panel_12.add(lblEnterFeeyear);
		
		feeFeild = new JTextField();
		feeFeild.setColumns(10);
		feeFeild.setBounds(336, 107, 118, 17);
		panel_12.add(feeFeild);
		
		JButton btnNewButton_1 = new JButton("Add stop");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action=1;
				try {
					PreparedStatement stmt=conn.prepareStatement("select * from busfare where route_no=?");
					stmt.setInt(1, Integer.parseInt(routeFeild.getText()));
					ResultSet rs=stmt.executeQuery();
					if(rs.next())
					{
						action=0;
					}
					else
					{
						action=JOptionPane.showConfirmDialog(null,"Route No. does not exist. Create new Route no. ?");
					}
				} catch (Exception e1) {				}
				if(action==0)
				{
					try {
						PreparedStatement stmt=conn.prepareStatement("insert into busfare values(?,?,?)");
						stmt.setString(1,stopFeild.getText());
						stmt.setInt(2,Integer.parseInt(feeFeild.getText()));
						stmt.setInt(3, Integer.parseInt(routeFeild.getText()));
						ResultSet rs=stmt.executeQuery();
						refreshBus();
					} catch (SQLException e1) {	}
					
					JOptionPane.showMessageDialog(null,"new stop added");
				}
			}
		});
		btnNewButton_1.setBounds(242, 157, 89, 23);
		panel_12.add(btnNewButton_1);
		
		JPanel panel_13 = new JPanel();
		tabbedPane_1.addTab("New tab", null, panel_13, null);
		panel_13.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 574, 195);
		panel_13.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		refreshBus();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		menuBar.setBounds(0, 0, 90, 21);
		panel.add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setBackground(Color.DARK_GRAY);
		menuBar.add(mnFile);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		mntmLogout.setIcon(new ImageIcon(Employee.class.getResource("/images/lock-open-blue.png")));
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Login login=new Login();
				login.setVisible(true);
			}
		});
		mnFile.add(mntmLogout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK));
		mntmExit.setIcon(new ImageIcon(Employee.class.getResource("/images/Crystal_Project_Exit.png")));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		mnFile.add(mntmExit);
		
		Panel panel_14 = new Panel();
		panel_14.setVisible(false);
		panel_14.setBounds(295, 10, 867, 64);
		panel.add(panel_14);
		panel_14.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 247, 42);
		panel_14.add(scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
		feeTable();
		
		JMenu mnDownload = new JMenu("Download");
		mnDownload.setBackground(Color.DARK_GRAY);
		menuBar.add(mnDownload);
		
		JMenuItem mnEmployeeDatabase = new JMenuItem("Employee database");
		mnEmployeeDatabase.setIcon(new ImageIcon(Employee.class.getResource("/images/download.jpg")));
		mnEmployeeDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					table.print(JTable.PrintMode.FIT_WIDTH);
				} catch (PrinterException e1) {}
			}
		});
		mnDownload.add(mnEmployeeDatabase);
		
		JMenu mnBus = new JMenu("Bus");
		mnBus.setIcon(new ImageIcon(Employee.class.getResource("/images/bus.png")));
		mnDownload.add(mnBus);
		
		JMenuItem mnFeeStructure = new JMenuItem("Fee structure");
		mnFeeStructure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					feeTable();
					table_2.print(JTable.PrintMode.FIT_WIDTH);
				} catch (Exception e1) {}
				
			}
		});
		mnBus.add(mnFeeStructure);
		
		JMenuItem mntmBusDatabse = new JMenuItem("Bus databse");
		mntmBusDatabse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					table_1.print(JTable.PrintMode.FIT_WIDTH);
				} catch (PrinterException e1) {}
			}
		});
		mnBus.add(mntmBusDatabse);
		
		
		lblNewLabel_6.setIcon(new ImageIcon(Employee.class.getResource("/images/brands-logos-3d-amd-logo-hd-background-theme-1080x1920px-amd-technology-picture-amd-hd-wallpaper.png")));
		lblNewLabel_6.setBounds(0, 0, 1188, 667);
		panel.add(lblNewLabel_6);
		
		
	}
}
