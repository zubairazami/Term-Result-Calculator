package DB;

import java.sql.*;    
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import MainPackage.Controll;


public class Result {
	private ConnectionManager TempCM ;
	private Controll TempControll;
	private Statement EStatement;
	private ResultSet EResultSet;
	private String DataTable[];
	private String TempString;
	private boolean Flag;
	private Vector<String>RollsOfBatch, ExamYears,Credit,CoursesAsTerm,RollsAsTerm,TempCoursesForTerm,TempRollsForTerm,ForCalculation;
	private int Serial []  = {4,0,5,1,6,2,7,3}; // for getting database table in order theory_first_year >> lab_first_year >> theory_second_year >> lab_second_year >> theory_third_year >> lab_third_year >> theory_fourth_year >> lab_fourth_year
	private DecimalFormat TwoDecimal = new DecimalFormat ("#.##");
	
	public Result(Controll x)
	{
	 TempControll = x;
	 TempCM = x.ConnectionManagerObject;  
	 DataTable = new String[8];
	 DataTable[0]="lab_first_year";
	 DataTable[1]="lab_second_year";
	 DataTable[2]="lab_third_year";
	 DataTable[3]="lab_fourth_year";
	 DataTable[4]="theory_first_year";
	 DataTable[5]="theory_second_year";
	 DataTable[6]="theory_third_year";
	 DataTable[7]="theory_fourth_year";
	 RollsOfBatch = new Vector<String>();
	 ExamYears = new Vector<String>();
	 Credit = new Vector<String>();
	 CoursesAsTerm = new Vector<String>();
	 RollsAsTerm = new Vector<String>();
	 TempCoursesForTerm = new Vector<String>();
	 TempRollsForTerm = new Vector<String>();
	 ForCalculation = new Vector<String>();
	}

	
// ------------------------------------------ for student result panel ------------------------------------------------------

	// for getting credit hours according  to a list of course_no
		public Vector<String> getCreditHours(Vector<String> CourseNo)
		{
			Credit.clear();
			closeAll();
			
			try 
			  {
				EStatement = TempCM.MyConnection.createStatement();
				
				int Size = CourseNo.size();
				for(int i=0;i<Size;i++)
				   {
					 
				     EResultSet = EStatement.executeQuery("SELECT course_credit FROM courses WHERE course_no = '"+CourseNo.elementAt(i)+"';");
				     if(EResultSet.next())
				       Credit.add(EResultSet.getObject(1).toString()); 
	    		     else
	    		    	 JOptionPane.showMessageDialog(null,"Data has been changed of Database 'termresultcalculator' from out the scope of this application.\nIt may create further problem while this application is running.\nPlease, Restore the Database 'termresultcalculator' from your backup file.","Potential Error !!",JOptionPane.ERROR_MESSAGE);
	    			}
			  } 
			catch (SQLException e) 
			  {
				Credit.clear();
				JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getCreditHour() method", JOptionPane.ERROR_MESSAGE);
			    }
			
			closeAll();
			return Credit;
		}
	
	//preparing data for setting up Result Panel according to a student's roll
	public boolean hasValidStudentInformation(String Roll, String ExamYear)
	{
		Flag = true;
		closeAll();
		DataTransfer.refreshAll();
		
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
			
			if(ExamYear.equalsIgnoreCase("all"))
			  for(int i=0; i<8 ;i++)
			     {
				   EResultSet = EStatement.executeQuery("SELECT course_no,total,exam_type,exam_year FROM "+DataTable[Serial[i]]+" WHERE roll = '"+Roll+"';");
		           EResultSet.previous();
				   while(EResultSet.next())
		                {
		    	          DataTransfer.Courses.add(EResultSet.getObject(1).toString());
		    	          TempString = EResultSet.getObject(2).toString();
		    	          DataTransfer.ExamType.add(EResultSet.getObject(3).toString());
		    	          DataTransfer.Total.add(TempString);
		    	          DataTransfer.GradePoint.add(Calculation.getGradePoint(TempString));
		    	          DataTransfer.LetterGrade.add(Calculation.getLetterGrade(TempString));
		    	          String X = EResultSet.getObject(4).toString();
		    	          if(!DataTransfer.ExamYears.contains(X))
		    	        	  DataTransfer.ExamYears.add(X);
		    	            
		                }
			     }
			
			else
			  for(int i=0; i<8 ;i++)
			     {
				   EResultSet = EStatement.executeQuery("SELECT course_no, total, exam_type FROM "+DataTable[Serial[i]]+" WHERE roll = '"+Roll+"' AND exam_year = '"+ExamYear+"';");
		           EResultSet.previous();
				   while(EResultSet.next())
		                {
		    	          DataTransfer.Courses.add(EResultSet.getObject(1).toString());
		    	          TempString = EResultSet.getObject(2).toString();
		    	          DataTransfer.Total.add(TempString);
		    	          DataTransfer.GradePoint.add(Calculation.getGradePoint(TempString));
		    	          DataTransfer.LetterGrade.add(Calculation.getLetterGrade(TempString));
		    	          DataTransfer.ExamType.add(EResultSet.getObject(3).toString());
		                }
			     }
		
			if(DataTransfer.Courses.size()<=0)
				 Flag = false;
			
		  }  
		catch (SQLException e) 
		  {
			DataTransfer.refreshAll();
			Flag = false;
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in hasValidStudentInformation() method", JOptionPane.ERROR_MESSAGE);
		    }
		closeAll();
		return Flag;
	}
	
	
// -------------------------------------preparing data for setting up Result Panel according to course ---------------------------------
	public boolean hasValidCourseLabInformation(String Course, String ExamYear)
	{
		Flag = true;
		closeAll();
		DataTransfer.refreshAll();
		TempString = getDataTable(Course);
		
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
			EResultSet = EStatement.executeQuery("SELECT roll, total, exam_type FROM "+TempString+" WHERE course_no = '"+Course+"' AND exam_year = '"+ExamYear+"' ORDER BY batch DESC, roll ASC ;");
			EResultSet.previous();
			while(EResultSet.next())
			     {
				  DataTransfer.Roll.add(EResultSet.getObject(1).toString());
				  TempString = EResultSet.getObject(2).toString();
				  DataTransfer.Total.add(TempString);
				  DataTransfer.ExamType.add(EResultSet.getObject(3).toString());
				  DataTransfer.LetterGrade.add(Calculation.getLetterGrade(TempString));
				  DataTransfer.GradePoint.add(Calculation.getGradePoint(TempString));
			     }
			if(DataTransfer.Roll.size()<=0)
				Flag = false;
		  } 
		
		catch (SQLException e) 
		  {
			DataTransfer.refreshAll();
			Flag = false;
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in hasValidCourseLabInformation() method", JOptionPane.ERROR_MESSAGE);
		    }
		
		closeAll();
		return Flag;
	}
	
	public boolean hasValidCourseTheoryInformation(String Course, String ExamYear)
	{
		Flag = true;
		closeAll();
		DataTransfer.refreshAll();
		TempString = getDataTable(Course);
		
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
			EResultSet = EStatement.executeQuery("SELECT roll, m_section_a, m_section_b, m_performance, total, exam_type FROM "+TempString+" WHERE course_no = '"+Course+"' AND exam_year = '"+ExamYear+"' ORDER BY batch DESC, roll ASC ;");
			EResultSet.previous();
			while(EResultSet.next())
			     {
				  DataTransfer.Roll.add(EResultSet.getObject(1).toString());
				  DataTransfer.SectionA.add(EResultSet.getObject(2).toString());
				  DataTransfer.SectionB.add(EResultSet.getObject(3).toString());
				  DataTransfer.Performance.add(EResultSet.getObject(4).toString());
				  TempString = EResultSet.getObject(5).toString();
				  DataTransfer.Total.add(TempString);
				  DataTransfer.ExamType.add(EResultSet.getObject(6).toString());
				  DataTransfer.LetterGrade.add(Calculation.getLetterGrade(TempString));
				  DataTransfer.GradePoint.add(Calculation.getGradePoint(TempString));
			     }
			if(DataTransfer.Roll.size()<=0)
				Flag = false;
		  } 
		
		catch (SQLException e) 
		  {
			DataTransfer.refreshAll();
			Flag = false;
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in hasValidCourseTheoryInformation() method", JOptionPane.ERROR_MESSAGE);
		  }
		
		closeAll();
		return Flag;
	}
	
	
	public Vector<String>getNameofRolls(Vector<String>Rolls)
	{
		Vector<String>Name = new Vector<String>();
		closeAll();
		try 
		  {
			int Size =Rolls.size();
			EStatement = TempCM.MyConnection.createStatement();
			
			for(int i=0; i<Size;i++)
				{
					EResultSet = EStatement.executeQuery("SELECT name FROM students_information WHERE roll = '"+Rolls.elementAt(i)+"';");
				    if(EResultSet.next())
				    	Name.add(EResultSet.getObject(1).toString());
				}
	      } 
	catch (SQLException e) 
	  {
		Name.clear();
		JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getNameofRolls() method", JOptionPane.ERROR_MESSAGE);
	  }
	closeAll();
	return Name;
}
	
// -------------------------------- Preparing data for Result Panel of Term ------------------------------------------------------------
	
	public boolean hasValidTermInformation(String Term, String ExamYear)
	{
		Flag = true;
		TempCoursesForTerm.clear();
		TempRollsForTerm.clear();	
		TempCoursesForTerm.addAll(getCoursesForTerm(Term));
		TempRollsForTerm.addAll(getRollsForTerm(TempCoursesForTerm,ExamYear));
		
		if(TempCoursesForTerm.size()>0 && TempRollsForTerm.size()>0)
		  {
			DataTransfer.refreshAll();
			DataTransfer.Courses.addAll(TempCoursesForTerm);
			DataTransfer.Roll.addAll(TempRollsForTerm);
		  }
		else
			Flag = false;
		return Flag;
	}
	
	
	
	public String[][] getAllNecessaryDataForTerm(Vector<String> Courses,Vector<String> Rolls,String ExamYear) 
	{
		closeAll();
		int CourseSize,RollSize;
		String SQLStatement,Roll,Course,Table;
		
		CourseSize = Courses.size();
		RollSize = Rolls.size();
		
		String Data [][] = new String[RollSize][CourseSize+4];
		
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
		    
			for(int i=0 ; i<RollSize ; i++)                  // setting rolls at the first column of each row
				 Data[i][0] = Rolls.elementAt(i);
			   
			for(int i=0 ; i<RollSize ; i++)
				for(int j=0 ; j<CourseSize ; j++ )
			       {
				      Roll = Rolls.elementAt(i);
				      Course = Courses.elementAt(j);
				      Table = getDataTable(Course);
				      SQLStatement = "SELECT total FROM "+Table+" WHERE roll = '"+Roll+"' AND exam_year = '"+ExamYear+"' AND course_no = '"+Course+"';";
				    	
				      EResultSet = EStatement.executeQuery(SQLStatement);
				    	
				      if(EResultSet.next())
				    	Data[i][j+1]= Calculation.getLetterGrade(EResultSet.getObject(1).toString());
			           else 
				    	   Data[i][j+1] = "-";
			       }
			
			
			for(int i=0 ; i<RollSize ; i++)
			   {
				 ForCalculation.clear();
				 for(int j=0 ; j<CourseSize ; j++ )   
				      ForCalculation.add(Data[i][j+1]);
				 
				 String [] X = getTotalGP(Courses);
				 
				 Data[i][CourseSize+1] = X[1];
				 Data[i][CourseSize+2] = X[0];
				 Data[i][CourseSize+3] = X[2];
				 
			   }
		  } 
		catch (SQLException e) 
		  {
			for(int i = 0; i<RollSize ; i++)
				for(int j=0 ; j<CourseSize ; j++ )
					Data[i][j] = "!!";
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getAllNecessaryDataForTerm() method", JOptionPane.ERROR_MESSAGE);
		  }
		return Data;
	}
	
	
	private String[] getTotalGP(Vector<String>Courses) throws SQLException 
    {
	    int Size = ForCalculation.size();
	    float TotalCreditHour , Total, Temp;
	    String [] Data = new String[3];
	    Vector<Float> Credits;
	    String LetterGrade;
	    
	    Credits = getCreditHour(Courses);
	    TotalCreditHour = Total = Temp = 0; 
	    
    	for(int i=0; i<Size ; i++  )
    	   {
    		LetterGrade = ForCalculation.elementAt(i);
    		if(LetterGrade.equals("-")||LetterGrade.equalsIgnoreCase("F"))
    			continue;
    		else
    		  {
    			Temp = Calculation.getGradePointFromletterGrade(LetterGrade);
    			TotalCreditHour += Credits.elementAt(i);
    			Total += Temp*Credits.elementAt(i);
    		  }
    	   }
    	if(Total==0||TotalCreditHour==0) 
    	  {
    		Data[0] = Float.toString(Float.valueOf(TwoDecimal.format(Total)));
    		Data[1] = Float.toString(Float.valueOf(TwoDecimal.format(TotalCreditHour)));
    		Data[2] = Float.toString(Float.valueOf(TwoDecimal.format(0)));
    	  }
    	else
    	  {
    		Data[0] = Float.toString(Float.valueOf(TwoDecimal.format(Total)));
    		Data[1] = Float.toString(Float.valueOf(TwoDecimal.format(TotalCreditHour)));
    		Data[2] = Float.toString(Float.valueOf(TwoDecimal.format(Total/TotalCreditHour)));
    	  }
    	
    	return  Data ;
    }
	
	private Vector <Float> getCreditHour(Vector<String>Courses) throws SQLException
	{
		Vector<Float> Credits = new Vector<Float>();
		Statement PrivateStatement = TempCM.MyConnection.createStatement();
		ResultSet PrivateResultSet;
		
		for(int i = 0; i<Courses.size(); i++)
		   {
		 	 PrivateResultSet = PrivateStatement.executeQuery("SELECT course_credit FROM courses WHERE course_no = '"+Courses.elementAt(i)+"';");
		 	 if(PrivateResultSet.next())
		 	   {
		 		 String x = PrivateResultSet.getObject(1).toString();
		         Credits.add( Float.parseFloat(x) );
		 	   }
		   }
		
		PrivateResultSet = null;
		PrivateStatement = null;
		return Credits;
	}
	


	private Vector<String> getCoursesForTerm(String Term)
    {
	
		String CourseType8Character,CourseType7Character,CourseType9Character,SQLStatement;
		CoursesAsTerm.clear();
		closeAll();
	
		if(Term.equalsIgnoreCase("1st Year 1st Semester"))
			{
			    CourseType9Character = "_____11__";
			    CourseType8Character = "____11__";
				CourseType7Character = "___11__" ;
			}
		else if (Term.equalsIgnoreCase("1st Year 2nd Semester"))
			{
			    CourseType9Character = "_____12__";
			    CourseType8Character = "____12__";
				CourseType7Character = "___12__" ;
			}
		else if (Term.equalsIgnoreCase("2nd Year 1st Semester"))
			{
			    CourseType9Character = "_____21__";
			    CourseType8Character = "____21__";
				CourseType7Character = "___21__" ;
			}
		else if (Term.equalsIgnoreCase("2nd Year 2nd Semester"))
			{
			    CourseType9Character = "_____22__";
				CourseType8Character = "____22__";
				CourseType7Character = "___22__" ;
			}
		else if (Term.equalsIgnoreCase("3rd Year 1st Semester"))
			{
			    CourseType9Character = "_____31__";
			    CourseType8Character = "____31__";
				CourseType7Character = "___31__" ;
				
			}
		else if (Term.equalsIgnoreCase("3rd Year 2nd Semester"))
			{
			    CourseType9Character = "_____32__";
				CourseType8Character = "____32__";
				CourseType7Character = "___32__" ;
			}
		else if (Term.equalsIgnoreCase("4th Year 1st Semester"))
			{
			    CourseType9Character = "_____41__";
			    CourseType8Character = "____41__";
				CourseType7Character = "___41__" ;
			}
		else if (Term.equalsIgnoreCase("4th Year 2nd Semester"))
			{
			    CourseType9Character = "_____42__";
				CourseType8Character = "____42__";
				CourseType7Character = "___42__" ;
			}
		else
			{
			    CourseType9Character = "_____XX__";
			    CourseType8Character = "____XX__";
				CourseType7Character = "___XX__" ;
			}
	
		SQLStatement ="SELECT course_no FROM courses WHERE course_no LIKE '"+CourseType8Character+"' OR course_no LIKE '"+CourseType7Character+"' OR course_no LIKE '"+CourseType9Character+"';";
	
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
			EResultSet = EStatement.executeQuery(SQLStatement);
			while(EResultSet.next())
			  	{
			  	 TempString = EResultSet.getObject(1).toString();
			  	 if(!CoursesAsTerm.contains(TempString))
			  		CoursesAsTerm.add(TempString);
			  	}
			Vector<String> Theory = new Vector<String>();
			Vector<String> Sessional = new Vector<String>();
			String Temp;
			
			for(int i=0;i<CoursesAsTerm.size();i++)              // spliting theory & sessional courses apart and then add them sequencially
			   {
				Temp = CoursesAsTerm.elementAt(i);
			      if(Integer.parseInt(Character.toString(Temp.charAt(Temp.length()-1)))%2==0)
			    	  Sessional.add(Temp);
			      else 
			    	  Theory.add(Temp);
			   }
			CoursesAsTerm.clear();
			CoursesAsTerm.addAll(Theory);
			CoursesAsTerm.addAll(Sessional);
		  } 
		catch (SQLException e) 
		  {
			CoursesAsTerm.clear();
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getCoursesForTerm() method", JOptionPane.ERROR_MESSAGE);		    
		  }
		return CoursesAsTerm;
    }

    private Vector<String> getRollsForTerm(Vector<String>Courses,String ExamYear)
    {
	   RollsAsTerm.clear();
	   closeAll();
	   String SQLStatement;
	
	   try 
	    {
		  EStatement = TempCM.MyConnection.createStatement();
		  int Size = Courses.size();
		  for(int i=0;i<Size;i++)
	         {
			  SQLStatement = "SELECT roll FROM "+getDataTable(Courses.elementAt(i))+" WHERE course_no = '"+Courses.elementAt(i)+"' AND exam_year = '"+ExamYear+"' ORDER BY batch DESC, roll ASC ;";
	          EResultSet = EStatement.executeQuery(SQLStatement);
	          EResultSet.previous();
	          while(EResultSet.next())
	               {
	        	    TempString = EResultSet.getObject(1).toString();
	        	    if(!RollsAsTerm.contains(TempString))
	        	  	   RollsAsTerm.add(TempString);
	               }
	         }
	    } 
	   catch (SQLException e)
        {
	     RollsAsTerm.clear();
	     JOptionPane.showMessageDialog(null, "Error in getRollsOfTerm() method \n"+e.getErrorCode()+" : "+e.getMessage());
        }
	   return RollsAsTerm;
    }
    
// ---------------------------------------------For term, course, term in result handle panel -------------------------------------------------------
// -----------------------------getting existing examination year according to batch, student or course----------------------------------------------
	
    public Vector<String> getExistingExamYear(String Target,int Decision)
	{
		closeAll();
		ExamYears.clear();
		
		if(Decision==1)
		  {
		   try 
		     {
			   EStatement = TempCM.MyConnection.createStatement();
			   for(int i = 0; i<8; i++)
			      {
			        EResultSet = EStatement.executeQuery("SELECT DISTINCT exam_year FROM "+DataTable[Serial[i]] +";");
		            while(EResultSet.next())
		    	         {
		        	      TempString = EResultSet.getObject(1).toString();
		        	      if(!ExamYears.contains(TempString))
		    	    	     ExamYears.add(TempString); 
		    	         }
			      }
		     } 
		   catch (SQLException e) 
		     {
			   ExamYears.clear();
			   JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getExistingExamYear() method in batchportion ", JOptionPane.ERROR_MESSAGE);
			  }
		  }
		else if(Decision==2)
		  {
		   try 
		     {
			   EStatement = TempCM.MyConnection.createStatement();
			   for(int i = 0; i<8; i++)
			      {
			        EResultSet = EStatement.executeQuery("SELECT DISTINCT exam_year FROM "+DataTable[Serial[i]] +" WHERE roll = '"+Target+"';");
		            while(EResultSet.next())
		    	         {
		        	      TempString = EResultSet.getObject(1).toString();
		        	      if(!ExamYears.contains(TempString))
		    	    	     ExamYears.add(TempString); 
		    	         }
			      }
		     } 
		   catch (SQLException e) 
		     {
			   ExamYears.clear();
			   JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getExistingExamYear() method in rollportion ", JOptionPane.ERROR_MESSAGE);
		    }
		  }
		else
		  {
			TempString = getDataTable(Target);
			try 
			  {
				EStatement = TempCM.MyConnection.createStatement();
				EResultSet = EStatement.executeQuery("SELECT DISTINCT exam_year FROM "+TempString+" WHERE course_no = '"+Target+"';");
				while(EResultSet.next())
    	              ExamYears.add(EResultSet.getObject(1).toString()); 
				EResultSet.close();
			  } 
			catch (SQLException e) 
			  {
				   ExamYears.clear();
				   JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getExistingExamYear() method in courseportion ", JOptionPane.ERROR_MESSAGE);
			  }
		  }
		closeAll();
		return ExamYears;
	}
	
	// for getting enrolled student's roll according to batch year
	public Vector<String> getRollsofBatch(String Batch)
	{
		closeAll();
		RollsOfBatch.clear();
		try 
		  {
			EStatement = TempCM.MyConnection.createStatement();
			EResultSet = EStatement.executeQuery("SELECT roll FROM students_information WHERE batch = '"+Batch+"' ORDER BY roll ASC;");
		    while(EResultSet.next())
		    	RollsOfBatch.add(EResultSet.getObject(1).toString());
		  } 
		catch (SQLException e) 
		  {
			RollsOfBatch.clear();	
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getRollofBatch() method ", JOptionPane.ERROR_MESSAGE);
		  }
		closeAll();
		return RollsOfBatch;
	}
	
	
	
	// ------------------------------------------------------- private for this class ---------------------------------------------------------------
	

	private String getDataTable(String Course)
	{
		int Year,Type;
		String Table="";
		
		if(Course.length()==8)   //like cse ,eee,ece
		  {
			Year = Integer.parseInt(Character.toString(Course.charAt(4)));
			Type = Integer.parseInt(Character.toString(Course.charAt(7)))%2;
		  }
		else if(Course.length()==9)
		  {
			 Year = Integer.parseInt(Character.toString(Course.charAt(5)));
			 Type = Integer.parseInt(Character.toString(Course.charAt(8)))%2;
		  }
		else   // me,ce,le
		  {
			 Year = Integer.parseInt(Character.toString(Course.charAt(3)));
			 Type = Integer.parseInt(Character.toString(Course.charAt(6)))%2;
		  }
		
		if(Type == 0)
		  Table =DataTable[Year-1]; // for lab_******_year, see the constructor
		else
		  Table =DataTable[Year+3]; // for theory_******_year, see the constructor
		
		return Table;
	}
	
	private void  closeAll()
	{
		if(EResultSet!=null)
		   EResultSet = null;
		if(EStatement!=null)
		   EStatement = null;
	}

}
