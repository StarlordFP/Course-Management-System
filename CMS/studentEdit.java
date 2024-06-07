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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class studentEdit extends winForm{

	public JFrame frmEditStudent;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JPasswordField txtPwd;
	
	private int _sid;
	private int level;
    private int semester;
	private JTextField txtLevel;
	private JTextField txtSemester;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					teacherEdit window = new teacherEdit(3);
//					window.frmEditStudent.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public studentEdit(int id) {
		_sid=id;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEditStudent = new JFrame();
		frmEditStudent.setTitle("Edit Student");
		frmEditStudent.getContentPane().setBackground(new Color(255, 255, 204));
		frmEditStudent.setBounds(100, 100, 380, 453);
		frmEditStudent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEditStudent.getContentPane().setLayout(null);
		
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmEditStudent.dispose();
				studentList tl = new studentList();
				tl.display(tl.frame);
			}
		});
		btnCancel.setBounds(236, 357, 92, 38);
		frmEditStudent.getContentPane().add(btnCancel);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(134, 21, 194, 38);
		frmEditStudent.getContentPane().add(txtFirstName);
		txtFirstName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("First name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(21, 21, 103, 36);
		frmEditStudent.getContentPane().add(lblNewLabel);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(134, 69, 194, 38);
		frmEditStudent.getContentPane().add(txtLastName);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLastName.setBounds(21, 69, 103, 36);
		frmEditStudent.getContentPane().add(lblLastName);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(134, 117, 194, 38);
		frmEditStudent.getContentPane().add(txtEmail);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmail.setBounds(21, 117, 103, 36);
		frmEditStudent.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(21, 165, 103, 36);
		frmEditStudent.getContentPane().add(lblPassword);
		
		txtPwd = new JPasswordField();
		txtPwd.setBounds(134, 165, 194, 36);
		frmEditStudent.getContentPane().add(txtPwd);
		
		JLabel lblLevel = new JLabel("Level");
		lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLevel.setBounds(21, 253, 103, 36);
		frmEditStudent.getContentPane().add(lblLevel);
		
		txtLevel = new JTextField();
		txtLevel.setColumns(10);
		txtLevel.setBounds(134, 253, 194, 38);
		frmEditStudent.getContentPane().add(txtLevel);
		
		JLabel lblSemester = new JLabel("Semester");
		lblSemester.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSemester.setBounds(21, 301, 103, 36);
		frmEditStudent.getContentPane().add(lblSemester);
		
		txtSemester = new JTextField();
		txtSemester.setColumns(10);
		txtSemester.setBounds(134, 301, 194, 38);
		frmEditStudent.getContentPane().add(txtSemester);
		
		
	    
		JLabel lblCourse = new JLabel("Course");
		lblCourse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCourse.setBounds(21, 211, 103, 36);
		frmEditStudent.getContentPane().add(lblCourse);
		
		JLabel lblsCourse = new JLabel("course");
		lblsCourse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblsCourse.setBounds(134, 211, 103, 36);
		frmEditStudent.getContentPane().add(lblsCourse);
		
		JButton btnSaveStudent = new JButton("Save");
		btnSaveStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtFirstName.getText();
		        String lastname = txtLastName.getText();
		        String email = txtEmail.getText();
		        String pwd = new String(txtPwd.getPassword());

		        try (Connection connection = database.connect()) {
		            String sql = "UPDATE user SET name = ?, lastname = ?, email = ?, pwd = ? WHERE id = ?";
		            PreparedStatement statement = connection.prepareStatement(sql);
		            statement.setString(1, name);
		            statement.setString(2, lastname);
		            statement.setString(3, email);
		            statement.setString(4, pwd);
		            statement.setInt(5, _sid);
		            
		            int rowsUpdated = statement.executeUpdate();

		            //UPDATE LEVEL and SEMESTER		
//		            //get course id of student
//		            int cid=0;
//		            sql = "SELECT * FROM user_module WHERE userid = ? LIMIT 1";
//		            statement = connection.prepareStatement(sql);
//		            statement.setInt(1, _sid);
//		            ResultSet rs = statement.executeQuery();
//		            while(rs.next()) {
//		            	cid=rs.getInt("courseid");
//		            }
		            
		            
		            
		            //search modules for course,level,semester
		            String cname= lblsCourse.getText();		            
		            level =Integer.parseInt(txtLevel.getText());
            	    semester=Integer.parseInt(txtSemester.getText());
            	    
            	    
                    
                    sql = "SELECT COUNT(*) AS count  FROM course_module cm " +
                      		 "INNER JOIN courses m ON cm.courseid = m.id " +
                      		 "WHERE cm.level=? and cm.semester=? and m.course_name=?";
                       
                       statement = connection.prepareStatement(sql);                    
                       statement.setInt(1, level);
                       statement.setInt(2, semester);
                       statement.setString(3, cname);
                       
                       ResultSet resultSet = statement.executeQuery();
                       resultSet.next(); // Move cursor to the first row
                       int count = resultSet.getInt("count");
                       
                    if (count>0) {
                    	
                    	sql = "SELECT * FROM course_module cm " +
                          		 "INNER JOIN courses m ON cm.courseid = m.id " +
                          		 "WHERE cm.level=? and cm.semester=? and m.course_name=?";
                           
                           statement = connection.prepareStatement(sql);                    
                           statement.setInt(1, level);
                           statement.setInt(2, semester);
                           statement.setString(3, cname);
                           
                           resultSet = statement.executeQuery();
                           
                    	//DELETE USER From student_module
    		            sql = "DELETE FROM student_module WHERE userid = ?";
    		            statement = connection.prepareStatement(sql);
    		            statement.setInt(1, _sid);
    		            rowsUpdated = statement.executeUpdate();
    		            
                    	while (resultSet.next()) {//   		                             
                          	 
	                      	 String updateModuleSQL = "INSERT INTO student_module (userid,courseid,moduleid,level,semester,marks) VALUES(?,?,?,?,?,?)";
	   		                     PreparedStatement updateModuleStatement = connection.prepareStatement(updateModuleSQL);
	   		                     updateModuleStatement.setInt(1, _sid);
	   		                     updateModuleStatement.setInt(2, resultSet.getInt("courseid"));
	   		                     updateModuleStatement.setInt(3, resultSet.getInt("moduleid"));
	   		                     updateModuleStatement.setInt(4, level);
	   		                     updateModuleStatement.setInt(5, semester);
	   		                     updateModuleStatement.setInt(6, 0);
	   		                     
	   		                     updateModuleStatement.executeUpdate();
	                           
	                       }
                    	
                    	if (rowsUpdated > 0) {
    		                JOptionPane.showMessageDialog(frmEditStudent, "Student updated successfully.");
    		                frmEditStudent.dispose();
    		                studentList tl = new studentList();
    						tl.display(tl.frame);
    		            } 
                    	
                    } else {
                    	JOptionPane.showMessageDialog(frmEditStudent, "No module found for course: " + cname, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                   
		            
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(frmEditStudent, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
						 
		        
			}
		});
		btnSaveStudent.setBounds(135, 357, 92, 38);
		frmEditStudent.getContentPane().add(btnSaveStudent);
		
		
		try (Connection connection = database.connect()) {
//			String firstName =  txtFirstName.getText();
//			String lastName =txtLastName.getText();
//			String email =txtEmail.getText();
//			String pwd=new String(txtPwd.getPassword());
//			String role="Teacher";
			
            String q = "SELECT * FROM USER WHERE id="+_sid + " LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(q);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
//                
                txtFirstName.setText(resultSet.getString("name"));
                txtLastName.setText(resultSet.getString("lastname"));
                txtEmail.setText(resultSet.getString("email"));
                txtPwd.setText(resultSet.getString("pwd"));
                
//                SELECT * FROM student_module sm
//                INNER JOIN courses m ON sm.courseid = m.id 
//                WHERE sm.userid = 15 AND sm.semester = (SELECT MAX(semester) FROM student_module WHERE userid = 15) LIMIT 1
                
                q = "SELECT * FROM student_module sm " +
                	"INNER JOIN courses m ON sm.courseid = m.id "+
                	" WHERE sm.userid=? AND "+
                	" sm.semester = (SELECT MAX(semester) FROM student_module WHERE userid = ?) LIMIT 1";
                
                statement = connection.prepareStatement(q);
                
                statement.setInt(1, _sid); // Set the userid
                statement.setInt(2, _sid); // Set the userid again for the subquery
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    // Retrieve record values and do something with them
                	lblsCourse.setText(rs.getString("course_name"));
                	txtLevel.setText(rs.getString("level"));
                	txtSemester.setText(rs.getString("semester"));

                	level =rs.getInt("level");
            	    semester=rs.getInt("semester");
                    
                } else {
                    System.out.println("No record found for userid .");
                }
                
//                lblCourse.setVisible(false);
//                lblsCourse.setVisible(false);
//                txtLevel.setVisible(false);
//                lblLevel.setVisible(false);
//                lblSemester.setVisible(false);
//                txtSemester.setVisible(false);
            } 
            
        } catch (SQLException ex) {
            ex.printStackTrace();
             
        }
	}
}
