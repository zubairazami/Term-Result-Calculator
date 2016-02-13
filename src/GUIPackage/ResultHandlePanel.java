package GUIPackage;

import DB.DataTransfer;   
import MainPackage.*;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;


public class ResultHandlePanel extends JPanel implements ActionListener
{

	private JButton ProceedButton;
	private JRadioButton StudentRadioButton,TermRadioButton,CourseRadioButton;
	private JLabel ExamYearLabel1,RollLabel,BatchLabel1,CourseLabel,ExamYearLabel3, ExamYearLabel2,YearTermLabel;;
	private JComboBox MyComboBox[] = new JComboBox[8];
	private Color MyColor = new Color(58,134,209);
	private Border MyBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.BLACK),"                               ",TitledBorder.CENTER,TitledBorder.TOP);
	private ButtonGroup DataGroup;
	private Controll TempControll; 
	private Vector<String> Courses, Batches,Rolls, ExamYears;
	private String EmptySpace;
	private Border ComboBorder = new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK);
	
	public ResultHandlePanel(Controll x)
	{
		TempControll  = x;
		Courses = new Vector<String>();
		Batches = new Vector<String>();	
		Rolls = new Vector<String>();
		ExamYears = new Vector<String>();
	}
	
	public JPanel getResultHandlePanel()
	{
		
		UIManager.put("ComboBox.disabledBackground", Color.WHITE);  
		UIManager.put("ComboBox.disabledForeground", Color.LIGHT_GRAY);    
		
		DataGroup = new ButtonGroup();
		setLayout(null);
		
		StudentRadioButton = new JRadioButton("Student");
		StudentRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
	    StudentRadioButton.setForeground(Color.BLACK);
		StudentRadioButton.setFont(new Font("SERRIF", Font.BOLD, 17));
		StudentRadioButton.setBounds(256, 28, 109, 23);
		StudentRadioButton.setContentAreaFilled(false);
		StudentRadioButton.setOpaque(false);
		StudentRadioButton.addActionListener(this);
		DataGroup.add(StudentRadioButton);
		add(StudentRadioButton);
		
		CourseRadioButton = new JRadioButton("Course");
		CourseRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		CourseRadioButton.setForeground(Color.BLACK);
		CourseRadioButton.setFont(new Font("SERRIF", Font.BOLD, 17));
		CourseRadioButton.setBounds(256, 171, 109, 23);
		CourseRadioButton.setContentAreaFilled(false);
		CourseRadioButton.setOpaque(false);
		CourseRadioButton.addActionListener(this);
		DataGroup.add(CourseRadioButton);
		add(CourseRadioButton);
		
		TermRadioButton = new JRadioButton("Term");
		TermRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		TermRadioButton.setForeground(Color.BLACK);
		TermRadioButton.setFont(new Font("SERRIF", Font.BOLD, 17));
		TermRadioButton.setBounds(256, 312, 109, 23);
		TermRadioButton.setContentAreaFilled(false);
		TermRadioButton.setOpaque(false);
		TermRadioButton.addActionListener(this);
		DataGroup.add(TermRadioButton);
		add(TermRadioButton);

//  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
		
		BatchLabel1 = new JLabel("Batch  :", SwingConstants.RIGHT);
		BatchLabel1.setForeground(Color.BLACK);
		BatchLabel1.setFont(new Font("Tahoma", Font.BOLD, 11));
		BatchLabel1.setBounds(126, 57, 91, 20);	
		BatchLabel1.setEnabled(false);
		add(BatchLabel1);
		
	    RollLabel = new JLabel("Roll  :",SwingConstants.RIGHT);
	    RollLabel.setForeground(Color.BLACK);
	    RollLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
	    RollLabel.setBounds(126, 89, 91, 20);
	    RollLabel.setEnabled(false);
	    add(RollLabel);
		
		ExamYearLabel1 = new JLabel("Exam Year  :",SwingConstants.RIGHT);   
		ExamYearLabel1.setForeground(Color.BLACK);
		ExamYearLabel1.setFont(new Font("Tahoma", Font.BOLD, 11));
		ExamYearLabel1.setBounds(126, 120, 91, 20);
		ExamYearLabel1.setEnabled(false);
		add(ExamYearLabel1);

//  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
			
		CourseLabel = new JLabel("Course  :",SwingConstants.RIGHT);
		CourseLabel.setForeground(Color.BLACK);
		CourseLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		CourseLabel.setBounds(98, 206, 119, 23);
		CourseLabel.setOpaque(false);
		CourseLabel.setEnabled(false);
		add(CourseLabel);
			
		ExamYearLabel2 = new JLabel("Exam Year  :",SwingConstants.RIGHT);
		ExamYearLabel2.setForeground(Color.BLACK);
		ExamYearLabel2.setFont(new Font("Tahoma", Font.BOLD, 11));
		ExamYearLabel2.setBounds(98, 236, 119, 23);
		ExamYearLabel2.setOpaque(false);
		ExamYearLabel2.setEnabled(false);
		add(ExamYearLabel2);

//  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
			
//		BatchLabel2 =  new JLabel("Batch  :",SwingConstants.RIGHT);
//		BatchLabel2.setForeground(Color.BLACK);
//		BatchLabel2.setFont(new Font("Tahoma", Font.BOLD, 11));
//		BatchLabel2.setBounds(98, 342, 119, 23);  
//		BatchLabel2.setOpaque(false);
//		BatchLabel2.setEnabled(false);
//		add(BatchLabel2);
		
		YearTermLabel =  new JLabel("Term  :",SwingConstants.RIGHT);
		YearTermLabel.setForeground(Color.BLACK);
		YearTermLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		YearTermLabel.setBounds(98, 342, 119, 23);
		YearTermLabel.setOpaque(false);
		YearTermLabel.setEnabled(false);
		add(YearTermLabel);
		
		ExamYearLabel3 =  new JLabel("Exam Year  :",SwingConstants.RIGHT);
		ExamYearLabel3.setForeground(Color.BLACK);
		ExamYearLabel3.setFont(new Font("Tahoma", Font.BOLD, 11));
		ExamYearLabel3.setBounds(98, 373, 119, 23);   
		ExamYearLabel3.setOpaque(false);
		ExamYearLabel3.setEnabled(false);
		add(ExamYearLabel3);

//  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
				
	    for(int i =0;i<8;i++) 
	    	{
	    		if(i==5) continue;
	    	  MyComboBox[i] = new JComboBox();
	    	}
			
		MyComboBox[0].setBounds(250,  58, 200, 20);
		MyComboBox[1].setBounds(250,  90, 200, 20);
		MyComboBox[2].setBounds(250, 121, 200, 20);
		MyComboBox[3].setBounds(250, 209, 200, 20);
		MyComboBox[4].setBounds(250, 239, 200, 20);
		//MyComboBox[5].setBounds(250, 345, 200, 20);
		MyComboBox[6].setBounds(250, 345, 200, 20);
		MyComboBox[7].setBounds(250, 376, 200, 20);//.setBounds(250, 407, 200, 20);  
		
		MyComboBox[0].addActionListener(this);
		MyComboBox[1].addActionListener(this);
		MyComboBox[3].addActionListener(this);
		//MyComboBox[5].addActionListener(this);
		
		
		
		for(int i =0;i<8;i++) 
		   {
			if(i==5) continue;
			MyComboBox[i].setForeground(Color.BLACK);
		    //MyComboBox[i].setBackground(MyColor);
		    //MyComboBox[i].setOpaque(false);
		    MyComboBox[i].setMaximumRowCount(5);
		    MyComboBox[i].setBorder(ComboBorder);
		    MyComboBox[i].setFont(new Font ("SERRIF",Font.BOLD,10));
		    MyComboBox[i].setEnabled(false);
		    add(MyComboBox[i]);
		    }		   
		 
		ProceedButton = new JButton();
		ProceedButton.setForeground(Color.BLACK);
		ProceedButton.setText("Proceed");
		ProceedButton.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 15));
		ProceedButton.setBounds(219, 463, 199, 39);
		ProceedButton.setBackground(MyColor);
		ProceedButton.setContentAreaFilled(false);
		ProceedButton.setOpaque(false);
		ProceedButton.addActionListener(this);
		add(ProceedButton);
		setOpaque(false);
		return this;
	}	
    protected void paintComponent(Graphics g)
	{
	 super.paintComponent(g);
	 g.setColor(Color.BLACK);
	 //g.drawImage(new ImageIcon(getClass().getResource("/Icons/5.jpg")).getImage(), 0, 0, 620,540, null);
	 MyBorder.paintBorder(this,g,110,32,400,125);
	 MyBorder.paintBorder(this,g,110,175,400,112);
	 MyBorder.paintBorder(this,g,110,316,400,112);
	 new BevelBorder(BevelBorder.RAISED,Color.DARK_GRAY,Color.BLACK).paintBorder(this, g, 217, 461, 203, 43);//268, 467, 103, 33);
	}

	@Override
	public void actionPerformed(ActionEvent AE) 
	{
		
		if(TempControll.ConnectionManagerObject.createConnection())
		  {
			if(AE.getSource()==StudentRadioButton)
			  {
				studentIsSelected(true);
				courseIsSelected(false);
				termtIsSelected(false);
			  }
			
			if(AE.getSource()==CourseRadioButton)
			  {
				studentIsSelected(false);
				courseIsSelected(true);
				termtIsSelected(false);
			  }
			
			if(AE.getSource()==TermRadioButton)
			  {
				studentIsSelected(false);
				courseIsSelected(false);
				termtIsSelected(true);
				if(MyComboBox[6].getItemCount()!=8)
				  {
					MyComboBox[6].removeAllItems();
					MyComboBox[6].addItem("1st Year 1st Semester");
					MyComboBox[6].addItem("1st Year 2nd Semester");
					MyComboBox[6].addItem("2nd Year 1st Semester");
					MyComboBox[6].addItem("2nd Year 2nd Semester");
					MyComboBox[6].addItem("3rd Year 1st Semester");
					MyComboBox[6].addItem("3rd Year 2nd Semester");
					MyComboBox[6].addItem("4th Year 1st Semester");
					MyComboBox[6].addItem("4th Year 2nd Semester");
				  }
				MyComboBox[7].removeAllItems();
				ExamYears.clear();
				ExamYears.addAll(TempControll.ResultObject.getExistingExamYear(" ", 1));  // ??
				if(ExamYears.size()>0)
				  {
					int ExamYearNumbers = ExamYears.size();
					for(int i=0 ; i<ExamYearNumbers; i++)
					      MyComboBox[7].addItem(ExamYears.elementAt(i));
				  }
			  }
			
			if(AE.getSource()==MyComboBox[0] && MyComboBox[0].getSelectedIndex()>=0)
			  {
				MyComboBox[1].removeAllItems();
				Rolls.clear();
				Rolls.addAll(TempControll.ResultObject.getRollsofBatch(MyComboBox[0].getSelectedItem().toString()));
	
				int RollsSize = Rolls.size();
				if(RollsSize>0)
					for(int i=0 ; i<RollsSize; i++)
						MyComboBox[1].addItem(Rolls.elementAt(i));
			  }
			
			if(AE.getSource()==MyComboBox[1] && MyComboBox[1].getSelectedIndex()>=0)
			  {	
				
				MyComboBox[2].removeAllItems();
				ExamYears.clear();
				ExamYears.addAll(TempControll.ResultObject.getExistingExamYear(MyComboBox[1].getSelectedItem().toString(), 2));  // ??
				if(ExamYears.size()>0)
				  {	
					int ExamYearNumbers = ExamYears.size();
					for(int i=0 ; i<ExamYearNumbers; i++)
						MyComboBox[2].addItem(ExamYears.elementAt(i));
					MyComboBox[2].addItem("All");
				  }
			  }
			
			if(AE.getSource()==MyComboBox[3] && MyComboBox[3].getSelectedIndex()>=0)
			  {
				MyComboBox[4].removeAllItems();
				ExamYears.clear();
				ExamYears.addAll(TempControll.ResultObject.getExistingExamYear(MyComboBox[3].getSelectedItem().toString(), 3));  
				if(ExamYears.size()>0)
				  {
					int ExamYearNumbers = ExamYears.size();
					for(int i=0 ; i<ExamYearNumbers; i++)
						MyComboBox[4].addItem(ExamYears.elementAt(i));
				  }
			  }
			
//			if(AE.getSource()==MyComboBox[5] && MyComboBox[5].getSelectedIndex()>=0)
//			  {
//				MyComboBox[7].removeAllItems();
//				ExamYears.clear();
//				ExamYears.addAll(TempControll.ResultObject.getExistingExamYear(MyComboBox[5].getSelectedItem().toString(), 1));  // ??
//				if(ExamYears.size()>0)
//				  {
//					int ExamYearNumbers = ExamYears.size();
//					for(int i=0 ; i<ExamYearNumbers; i++)
//					      MyComboBox[7].addItem(ExamYears.elementAt(i));
//				  }
//			  }
			
			if(AE.getSource()==ProceedButton)
			 {
			   
			   if(StudentRadioButton.isSelected())
				  actionStudent();
			   else if(CourseRadioButton.isSelected())
			      actionCourse();
			   else if(TermRadioButton.isSelected())
			      actionTerm();
			   else
			     JOptionPane.showMessageDialog(null, "Please, select one from the following fields:"+EmptySpace+"1. Student"+EmptySpace+"2. Course"+EmptySpace+"3. Term","Select One", JOptionPane.ERROR_MESSAGE);
			 }
		  }
	}

	private void  actionStudent()
	{
		if(MyComboBox[1].getSelectedIndex()>=0 && MyComboBox[2].getSelectedIndex()>=0)
		  {
			String Student_Roll = MyComboBox[1].getSelectedItem().toString();
			String Student_ExamYear = MyComboBox[2].getSelectedItem().toString();
			if(TempControll.ResultObject.hasValidStudentInformation(Student_Roll,Student_ExamYear))
			   TempControll.ResultPanelStudentObject.createDialogBox(Student_Roll,Student_ExamYear);
			else
				JOptionPane.showMessageDialog(null, "Can't find information for :\nRoll              :    "+Student_Roll+"\nExamYear  :    "+Student_ExamYear+"\nPlease check if data really exists or not.", "Unavailable", JOptionPane.ERROR_MESSAGE);
			}
	}
	
	private void actionCourse()
	{
		if(MyComboBox[3].getSelectedIndex()>=0 && MyComboBox[4].getSelectedIndex()>=0)
		  {
			String Course_No = MyComboBox[3].getSelectedItem().toString();
			String Course_ExamYear = MyComboBox[4].getSelectedItem().toString();
			
			int CourseDeterminer = Integer.parseInt(Course_No.substring(Course_No.length()-1, Course_No.length()))%2;
			
			if(CourseDeterminer==0)
			 {
				if(TempControll.ResultObject.hasValidCourseLabInformation(Course_No,Course_ExamYear))
					TempControll.ResultPanelCourseLabObject.createDialogBox(Course_No,Course_ExamYear);
				else
					JOptionPane.showMessageDialog(null, "Can't find information for :\nCourse            :    "+Course_No+"\nExamYear  :    "+Course_ExamYear+"\nPlease check if data really exists or not.", "Unavailable", JOptionPane.ERROR_MESSAGE);
			 }
			if(CourseDeterminer==1)
			 {
				if(TempControll.ResultObject.hasValidCourseTheoryInformation(Course_No,Course_ExamYear))
					TempControll.ResultPanelCourseTheoryObject.createDialogBox(Course_No,Course_ExamYear);
				else
					JOptionPane.showMessageDialog(null, "Can't find information for :\nCourse            :    "+Course_No+"\nExamYear  :    "+Course_ExamYear+"\nPlease check if data really exists or not.", "Unavailable", JOptionPane.ERROR_MESSAGE);
			 }
		  }	
	}
	
	private void actionTerm()
	{
		if(MyComboBox[6].getSelectedIndex()>=0 && MyComboBox[7].getSelectedIndex()>=0)
		  {
			//String Batch = MyComboBox[5].getSelectedItem().toString();
			String Term = MyComboBox[6].getSelectedItem().toString();
			String Exam = MyComboBox[7].getSelectedItem().toString();
		    
			if(TempControll.ResultObject.hasValidTermInformation(Term, Exam))
				TempControll.ResultPanelTermObject.createDialogBox(Term, Exam, DataTransfer.Courses, DataTransfer.Roll);
			else
				JOptionPane.showMessageDialog(null, "Can't find information for :\nTerm           :    "+Term+"\nExamYear  :    "+Exam+"\nPlease check if data really exists or not.", "Unavailable", JOptionPane.ERROR_MESSAGE);
		  }
	}
	
	private void studentIsSelected(boolean Flag)
	{
		for(int i = 0; i<=2;i++)
			MyComboBox[i].setEnabled(Flag);
		BatchLabel1.setEnabled(Flag);
		RollLabel.setEnabled(Flag);
		ExamYearLabel1.setEnabled(Flag);
		if(Flag)
		  updateBatchList();
	}
	
	private void courseIsSelected(boolean Flag)
	{
		MyComboBox[3].setEnabled(Flag);
		MyComboBox[4].setEnabled(Flag);
		CourseLabel.setEnabled(Flag);
		ExamYearLabel2.setEnabled(Flag);
		if(Flag)
		  updateCourseList();
	}
	
	private void termtIsSelected(boolean Flag)
	{
		for(int i = 6; i<=7; i++)
			MyComboBox[i].setEnabled(Flag);
		//BatchLabel2.setEnabled(Flag);
		YearTermLabel.setEnabled(Flag);
		ExamYearLabel3.setEnabled(Flag);
		if(Flag)
			  updateBatchList();
	}
	
	public void updateCourseList()
	{
		Courses.clear();
		Courses.addAll(TempControll.DataEntryObject.getEnrolledCourses());
		MyComboBox[3].removeAllItems();
        for(String x : Courses)
				MyComboBox[3].addItem(x) ;   
	}
	
	public void updateBatchList()
	{
		Batches.clear();
		Batches.addAll(TempControll.DataEntryObject.getEnrolledBatches());
		
		MyComboBox[0].removeAllItems();
		//MyComboBox[5].removeAllItems();
		
		for(String x : Batches)
		   {
			MyComboBox[0].addItem(x);
			//MyComboBox[5].addItem(x);
		   }
	}
}