package w10;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class generateReport {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					generateReport window = new generateReport();
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
	public generateReport() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 204));
		frame.setBounds(100, 100, 436, 525);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Generate Report");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		lblNewLabel.setBounds(91, 31, 228, 49);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblIssuingMarksFor = new JLabel("Module :");
		lblIssuingMarksFor.setHorizontalAlignment(SwingConstants.LEFT);
		lblIssuingMarksFor.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblIssuingMarksFor.setBounds(10, 120, 154, 38);
		frame.getContentPane().add(lblIssuingMarksFor);
		
		JLabel lblStudentId = new JLabel("Student ID :");
		lblStudentId.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentId.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblStudentId.setBounds(10, 179, 154, 38);
		frame.getContentPane().add(lblStudentId);
		
		JLabel lblStudentName = new JLabel("Student Name :");
		lblStudentName.setHorizontalAlignment(SwingConstants.LEFT);
		lblStudentName.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblStudentName.setBounds(10, 238, 154, 38);
		frame.getContentPane().add(lblStudentName);
		
		JLabel lblMarksObtained = new JLabel("Marks Obtained :");
		lblMarksObtained.setHorizontalAlignment(SwingConstants.LEFT);
		lblMarksObtained.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblMarksObtained.setBounds(10, 299, 154, 38);
		frame.getContentPane().add(lblMarksObtained);
		
		JButton btnSaveTeacher = new JButton("Issue");
		btnSaveTeacher.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnSaveTeacher.setBackground(new Color(255, 204, 0));
		btnSaveTeacher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSaveTeacher.setBounds(203, 400, 92, 38);
		frame.getContentPane().add(btnSaveTeacher);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnCancel.setBackground(new Color(255, 204, 0));
		btnCancel.setBounds(304, 400, 92, 38);
		frame.getContentPane().add(btnCancel);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(174, 120, 194, 38);
		frame.getContentPane().add(textField);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(174, 179, 194, 38);
		frame.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(174, 238, 194, 38);
		frame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(174, 299, 194, 38);
		frame.getContentPane().add(textField_3);
	}

}
