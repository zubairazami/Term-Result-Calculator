package GUIPackage;

import javax.swing.*; 
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import MainPackage.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DataEditPanelCourse implements ActionListener 
{
	
	private Controll TempControll;
	private JDialog DEPC;
	private JPanel Panel;
	private JLabel CourseNoLabel1,CourseNoLabel2,CourseNameLabel,CourseCreditLabel;
	private JTextField CourseNameField;
	private JComboBox<String> CourseCreditBox;
	private String CourseNo,CourseName,CourseCredit,TempCourseTitle,TempCourseCredit;
	private JButton UpdateButton;
	private Border ButtonBorder = new BevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.GRAY);
	private int CreditIndex;
	private float Credit;
	public DataEditPanelCourse(Controll x)
	{
		TempControll = x ;
	}
	
	
	public void createDataEditPanelCourse(String CourseNo,String CourseName,String CourseCredit)
	{
		
		this.CourseNo = CourseNo;
		this.CourseName  = CourseName;
		this.CourseCredit = CourseCredit;
		
		Credit = Float.parseFloat(CourseCredit);
		
		if(Credit==0.75) CreditIndex = 0;
		else if (Credit==1.50) CreditIndex = 1;
		else if(Credit==3.00) CreditIndex = 2;
		else CreditIndex = 3;
		
		DEPC = new JDialog();
		DEPC.setTitle("Course Edit Dialog");
	
		Panel = new JPanel()
		{
			 protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/Icons/8.jpg")).getImage(),0,0,600,230,null);
				ButtonBorder.paintBorder(this, g, 172, 136, 130, 25);
			   }
		};
		Panel.setForeground(Color.WHITE);
		Panel.setLayout(null);
		
		
		CourseNoLabel1 = new JLabel("Course No.          : "  );
		CourseNoLabel1.setForeground(Color.WHITE);
		CourseNoLabel1.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		CourseNoLabel1.setBounds(77, 29, 139, 27);
		Panel.add(CourseNoLabel1);
		
		CourseNoLabel2 = new JLabel(this.CourseNo);
		CourseNoLabel2.setForeground(Color.WHITE);
		CourseNoLabel2.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 12));
		CourseNoLabel2.setBounds(213, 29, 200, 27);
		Panel.add(CourseNoLabel2);
		
		CourseNameLabel = new JLabel("Course Title       :");
		CourseNameLabel.setForeground(Color.WHITE);
		CourseNameLabel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		CourseNameLabel.setBounds(77, 56, 139, 26);
		Panel.add(CourseNameLabel);
		
		CourseNameField = new JTextField(this.CourseName);
		CourseNameField.setForeground(Color.WHITE);
		CourseNameField.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 10));
		CourseNameField.setBounds(213, 60, 200, 20);
		CourseNameField.setOpaque(false);
		CourseNameField.setHorizontalAlignment(SwingConstants.CENTER);
		Panel.add(CourseNameField);
		
		CourseCreditLabel = new JLabel("Course Credit   :");
		CourseCreditLabel.setForeground(Color.WHITE);
		CourseCreditLabel.setBounds(77, 87, 139, 26);
		CourseCreditLabel.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 12));
		Panel.add(CourseCreditLabel);
		
		CourseCreditBox = new JComboBox<String>();
		CourseCreditBox.setBackground(Color.GRAY);
		CourseCreditBox.setForeground(new Color(28, 0, 0));
		CourseCreditBox.setBounds(213,91,103,20);
		CourseCreditBox.setBorder(ButtonBorder);		
		CourseCreditBox.addItem("0.75");
		CourseCreditBox.addItem("1.50");
		CourseCreditBox.addItem("3.00");
		CourseCreditBox.addItem("4.00");
		CourseCreditBox.setMaximumRowCount(3);
		CourseCreditBox.setSelectedIndex(CreditIndex);
		Panel.add(CourseCreditBox);
		
		UpdateButton = new JButton("Update");
		UpdateButton.setForeground(Color.WHITE);
		UpdateButton.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 10));
		UpdateButton.setBounds(173, 137, 128, 23);
		UpdateButton.setContentAreaFilled(false);
		UpdateButton.addActionListener(this);
		Panel.add(UpdateButton);
		
		DEPC.getContentPane().add(Panel);
		DEPC.setSize(500,230);
		DEPC.setLocation(500, 200);
		DEPC.setResizable(false);
		DEPC.setVisible(true);	
		DEPC.setModal(true);
	}

	@Override
	public void actionPerformed(ActionEvent AE) 
	{
		if(TempControll.ConnectionManagerObject.createConnection())
		  if(AE.getSource()==UpdateButton)
		    {
			  TempCourseTitle = CourseNameField.getText();
			  TempCourseCredit =(String) CourseCreditBox.getSelectedItem();
			  if(!TempCourseTitle.equals(""))
			    {
			        if(TempControll.DataEraseEditObject.editCourse(this.CourseNo, TempCourseTitle, TempCourseCredit))
			          JOptionPane.showMessageDialog(DEPC, "Information of '"+this.CourseNo+"' has been updated successfully.", "Updated", JOptionPane.INFORMATION_MESSAGE);
			        else
			          JOptionPane.showMessageDialog(DEPC, "Information of '"+this.CourseNo+"' hasn't been updated.\nCheck if this course is still available & try again.", "Updated", JOptionPane.INFORMATION_MESSAGE);
			        DEPC.dispose();
			    }
		      else
			   	JOptionPane.showMessageDialog(DEPC, "Please, complete 'Course Title'  field.", "Empty Course Title Field", JOptionPane.ERROR_MESSAGE);
		    }
	}
}