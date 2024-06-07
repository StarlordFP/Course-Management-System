package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class teacherStudent extends winForm{

	public JFrame frame;
	private int _tid;
	private JTable table = new JTable();
	private String _tmids="";
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					teacherStudent window = new teacherStudent();
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
	public teacherStudent(int tid) {
		_tid=tid;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 694, 472);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Issue mark");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
		        if (selectedRow == -1) {
		            JOptionPane.showMessageDialog(frame, "Please select a student to issue mark.", "Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int sid= (int) table.getValueAt(selectedRow, 0);
		        
				issuemarks se= new issuemarks(sid,_tid,_tmids);
				se.display(se.frmIssueMark);
				
				frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(10, 10, 119, 32);
		frame.getContentPane().add(btnNewButton);
		
		DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Email");

        // Fetch data from the database and populate the model
        
        try (
        	Connection connection = database.connect())
        {
        	String cmids="";
        	String sql = "SELECT * FROM teacher_module where userid=?";
        	PreparedStatement st = connection.prepareStatement(sql);
        	st.setInt(1, _tid);
        	ResultSet rs = st.executeQuery();
        	while (rs.next()) {
        		cmids+=rs.getInt("moduleid")+",";              
        	}
        	
        	_tmids = "";
            sql ="";
            if (!cmids.equals("")) {
            	_tmids = cmids.substring(0, cmids.length() - 1);
            	sql = "SELECT DISTINCT u.id,u.name,u.lastname,u.email FROM student_module sm "+
            		"INNER JOIN user u ON sm.userid=u.id " +
            		" WHERE  u.role='Student' AND sm.moduleid IN (" +_tmids +")";
            	            	
            	st = connection.prepareStatement(sql);
            	rs = st.executeQuery();
            	
            	while (rs.next()) {
                    Object[] row = {
                    		rs.getInt("id"), 
                    		rs.getString("name")+" "+ rs.getString("lastname"),
                    		rs.getString("email")                		
                    };
                    model.addRow(row);
                }
            }
             
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the model to the table
        table.setModel(model);

        JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 52, 660, 373);
		frame.getContentPane().add(scrollPane);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				teacherpanel teapnl = new teacherpanel(_tid);
        		teapnl.display(teapnl.frame);
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClose.setBounds(575, 10, 95, 32);
		frame.getContentPane().add(btnClose);
	}
}
