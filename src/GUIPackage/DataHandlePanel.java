package GUIPackage;

import javax.swing.*;        
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import DB.DataTransfer;
import MainPackage.Controll;

import java.awt.Font;
import java.awt.Color; 
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class DataHandlePanel extends JPanel implements ActionListener
{
	private JButton ProceedButton;
	private JRadioButton DataEntryRadioButton,DataEditRadioButton,DataEraseRadioButton,CourseNoRadioButton1,CourseNoRadioButton2,ExaminationRadioButton1,ExaminationRadioButton2, BatchRadioButton1,BatchRadioButton2;
	private JLabel CourseLabel,ExamYearLabel,BatchLabel,CourseFilterLabel;
	private JComboBox MyComboBox[] = new JComboBox[11];
	private Color MyColor = new Color(58,134,209);
	private Border MyBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.BLACK),"                                     ",TitledBorder.CENTER,TitledBorder.TOP);
	private Border MyFilterBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.BLACK),"",TitledBorder.CENTER,TitledBorder.TOP);
	private ButtonGroup DataGroup,DataEraseGroup,DataEditGroup ;
	private Vector<String> Courses = new Vector<String>();
	private Vector<String> Batches = new Vector<String>();
	private Vector<String> AsPerCourse = new Vector<String>();
	private Controll TempControll;
	private JComboBox CourseFilterTermComboBox,CourseFilterYearComboBox;
	private Border ComboBorder = new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.BLACK);
	
	public DataHandlePanel(Controll x) 
	{
		TempControll = x;		
	}
	
	public JPanel getDataHandlePanel()
	{
		
		UIManager.put("ComboBox.disabledBackground", Color.WHITE);  
		UIManager.put("ComboBox.disabledForeground", Color.LIGHT_GRAY);   
		
		DataGroup = new ButtonGroup();
		DataEraseGroup = new ButtonGroup();
		DataEditGroup = new ButtonGroup();
		
		setLayout(null);
		
	    DataEntryRadioButton = new JRadioButton("Data Enrty");
	    DataEntryRadioButton.setForeground(Color.BLACK);
		DataEntryRadioButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		DataEntryRadioButton.setBounds(256, 48, 109, 23);
		DataEntryRadioButton.setContentAreaFilled(false);
		DataEntryRadioButton.setOpaque(false);
		DataGroup.add(DataEntryRadioButton);
		DataEntryRadioButton.addActionListener(this);
		add(DataEntryRadioButton);
		
		DataEraseRadioButton = new JRadioButton("Data Erase");
		DataEraseRadioButton.setForeground(Color.BLACK);
		DataEraseRadioButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		DataEraseRadioButton.setBounds(256, 176, 109, 23);
		DataEraseRadioButton.setContentAreaFilled(false);
		DataEraseRadioButton.setOpaque(false);
		DataEraseRadioButton.addActionListener(this);
		DataGroup.add(DataEraseRadioButton);
		add(DataEraseRadioButton);
		
		DataEditRadioButton = new JRadioButton("   Data Edit");
		DataEditRadioButton.setForeground(Color.BLACK);
		DataEditRadioButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		DataEditRadioButton.setBounds(256, 332, 109, 23);
		DataEditRadioButton.setContentAreaFilled(false);
		DataEditRadioButton.setOpaque(false);
		DataEditRadioButton.addActionListener(this);
		DataGroup.add(DataEditRadioButton);
		add(DataEditRadioButton);
		
		
		BatchRadioButton1 = new JRadioButton("      Batch         :");
		BatchRadioButton1.setForeground(Color.BLACK);
		BatchRadioButton1.setFont(new Font("Tahoma", Font.BOLD, 11));
		BatchRadioButton1.setBounds(130, 235, 119, 23);
		BatchRadioButton1.setContentAreaFilled(false);
		BatchRadioButton1.setOpaque(false);
		BatchRadioButton1.setEnabled(false);
		DataEraseGroup.add(BatchRadioButton1);
		add(BatchRadioButton1);
		
		
		ExaminationRadioButton1 = new JRadioButton("Examination  :");
		ExaminationRadioButton1.setForeground(Color.BLACK);
		ExaminationRadioButton1.setFont(new Font("Tahoma", Font.BOLD, 11));
		ExaminationRadioButton1.setBounds(130, 265, 119, 23);
		ExaminationRadioButton1.setContentAreaFilled(false);
		ExaminationRadioButton1.setOpaque(false);
		ExaminationRadioButton1.setEnabled(false);
		DataEraseGroup.add(ExaminationRadioButton1);
		add(ExaminationRadioButton1);
		
		CourseNoRadioButton1 = new JRadioButton("Course No       :");
		CourseNoRadioButton1.setForeground(Color.BLACK);
		CourseNoRadioButton1.setFont(new Font("Tahoma", Font.BOLD, 11));
		CourseNoRadioButton1.setBounds(130, 202, 119, 23);
		CourseNoRadioButton1.setContentAreaFilled(false);
		CourseNoRadioButton1.setOpaque(false);
		CourseNoRadioButton1.setEnabled(false);
		DataEraseGroup.add(CourseNoRadioButton1);
		add(CourseNoRadioButton1);
		
		ExaminationRadioButton2 = new JRadioButton("Examination   :");
		ExaminationRadioButton2.setForeground(Color.BLACK);
		ExaminationRadioButton2.setFont(new Font("Tahoma", Font.BOLD, 11));
		ExaminationRadioButton2.setBounds(130, 424, 119, 23);  
		ExaminationRadioButton2.setContentAreaFilled(false);
		ExaminationRadioButton2.setOpaque(false);
		ExaminationRadioButton2.setEnabled(false);
		DataEditGroup.add(ExaminationRadioButton2);
		add(ExaminationRadioButton2);
		
		CourseNoRadioButton2 = new JRadioButton("Course No        :");
		CourseNoRadioButton2.setForeground(Color.BLACK);
		CourseNoRadioButton2.setFont(new Font("Tahoma", Font.BOLD, 11));
		CourseNoRadioButton2.setBounds(130, 362, 119, 23);  
		CourseNoRadioButton2.setContentAreaFilled(false);
		CourseNoRadioButton2.setOpaque(false);
		CourseNoRadioButton2.setEnabled(false);
		DataEditGroup.add(CourseNoRadioButton2);
		add(CourseNoRadioButton2);
		
		BatchRadioButton2 = new JRadioButton("      Batch         :");
		BatchRadioButton2.setForeground(Color.BLACK);
		BatchRadioButton2.setFont(new Font("Tahoma", Font.BOLD, 11));
		BatchRadioButton2.setBounds(130, 393, 119, 23);  
		BatchRadioButton2.setContentAreaFilled(false);
		BatchRadioButton2.setOpaque(false);
		BatchRadioButton2.setEnabled(false);
		DataEditGroup.add(BatchRadioButton2);
		add(BatchRadioButton2);
		
		
		CourseFilterYearComboBox = new JComboBox();
		CourseFilterYearComboBox.addItem("1st Year");  CourseFilterYearComboBox.addItem("2nd Year"); CourseFilterYearComboBox.addItem("3rd Year"); CourseFilterYearComboBox.addItem("4th Year");
		CourseFilterYearComboBox.setForeground(Color.BLACK);
		//CourseFilterYearComboBox.setBackground(MyColor);
		//CourseFilterYearComboBox.setOpaque(false);
		CourseFilterYearComboBox.setMaximumRowCount(5);
		CourseFilterYearComboBox.setBounds(250,21,95,20);
		CourseFilterYearComboBox.addActionListener(this);
		CourseFilterYearComboBox.setBorder(ComboBorder);
	    add(CourseFilterYearComboBox);
	    
	    CourseFilterTermComboBox = new JComboBox();
		CourseFilterTermComboBox.addItem("1st Term");  CourseFilterTermComboBox.addItem("2nd Term");
		CourseFilterTermComboBox.setForeground(Color.BLACK);
		//CourseFilterTermComboBox.setBackground(MyColor);
		//CourseFilterTermComboBox.setOpaque(false);
		CourseFilterTermComboBox.setMaximumRowCount(5);
		CourseFilterTermComboBox.setBounds(355,21,95,20);
		CourseFilterTermComboBox.setBorder(ComboBorder);
		CourseFilterTermComboBox.addActionListener(this);
	    add(CourseFilterTermComboBox);
		
		
	    for(int i =0;i<11;i++) 
	    	MyComboBox[i] = new JComboBox();
			
		MyComboBox[0].setBounds(250,  78, 200, 20);
		MyComboBox[1].setBounds(250, 110, 200, 20);
		MyComboBox[2].setBounds(250, 141, 200, 20);
		MyComboBox[3].setBounds(250, 205, 200, 20);
		MyComboBox[4].setBounds(250, 236, 200, 20);
		MyComboBox[5].setBounds(250, 267, 200, 20);
		MyComboBox[6].setBounds(250, 296, 200, 20);
		MyComboBox[7].setBounds(250, 365, 200, 20);
		MyComboBox[8].setBounds(250, 396, 200, 20);   //9
		MyComboBox[9].setBounds(250, 427, 200, 20);   //10
		MyComboBox[10].setBounds(250, 458, 200, 20);  //8
		
		MyComboBox[2].addActionListener(this);
		MyComboBox[5].addActionListener(this);
		MyComboBox[9].addActionListener(this);
		
		for(int i =0;i<11;i++) 
		   {
		    MyComboBox[i].setForeground(Color.BLACK);
		    //MyComboBox[i].setBackground(MyColor);
		    //MyComboBox[i].setOpaque(false);
		    MyComboBox[i].setFont(new Font ("SERRIF",Font.BOLD,10));
		    MyComboBox[i].setMaximumRowCount(5);
		    MyComboBox[i].setEnabled(false);
		    MyComboBox[i].setBorder(ComboBorder);
		    add(MyComboBox[i]);
		    }		   
		 
		ProceedButton = new JButton();
		ProceedButton.setFont(new Font("Rockwell Extra Bold", Font.BOLD, 11));
		ProceedButton.setForeground(Color.BLACK);
		ProceedButton.setText("Proceed");
		ProceedButton.setBounds(257, 500, 119, 23);
		ProceedButton.setBackground(MyColor);
		ProceedButton.setContentAreaFilled(false);
		ProceedButton.setOpaque(false);
		ProceedButton.addActionListener(this);
		add(ProceedButton);
			
		
		CourseFilterLabel = new JLabel("Course Filter  :",SwingConstants.RIGHT);   
		CourseFilterLabel.setForeground(Color.BLACK);
		CourseFilterLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		CourseFilterLabel.setBounds(126, 20, 91, 20);	
		add(CourseFilterLabel);
		
		CourseLabel = new JLabel("Course No  :",SwingConstants.RIGHT);   
		CourseLabel.setForeground(Color.BLACK);
		CourseLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		CourseLabel.setEnabled(false);
		CourseLabel.setBounds(126, 77, 91, 20);	
		add(CourseLabel);
			
		ExamYearLabel = new JLabel("Exam Year  :",SwingConstants.RIGHT);
		ExamYearLabel.setForeground(Color.BLACK);
		ExamYearLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		ExamYearLabel.setBounds(126, 109, 91, 20);
		ExamYearLabel.setEnabled(false);
		add(ExamYearLabel);
			
		BatchLabel = new JLabel("Batch  :", SwingConstants.RIGHT);
		BatchLabel.setForeground(Color.BLACK);
		BatchLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		BatchLabel.setBounds(126, 140, 91, 20);
		BatchLabel.setEnabled(false);
		add(BatchLabel);
		setOpaque(false);
			
		return this;	
	}	
	
    protected void paintComponent(Graphics g)
	{
	 super.paintComponent(g);
	 g.setColor(Color.BLACK);
	 
	 MyFilterBorder.paintBorder(this,g,110,15,400,35);
	 MyBorder.paintBorder(this,g,110,51,400,125);
	 MyBorder.paintBorder(this,g,110,180,400,148);
	 MyBorder.paintBorder(this,g,110,336,400,154);
	 new BevelBorder(BevelBorder.RAISED,Color.DARK_GRAY,Color.BLACK).paintBorder(this, g, 255, 498, 123, 27);
	}
	
	public void updateAllCourseList()
	{
		updateCourseList(0);
		updateCourseList(3);
		updateCourseList(5);
		updateCourseList(7);
		updateCourseList(9);
	}
	
	public void updateAllBatchList()
	{
		updateBatchList(2);
		updateBatchList(4);
		updateBatchList(8);
	}
	
	private void updateAllCourseListAccordingToFilter(int Year,int Term)
	{
		Courses.clear();
		MyComboBox[0].removeAllItems();
		MyComboBox[3].removeAllItems();
		MyComboBox[5].removeAllItems();
		MyComboBox[7].removeAllItems();
		MyComboBox[9].removeAllItems();
		Courses.addAll(TempControll.DataEntryObject.getEnrolledCourses(Year,Term));
		
		for(String x : Courses)
		{
			MyComboBox[0].addItem(x);
			MyComboBox[3].addItem(x);
			MyComboBox[5].addItem(x);
			MyComboBox[7].addItem(x);
			MyComboBox[9].addItem(x);
		}
		
	}
	
    private void  updateCourseList(int i)
	{
		Courses.clear();
		MyComboBox[i].removeAllItems();
		Courses.addAll(TempControll.DataEntryObject.getEnrolledCourses());
		
		for(String x : Courses)
			MyComboBox[i].addItem(x);
	}

	private void updateBatchList(int i)
	{
		Batches.clear();
		MyComboBox[i].removeAllItems();
		Batches.addAll(TempControll.DataEntryObject.getEnrolledBatches());
		
		for(String x : Batches)
			MyComboBox[i].addItem(x);
	}

	private void updateAsCourse(int i) 
	{
		AsPerCourse.clear();
		String Course;
		if(MyComboBox[i].getSelectedIndex()>=0)    // checking if any item is selected or not 
		  {
		    Course = MyComboBox[i].getSelectedItem().toString();
		    AsPerCourse.addAll(TempControll.DataEntryObject.getEnrolledExamYearAsCourses(Course));
		
		    MyComboBox[i+1].removeAllItems();
		    for(String x : AsPerCourse )
			    MyComboBox[i+1].addItem(x);	
		  }
	}
	
	private void doFilter()
	{
	   int Year = CourseFilterYearComboBox.getSelectedIndex();
	   int Term = CourseFilterTermComboBox.getSelectedIndex();
	   if(Year>=0 && Term>=0)
	      updateAllCourseListAccordingToFilter(Year+1,Term+1);
	}

	@Override
	public void actionPerformed(ActionEvent AE) 
	{
		if(TempControll.ConnectionManagerObject.createConnection())
		  {
			if(AE.getSource()==MyComboBox[5])
		      updateAsCourse(5);  
		    if(AE.getSource()==MyComboBox[9])
		      updateAsCourse(9); 
		    if(AE.getSource()==MyComboBox[2])  // to ensure valid examination year for an enrolled batch
		      {
			   if(MyComboBox[2].getSelectedIndex()>=0)
			     {
			      int B = Integer.parseInt(MyComboBox[2].getSelectedItem().toString()); // getting the batch year
			      
			      MyComboBox[1].removeAllItems();
			      for(int i=B+1;i<=(B+7);i++)
				     MyComboBox[1].addItem(i);
			     }
		      }
		    if(AE.getSource()==CourseFilterTermComboBox||AE.getSource()==CourseFilterYearComboBox)
		      {
		    	doFilter();
		      }
	        if(AE.getSource()==ProceedButton)
	          {
	    	   if(DataEntryRadioButton.isSelected())
	    		  actionDataEntry();
	    	   else if (DataEraseRadioButton.isSelected())
	    		  actionDataErase();
	    	   else if (DataEditRadioButton.isSelected())
	    		  actionDataEdit();
	    	   else 
	    		   JOptionPane.showMessageDialog(null,"Please Select any field from\n     1. Data Entry\n     2. Data Erase\n     3. Data Erase", "No selected Field",  JOptionPane.ERROR_MESSAGE);
	    	   updateAllBatchList();
	   		   doFilter();
	   		   TempControll.ResultHandlePanelObject.updateBatchList();
	   		   TempControll.ResultHandlePanelObject.updateCourseList();
	          }
	        if(AE.getSource()==DataEntryRadioButton)
			  {
				doFilter();
				updateBatchList(2);
				for(int i=0;i<11;i++)
				   {
					if(i<=2)
				      MyComboBox[i].setEnabled(true);
					else
					  MyComboBox[i].setEnabled(false);		
				   }
				
				BatchRadioButton1.setEnabled(false);
				ExaminationRadioButton1.setEnabled(false);
				CourseNoRadioButton1.setEnabled(false);
				ExaminationRadioButton2.setEnabled(false);
				CourseNoRadioButton2.setEnabled(false);
				BatchRadioButton2.setEnabled(false);
				CourseLabel.setEnabled(true);  
				ExamYearLabel.setEnabled(true);
				BatchLabel.setEnabled(true);
			  }
			
			if(AE.getSource()==DataEraseRadioButton)
			{
				doFilter();
				updateBatchList(4);
				CourseLabel.setEnabled(false);  
				ExamYearLabel.setEnabled(false);
				BatchLabel.setEnabled(false);
				
				for(int i=0;i<11;i++)
				   {
					if(i>=3 && i<=6)
				      MyComboBox[i].setEnabled(true);
					else
					  MyComboBox[i].setEnabled(false);		
				   }
				BatchRadioButton1.setEnabled(true);
				ExaminationRadioButton1.setEnabled(true);
				CourseNoRadioButton1.setEnabled(true);
				ExaminationRadioButton2.setEnabled(false);
				CourseNoRadioButton2.setEnabled(false);
				BatchRadioButton2.setEnabled(false);
			}
			
			if(AE.getSource()==DataEditRadioButton)
			{
				updateBatchList(8);
				CourseLabel.setEnabled(false);  
				ExamYearLabel.setEnabled(false);
				BatchLabel.setEnabled(false);
			
				for(int i=0;i<11;i++)
				   {
					if(i>=7)
				      MyComboBox[i].setEnabled(true);
					else
					  MyComboBox[i].setEnabled(false);		
				   }
				BatchRadioButton1.setEnabled(false);
				ExaminationRadioButton1.setEnabled(false);
				CourseNoRadioButton1.setEnabled(false);
				ExaminationRadioButton2.setEnabled(true);
				CourseNoRadioButton2.setEnabled(true);
				BatchRadioButton2.setEnabled(true);
				doFilter();
			}
		  }
	}
	    
    private void actionDataEntry()
	{
		if(MyComboBox[0].getSelectedIndex()>=0 && MyComboBox[1].getSelectedIndex()>=0 && MyComboBox[2].getSelectedIndex()>=0 )
   		  {
			String Course,Year,Batch;
			Course = MyComboBox[0].getSelectedItem().toString();
			Year = MyComboBox[1].getSelectedItem().toString();
			Batch = MyComboBox[2].getSelectedItem().toString();
			if(TempControll.DataEntryObject.validateDataEntry(Course,Year,Batch) )
			  {
				int Theory_Lab_Determine = Integer.parseInt (  Character.toString (Course.charAt(Course.length()-1) )  )%2;
				if(Theory_Lab_Determine==0)
				  TempControll.DataEntryPanelLabObject.createDataEntryPanelLab(Course, Batch, Year);
				else
				  TempControll.DataEntryPanelTheoryObject.createDataEntryPanelTheory(Course, Batch, Year);	
			  }
			else
			  JOptionPane.showMessageDialog(null, "Data exists for  :\nBatch            :     "+Batch+"\nCourse no    :     "+Course+"\nExam Year   :     "+Year+"\nYou can edit them selecting from Data Edit field", "Data already exists ", JOptionPane.ERROR_MESSAGE);  
		  }
		else 
   			JOptionPane.showMessageDialog(null,"Please, make sure that 'Course No' & 'Batch' field is available in Data Entry Field" , "Unavailable",  JOptionPane.ERROR_MESSAGE);
		
	}
    
    private void actionDataErase()
	{
	   if(BatchRadioButton1.isSelected())
	     {
		   if(MyComboBox[4].getSelectedIndex()>=0)
		     {
			   String Batch = MyComboBox[4].getSelectedItem().toString();
			       
			   if(JOptionPane.showConfirmDialog(null, "All data related to   BATCH : "+Batch+"   will be erased.\nStill want to proceed ?? ", "Warning !!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
			     {
			       if(TempControll.DataEraseEditObject.eraseBatch(Batch))
				     JOptionPane.showMessageDialog(null, "Batch  :  "+Batch+"  is successfully erased from database.", "Success!!", JOptionPane.INFORMATION_MESSAGE) ;
		           else
		    	     JOptionPane.showMessageDialog(null, "Batch  :  "+Batch+"  couldn't be erased.\n>>  Probably : \n               1. This batch is not available in database.\n               2. Data exists in database involving this batch.\n>>  Recommendation : \n               1. Check if the batch is still available & try to erase again.\n               2. Erase examination's data that involves this batch selecting  'Examination'  of  'Data Erase'  field.", "Error", JOptionPane.ERROR_MESSAGE) ;
		         }
		     }
		   else 
			   JOptionPane.showMessageDialog(null,"Please, make sure that 'Batch' field or it's selected data is available or selected data in Data Erase Field" , "Unavailable",  JOptionPane.ERROR_MESSAGE);
	     }
	   else if(ExaminationRadioButton1.isSelected())
	     {
		   if(MyComboBox[5].getSelectedIndex()>=0 && MyComboBox[6].getSelectedIndex()>=0)
		     {
			   String Course = MyComboBox[5].getSelectedItem().toString();
			   String ExamYear = MyComboBox[6].getSelectedItem().toString();
			   
			   if(JOptionPane.showConfirmDialog(null, "All data related to Examination : "+Course +" ( "+ExamYear+" )"+"  will be erased.\nStill want to proceed ?? ", "Warning !!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
			     {
				   if(TempControll.DataEraseEditObject.eraseExamination(Course, ExamYear))
					   JOptionPane.showMessageDialog(null,"Data has been erased successfully. " +"\nCourse                      :     "+Course+"\nExamination Year   :     "+ExamYear, "Success!!", JOptionPane.INFORMATION_MESSAGE) ;
				   else
					   JOptionPane.showMessageDialog(null, "Couldn't be erased : \nCourse                       :  "+Course+"\nExamination Year   :  "+ExamYear+"\n>>  Probably : \n               Data for this Examination is not available in database.\n>>  Recommendation : \n               Check if Data for this examination is available & try again.", "Unavailable", JOptionPane.ERROR_MESSAGE) ;
			     }
		     }
		   else
			   JOptionPane.showMessageDialog(null,"Please, make sure that both fields under  'Examination'  of  'Data Erase'  have available or selected data." , "Unavailable",  JOptionPane.ERROR_MESSAGE);
	     }
	   else if(CourseNoRadioButton1.isSelected())     // to erase course 
	     {
		   if(MyComboBox[3].getSelectedIndex()>=0)    // check that an item is selected or ComboBox is not empty
		     {  
			   String Course = MyComboBox[3].getSelectedItem().toString();
		       
			   if(JOptionPane.showConfirmDialog(null, "All data related to Course : "+Course +"  will be erased.\nStill want to proceed ?? ", "Warning !!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
			     {
			   		if(TempControll.DataEraseEditObject.eraseCourse(Course))
			   			JOptionPane.showMessageDialog(null, "Course  :  "+Course+"  is successfully erased from database.", "Success!!", JOptionPane.INFORMATION_MESSAGE) ;
			   		else
			   			JOptionPane.showMessageDialog(null, "Course  :  "+Course+"  couldn't be erased.\n>>  Probably : \n               1. This course is not available in database.\n               2. Data exists in database involving this course.\n>>  Recommendation : \n               1. Check if the course is still available & try to erase again.\n               2. Erase examination's data that involves this course selecting  'Examination'  of  'Data Erase'  field.", "Error", JOptionPane.ERROR_MESSAGE) ;
			     }
		     }
		   else JOptionPane.showMessageDialog(null,"Please, make sure that 'Course No' field or it's selected data is available in  'Data Erase'  Field" , "Unavailable",  JOptionPane.ERROR_MESSAGE);
	     }
	   else
		 JOptionPane.showMessageDialog(null,"Please Select any field from\n     1. Course No\n     2. Batch\n     3.Examination", "No selected Field",  JOptionPane.ERROR_MESSAGE);
	}
    
    private void actionDataEdit()
    {
        String Course,ExamYear;
        int Theory_Lab_Determine;
    	if(ExaminationRadioButton2.isSelected())
	     {
    	   if(MyComboBox[9].getSelectedIndex()>=0 && MyComboBox[10].getSelectedIndex()>=0)
		     {
			   Course   = MyComboBox[9].getSelectedItem().toString();
			   ExamYear = MyComboBox[10].getSelectedItem().toString();
			   Theory_Lab_Determine = Integer.parseInt (  Character.toString (Course.charAt(Course.length()-1) )  )%2;
			   if(Theory_Lab_Determine==1)
			     {
				   if(TempControll.DataEraseEditObject.hasExistingExaminationData(Course, ExamYear, true))
					  TempControll.DataEditPanelTheoryObject.createDataEditPanelTheory(Course, ExamYear, DataTransfer.Roll.size());
				   else 
					  JOptionPane.showMessageDialog(null, "No data found to edit : \nCourse                       :  "+Course+"\nExamination Year   :  "+ExamYear+"\n>>  Probably : \n               Data for this Examination is not available in database.\n>>  Recommendation : \n               Check if Data for this examination is available & try again.", "Unavailable", JOptionPane.ERROR_MESSAGE) ;   
			     }
			   else
			     {
				   if(TempControll.DataEraseEditObject.hasExistingExaminationData(Course, ExamYear, false))
					  TempControll.DataEditPanelLabObject.createDataEditPanelLab(Course, ExamYear, DataTransfer.Roll.size());
				   else 
					  JOptionPane.showMessageDialog(null, "No data found to edit : \nCourse                       :  "+Course+"\nExamination Year   :  "+ExamYear+"\n>>  Probably : \n               Data for this Examination is not available in database.\n>>  Recommendation : \n               Check if Data for this examination is available & try again.", "Unavailable", JOptionPane.ERROR_MESSAGE) ;   				   
			     }
		     }
		   else
			   JOptionPane.showMessageDialog(null,"Please, make sure that 'Examination' field or it's selected data is available in  'Data Edit'  Field" , "Unavailable",  JOptionPane.ERROR_MESSAGE);
		       
	     }
	   else if(CourseNoRadioButton2.isSelected())
	     {
		   if(MyComboBox[7].getSelectedIndex()>=0)
		     {
			   String Course_No = MyComboBox[7].getSelectedItem().toString() ;
			   if(TempControll.DataEraseEditObject.getCourseNameAndCourseCredit(Course_No))
			     TempControll.DataEditPanelCourseObject.createDataEditPanelCourse(Course_No, DataTransfer.CourseName, DataTransfer.CourseCredit);
			   else
				 JOptionPane.showMessageDialog(null, "'"+Course_No+"' is not available.\nTry again.", "Unavailable", JOptionPane.INFORMATION_MESSAGE);
		     }
		   else
			   JOptionPane.showMessageDialog(null,"Please, make sure that 'Course No' field or it's selected data is available in  'Data Edit'  Field" , "Unavailable",  JOptionPane.ERROR_MESSAGE);
	     }
	   else if(BatchRadioButton2.isSelected())
	     {  
		   if(MyComboBox[8].getSelectedIndex()>=0)
		     {
			   String SelectedBatch = MyComboBox[8].getSelectedItem().toString();
			   if(TempControll.DataEraseEditObject.hasStudentsDataOfBatch(SelectedBatch))
			      TempControll.DataEditPanelBatchObject.createDialogBox(SelectedBatch);
			   else
			      JOptionPane.showMessageDialog(null, "'"+SelectedBatch+"' is not available.\nTry again.", "Unavailable", JOptionPane.INFORMATION_MESSAGE);
		     }
		   else
		     JOptionPane.showMessageDialog(null,"Please, make sure that 'Batch' field or it's selected data is available in  'Data Edit'  Field" , "Unavailable",  JOptionPane.ERROR_MESSAGE);
     }
	   else
		 JOptionPane.showMessageDialog(null,"Please Select any field from\n     1. Course No\n     2. Examination", "No selected Field",  JOptionPane.ERROR_MESSAGE);
	}
}