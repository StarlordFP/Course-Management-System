package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

public class adminpanel extends winForm{

	public JFrame frame;

	JLabel lbltc = new JLabel("0");
	JLabel lblsc = new JLabel("0");
	JLabel lblcc = new JLabel("0");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adminpanel window = new adminpanel();
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
	public adminpanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 204, 0));
		frame.setBounds(100, 100, 909, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 10, 3, 3);
		frame.getContentPane().add(panel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 204));
//		panel_2.setBackground(new Color(102, 255, 255));
		panel_2.setBounds(266, 0, 629, 606);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblGoodToSee = new JLabel("Good To See You Again, Admin!");
		lblGoodToSee.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblGoodToSee.setBounds(153, 45, 448, 49);
		panel_2.add(lblGoodToSee);
		
		JButton logOutButton = new JButton("Logout");
		logOutButton.setBounds(487, 533, 109, 38);
		panel_2.add(logOutButton);
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				login ln= new login();
				ln.display(ln.frame);
			}
		});
		logOutButton.setIconTextGap(16);
		logOutButton.setForeground(new Color(0, 0, 0));
		logOutButton.setFont(new Font("Dialog", Font.BOLD, 18));
		logOutButton.setBorderPainted(false);
		logOutButton.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		logOutButton.setBackground(new Color(0, 255, 255));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(53, 143, 215, 161);
		panel_2.add(panel_1);
		panel_1.setLayout(null);
		
		
		
		lbltc.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbltc.setHorizontalAlignment(SwingConstants.CENTER);
		lbltc.setBounds(10, 10, 195, 45);
		panel_1.add(lbltc);
		
		JLabel lblTeachers = new JLabel("Teachers");
		lblTeachers.setHorizontalAlignment(SwingConstants.CENTER);
		lblTeachers.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTeachers.setBounds(10, 84, 195, 45);
		panel_1.add(lblTeachers);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBounds(343, 143, 215, 161);
		panel_2.add(panel_1_1);
		
		
		lblsc.setHorizontalAlignment(SwingConstants.CENTER);
		lblsc.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblsc.setBounds(10, 10, 195, 45);
		panel_1_1.add(lblsc);
		
		JLabel lblStudents = new JLabel("Students");
		lblStudents.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudents.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblStudents.setBounds(10, 84, 195, 45);
		panel_1_1.add(lblStudents);
		
		JPanel panel_1_2 = new JPanel();
		panel_1_2.setLayout(null);
		panel_1_2.setBounds(53, 361, 215, 161);
		panel_2.add(panel_1_2);
		
		
		lblcc.setHorizontalAlignment(SwingConstants.CENTER);
		lblcc.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblcc.setBounds(10, 10, 195, 45);
		panel_1_2.add(lblcc);
		
		JLabel lblCourses = new JLabel("Courses");
		lblCourses.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourses.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCourses.setBounds(10, 84, 195, 45);
		panel_1_2.add(lblCourses);
		
		JButton btnCourses = new JButton("Courses");
		btnCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				courseList cl = new courseList();
				cl.display(cl.frmCourses);
			}
		});
		btnCourses.setIconTextGap(15);
		btnCourses.setForeground(Color.WHITE);
		btnCourses.setFont(new Font("Dialog", Font.BOLD, 18));
		btnCourses.setBorderPainted(false);
		btnCourses.setBorder(null);
		btnCourses.setBackground(new Color(0, 204, 153));
		btnCourses.setBounds(57, 306, 137, 38);
		frame.getContentPane().add(btnCourses);
		
		JButton btnStudents = new JButton("Students");
		btnStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentList sl= new studentList();
				sl.display(sl.frame);
				
				frame.dispose();
			}
		});
		btnStudents.setIconTextGap(15);
		btnStudents.setForeground(Color.WHITE);
		btnStudents.setFont(new Font("Dialog", Font.BOLD, 18));
		btnStudents.setBorderPainted(false);
		btnStudents.setBorder(null);
		btnStudents.setBackground(new Color(0, 204, 153));
		btnStudents.setBounds(57, 228, 137, 38);
		frame.getContentPane().add(btnStudents);
		
		JButton btnTeachers = new JButton("Teachers");
		btnTeachers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				teacherList tl= new teacherList();
				tl.display(tl.frmTeachers);
				
				frame.dispose();
			}
		});
		btnTeachers.setIconTextGap(15);
		btnTeachers.setForeground(Color.WHITE);
		btnTeachers.setFont(new Font("Dialog", Font.BOLD, 18));
		btnTeachers.setBorderPainted(false);
		btnTeachers.setBorder(null);
		btnTeachers.setBackground(new Color(0, 204, 153));
		btnTeachers.setBounds(57, 147, 137, 38);
		frame.getContentPane().add(btnTeachers);
		
		JButton btnModules = new JButton("Modules");
		btnModules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moduleList ml= new moduleList();
				ml.display(ml.frame);
			}
		});
		btnModules.setIconTextGap(15);
		btnModules.setForeground(Color.WHITE);
		btnModules.setFont(new Font("Dialog", Font.BOLD, 18));
		btnModules.setBorderPainted(false);
		btnModules.setBorder(null);
		btnModules.setBackground(new Color(0, 204, 153));
		btnModules.setBounds(57, 389, 137, 38);
		frame.getContentPane().add(btnModules);
		
		
		RefreshDashboard();
		 
		
	}

	public void RefreshDashboard() {
		try {
			Connection conn = database.connect();
			String q = "SELECT COUNT(*) AS count FROM user WHERE role='Teacher'";
			
			PreparedStatement stm = conn.prepareStatement(q);             
			ResultSet rt = stm.executeQuery();			
			if (rt.next()) {                
                int tc =  rt.getInt("count");
    			lbltc.setText(String.valueOf(tc));
            }
			
			//students
			q = "SELECT COUNT(*) AS count FROM user WHERE role='Student'";
			
			stm = conn.prepareStatement(q);             
			rt = stm.executeQuery();			
			if (rt.next()) {                
                int sc =  rt.getInt("count");
    			lblsc.setText(String.valueOf(sc));
            }
			
			//students
			q = "SELECT COUNT(*) AS count FROM courses";
			
			stm = conn.prepareStatement(q);             
			rt = stm.executeQuery();			
			if (rt.next()) {                
                int cc =  rt.getInt("count");
    			lblcc.setText(String.valueOf(cc));
            }
			
		}
		catch (SQLException ex) {
            ex.printStackTrace();
             
		}
	}
	
//	 public void display() {
//	        SwingUtilities.invokeLater(() -> {
//	            
//	            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//	            
//	            // Add components to the frame
//	            
//	            frame.setVisible(true);
//	        });
//	    }
	public static Window getFrmadminpanel() {
		// TODO Auto-generated method stub
		return null;
	}
}
