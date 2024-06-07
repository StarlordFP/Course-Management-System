package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class issuemarks extends winForm{

	public JFrame frmIssueMark;
	private String _tmids;
	private int _sid;
	private int _tid;

	
	private List<JLabel> lblModidList = new ArrayList<>();
	private List<JTextField> txtMarkList = new ArrayList<>();
	
	
//	JLabel[] labels = new JLabel[rowCount-1];
////	JLabel[] lblModid = new JLabel[4];
//	JTextField[] txtMark= new JTextField[rowCount-1];
	
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					issuemarks window = new issuemarks();
//					window.frmIssueMark.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public issuemarks(int sid, int tid, String tmids) {
		_sid=sid;
		_tid=tid;
		_tmids=tmids;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmIssueMark = new JFrame();
		frmIssueMark.setTitle("Issue mark");
		frmIssueMark.getContentPane().setBackground(new Color(255, 255, 204));
		frmIssueMark.setBounds(100, 100, 498, 674);
		frmIssueMark.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmIssueMark.getContentPane().setLayout(null);
		
		JLabel lblIssuingMarksFor = new JLabel("Level :");
		lblIssuingMarksFor.setHorizontalAlignment(SwingConstants.LEFT);
		lblIssuingMarksFor.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblIssuingMarksFor.setBounds(10, 120, 154, 38);
		frmIssueMark.getContentPane().add(lblIssuingMarksFor);
		
		JLabel lblStudentId = new JLabel("Student ID :");
		lblStudentId.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentId.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentId.setBounds(10, 20, 154, 38);
		frmIssueMark.getContentPane().add(lblStudentId);
		
		JLabel lblStudentName = new JLabel("Student Name :");
		lblStudentName.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentName.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentName.setBounds(10, 72, 154, 38);
		frmIssueMark.getContentPane().add(lblStudentName);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnSave.setBackground(new Color(255, 204, 0));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try (Connection connection = database.connect()) {
					
					for (int i = 0; i < lblModidList.size(); i++) {
						int mid= Integer.parseInt(lblModidList.get(i).getText());
						float mark = Float.parseFloat(txtMarkList.get(i).getText());
						
						String sql = "UPDATE student_module SET marks = ? WHERE userid = ? and moduleid=?";
			            PreparedStatement statement = connection.prepareStatement(sql);
			            statement.setFloat(1, mark);
			            statement.setInt(2, _sid);
			            statement.setInt(3, mid);
			            
			            
			            int rowsUpdated = statement.executeUpdate();
			        }
					JOptionPane.showMessageDialog(frmIssueMark, "Mark issued successfully.");
					frmIssueMark.dispose();
					teacherStudent ts = new teacherStudent(_tid);
					ts.display(ts.frame);
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(frmIssueMark, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
				
					
			}
		});
		btnSave.setBounds(10, 589, 92, 38);
		frmIssueMark.getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmIssueMark.dispose();
				
				teacherStudent ts = new teacherStudent(_tid);
				ts.display(ts.frame);
			}
		});
		btnCancel.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnCancel.setBackground(new Color(255, 204, 0));
		btnCancel.setBounds(111, 589, 92, 38);
		frmIssueMark.getContentPane().add(btnCancel);
		
		
		
		JLabel lblStudentid = new JLabel("Student ID :");
		lblStudentid.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentid.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentid.setBounds(215, 20, 154, 38);
		frmIssueMark.getContentPane().add(lblStudentid);
		
		JLabel lblStudentname = new JLabel("Student Name :");
		lblStudentname.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentname.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblStudentname.setBounds(215, 72, 154, 38);
		frmIssueMark.getContentPane().add(lblStudentname);
		
		JLabel lblLevel = new JLabel("Level :");
		lblLevel.setHorizontalAlignment(SwingConstants.LEFT);
		lblLevel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblLevel.setBounds(215, 120, 154, 38);
		frmIssueMark.getContentPane().add(lblLevel);
		
		JLabel lblSemesters = new JLabel("Semester :");
		lblSemesters.setHorizontalAlignment(SwingConstants.LEFT);
		lblSemesters.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblSemesters.setBounds(10, 167, 154, 38);
		frmIssueMark.getContentPane().add(lblSemesters);
		
		JLabel lblSemester = new JLabel("Semester :");
		lblSemester.setHorizontalAlignment(SwingConstants.LEFT);
		lblSemester.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblSemester.setBounds(215, 167, 154, 38);
		frmIssueMark.getContentPane().add(lblSemester);
		
		
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
	            if (!_tmids.equals("")) {

	            	sql = "SELECT * FROM student_module sm "+
	            		"INNER JOIN modules m ON sm.moduleid=m.id " +
	            		" WHERE  sm.userid=? AND sm.moduleid IN (" +_tmids +")";
	            	            	
	            	 st = connection.prepareStatement(sql);
	            	st.setInt(1, _sid);
	            	rs = st.executeQuery();
	            	
	            	int rowCount = 0;
//	                while (rs.next()) {
//	                    rowCount++;
//	                }
	                
	            	
	            		
                	while (rs.next()) {
                		
                		JLabel lblid= new JLabel(rs.getString("moduleid"));
                		lblid.setHorizontalAlignment(SwingConstants.LEFT);
                		lblid.setFont(new Font("SansSerif", Font.PLAIN, 14));
                		lblid.setBounds(156, 215 + (rowCount * 48), 15, 20); // Setting bounds for positioning
                		lblid.setVisible(false);
                		lblModidList.add(lblid);
                		frmIssueMark.getContentPane().add(lblid);
                        
                        
                        JLabel lbl= new JLabel("("+rs.getString("code")+") "+ rs.getString("module_name"));
                        lbl.setHorizontalAlignment(SwingConstants.LEFT);
                        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
                        lbl.setBounds(10, 215 + (rowCount * 48), 154, 38); // Setting bounds for positioning
                        frmIssueMark.getContentPane().add(lbl); // Adding JLabel to the frame
                        
                        
                        JTextField txtMark = new JTextField(rs.getString("marks"));
                        txtMark.setFont(new Font("Tahoma", Font.PLAIN, 14));
                        txtMark.setColumns(10);
                        txtMark.setBounds(212, 215 + (rowCount * 48), 194, 38);
                        txtMarkList.add(txtMark);
                        frmIssueMark.getContentPane().add(txtMark); 
                        
                        rowCount++;
                    }
	                 
//	            	while (rs.next()) {
//	                    Object[] row = {
//	                    		rs.getInt("id"), 
//	                    		rs.getString("name")+" "+ rs.getString("lastname"),
//	                    		rs.getString("email")                		
//	                    };
//	                    model.addRow(row);
//	                }
	            }
	             
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
				
		
	}
}
