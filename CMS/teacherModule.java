package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class teacherModule extends winForm{

	public JFrame frmTeacherModule;
	private int _tid;
	private String _name;
	
	private JTable atable = new JTable();
	private JTable stable = new JTable();
		
	private DefaultTableModel amodel = new DefaultTableModel();
	private DefaultTableModel smodel = new DefaultTableModel();
	 
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					teacherModule window = new teacherModule();
//					window.frmTeacherModule.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public teacherModule(int id, String name) {
		_tid=id;
		_name=name;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		DefaultTableModel amodel = new DefaultTableModel();
		amodel.addColumn("ID");
		amodel.addColumn("Module Code");
		amodel.addColumn("Module Name");
       

		
		smodel.addColumn("ID");
		smodel.addColumn("Module Code");
		smodel.addColumn("Module Name");
		
		frmTeacherModule = new JFrame();
		frmTeacherModule.setTitle("Teacher Module");
		frmTeacherModule.getContentPane().setBackground(new Color(255, 255, 204));
		frmTeacherModule.getContentPane().setLayout(null);
		
		
		try (
	        	Connection connection = database.connect())
	        {
	        	 
	        	String cmids="";
	        	 
	        	//Load compulsory modules
	            String cq = "SELECT cm.moduleid,m.code,m.module_name " +
	            		"FROM teacher_module cm "+
	            		"INNER JOIN modules m ON cm.moduleid = m.id " +
	            		"WHERE cm.userid=? ";
	            
	            PreparedStatement cqs = connection.prepareStatement(cq);
	            cqs.setInt(1, _tid);
	             
	            
	            ResultSet crs = cqs.executeQuery();
	            while (crs.next()) {            
	                cmids+=crs.getInt("moduleid")+",";               
	            }
	        	
	          		            
	            String newStr = "";
	            String sql ="";
	            if (cmids.equals("")) {
	            	sql = "SELECT * FROM modules ";
	            }
	            else {
	            	newStr = cmids.substring(0, cmids.length() - 1);
	            	sql = "SELECT * FROM modules WHERE id NOT IN (" +newStr +")";
	            }
	        	 
	        		        	
	            PreparedStatement statement = connection.prepareStatement(sql);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                Object[] row = {
	                		resultSet.getInt("id"), 
	                		resultSet.getString("code"),
	                		resultSet.getString("module_name")                		                		
	                };
	                amodel.addRow(row);
	                
	            }
	                        
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
		atable.setModel(amodel);
        
        atable.getColumnModel().getColumn(0).setMinWidth(0);
        atable.getColumnModel().getColumn(0).setMaxWidth(0);
        atable.getColumnModel().getColumn(0).setWidth(0);
        

        JScrollPane spAvailable = new JScrollPane(atable);        
		spAvailable.setBounds(10, 107, 273, 292);
		frmTeacherModule.getContentPane().add(spAvailable);
		
		stable.setModel(smodel);
        
		stable.getColumnModel().getColumn(0).setMinWidth(0);
		stable.getColumnModel().getColumn(0).setMaxWidth(0);
		stable.getColumnModel().getColumn(0).setWidth(0);
        
		JScrollPane spSelected = new JScrollPane(stable);
		spSelected.setBounds(376, 107, 273, 292);
		frmTeacherModule.getContentPane().add(spSelected);
		
		JLabel lblAvailableModules = new JLabel("Available modules");
		lblAvailableModules.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAvailableModules.setBounds(10, 67, 132, 32);
		frmTeacherModule.getContentPane().add(lblAvailableModules);
		
		JLabel lblNewLabel_1_1 = new JLabel("Selected Modules");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(379, 65, 132, 32);
		frmTeacherModule.getContentPane().add(lblNewLabel_1_1);
		
		JButton btnSelect = new JButton(">");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = atable.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected in the source table
                    // Get data from the selected row
                    Object[] rowData = new Object[3];
                    for (int i = 0; i < 3; i++) {
                        rowData[i] = atable.getValueAt(selectedRow, i);
                    }

                    // Add the data to the destination table
                    smodel.addRow(rowData);

                    // Remove the row from the source table
                    amodel.removeRow(selectedRow);
                }
			}
		});
		btnSelect.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSelect.setBounds(293, 136, 66, 32);
		frmTeacherModule.getContentPane().add(btnSelect);
		
		JButton btnDeselect = new JButton("<");
		btnDeselect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = stable.getSelectedRow();
                if (selectedRow != -1) { // If a row is selected in the source table
                    // Get data from the selected row
                    Object[] rowData = new Object[3];
                    for (int i = 0; i < 3; i++) {
                        rowData[i] = stable.getValueAt(selectedRow, i);
                    }

                    // Add the data to the destination table
                    amodel.addRow(rowData);

                    // Remove the row from the source table
                    smodel.removeRow(selectedRow);
                }
			}
		});
		btnDeselect.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDeselect.setBounds(293, 178, 66, 32);
		frmTeacherModule.getContentPane().add(btnDeselect);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < smodel.getRowCount(); i++) {
					Object[] rowData = new Object[3];
                    for (int j = 0; j < 3; j++) {
                        rowData[j] = stable.getValueAt(i, j);
                    }

		            amodel.addRow(rowData);
		            
		        }
				smodel.setRowCount(0);
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClear.setBounds(583, 65, 66, 32);
		frmTeacherModule.getContentPane().add(btnClear);
		frmTeacherModule.setBounds(100, 100, 673, 505);
		frmTeacherModule.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JLabel lblNewLabel = new JLabel("Teacher Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 132, 32);
		frmTeacherModule.getContentPane().add(lblNewLabel);
		
		JLabel lblTeachername = new JLabel("abc");
		lblTeachername.setText(_name);
		lblTeachername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTeachername.setBounds(151, 10, 132, 32);
		frmTeacherModule.getContentPane().add(lblTeachername);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection connection = database.connect()) {
					
					//Save Teacher Module
					if(smodel.getRowCount()>0) {
						String q= "DELETE FROM teacher_module WHERE userid=?";
						PreparedStatement cs = connection.prepareStatement(q);
						cs.setInt(1, _tid);	
						
						cs.executeUpdate();
						
						for (int i = 0; i < smodel.getRowCount(); i++) {
							
							int mid = (int) stable.getValueAt(i, 0);
							
							String inq = "INSERT INTO teacher_module (userid,moduleid) VALUES(?,?)";
							PreparedStatement ins = connection.prepareStatement(inq);
							ins.setInt(1, _tid);	
							ins.setInt(2, mid);
							 
						 
							int rowsInserted = ins.executeUpdate();
			                if (rowsInserted > 0) {		                	
			                	
				                System.out.println("A new course module successfully managed!");
				                
				                
				            }
							
				        }
						
						frmTeacherModule.dispose();
		                
		                teacherList tl= new teacherList();
						tl.display(tl.frmTeachers);
					}
					
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		             
		        }
				
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(10, 426, 89, 32);
		frmTeacherModule.getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmTeacherModule.dispose();
				
				teacherList tl= new teacherList();
				tl.display(tl.frmTeachers);
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(119, 426, 89, 32);
		frmTeacherModule.getContentPane().add(btnCancel);
		
		
		updateCompulosryMod();
	}

	private void updateCompulosryMod() {
		try (
	        	Connection connection = database.connect())
	        {
	        	
	            String cq = "SELECT cm.moduleid,m.code,m.module_name " +
	            		"FROM teacher_module cm "+
	            		"INNER JOIN modules m ON cm.moduleid = m.id " +
	            		"WHERE cm.userid=?";
	            
	            PreparedStatement cqs = connection.prepareStatement(cq);
	            cqs.setInt(1, _tid);
	            
	            
	            ResultSet crs = cqs.executeQuery();
	            smodel.setRowCount(0);
	            while (crs.next()) {
	                Object[] row = {
	                		crs.getInt("moduleid"), 
	                		crs.getString("code"),
	                		crs.getString("module_name")                		                		
	                };
	                
	                smodel.addRow(row);
	                
	            }
	        	
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
}
