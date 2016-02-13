package GUIPackage;

import MainPackage.Controll;      
import javax.swing.*;      
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class EnrollmentPanel extends JPanel {
	
	
	private int StudentCapacity[] = new int [240];
	private String DepartmentName[] = {"CSE","EEE","ME","ECE","CHEM","HUM","MATH","PHY","CE","IEM","URP","LE"};
	
	private int Value [] = {1,2,3,4};
	private JButton EnrollButton = new JButton("Enroll");
	
	private JRadioButton RB1 = new JRadioButton("Batch Enrollment");
	private JRadioButton RB2 = new JRadioButton("Course Enrollment");
	private JRadioButton RB3 = new JRadioButton("Multiple Course Enrollment");
	
	private Color MyColor = new Color(58,134,209);
	
	
	private JLabel BatchYearLabel = new JLabel("Batch Year : ",SwingConstants.RIGHT);
	private JLabel NumberOfStudentsLabel = new JLabel("Number Of Students : ",SwingConstants.RIGHT);
	private JLabel DepartmentsLabel =new JLabel("Course under Department of : ",SwingConstants.RIGHT);
	private JLabel YearLabel = new JLabel("Course for Year : ",SwingConstants.RIGHT);
	private JLabel TermLabel = new JLabel("Term : ",SwingConstants.RIGHT);
	private JLabel CourseSuffixLabel = new JLabel("Course Suffix : ",SwingConstants.RIGHT);
	private JLabel CourseTitleLabel = new JLabel("Course Title : ",SwingConstants.RIGHT);
	private JLabel CourseCreditLabel = new JLabel("Course Credit : ",SwingConstants.RIGHT);
	private JLabel CourseNoLabel = new JLabel ("Course Name : ",SwingConstants.RIGHT) ;
	private JLabel CourseNo = new JLabel();
	private JLabel NumberOfCourseLabel = new JLabel("Number Of Course : ",SwingConstants.RIGHT);
	
	private JTextField CourseTitle = new JTextField(80);
	
	private JComboBox BatchYear = new JComboBox(); 
	private JComboBox NumberOfStudents = new JComboBox(); 
	private JComboBox Departments = new JComboBox(); 
	private JComboBox Years = new JComboBox(); 
	private JComboBox Terms = new JComboBox(); 
	private JComboBox CourseCredit = new JComboBox();
	private JComboBox CourseSuffix = new JComboBox();
	private JComboBox NumberOfCourses = new JComboBox(); 
	
	private ButtonGroup RBGroup = new ButtonGroup();
	
	private Border MyBorder = new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.DARK_GRAY);
	private Border MyTitledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.BLACK),"                                                                  ",TitledBorder.CENTER,TitledBorder.TOP);
	private Border MySpecialTitledBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.BLACK),"                                                                            ",TitledBorder.CENTER,TitledBorder.TOP);
	private Controll TempControll;
    
    public EnrollmentPanel(Controll x ) {
		 
		 TempControll = x;
		 setLayout(null);
		 
		 RB1.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
		 RB1.setForeground(Color.BLACK);
		 RB1.setContentAreaFilled(false);
		 RB1.setHorizontalAlignment(SwingConstants.CENTER);
		 RB1.setBounds(0, 20, 610, 30);

		 RB2.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
		 RB2.setHorizontalAlignment(SwingConstants.CENTER);
		 RB2.setForeground(Color.BLACK);
		 RB2.setContentAreaFilled(false);
		 RB2.setBounds(0,125,610, 30);
		 
		 RB3.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
		 RB3.setHorizontalAlignment(SwingConstants.CENTER);
		 RB3.setForeground(Color.BLACK);
         RB3.setContentAreaFilled(false);
		 RB3.setBounds(0,365, 610, 30);//320,30
  		 
		 RBGroup.add(RB1);
		 RBGroup.add(RB2);
		 RBGroup.add(RB3);

//  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		 
		 BatchYearLabel.setBounds(100,57,180,15);
		 BatchYearLabel.setForeground(Color.BLACK);
		 BatchYearLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		
		 NumberOfStudentsLabel.setBounds(100,85,180,15);
		 NumberOfStudentsLabel.setForeground(Color.BLACK);
		 NumberOfStudentsLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		 
		 for (int i = 2000;i<2100;i++)
			 BatchYear.addItem(i);
		 
		 BatchYear.setBounds(300,55,200,20);
		 BatchYear.setFont(new Font ("SERRIF",Font.BOLD,10));
		 BatchYear.setMaximumRowCount(10);
		 BatchYear.setEditable(true);
		 BatchYear.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK));
		 ((JTextField)BatchYear.getEditor().getEditorComponent()).setHorizontalAlignment(SwingConstants.CENTER);
		 ((JTextField)BatchYear.getEditor().getEditorComponent()).setForeground(Color.BLACK);
		 BatchYear.setSelectedIndex(0);
		 
		 
		 for(int i=0;i<240;i++)
			 StudentCapacity[i] = i+1;
		 
		 for (int Number : StudentCapacity)
			 NumberOfStudents.addItem(Number);
		 NumberOfStudents.setBounds(300,83,200,20);
		 NumberOfStudents.setFont(new Font ("SERRIF",Font.BOLD,10));
		 NumberOfStudents.setMaximumRowCount(10);
		 NumberOfStudents.setEditable(true);
		 NumberOfStudents.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK));
		 ((JTextField)NumberOfStudents.getEditor().getEditorComponent()).setHorizontalAlignment(SwingConstants.CENTER);
		 ((JTextField)NumberOfStudents.getEditor().getEditorComponent()).setForeground(Color.BLACK);
		 NumberOfStudents.setSelectedIndex(59);
		 
		 //  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
				 
		 DepartmentsLabel.setBounds(20,164,260,15);
		 DepartmentsLabel.setForeground(Color.BLACK);
		 DepartmentsLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		 
		 YearLabel.setBounds(30,192,250,15);
		 YearLabel.setForeground(Color.BLACK);
		 YearLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		 
		 TermLabel.setBounds(30,220,250,15);
		 TermLabel.setForeground(Color.BLACK);
		 TermLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		 
		 CourseSuffixLabel.setBounds(30,248,250,15);
		 CourseSuffixLabel.setForeground(Color.BLACK);
		 CourseSuffixLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		 
		 CourseNoLabel.setBounds(30,276,250,15);
		 CourseNoLabel.setForeground(Color.BLACK);
		 CourseNoLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		 
		 CourseTitleLabel.setBounds(30,304,250,15);
		 CourseTitleLabel.setForeground(Color.BLACK);
		 CourseTitleLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		 
		 CourseCreditLabel.setBounds(30,332,250,15);
		 CourseCreditLabel.setForeground(Color.BLACK);
		 CourseCreditLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		 
		 NumberOfCourseLabel.setBounds(30,407,250,15);
		 NumberOfCourseLabel.setForeground(Color.BLACK);
		 NumberOfCourseLabel.setFont(new Font ("SERRIF",Font.BOLD,13));
		 
		 for (String DN : DepartmentName)
			 Departments.addItem(DN);
		 Departments.setBounds(290,162,200,20);
		 Departments.setFont(new Font ("SERRIF",Font.BOLD,10));
		 Departments.setForeground(Color.BLACK);
		 Departments.setMaximumRowCount(4);
		 Departments.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK));
		 
		 
		 Years.addItem("1st");Years.addItem("2nd");Years.addItem("3rd");Years.addItem("4th");
		 Years.setBounds(290,190,200,20);
		 Years.setFont(new Font ("SERRIF",Font.BOLD,10));
		 Years.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK));
		 Years.setForeground(Color.BLACK);
		 Years.setMaximumRowCount(4);
		 ((JTextField)Years.getEditor().getEditorComponent()).setOpaque(false);
		 ((JTextField)Years.getEditor().getEditorComponent()).setForeground(Color.BLACK);
		 
		 Terms.addItem("1st");Terms.addItem("2nd");
		 Terms.setBounds(290,218,200,20);
		 Terms.setForeground(Color.BLACK);
		 Terms.setMaximumRowCount(2);
		 Terms.setFont(new Font ("SERRIF",Font.BOLD,10));
		 Terms.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK));
		
		 
		 for(int i=0;i<100;i++)
		    {
			 if(i<10) CourseSuffix.addItem(String.valueOf(0)+String.valueOf(i)); 
			 else CourseSuffix.addItem(String.valueOf(i)); 
		    }
		 CourseSuffix.setBounds(290,246,200,20);
		 CourseSuffix.setForeground(Color.BLACK);
		 CourseSuffix.setFont(new Font ("SERRIF",Font.BOLD,10));
		 CourseSuffix.setMaximumRowCount(5);
		 //CourseSuffix.setBackground(MyColor);
		 //CourseSuffix.setOpaque(false);
		 CourseSuffix.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK));
		 
		
		 CourseNo.setBounds(290,278,250,15);
		 CourseNo.setForeground(Color.BLACK);
		 CourseNo.setFont(new Font ("SERRIF",Font.BOLD,13));
		 CourseNo.setText("CSE-1100");
		 
		 CourseTitle.setBounds(290,302,200,20);
		 CourseTitle.setForeground(Color.BLACK);
		 CourseTitle.setFont(new Font ("SERRIF",Font.BOLD,10));
		 CourseTitle.setOpaque(false);
		 CourseTitle.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK));
		 CourseTitle.setHorizontalAlignment(SwingConstants.CENTER);
		 
		 CourseCredit.addItem(0.75);CourseCredit.addItem(2.00); CourseCredit.addItem(1.50); CourseCredit.addItem(3.00); CourseCredit.addItem(4.00);
		 CourseCredit.setBounds(290,326,200,20);
		 CourseCredit.setForeground(Color.BLACK);
		 CourseCredit.setFont(new Font ("SERRIF",Font.BOLD,10));
		 CourseCredit.setMaximumRowCount(4);
		 //CourseCredit.setBackground(MyColor);
		 //CourseCredit.setOpaque(false);
		 CourseCredit.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK));
		


		 for(int i=5;i<=20;i++)
		 NumberOfCourses.addItem(i);
		 NumberOfCourses.setBounds(290,405,200,20);
		 NumberOfCourses.setForeground(Color.BLACK);
		 NumberOfCourses.setFont(new Font ("SERRIF",Font.BOLD,10));
		 NumberOfCourses.setMaximumRowCount(4);
		 //NumberOfCourses.setBackground(MyColor);
		 //NumberOfCourses.setOpaque(false);
		 NumberOfCourses.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK));
		 
		 
		 EnrollButton.setForeground(Color.BLACK);
		 EnrollButton.setFont(new Font ("Rockwell Extra Bold",Font.BOLD,20));
		 EnrollButton.setContentAreaFilled(false);
		 EnrollButton.setBounds(193,473,250,32);
		 EnrollButton.setFocusPainted(true);
		 
		 add(RB2); add(RB1); add(RB3);  add(EnrollButton);
		 
		 add(BatchYearLabel); add(NumberOfStudentsLabel); add(DepartmentsLabel); add(YearLabel); add(TermLabel);
		 add(CourseSuffixLabel);add(CourseTitleLabel);add(CourseCreditLabel);add(CourseNoLabel); add(NumberOfCourseLabel);
		 
		 add(NumberOfStudents); add(BatchYear); add(Departments); add(Years); add(Terms);
		 add(CourseSuffix); add(CourseTitle); add(CourseCredit);
		 add(CourseNo); add(NumberOfCourses);
		 setOpaque(false);
		
		 
		 ItemHandler IH = new ItemHandler();
		 RB2.addItemListener(IH);
		 RB1.addItemListener(IH);
		 EnrollButton.addActionListener(IH); 
		 Departments.addItemListener(IH);
		 Years.addItemListener(IH);
		 Terms.addItemListener(IH);
		 CourseSuffix.addItemListener(IH); 
	}
	
    
	protected void paintComponent(Graphics g)
	{
	 super.paintComponent(g);
	 g.setColor(Color.BLACK);
     new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.DARK_GRAY).paintBorder(this, g, 192, 472, 252, 34);
     MyTitledBorder.paintBorder(this,g,80,28,460,93);
	 MyTitledBorder.paintBorder(this,g,80,132,460,230);
	 MySpecialTitledBorder.paintBorder(this,g,80,373,460,70);
     
	}
	
	public Component getEnrollmentPanelScroll() 
	{	
	  return this;
	}
   
	private class ItemHandler implements ItemListener,ActionListener
	{
		String name = new String("");
		int NumberOfCourse;
		int Batch;
		int Student;
		
		private void setToDefault()
		{
		  BatchYear.setSelectedIndex(0);
		  NumberOfStudents.setSelectedIndex(59);
	      Departments.setSelectedIndex(0); 
		  Years.setSelectedIndex(0); 
		  Terms.setSelectedIndex(0);
		  CourseSuffix.setSelectedIndex(0);
		  CourseNo.setText("CSE-1100");
		  CourseTitle.setText("");
		  CourseCredit.setSelectedIndex(0);
		  NumberOfCourses.setSelectedIndex(0);
		}
		
		
		public void itemStateChanged(ItemEvent IE) 
		   {
			name = Departments.getSelectedItem().toString()+"-"+Years.getSelectedItem().toString().charAt(0)+Terms.getSelectedItem().toString().charAt(0)+CourseSuffix.getSelectedItem().toString();
			CourseNo.setText(name);
		   }
		
		public void actionPerformed(ActionEvent AE) 
		   {
			 if(TempControll.ConnectionManagerObject.createConnection()) // securing a valid connection 
			   {
				 if(RB1.isSelected())
			       {
					 if(BatchYear.getSelectedIndex()>=0 && NumberOfStudents.getSelectedIndex()>=0)
				       {
						 Batch=  Integer.parseInt( BatchYear.getSelectedItem().toString()) ;
						 Student = Integer.parseInt( NumberOfStudents.getSelectedItem().toString());

						 if(TempControll.EnrollmentObject.batchExists(Batch)) //  // checks if the batch year already exists
						     JOptionPane.showMessageDialog(null,Integer.toString(Batch)+" already exists.","Error", JOptionPane.ERROR_MESSAGE);
						 else
							 TempControll.BatchEnrollmentDialogObject.createDialogBox(Batch,Student); 
				       }
					 else 
						 JOptionPane.showMessageDialog(null, "Please make sure that, data in Batch Year & Number Of Student are valid.","Invalid Data",JOptionPane.ERROR_MESSAGE);
			       }
			     else if(RB2.isSelected())
			       {
				     CourseNo.getText().toString();
				     String Title = CourseTitle.getText().toString();
				     float CC = Float.parseFloat(CourseCredit.getSelectedItem().toString());			
				     
				     if(CourseTitle.getText().equals(""))
				    	 JOptionPane.showMessageDialog(null, "Complete Course Title field." , "Error", JOptionPane.ERROR_MESSAGE);
				     else 
				       {
				    	if( TempControll.EnrollmentObject.enrollCourses(CourseNo.getText(),CourseTitle.getText(), Float.parseFloat(CourseCredit.getSelectedItem().toString()) ) )
				    	  JOptionPane.showMessageDialog(null, "' "+CourseNo.getText() +"' has been enrolled successfully.", "Success!!", JOptionPane.INFORMATION_MESSAGE);		
				       }
			        }
			     else if (RB3.isSelected())
			        {
				     NumberOfCourse = Integer.parseInt(NumberOfCourses.getSelectedItem().toString());
				     TempControll.MultipleCourseEnrollmentDialogObject.createDialogBox(NumberOfCourse); // for more than one course Enrollment
			        }
			     else JOptionPane.showMessageDialog(null, "Choose a Field from:\n       1. Batch Enrollment\n       2. Course Enrollment\n       3. Multiple Course Enrollment.", "Error", JOptionPane.ERROR_MESSAGE);
			   }
			 setToDefault();
			 TempControll.DataHandlePanelObject.updateAllCourseList(); 
		     TempControll.DataHandlePanelObject.updateAllBatchList(); 
		     TempControll.ResultHandlePanelObject.updateBatchList();
		     TempControll.ResultHandlePanelObject.updateCourseList();
		   }	
	}
}
	