package DB;

import java.sql.ResultSet;    
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

import MainPackage.Controll;

public class Enrollment {

	private ConnectionManager TempCM ;
	private Statement EStatement;
	private ResultSet EResultSet;
	private Vector<String>CourseNoFromDB = new Vector<String>();
	private Vector<String>ExistingCourses = new Vector<String>();
	
	public Enrollment(Controll x)
	{
	 TempCM = x.ConnectionManagerObject;  
	}
	
	public boolean batchExists(int Given)
	{
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery("SELECT batch_no FROM batch");
			 if(!EResultSet.next())
		       {
		  	    closeAll();
		  	    return false;
		       }
		     else
		       EResultSet.previous();  
		      
             while(EResultSet.next())
		        {
            	 if( Given==Integer.parseInt( EResultSet.getObject(1).toString()) )
		  	       {
		  		     closeAll();
		  		     return true;
		  	       }
		        }
		     return false;
		    } 
		catch (SQLException e) 
		    {
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in batchExists() method ", JOptionPane.ERROR_MESSAGE);
			 return false;
		    }
	}
	
	public boolean enrollBatch(Vector<String>roll,Vector<String>name,Vector<String>contact,int Batch,int Students)
	{
		String SQL;
		if(TempCM.createConnection())
		   try {
			    EStatement = TempCM.MyConnection.createStatement();
			    for(int i =0;i<Students;i++)
			       {
				    SQL = "INSERT INTO `students_information` (`Roll` ,`Name` ,`Contact` ,`Batch`)VALUES ('"+roll.elementAt(i)+"', '"+name.elementAt(i)+"', '"+contact.elementAt(i)+"', '"+Integer.toString(Batch)+"');";
				    EStatement.executeUpdate(SQL); 
			       }
			    SQL =  "INSERT INTO `batch` (`batch_no` ,`number_of_student`)VALUES ('"+Batch+"', '"+Students+"');";
			    EStatement.executeUpdate(SQL); 
			    return true;
		       }  
		   catch (SQLException e) 
		       {
				JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in enrollBatch() method ", JOptionPane.ERROR_MESSAGE);
			    return false;
		       } 
		else return false;
	}
	
	public Vector<String>getIdenticalCourses()
	{
		return ExistingCourses;
	}
	
	public boolean enrollCourses(String CourseNo, String CourseTitle, float CourseCredit) 
	{
		String SQL = new String();
		if(coursesExists(CourseNo))
		  return false;
		else
		  {
			try 
			   {
			     EStatement = TempCM.MyConnection.createStatement();
			     SQL = "INSERT INTO `courses` (`course_no` ,`course_name` ,course_credit)VALUES ('"+CourseNo+"', '"+CourseTitle+"', '"+CourseCredit+"');";
			     EStatement.executeUpdate(SQL);
			     closeAll();
			     return true;
			   } 
			catch (SQLException e) 
			   {
				JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getIdenticalCourses() method ", JOptionPane.ERROR_MESSAGE);
				return false;
			   }
		  }
		
	}	
	private boolean coursesExists(String CourseNo)
	{
		CourseNoFromDB.clear();
		try {
			 EStatement = TempCM.MyConnection.createStatement();
			 EResultSet = EStatement.executeQuery("SELECT course_no FROM courses");
			 if(!EResultSet.next())
		       {
		  	    closeAll();
		  	    return false;
		       }
		     else
		       EResultSet.previous(); 
			
			 while(EResultSet.next())
			   	 CourseNoFromDB.add(EResultSet.getObject(1).toString());
			 closeAll();
			 if(CourseNoFromDB.contains(CourseNo))
				{
				 JOptionPane.showMessageDialog(null,CourseNo+ " has already been enrolled.", "Existing Course", JOptionPane.ERROR_MESSAGE);
				 return true;
				}
		     return false;
		    } 
		 catch (SQLException e) 
		    {
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in coursesExist() method ", JOptionPane.ERROR_MESSAGE);
			 return false;
		    }
		
	}
	
	public boolean enrollCourses(int NumberOfCourses, Vector<String>CourseNo,Vector<String>CourseTitle,Vector<String>CourseCredit)
	{
		String SQL = new String(); 
		
		if(TempCM.createConnection())
		  try 
			{
			  if(courseExists(CourseNo))	 
				 return false;
			  else
				{
				  EStatement = TempCM.MyConnection.createStatement();
				  for(int i=0;i<NumberOfCourses;i++)
				    {
				      SQL = "INSERT INTO `courses` (`course_no` ,`course_name` ,course_credit)VALUES ('"+CourseNo.elementAt(i)+"', '"+CourseTitle.elementAt(i)+"', '"+Float.parseFloat(CourseCredit.elementAt(i))+"');";
				      EStatement.executeUpdate(SQL);
				    }
				 closeAll();
				 return true;
				}
			} 
		  catch (SQLException e) 
			{
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in enrollCourses() method ", JOptionPane.ERROR_MESSAGE);
			 return false;
			}
		else return false;
	}
	
	private boolean courseExists( Vector<String>CourseNo )
	{
		CourseNoFromDB.clear();
		ExistingCourses.clear();
			try 
			  {
				EStatement = TempCM.MyConnection.createStatement();
				EResultSet = EStatement.executeQuery("SELECT course_no FROM courses");
				if(!EResultSet.next())
			       {
			  	    closeAll();
			  	    return false;
			       }
			    else
			       EResultSet.previous(); 
				
				while(EResultSet.next())
				   	 CourseNoFromDB.add(EResultSet.getObject(1).toString());

                for(int i =0;i<CourseNo.size();i++)
                   	if(CourseNoFromDB.contains(CourseNo.elementAt(i)) )
                		ExistingCourses.add(CourseNo.elementAt(i));
                
                closeAll();
                if(ExistingCourses.isEmpty())             	
                  return false;
                else return true;
			  } 
			catch (SQLException e) 
			  {
				JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in coursesExists() method ", JOptionPane.ERROR_MESSAGE);
				return false;
			  }
	}
	
	private void  closeAll()
	{
		if(EResultSet!=null)
		   EResultSet = null;
		if(EStatement!=null)
		   EStatement = null;
	}

	
}
