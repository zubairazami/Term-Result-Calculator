package GUIPackage;

import java.awt.*;   
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Vector; 

import javax.swing.*;
import javax.swing.border.*;

import DB.DataTransfer;
import MainPackage.Controll;

public class ResultPanelStudent implements ActionListener {
	
	private int NumberOfCourses,Selected;
	private String Roll, ExamYear,Taken,Completed,GPA,Session;
	private JDialog RPS;
	private JLabel ColumnName,RollLabel,SessionLabel,TakenLabel1,CompletedLabel1,GPALabel1,TakenLabel2,CompletedLabel2,GPALabel2 ;
	private JButton DocButton ;
	private JPanel Panel ;
	private JScrollPane Scroll;
	private MyJObject[] My;
	private Controll TempControll;
	private JCheckBox CheckAll;
	private Vector<String> Credit = new Vector<String>();
	private Border ButtonBorder = new BevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.WHITE);
	private DecimalFormat TwoDecimal = new DecimalFormat ("#.##");
	private Vector<String> ToBeWritten = new Vector<String> ();
	
	
	
	public ResultPanelStudent(Controll x)
	{
		TempControll = x;
	}
	public void createDialogBox(String Roll,String ExamYear)
	{
		RPS = new JDialog(); 
		
		this.NumberOfCourses = DataTransfer.Courses.size();
		this.Roll = Roll;
		this.ExamYear = ExamYear;
		this.Session = setSession();
		final int Final = NumberOfCourses; 
		final int Height = (Final*40+270>600)?Final*40+270:600;
		
		Panel = new JPanel()
		{	
			protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/Icons/8.jpg")).getImage(),0,0,950,Height,null);
			    ButtonBorder.paintBorder(this, g, 284,84+Final*40+60+60,252,32);
			   }
		};
		
		Panel.setPreferredSize(new Dimension(600,NumberOfCourses*40+150+60+60));  // 50+(100*5+3*5)+50, 120+NumberOfCourses*30+(NumberOfCourses-1)*10+50+60          
		Panel.setLayout(null);
		
		RollLabel =new JLabel("Roll  :  "+this.Roll,SwingConstants.CENTER);
		RollLabel.setForeground(Color.WHITE);
		RollLabel.setFont(new Font("SERRIF",Font.BOLD,15));
		RollLabel.setBounds(112, 20, 615, 20);
		Panel.add(RollLabel);
		
		SessionLabel =new JLabel("Session  :  "+this.Session,SwingConstants.CENTER);
		SessionLabel.setForeground(Color.WHITE);
		SessionLabel.setFont(new Font("SERRIF",Font.BOLD,15));
		SessionLabel.setBounds(112, 40, 615, 20);
		Panel.add(SessionLabel);
		
		ColumnName =new JLabel("",SwingConstants.LEFT);
		ColumnName.setText("     COURSE NO.        TOTAL MARKS      GRADE POINT      LETTER GRADE        COURSE CREDIT                 EXAM-TYPE");
		ColumnName.setForeground(Color.WHITE);
		ColumnName.setFont(new Font("SERRIF",Font.BOLD,10));
		ColumnName.setBounds(112, 80, 635, 30);
		Panel.add(ColumnName);

		
		TakenLabel1 =new JLabel("Credit Hour Taken  :  ",SwingConstants.RIGHT);
		TakenLabel1.setForeground(Color.WHITE);
		TakenLabel1.setFont(new Font("SERRIF",Font.ITALIC,12));
		TakenLabel1.setBounds(112,85+NumberOfCourses*40+60,130,20);
		Panel.add(TakenLabel1);
	 
		TakenLabel2 =new JLabel("",SwingConstants.LEFT);
		TakenLabel2.setForeground(Color.WHITE);
		TakenLabel2.setFont(new Font("SERRIF",Font.ITALIC,12));
		TakenLabel2.setBounds(242,85+NumberOfCourses*40+60,50,20);
		Panel.add(TakenLabel2);
		
		CompletedLabel1 =new JLabel("Credit Hour Completed  :  ",SwingConstants.RIGHT);
		CompletedLabel1.setForeground(Color.WHITE);
		CompletedLabel1.setFont(new Font("SERRIF",Font.ITALIC,12)); //50,85+NumberOfCourses*40+60+20,150,20
		CompletedLabel1.setBounds(342,85+NumberOfCourses*40+60,150,20);
		Panel.add(CompletedLabel1);
		
		CompletedLabel2 =new JLabel("opps",SwingConstants.LEFT);
		CompletedLabel2.setForeground(Color.WHITE);
		CompletedLabel2.setFont(new Font("SERRIF",Font.ITALIC,12));
		CompletedLabel2.setBounds(492,85+NumberOfCourses*40+60,50,20);
		Panel.add(CompletedLabel2);

		GPALabel1 =new JLabel("GPA  :  ",SwingConstants.RIGHT);
		GPALabel1.setForeground(Color.WHITE);
		GPALabel1.setFont(new Font("SERRIF",Font.ITALIC,12));
 		GPALabel1.setBounds(552,85+NumberOfCourses*40+60,80,20);
		Panel.add(GPALabel1);
		
		GPALabel2 =new JLabel("36.25",SwingConstants.LEFT);
		GPALabel2.setForeground(Color.WHITE);
		GPALabel2.setFont(new Font("SERRIF",Font.ITALIC,12));
		GPALabel2.setBounds(632,85+NumberOfCourses*40+60,50,20);
		Panel.add(GPALabel2);
		
		DocButton = new JButton("Create Document");
		DocButton.setFont(new Font("SERRIF",Font.BOLD,15));
		DocButton.setBounds(285,85+NumberOfCourses*40+60+60,250,30); // 50+NumberOfCourses*30+(NumberOfCourses-1)*10+35
		DocButton.addActionListener(this);
		Panel.add(DocButton);
		
		CheckAll = new JCheckBox("Uncheck all");
		CheckAll.setForeground(Color.WHITE);
		CheckAll.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
		CheckAll.setOpaque(false);
		CheckAll.setSelected(true);
		CheckAll.addActionListener(this);
		
		setComponentsOnTheGrid();
		
		Scroll = new JScrollPane(Panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		RPS.add(Scroll);
		
		RPS.setModal(true);
		RPS.setTitle(" Result : Particular Student ");
		RPS.setResizable (false);
		RPS.setSize(840,565); 
		RPS.setLocation(250,100+(565-RPS.getHeight())/2); // setiing RPS dialogbox in the middle of MenuFrame
		RPS.setVisible(true);
	}
	
	
	private String  setSession()
	{
		
		String Session;
		int Max = 0;
		int Min = 5000;
		if(this.ExamYear.equalsIgnoreCase("All"))
		  {
			for(int i=0;i<DataTransfer.ExamYears.size();i++)
			   {
				int X = Integer.parseInt(DataTransfer.ExamYears.elementAt(i));
				if(X>Max)
					Max = X;
				if(X<Min)
					Min = X;
			   }	
			if(Max!=Min)
			  Session = "("+Integer.toString(Min-1)+"-"+Integer.toString(Min) + ") to (" + Integer.toString(Max-1)+"-"+Integer.toString(Max)+")";
			else
			  Session = Integer.toString(Min-1)+"-"+Integer.toString(Min);
		  }
		else
		  Session = Integer.toString(Integer.parseInt(this.ExamYear)-1)+"-"+Integer.toString(Integer.parseInt(this.ExamYear));
		   
		return Session;
	}
	
	private void setComponentsOnTheGrid()
	{
		JPanel GridPanel = new JPanel(new GridLayout(0,6,5,10));
		My = new MyJObject[NumberOfCourses+1];
		
		Credit.clear();
		Credit.addAll(TempControll.ResultObject.getCreditHours(DataTransfer.Courses));
		
		
		for(int i = 0;i<NumberOfCourses;i++)
		   {
			My[i] = new MyJObject(i);
			GridPanel.add(My[i].CourseCheckBox);
			GridPanel.add(My[i].TotalMarks);
			GridPanel.add(My[i].GradePoint);
			GridPanel.add(My[i].LetterGrade);
			GridPanel.add(My[i].CreditLabel);
			GridPanel.add(My[i].ExamTypeLabel);
		   }
		GridPanel.add(CheckAll);
		
		if(NumberOfCourses>1)
			GridPanel.setBounds(112, 120,615 , 40*NumberOfCourses-10); // 6*100+3*5,NumberOfCourses*30+(NumberOfCourses-1)*10
		else
			GridPanel.setBounds(112, 120,615 , 40*NumberOfCourses); // 6*100+3*5,NumberOfCourses*30+(NumberOfCourses-1)*10
			
		GridPanel.setOpaque(false);
		Panel.add(GridPanel);
		
		TakenLabel2.setText(getTakenCourseHour());
		CompletedLabel2.setText(getCompletedCreditHour());
		GPALabel2.setText(getCGPA());
	}
	
	private String getTakenCourseHour()
	{
		Vector<String> LocalCourses = new Vector<String>();
		String Temp;
		float Total = 0;
		
		for(int i = 0 ; i<NumberOfCourses;i++)
		   {
			Temp =  My[i].CourseCheckBox.getText();
			if(!LocalCourses.contains(Temp))
				 Total += Float.parseFloat(My[i].CreditLabel.getText()); 
			}
		Temp = Float.toString(Total);
		return Temp;   
	}
	
	private String getCompletedCreditHour()
	{
		String Temp;
		float Total = 0;
		
		for(int i = 0 ; i<NumberOfCourses;i++)
		   {
			Temp =  My[i].LetterGrade.getText();
			if(!Temp.equalsIgnoreCase("F"))
				 Total += Float.parseFloat(My[i].CreditLabel.getText()); 
			}
		Temp = Float.toString(Total);
		return Temp;   
	}
	
	private String getCGPA()
	{
		String Temp;
		float Credit, Total, Hour, TotalHour ;
		Credit = Hour = Total = TotalHour = 0 ;
		
		for(int i = 0 ; i<NumberOfCourses;i++)
		   {
			Temp =  My[i].LetterGrade.getText();
			if(!Temp.equalsIgnoreCase("F"))
			  {
				Hour   = Float.parseFloat(My[i].CreditLabel.getText()) ;
				Credit = Float.parseFloat(My[i].GradePoint.getText());
				
				Total     +=  Credit*Hour;
				TotalHour += Hour ;
				
			  }
			}
		if(Total==0||TotalHour==0)
			Temp = "0.0";
		else 
			Temp = Float.toString(Float.valueOf(TwoDecimal.format(Total/TotalHour)));
			
		return Temp;   
	}
	
	private boolean gatherDataForDocument()
	{
		boolean Flag = false;
		this.Selected = 0;
		DataTransfer.refreshAll();
		for(int i=0;i<this.NumberOfCourses;i++)
			if(My[i].CourseCheckBox.isSelected())
			{
				DataTransfer.Serial.add(Integer.toString(i+1));
				DataTransfer.Courses.add(My[i].CourseCheckBox.getText());
				DataTransfer.Total.add(My[i].TotalMarks.getText());
				DataTransfer.GradePoint.add(My[i].GradePoint.getText());
				DataTransfer.LetterGrade.add(My[i].LetterGrade.getText());
				DataTransfer.Credits.add(My[i].CreditLabel.getText());
				DataTransfer.ExamType.add(My[i].ExamTypeLabel.getText());
				this.Selected++;
			    Flag = true; 
			}
		
		this.Taken = TakenLabel1.getText()+TakenLabel2.getText();
		this.Completed = CompletedLabel1.getText()+CompletedLabel2.getText();
		this.GPA = GPALabel1.getText()+GPALabel2.getText();
		
		return Flag;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent AE) 
	{
	    if(AE.getSource()==CheckAll)
	      {
	    	boolean Selection = CheckAll.isSelected();
	    	if(Selection)
	     	  CheckAll.setText("Uncheck all");
	    	else
	    	  CheckAll.setText("Check all");
	    	for(int i=0;i<NumberOfCourses;i++)
	    		My[i].CourseCheckBox.setSelected(Selection);
	      }
	    
		if(AE.getSource()==DocButton)
		   if(TempControll.ConnectionManagerObject.createConnection())
		     {
			   
			   new File(System.getProperty("user.home")+"/TermResultCalculator/StudentDocs/").mkdirs();
			   if(gatherDataForDocument())
			     {
			      if(TempControll.StudentPdfObject.createPDF(this.Roll, this.Session,this.Selected , this.Taken, this.Completed, this.GPA) )
			        JOptionPane.showMessageDialog(RPS, "Report created successfully at "+System.getProperty("user.home")+"/TermResultCalculator/StudentDocs/", "Success", JOptionPane.INFORMATION_MESSAGE);
			      else 
			    	  JOptionPane.showMessageDialog(RPS, "Error while creating report.", "Error", JOptionPane.ERROR_MESSAGE);	  
			     }
			   else 
				   JOptionPane.showMessageDialog(RPS, "At least one course has to be selected.","Error : No Selection", JOptionPane.ERROR_MESSAGE);
			   
		     }
	}
	
	
	private class MyJObject 
	{
		JLabel TotalMarks,GradePoint,LetterGrade,CreditLabel,ExamTypeLabel;
		JCheckBox CourseCheckBox;
		
		float Temp;
		
		public MyJObject(int i)
		{
			CourseCheckBox = new JCheckBox(DataTransfer.Courses.elementAt(i));
			CourseCheckBox.setForeground(Color.WHITE);
			CourseCheckBox.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,13));
			CourseCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
			CourseCheckBox.setOpaque(false);
			CourseCheckBox.setSelected(true);
			
			TotalMarks = new JLabel(Float.toString(Float.valueOf(TwoDecimal.format(Float.parseFloat(DataTransfer.Total.elementAt(i))))),SwingConstants.CENTER);
			TotalMarks.setForeground(Color.WHITE);
			TotalMarks.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,13));
			
			GradePoint = new JLabel(DataTransfer.GradePoint.elementAt(i),SwingConstants.LEFT);
			GradePoint.setForeground(Color.WHITE);
			GradePoint.setFont(new Font("SERRIF",Font.ITALIC,13));
			
			LetterGrade = new  JLabel(DataTransfer.LetterGrade.elementAt(i),SwingConstants.LEFT);
			LetterGrade.setForeground(Color.WHITE);
			LetterGrade.setFont(new Font("SERRIF",Font.PLAIN,13));
			
			CreditLabel = new  JLabel(Credit.elementAt(i),SwingConstants.LEFT);
			CreditLabel.setForeground(Color.WHITE);
			CreditLabel.setFont(new Font("SERRIF",Font.PLAIN,13));
			
			ExamTypeLabel = new  JLabel(DataTransfer.ExamType.elementAt(i),SwingConstants.LEFT);
			ExamTypeLabel.setForeground(Color.WHITE);
			ExamTypeLabel.setFont(new Font("SERRIF",Font.PLAIN,12));
		}
	}

}
