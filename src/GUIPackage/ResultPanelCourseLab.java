package GUIPackage;

import java.awt.*;    
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.*;

import DB.DataTransfer;
import MainPackage.Controll;

public class ResultPanelCourseLab implements ActionListener {
	
	private int NumberOfStudents,Selected,Effected;
	private String Course, ExamYear;
	private JDialog RPCL;
	private JLabel ColumnName,CourseLabel,ExamYearLabel;
	private JButton DocButton ;
	private JPanel Panel ;
	private JScrollPane Scroll;
	private MyJObject[] My;
	private Controll TempControll;
	private JCheckBox CheckAll;
	private Border ButtonBorder = new BevelBorder(BevelBorder.LOWERED,Color.DARK_GRAY,Color.WHITE);
	private DecimalFormat TwoDecimal = new DecimalFormat ("#.##");
	
	
	public ResultPanelCourseLab(Controll x)
	{
		TempControll = x;
	}
	public void createDialogBox(String Course,String ExamYear)
	{
		RPCL = new JDialog(); 
		
		this.NumberOfStudents = DataTransfer.Roll.size();
		this.Course = Course;
		this.ExamYear = ExamYear;
		final int Final = NumberOfStudents; 
		final int Height = (Final*40+270>600)?Final*40+270:600;
		
		Panel = new JPanel()
		{	
			protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/Icons/8.jpg")).getImage(),0,0,900,Height,null);
			    ButtonBorder.paintBorder(this, g, 334,84+Final*40+60,252,32);
			   }
		};
		
		Panel.setPreferredSize(new Dimension(585,NumberOfStudents*40+150+60));  // 50+(100*5+3*5)+50, 120+NumberOfStudents*30+(NumberOfStudents-1)*10+50+60          
		Panel.setLayout(null);
		
		CourseLabel =new JLabel("Course  :  "+this.Course,SwingConstants.CENTER);
		CourseLabel.setForeground(Color.WHITE);
		CourseLabel.setFont(new Font("SERRIF",Font.BOLD,15));
		CourseLabel.setBounds(163, 20, 515, 20);
		Panel.add(CourseLabel);
		
		ExamYearLabel =new JLabel("Examination Year  :  "+this.ExamYear,SwingConstants.CENTER);
		ExamYearLabel.setForeground(Color.WHITE);
		ExamYearLabel.setFont(new Font("SERRIF",Font.BOLD,15));
		ExamYearLabel.setBounds(163, 40, 515, 20);
		Panel.add(ExamYearLabel);
		
		ColumnName =new JLabel("",SwingConstants.LEFT);
		ColumnName.setText("     COURSE NO.        TOTAL MARKS      GRADE POINT      LETTER GRADE                  EXAM-TYPE");
		ColumnName.setForeground(Color.WHITE);
		ColumnName.setFont(new Font("SERRIF",Font.BOLD,10));
		ColumnName.setBounds(163, 100, 535, 30);
		Panel.add(ColumnName);

		
		DocButton = new JButton("Create Document");
		DocButton.setFont(new Font("SERRIF",Font.BOLD,15));
		DocButton.setBounds(335,85+NumberOfStudents*40+60,250,30); // 50+NumberOfStudents*30+(NumberOfStudents-1)*10+35
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
		RPCL.add(Scroll);
		
		RPCL.setModal(true);
		RPCL.setTitle(" Result : Particular Course ");
		RPCL.setResizable (false);
		RPCL.setSize(840,565);
		RPCL.setLocation(250,100+(565-RPCL.getHeight())/2); // setiing RPCL dialogbox in the middle of MenuFrame
		RPCL.setVisible(true);
	}
	
	private void setComponentsOnTheGrid()
	{
		JPanel GridPanel = new JPanel(new GridLayout(0,5,5,10));
		My = new MyJObject[NumberOfStudents+1];
		
		
		for(int i = 0;i<NumberOfStudents;i++)
		   {
			My[i] = new MyJObject(i);
			GridPanel.add(My[i].RollCheckBox);
			GridPanel.add(My[i].TotalMarks);
			GridPanel.add(My[i].GradePoint);
			GridPanel.add(My[i].LetterGrade);
			GridPanel.add(My[i].ExamTypeLabel);
		   }
		GridPanel.add(CheckAll);
		
		if(NumberOfStudents>1)
			GridPanel.setBounds(163, 120,515 , 40*NumberOfStudents-10); // 5*100+3*5,NumberOfStudents*30+(NumberOfStudents-1)*10
		else
			GridPanel.setBounds(163, 120,515 , 40*NumberOfStudents); // 5*100+3*5,NumberOfStudents*30+(NumberOfStudents-1)*10
			
		GridPanel.setOpaque(false);
		Panel.add(GridPanel);
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
	    	for(int i=0;i<NumberOfStudents;i++)
	    		My[i].RollCheckBox.setSelected(Selection);
	      }
	    
		if(AE.getSource()==DocButton)
		   if(TempControll.ConnectionManagerObject.createConnection())
		     {
				new File(System.getProperty("user.home")+"/TermResultCalculator/CourseDocs/").mkdirs();  // creating directories
				gatherData();
				if(this.Selected>0)
					{
					  if(TempControll.CoursePdfLabObject.createPDF(this.Course, this.ExamYear, this.Selected))
						JOptionPane.showMessageDialog(RPCL, "Report has been created at "+System.getProperty("user.home")+"/TermResultCalculator/CourseDocs/", "Success", JOptionPane.INFORMATION_MESSAGE) ;
					  else 
						JOptionPane.showMessageDialog(RPCL, "Error occured while creating Report", "Error", JOptionPane.ERROR_MESSAGE);
					}
				else 
				   JOptionPane.showMessageDialog(RPCL, "No Item Selected", "Error",JOptionPane.ERROR_MESSAGE);
		     }
	}
		
	private void gatherData()
	{
		this.Selected = 0;
		DataTransfer.refreshAll();
		for(int i=0;i<this.NumberOfStudents;i++)
		    if(My[i].RollCheckBox.isSelected())
			  {
				DataTransfer.Serial.add(Integer.toString(this.Selected+1));
				DataTransfer.Roll.add(My[i].RollCheckBox.getText());
				DataTransfer.Total.add(My[i].TotalMarks.getText());
				DataTransfer.LetterGrade.add(My[i].LetterGrade.getText());
				DataTransfer.GradePoint.add(My[i].GradePoint.getText());
			    (this.Selected)++;
			  }
		DataTransfer.Name.addAll(TempControll.ResultObject.getNameofRolls(DataTransfer.Roll));
		TempControll.DataEraseEditObject.getCourseNameAndCourseCredit(this.Course);
	}
	
	private class MyJObject 
	{
		JLabel TotalMarks,GradePoint,LetterGrade,ExamTypeLabel;
		JCheckBox RollCheckBox;
		
		
		public MyJObject(int i)
		{
			RollCheckBox = new JCheckBox(DataTransfer.Roll.elementAt(i));
			RollCheckBox.setForeground(Color.WHITE);
			RollCheckBox.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
			RollCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
			RollCheckBox.setOpaque(false);
			RollCheckBox.setSelected(true);
			
			TotalMarks = new JLabel(Float.toString(Float.valueOf(TwoDecimal.format(Float.parseFloat(DataTransfer.Total.elementAt(i))))),SwingConstants.CENTER);
			TotalMarks.setForeground(Color.WHITE);
			TotalMarks.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,15));
			
			GradePoint = new JLabel(DataTransfer.GradePoint.elementAt(i),SwingConstants.LEFT);
			GradePoint.setForeground(Color.WHITE);
			GradePoint.setFont(new Font("SERRIF",Font.ITALIC,15));
			
			LetterGrade = new  JLabel(DataTransfer.LetterGrade.elementAt(i),SwingConstants.LEFT);
			LetterGrade.setForeground(Color.WHITE);
			LetterGrade.setFont(new Font("SERRIF",Font.PLAIN,15));
			
			ExamTypeLabel = new  JLabel(DataTransfer.ExamType.elementAt(i),SwingConstants.LEFT);
			ExamTypeLabel.setForeground(Color.WHITE);
			ExamTypeLabel.setFont(new Font("SERRIF",Font.PLAIN,12));
		}
	}

}
