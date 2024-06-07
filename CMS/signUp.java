package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class signUp extends winForm{

    JFrame frmSignUp;
    private JTextField txtFirstname;
    private JTextField txtEmail;
    private JPasswordField txtPwd;
    private JTextField txtLastname;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    signUp window = new signUp();
                    window.frmSignUp.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    
    public signUp() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmSignUp = new JFrame();
        frmSignUp.setTitle("Sign Up");
        frmSignUp.getContentPane().setBackground(new Color(255, 255, 204));
        frmSignUp.getContentPane().setLayout(null);
        
        txtFirstname = new JTextField();
        txtFirstname.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtFirstname.setBounds(21, 50, 289, 48);
        frmSignUp.getContentPane().add(txtFirstname);
        txtFirstname.setColumns(10);
        
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtEmail.setBounds(21, 258, 289, 48);
        frmSignUp.getContentPane().add(txtEmail);
        txtEmail.setColumns(10);
        
        txtPwd = new JPasswordField();
        txtPwd.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtPwd.setBounds(21, 377, 289, 48);
        frmSignUp.getContentPane().add(txtPwd);
        
        JLabel lblFName = new JLabel("First Name");
        lblFName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 18));
        lblFName.setBounds(21, 10, 158, 30);
        frmSignUp.getContentPane().add(lblFName);
        
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
        lblEmail.setBounds(21, 218, 85, 30);
        frmSignUp.getContentPane().add(lblEmail);
        
        JLabel lblCreatePassword = new JLabel("Create Password");
        lblCreatePassword.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 18));
        lblCreatePassword.setBounds(21, 337, 158, 30);
        frmSignUp.getContentPane().add(lblCreatePassword);
JButton btnSignUp = new JButton("Sign Up");
        btnSignUp.setForeground(new Color(0, 0, 0));
        btnSignUp.setFont(new Font("Dubai", Font.PLAIN, 20));
        btnSignUp.setBackground(new Color(0, 255, 255));
        btnSignUp.setBounds(21, 551, 125, 48);
        frmSignUp.getContentPane().add(btnSignUp);
        
        JLabel lblAlreadyHaveAn = new JLabel("Already have an account? ");
        lblAlreadyHaveAn.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
        lblAlreadyHaveAn.setBounds(21, 609, 170, 21);
        frmSignUp.getContentPane().add(lblAlreadyHaveAn);
        
        JButton btnNewButton_1 = new JButton("Login");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frmSignUp.dispose();
				
				login ln= new login();
				ln.display(ln.frame);
        	}
        });
        btnNewButton_1.setForeground(new Color(0, 0, 255));
        btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 12));
        btnNewButton_1.setBorder(null);
        btnNewButton_1.setBackground(new Color(255, 255, 204));
        btnNewButton_1.setBounds(186, 611, 40, 17);
        frmSignUp.getContentPane().add(btnNewButton_1);
        
        JLabel lblCourses = new JLabel("Courses");
        lblCourses.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 18));
        lblCourses.setBounds(31, 435, 158, 30);
        frmSignUp.getContentPane().add(lblCourses);
        
        JComboBox<String> cboCourse = new JComboBox<String>();
        cboCourse.setBounds(21, 475, 285, 48);
        frmSignUp.getContentPane().add(cboCourse);
        
        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 18));
        lblLastName.setBounds(21, 118, 158, 30);
        frmSignUp.getContentPane().add(lblLastName);
        
        txtLastname = new JTextField();
        txtLastname.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtLastname.setColumns(10);
        txtLastname.setBounds(21, 158, 289, 48);
        frmSignUp.getContentPane().add(txtLastname);
        btnSignUp.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
            	   try (Connection connection = database.connect()) {
   					String firstName =  txtFirstname.getText();
   					String lastName =txtLastname.getText();
   					String email =txtEmail.getText();
   					String pwd=new String(txtPwd.getPassword());
   					String role="Student";
   					
   		            String query = "INSERT INTO user (name,lastname,email,pwd,role) VALUES(?,?,?,?,?)";
   		         
   		        		 
   		            try (PreparedStatement preparedStatement = connection.prepareStatement(query,PreparedStatement.RETURN_GENERATED_KEYS)) {
   		                preparedStatement.setString(1, firstName);
   		                preparedStatement.setString(2, lastName);
   		                preparedStatement.setString(3, email);
   		                preparedStatement.setString(4, pwd);
   		                preparedStatement.setString(5, role);
   		             
   		             
   		                int rowsInserted = preparedStatement.executeUpdate();
   		                if (rowsInserted > 0) {
   		                	 
   		                 int studentID = -1;
   		                 try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
   		                     if (generatedKeys.next()) {
   		                         studentID = generatedKeys.getInt(1);
   		                     }
   		                 }

   		                 if (studentID != -1) {
   		                	 
   		                	 String mname =  (String)cboCourse.getSelectedItem();
   		                	 int level =4;
   		                	 int semester=1;
   		                	 
	                         String sql = "SELECT * FROM course_module cm " +
	                        		 "INNER JOIN courses m ON cm.courseid = m.id " +
	                        		 "WHERE cm.level=? and cm.semester=? and m.course_name=?";
	                         
	                         PreparedStatement statement = connection.prepareStatement(sql);
	                         
	                         statement.setInt(1, level);
	                         statement.setInt(2, semester);
	                         statement.setString(3, mname);
	                         
	                         ResultSet resultSet = statement.executeQuery();

	                         while (resultSet.next()) {//   		                             
	                        	 
	                        	 String updateModuleSQL = "INSERT INTO student_module (userid,courseid,moduleid,level,semester,marks) VALUES(?,?,?,?,?,?)";
	   		                     PreparedStatement updateModuleStatement = connection.prepareStatement(updateModuleSQL);
	   		                     updateModuleStatement.setInt(1, studentID);
	   		                     updateModuleStatement.setInt(2, resultSet.getInt("courseid"));
	   		                     updateModuleStatement.setInt(3, resultSet.getInt("moduleid"));
	   		                     updateModuleStatement.setInt(4, level);
	   		                     updateModuleStatement.setInt(5, semester);
	   		                     updateModuleStatement.setInt(6, 0);
	   		                     
	   		                     updateModuleStatement.executeUpdate();
	                             
	                         }
   		                      
   		                	
   		                	 
   		                     // Update student-module table
   		                     

   		                     JOptionPane.showMessageDialog(frmSignUp, "Student added and module updated successfully.");
   		                 } else {
   		                     JOptionPane.showMessageDialog(frmSignUp, "Failed to retrieve auto-generated student ID.", "Error", JOptionPane.ERROR_MESSAGE);
   		                 } 
   	        				
   		                	frmSignUp.dispose();
   		        			 
   			                System.out.println("A new user was inserted successfully!");
   			            }
   		                
   		            }
   		        } catch (SQLException ex) {
   		            ex.printStackTrace();
   		             
   		        }
               }
        });
        
        frmSignUp.setBackground(new Color(255, 255, 204));
        frmSignUp.setBounds(100, 100, 341, 677);
        frmSignUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try (
            	Connection connection = database.connect())
            {
                String sql = "SELECT * FROM courses WHERE active=1";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    cboCourse.addItem(resultSet.getString("course_name"));
                    
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
