package w10;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class report extends winForm{

	public JFrame frame;
	private String _tmids;
	private int _sid;
	private int _level;
	private int _semester;

	
	
	
	
	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	public report(int sid, int level, int semester) {
		_sid=sid;
		_level=level;
		_semester=semester;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Report");
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 682, 674);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIssuingMarksFor = new JLabel("Level :");
		lblIssuingMarksFor.setHorizontalAlignment(SwingConstants.LEFT);
		lblIssuingMarksFor.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblIssuingMarksFor.setBounds(10, 120, 304, 38);
		frame.getContentPane().add(lblIssuingMarksFor);
		
		JLabel lblStudentId = new JLabel("Student ID :");
		lblStudentId.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentId.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentId.setBounds(10, 20, 304, 38);
		frame.getContentPane().add(lblStudentId);
		
		JLabel lblStudentName = new JLabel("Student Name :");
		lblStudentName.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentName.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentName.setBounds(10, 72, 304, 38);
		frame.getContentPane().add(lblStudentName);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				studentpanel ts = new studentpanel(_sid);
				ts.display(ts.frame);
			}
		});
		btnOk.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnOk.setBackground(new Color(255, 204, 0));
		btnOk.setBounds(10, 589, 92, 38);
		frame.getContentPane().add(btnOk);
		
		
		
		JLabel lblStudentid = new JLabel("Student ID :");
		lblStudentid.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentid.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentid.setBounds(335, 20, 296, 38);
		frame.getContentPane().add(lblStudentid);
		
		JLabel lblStudentname = new JLabel("Student Name :");
		lblStudentname.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentname.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentname.setBounds(335, 72, 296, 38);
		frame.getContentPane().add(lblStudentname);
		
		JLabel lblLevel = new JLabel("Level :");
		lblLevel.setHorizontalAlignment(SwingConstants.LEFT);
		lblLevel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblLevel.setBounds(335, 120, 296, 38);
		frame.getContentPane().add(lblLevel);
		
		JLabel lblSemesters = new JLabel("Semester :");
		lblSemesters.setHorizontalAlignment(SwingConstants.LEFT);
		lblSemesters.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblSemesters.setBounds(10, 167, 304, 38);
		frame.getContentPane().add(lblSemesters);
		
		JLabel lblSemester = new JLabel("Semester :");
		lblSemester.setHorizontalAlignment(SwingConstants.LEFT);
		lblSemester.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblSemester.setBounds(335, 167, 296, 38);
		frame.getContentPane().add(lblSemester);
		
		JLabel lblResult = new JLabel("New label");
		lblResult.setForeground(new Color(0, 102, 255));
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblResult.setBounds(10, 546, 648, 26);
		frame.getContentPane().add(lblResult);
		
		JLabel lblTotal = new JLabel("Total marks");
		lblTotal.setForeground(new Color(0, 102, 255));
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotal.setBounds(10, 509, 648, 26);
		frame.getContentPane().add(lblTotal);
		
		
		try (
	        	Connection connection = database.connect())
	        {
//			SELECT DISTINCT sm.userid,u.name,u.lastname,u.email,sm.level,sm.semester FROM student_module sm 
//			INNER JOIN user u ON sm.userid=u.id
//			WHERE  sm.userid=15
			
	        	String sql = "SELECT * FROM student_module sm "+
	            		"INNER JOIN user u ON sm.userid=u.id " +
	            		" WHERE  sm.userid=? LIMIT 1";
	        	PreparedStatement st = connection.prepareStatement(sql);
	        	st.setInt(1, _sid);
	        	ResultSet rs = st.executeQuery();
	        	while (rs.next()) {
	        		lblStudentid.setText(rs.getString("userid"));
	        		lblStudentname.setText(rs.getString("name")+" "+rs.getString("lastname"));
	        		lblLevel.setText(rs.getString("level"));
	        		lblSemester.setText(rs.getString("semester"));
	        		              
	        	}
	        	
	        	sql ="";
	        	sql = "SELECT * FROM student_module sm "+
	            		"INNER JOIN modules m ON sm.moduleid=m.id " +
	            		" WHERE  sm.userid=? AND level=? AND semester=?";
	            	            	
            	st = connection.prepareStatement(sql);
            	st.setInt(1, _sid);
            	st.setInt(2, _level);
            	st.setInt(3, _semester);
            	rs = st.executeQuery();
            	
            	int rowCount = 0;
            	float totalmark=0;
            	float percent=0;
            	while (rs.next()) {
            		
            		JLabel lblid= new JLabel(rs.getString("moduleid"));
            		lblid.setHorizontalAlignment(SwingConstants.LEFT);
            		lblid.setFont(new Font("SansSerif", Font.PLAIN, 14));
            		lblid.setBounds(156, 215 + (rowCount * 48), 15, 20); // Setting bounds for positioning
            		lblid.setVisible(false);
            		 
            		frame.getContentPane().add(lblid);
                    
                    
                    JLabel lbl= new JLabel("("+rs.getString("code")+") "+ rs.getString("module_name"));
                    lbl.setHorizontalAlignment(SwingConstants.LEFT);
                    lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    lbl.setBounds(10, 215 + (rowCount * 48), 304, 38); // Setting bounds for positioning
                    frame.getContentPane().add(lbl); // Adding JLabel to the frame
                    
                    
                    JLabel txtMark = new JLabel(rs.getString("marks"));
                    totalmark+=rs.getFloat("marks");
                    txtMark.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    lbl.setHorizontalAlignment(SwingConstants.LEFT);
//                    txtMark.setColumns(10);
                    txtMark.setBounds(212, 215 + (rowCount * 48), 194, 38);
                    
                    frame.getContentPane().add(txtMark); 
                    
                    rowCount++;
                }
            	
            	percent=totalmark/rowCount;
            	
            	lblTotal.setText("Percentage: "+ String.format("%.2f", percent));
            	if(percent>40) {
            		lblResult.setText("Congratulations, you are progressed to next level.");
            	}
            	else {
            		lblResult.setText("Sorry, you are not progressed to next level.");
            	}
	             
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
				
		
	}

}
