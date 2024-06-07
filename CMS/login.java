package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class login extends winForm{

    public JFrame frame;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private int _userid;
     /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    login window = new login();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public login() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(255, 255, 204));
        frame.setBounds(100, 100, 511, 615);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        userNameField = new JTextField();
        userNameField.setText("bikram@gmai.com");
        userNameField.setBounds(46, 201, 413, 36);
        userNameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        frame.getContentPane().add(userNameField);
        userNameField.setColumns(10);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(46, 296, 413, 36);
        frame.getContentPane().add(passwordField);
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setBounds(46, 445, 139, 48);
        btnLogIn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnLogIn.setBackground(new Color(0, 255, 255));
        btnLogIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = userNameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                String userRole=loginUser(enteredUsername, enteredPassword);
                userRole=userRole.trim().toLowerCase();
                if (userRole.equals("")) {
                	JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    // You can display an error message or take appropriate action
                   
                } else {
//                	 JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
//                     // You can add code here to open a new window or perform other actions
                	if(userRole.equals("admin")) {
                		
                		adminpanel admnpnl = new adminpanel();
                		admnpnl.display(admnpnl.frame);
                		frame.dispose();
                	}
                	else if(userRole.equals("teacher")) {
                		teacherpanel teapnl = new teacherpanel(_userid);
                		teapnl.display(teapnl.frame);
                		frame.dispose();
                	}
                	else if(userRole.equals("student")) {
                		studentpanel stdpnl = new studentpanel(_userid);
                		stdpnl.display(stdpnl.frame);
                		frame.dispose();
                	}
                }
            }
        });
        frame.getContentPane().add(btnLogIn);
        
        JLabel lblUserName = new JLabel("User name");
        lblUserName.setBounds(46, 166, 139, 25);
        lblUserName.setFont(new Font("SansSerif", Font.BOLD, 18));
        frame.getContentPane().add(lblUserName);
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(46, 261, 139, 25);
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 18));
        frame.getContentPane().add(lblPassword);
        
        JLabel lblLogin = new JLabel("Login");
        lblLogin.setBounds(46, 55, 216, 80);
        lblLogin.setFont(new Font("SansSerif", Font.BOLD, 30));
        frame.getContentPane().add(lblLogin);
        
        JLabel lblDontHaveAn = new JLabel("Don't have an account?");
        lblDontHaveAn.setBounds(46, 515, 155, 25);
        lblDontHaveAn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        frame.getContentPane().add(lblDontHaveAn);
        
        JButton btnNewButton = new JButton("Sign up");
        btnNewButton.setBounds(197, 519, 85, 21);
        btnNewButton.addActionListener(new ActionListener() {
        	
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        		signUp sp= new signUp();
        		sp.display(sp.frmSignUp);
        	}
        });
        btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
        btnNewButton.setForeground(new Color(0, 0, 255));
        btnNewButton.setBackground(new Color(0, 204, 153));
        frame.getContentPane().add(btnNewButton);
    }
    
    // Method to check user credentials in the database
    private String loginUser(String username, String password) {
        try (Connection connection = database.connect()) {
            String query = "SELECT * FROM user WHERE email = ? AND pwd = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
//                database.close(connection);
                
                if (resultSet.next()) {
                	_userid=resultSet.getInt("id");
                	return resultSet.getString("role");
                }                	
                else
                	return "";
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";
        }
    }

	public Dialog getFrmlogin() {
		// TODO Auto-generated method stub
		return null;
	}
}
