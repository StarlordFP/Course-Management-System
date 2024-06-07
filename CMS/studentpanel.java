package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class studentpanel extends winForm{

	public JFrame frame;
	private int _sid;
	private int _level;
	private int _semester;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					studentpanel window = new studentpanel();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public studentpanel(int sid) {
		_sid=sid;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 827, 628);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnBack = new JButton("Close");
		btnBack.setIconTextGap(16);
		btnBack.setForeground(Color.BLACK);
		btnBack.setFont(new Font("Dialog", Font.BOLD, 18));
		btnBack.setBorderPainted(false);
		btnBack.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnBack.setBackground(Color.CYAN);
		btnBack.setBounds(674, 532, 109, 38);
		frame.getContentPane().add(btnBack);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 0));
		panel.setBounds(0, 0, 267, 591);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		

		JLabel lblNewLabel_1 = new JLabel("Dashboard");
		lblNewLabel_1.setBounds(61, 55, 137, 38);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("SansSerif", Font.BOLD, 25));
		
		JButton btnViewReport = new JButton("View Report");
		btnViewReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				report rp = new report(_sid,_level,_semester);
				rp.display(rp.frame);
			}
		});
		btnViewReport.setIconTextGap(16);
		btnViewReport.setForeground(Color.BLACK);
		btnViewReport.setFont(new Font("Dialog", Font.BOLD, 18));
		btnViewReport.setBorderPainted(false);
		btnViewReport.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnViewReport.setBackground(Color.CYAN);
		btnViewReport.setBounds(50, 125, 161, 38);
		panel.add(btnViewReport);
		
		
		JLabel labelc = new JLabel("Course:");
		labelc.setHorizontalAlignment(SwingConstants.LEFT);
		labelc.setFont(new Font("SansSerif", Font.PLAIN, 14));
		labelc.setBounds(300, 283, 114, 38);
		frame.getContentPane().add(labelc);
		
		JLabel lblStudentId = new JLabel("Student ID :");
		lblStudentId.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentId.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentId.setBounds(300, 183, 114, 38);
		frame.getContentPane().add(lblStudentId);
		
		JLabel lblStudentid = new JLabel("Student ID :");
		lblStudentid.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentid.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentid.setBounds(422, 183, 154, 38);
		frame.getContentPane().add(lblStudentid);
		
		JLabel lblStudentName = new JLabel("Student Name :");
		lblStudentName.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentName.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentName.setBounds(300, 235, 114, 38);
		frame.getContentPane().add(lblStudentName);
		
		
		JLabel lblStudentname = new JLabel("Student Name :");
		lblStudentname.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentname.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentname.setBounds(422, 235, 154, 38);
		frame.getContentPane().add(lblStudentname);
		
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setHorizontalAlignment(SwingConstants.LEFT);
		lblCourse.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblCourse.setBounds(422, 283, 154, 38);
		frame.getContentPane().add(lblCourse);
		
		JLabel lblSemesters = new JLabel("Semester :");
		lblSemesters.setHorizontalAlignment(SwingConstants.LEFT);
		lblSemesters.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblSemesters.setBounds(300, 375, 114, 38);
		frame.getContentPane().add(lblSemesters);
		
		JLabel lblSemester = new JLabel("Semester :");
		lblSemester.setHorizontalAlignment(SwingConstants.LEFT);
		lblSemester.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblSemester.setBounds(422, 375, 154, 38);
		frame.getContentPane().add(lblSemester);
		
		JLabel lblGoodToSee_1 = new JLabel("Good To See You Again!");
		lblGoodToSee_1.setBounds(298, 47, 448, 49);
		frame.getContentPane().add(lblGoodToSee_1);
		lblGoodToSee_1.setFont(new Font("SansSerif", Font.BOLD, 25));
		
		JLabel lblIssuingMarksFor_1 = new JLabel("Level :");
		lblIssuingMarksFor_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblIssuingMarksFor_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblIssuingMarksFor_1.setBounds(300, 331, 114, 38);
		frame.getContentPane().add(lblIssuingMarksFor_1);
		
		JLabel lblLevel = new JLabel("Level :");
		lblLevel.setHorizontalAlignment(SwingConstants.LEFT);
		lblLevel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblLevel.setBounds(422, 331, 154, 38);
		frame.getContentPane().add(lblLevel);
		
		
		try (
	        	Connection connection = database.connect())
	        {
//			SELECT DISTINCT sm.userid,u.name,u.lastname,u.email,sm.level,sm.semester FROM student_module sm 
//			INNER JOIN user u ON sm.userid=u.id
//			WHERE  sm.userid=15
			
	        	String sql = "SELECT * FROM student_module sm "+
	            		"INNER JOIN user u ON sm.userid=u.id " +
	            		"INNER JOIN courses c ON sm.courseid=c.id " +
	            		" WHERE  sm.userid=? LIMIT 1";
	        	PreparedStatement st = connection.prepareStatement(sql);
	        	st.setInt(1, _sid);
	        	ResultSet rs = st.executeQuery();
	        	while (rs.next()) {
	        		lblStudentid.setText(rs.getString("userid"));
	        		lblStudentname.setText(rs.getString("name")+" "+rs.getString("lastname"));
	        		lblCourse.setText(rs.getString("course_name"));
	        		_level=rs.getInt("level");
	        		lblLevel.setText(rs.getString("level"));
	        		_semester=rs.getInt("semester");
	        		lblSemester.setText(rs.getString("semester"));
	        		              
	        	}
	        	
	        	
	          
	             
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
}
