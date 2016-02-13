package GUIPackage;

import java.awt.*;      
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Vector; 
import javax.swing.*;
import javax.swing.border.*; 
import MainPackage.Controll;

public class ResultPanelTerm implements ActionListener 
{
	private int NumberOfCourses,NumberOfRolls,NumberOfSelectedRoll,NumberOfSelectedCourse,NumberOfSelectedTheory,NumberOfSelectedSessional;
	private String ExamYear,Term;
	private JDialog RPT;
	private JButton DocButton ;
	private JPanel Panel,GridPanel ;
	private JScrollPane Scroll;
	private MyJObject[] My;
	private Controll TempControll;
	private JCheckBox CheckAll;
	private JCheckBox[] ColumnCheckBox;
	private JLabel Header1,Header2,Header3;
	private Vector<String> RollCollection = new Vector<String>();
	private Vector<String> CourseCollection = new Vector<String>();
	private Border ButtonBorder = new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.WHITE);
	private DecimalFormat TwoDecimal = new DecimalFormat ("#.##");	
	private String [][] Data;
	private Vector<String>Theory = new Vector<String>();
	private Vector<String>Sessional = new Vector<String>();
	private Vector<Integer> SelectedCourseIndex = new Vector<Integer>();
	
	public ResultPanelTerm(Controll x)
	{
		TempControll = x;
	}
	public void createDialogBox(String Term ,String ExamYear, Vector<String>Courses,Vector<String>Rolls)
	{
		RPT = new JDialog(); 
		this.Term = Term;
		this.ExamYear = ExamYear;
		this.NumberOfCourses = Courses.size();
		this.NumberOfRolls = Rolls.size();
		this.RollCollection.clear();
		this.CourseCollection.clear();
		this.RollCollection.addAll(Rolls);
		this.CourseCollection.addAll(Courses);
		
		final int FinalHeight =(this.NumberOfRolls*20+385>565)?this.NumberOfRolls*20+385:565;
        final int FinalWidth  = (this.NumberOfCourses*105+355>840)?this.NumberOfCourses*105+355:840;
		final int x = this.NumberOfCourses;
		final int y = this.NumberOfRolls;
        
		Panel = new JPanel()
		{	
			protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/Icons/6.jpg")).getImage(),0,0,FinalWidth*55+105+100,100+FinalHeight*20+35+100,null);
				ButtonBorder.paintBorder(this, g,(x*105+465)/2-111,y*20+234,252,32);
			   }
		};
		
		Panel.setPreferredSize(new Dimension(this.NumberOfCourses*105+515,this.NumberOfRolls*20+335));// 50+(this.NumberOfCourses*105+205)+50, 100+ {(this.NumberOfRolls+2)*15+(this.NumberOfRolls+2-1)*5}+200
		Panel.setLayout(null);
		
		DocButton = new JButton("Create Document");
		DocButton.setFont(new Font("SERRIF",Font.BOLD,15));
		DocButton.setBounds((this.NumberOfCourses*105+465)/2-110,this.NumberOfRolls*20+235,250,30);     
		DocButton.setForeground(Color.BLACK);
		DocButton.addActionListener(this);
		Panel.add(DocButton);
		
		CheckAll = new JCheckBox("Uncheck all");
		CheckAll.setForeground(Color.WHITE);
		CheckAll.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
		CheckAll.setHorizontalAlignment(SwingConstants.LEFT);
		CheckAll.setOpaque(false);
		CheckAll.setSelected(true);
		CheckAll.addActionListener(this);	
		
//		Header1 = new JLabel("Batch : "+this.Batch,SwingConstants.CENTER);
//		Header1.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,18));
//		Header1.setForeground(Color.WHITE);
//		Header1.setBounds(50, 30, this.NumberOfCourses*105+205, 20);
//		Panel.add(Header1);
		
		Header2 = new JLabel("Year : "+this.ExamYear,SwingConstants.CENTER);
		Header2.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,18));
		Header2.setForeground(Color.WHITE);
		Header2.setBounds(50, 60, this.NumberOfCourses*105+205, 20);
		Panel.add(Header2);
		
		Header3 = new JLabel(this.Term,SwingConstants.CENTER);
		Header3.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,18));
		Header3.setForeground(Color.WHITE);
		Header3.setBounds(50, 90, this.NumberOfCourses*105+205, 20);
		Panel.add(Header3);
		
		Data = TempControll.ResultObject.getAllNecessaryDataForTerm(this.CourseCollection,this.RollCollection,this.ExamYear);
		
		setComponentsOnTheGrid();
		
		Scroll = new JScrollPane(Panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		RPT.add(Scroll);
		
		RPT.setModal(true);
		RPT.setTitle(" Result : Particular TERM ");
		RPT.setResizable (true);
		RPT.setSize(840,565);//(NumberOfCourses*40+290>500)? 500:NumberOfCourses*40+290); // if RPT is longer then panel then dialog size decrease  //.setSize(840,565);
		RPT.setLocation(250,100+(565-RPT.getHeight())/2); // setiing RPT dialogbox in the middle of MenuFrame
		RPT.setVisible(true);
	}
	
	private void setComponentsOnTheGrid()
	{
		GridPanel = new JPanel(new GridLayout(0,this.NumberOfCourses+4,5,5));
		
		setUpColumnName();
		My = new MyJObject[this.NumberOfRolls];
		
		for(int i=0;i<this.NumberOfRolls;i++)
		   {
			My[i] = new MyJObject(i,this.NumberOfCourses);
			
			GridPanel.add(My[i].RollCheckBox);
			for(int j=0 ; j<this.NumberOfCourses; j++)
				GridPanel.add(My[i].Label[j]);
			GridPanel.add(My[i].EarnedCreditLabel);
			GridPanel.add(My[i].WeightedGPLabel);
			GridPanel.add(My[i].GPALabel);
		   }
		GridPanel.add(CheckAll);
		GridPanel.setOpaque(false);
		GridPanel.setBounds(50, 150,this.NumberOfCourses*105+415, this.NumberOfRolls*20+35); // (this.NumberofCourses+4)*100 + (this.NumberOfCourses+4-1)*5, (this.NumberOfRolls+2)*15+(this.NumberOfRolls+2-1)*5
		Panel.add(GridPanel);
	}
	
	private void setUpColumnName()
	{
		JLabel [] ColumnLabel = new JLabel[4];
		ColumnCheckBox= new JCheckBox[this.NumberOfCourses];
		for(int i=0; i<4; i++)
		   {
			ColumnLabel[i]= new JLabel("",SwingConstants.CENTER);
			ColumnLabel[i].setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
			ColumnLabel[i].setForeground(Color.WHITE);
		   }
		
		ColumnLabel[0].setText("Roll     ");
		ColumnLabel[0].setHorizontalTextPosition(SwingConstants.LEFT);
		ColumnLabel[1].setText("Earned Credits");
		ColumnLabel[2].setText("Weighted GP");
		ColumnLabel[3].setText("GPA");
		
		for(int i=0;i<this.NumberOfCourses;i++)
		   {
		     ColumnCheckBox[i]= new JCheckBox ( this.CourseCollection.elementAt(i) );
		     ColumnCheckBox[i].setForeground(Color.WHITE);
		     ColumnCheckBox[i].setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,10));
		     ColumnCheckBox[i].setHorizontalAlignment(SwingConstants.CENTER);
		     ColumnCheckBox[i].setOpaque(false);
		     ColumnCheckBox[i].setSelected(true);
		   }
		GridPanel.add(ColumnLabel[0]);
		for(int i = 0; i<this.NumberOfCourses;i++)
		   GridPanel.add(ColumnCheckBox[i]);
		GridPanel.add(ColumnLabel[1]);
		GridPanel.add(ColumnLabel[2]);
		GridPanel.add(ColumnLabel[3]);
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
	    	for(int i=0;i<NumberOfRolls;i++)
	    		My[i].RollCheckBox.setSelected(Selection);
	      }
		
		if(AE.getSource()==DocButton)
		  {			
			if(check())
			  {
				new File(System.getProperty("user.home")+"/TermResultCalculator/TermDocs/").mkdirs();  // creating directories
				String [][] SelectedData = gatherData();
				
				if(TempControll.TermPdfObject.createPDF(this.Term, this.ExamYear, this.NumberOfSelectedRoll, this.Theory, this.Sessional, SelectedData))
					JOptionPane.showMessageDialog(RPT, "Report has been created at "+System.getProperty("user.home")+"/TermResultCalculator/TermDocs/", "Success", JOptionPane.INFORMATION_MESSAGE) ;
				else 
					JOptionPane.showMessageDialog(RPT, "Error occured while creating Report", "Error", JOptionPane.ERROR_MESSAGE);
			  }
			else 
			  JOptionPane.showMessageDialog(RPT,"Make sure that your selection meets the following requirement : \n1. Number of selected courses has to be between 1 to 10.\n2. Number of selected students has to be a positive number.","Error", JOptionPane.ERROR_MESSAGE);
		  }
	}
	
	private class MyJObject 
	{
		JCheckBox RollCheckBox;
		JLabel Label [] ;
		JLabel EarnedCreditLabel,WeightedGPLabel, GPALabel;
		
		public MyJObject(int Row, int Column)
		{
			RollCheckBox = new JCheckBox(Data[Row][0]);
			RollCheckBox.setForeground(Color.WHITE);
			RollCheckBox.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
			RollCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
			RollCheckBox.setOpaque(false);
			RollCheckBox.setSelected(true);
			
			Label = new JLabel[Column];
			for(int i = 0; i<Column ; i++)
			   {
				 String x = Data[Row][i+1];
				 Label[i] = new JLabel(x,SwingConstants.CENTER);
				 Label[i].setForeground(Color.WHITE);
				 Label[i].setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
			   }
			
			EarnedCreditLabel = new JLabel(Data[Row][Column+1],SwingConstants.CENTER);
			EarnedCreditLabel.setForeground(Color.WHITE);
			EarnedCreditLabel.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
			
			WeightedGPLabel = new JLabel(Data[Row][Column+2],SwingConstants.CENTER);
			WeightedGPLabel.setForeground(Color.WHITE);
			WeightedGPLabel.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
			
			GPALabel = new JLabel(Data[Row][Column+3],SwingConstants.CENTER);
			GPALabel.setForeground(Color.WHITE);
			GPALabel.setFont(new Font("SERRIF",Font.BOLD+Font.ITALIC,12));
		}
	}

	private boolean check()
	{
		this.SelectedCourseIndex.clear();
		boolean Flag = true;
		this.Theory.clear();
		this.Sessional.clear();
		this.NumberOfSelectedCourse=this.NumberOfSelectedRoll=this.NumberOfSelectedSessional=this.NumberOfSelectedTheory=0;
		
		for(int i=0;i<this.NumberOfRolls;i++)
			if(My[i].RollCheckBox.isSelected())
			   this.NumberOfSelectedRoll++;
		
		for(int i=0; i<this.NumberOfCourses;i++)
		   if(ColumnCheckBox[i].isSelected())
			 {
				SelectedCourseIndex.add(i);
			    this.NumberOfSelectedCourse++;
				if(isTheoryCourse(ColumnCheckBox[i].getText()))
                   {
					  this.NumberOfSelectedTheory++;
                      this.Theory.add(ColumnCheckBox[i].getText());
                   }
				else 
				   {
					  this.NumberOfSelectedSessional++;
					  this.Sessional.add(ColumnCheckBox[i].getText());
				   }
			 }
		 
		if(this.NumberOfSelectedCourse>10 || this.NumberOfSelectedCourse<1 || this.NumberOfSelectedRoll<1)
			  Flag = false;
		  return Flag;
	}
	
	private String [][] gatherData()
	{
		String [][] SelectedData = new String [this.NumberOfSelectedRoll][18];
		int Counter,TS,SS,LabelSerial; 
		Counter=TS=SS=LabelSerial=0;
	
		for(int i=0,j=0;i<this.NumberOfRolls;i++)
		    if(My[i].RollCheckBox.isSelected())
			   {
				 SelectedData[j][0] = Integer.toString(++Counter);
				 SelectedData[j][1] = My[i].RollCheckBox.getText();
				   
				 TS = 2;
				 LabelSerial=0;
				 for(int k=0; k<this.NumberOfSelectedTheory;k++)
				     SelectedData[j][TS++] = My[i].Label[SelectedCourseIndex.elementAt(LabelSerial++)].getText(); 
				       
				 if(TS<7)
				   for(int k=TS; k<8;k++)
					    SelectedData[j][TS++] = " "; 
	
				 SS = 8;
				 for(int k=0; k<this.NumberOfSelectedSessional;k++)
				    SelectedData[j][SS++] = My[i].Label[SelectedCourseIndex.elementAt(LabelSerial++)].getText(); 
				 
				 if(SS<13)
				   for(int k=SS; k<14;k++)
					    SelectedData[j][SS++] = " "; 
				   
				 SelectedData[j][14] = My[i].EarnedCreditLabel.getText() ;
				 SelectedData[j][15] = My[i].WeightedGPLabel.getText();
				 SelectedData[j][16] = My[i].GPALabel.getText();
				 SelectedData[j][17] = " ";
				 j++;
			   }
		return SelectedData;
	}
	
	private boolean isTheoryCourse(String X)
	{
		if(Integer.parseInt(Character.toString(X.charAt(X.length()-1)))%2==0)
	      return false;
	    else 
	      return true;
	}
	
}