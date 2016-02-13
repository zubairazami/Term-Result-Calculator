package DB;
import java.sql.Connection;       
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import MainPackage.*;


public class ConnectionManager 
{
	public Connection MyConnection;
	private Connection VDBMSC,RootConnection;
	private Statement RootStatement ;
	private ResultSet RootResultSet;
	
	private String Username,Password;
	private String ConnectionString = "jdbc:mysql://localhost/termresultcalculator";
	private String ConnectionStringNoDB= "jdbc:mysql://localhost/";
	private Controll TempControll;
	private ResultSet VDBMSRS;
	private Statement VDBMSS;
	private String SQL[] = new String[32];
	
	public ConnectionManager(Controll x)
	{
		TempControll = x;
		SQL[0]  =  "CREATE DATABASE termresultcalculator;";
		SQL[1]  =  "USE termresultcalculator;";
		SQL[2]  =  "DROP TABLE IF EXISTS `batch`;";
		SQL[3]  =  "DROP TABLE IF EXISTS `courses`;";
		SQL[4]  =  "DROP TABLE IF EXISTS `student's information`;";
		SQL[5]  =  "DROP TABLE IF EXISTS `lab_first_year`;";
		SQL[6]  =  "DROP TABLE IF EXISTS `lab_second_year`;";
		SQL[7]  =  "DROP TABLE IF EXISTS `lab_third_year`;";
		SQL[8]  =  "DROP TABLE IF EXISTS `lab_fourth_year`;";
		SQL[9]  =  "DROP TABLE IF EXISTS `theory_third_year`;";
		SQL[10] =  "DROP TABLE IF EXISTS `theory_first_year`;";
		SQL[11] =  "DROP TABLE IF EXISTS `theory_fourth_year`;";
		SQL[12] =  "DROP TABLE IF EXISTS `theory_second_year`;";
		SQL[13] =  "CREATE TABLE IF NOT EXISTS `students_information` (`Roll` varchar(7) NOT NULL,`name` varchar(100) NOT NULL,`contact` varchar(100) NOT NULL,`batch` int( 10 ) unsigned NOT NULL,PRIMARY KEY (`Roll`));";
		SQL[14] =  "CREATE TABLE IF NOT EXISTS `batch` (`batch_no` int(4) unsigned NOT NULL,`number_of_student` int(3) unsigned NOT NULL,PRIMARY KEY (`batch_no`));";
		SQL[15] =  "CREATE TABLE IF NOT EXISTS `courses` (`course_no` varchar(10) NOT NULL,`course_name` varchar(100) NOT NULL,`course_credit` float NOT NULL,PRIMARY KEY (`course_no`));";
		SQL[16] =  "CREATE TABLE IF NOT EXISTS `lab_first_year` (`roll` varchar(7) NOT NULL,`exam_year` int(10) unsigned NOT NULL,`course_no` varchar(10) NOT NULL,`batch` int(10) unsigned NOT NULL,`exam_type` varchar(15) NOT NULL,`attendance_mark` float unsigned NOT NULL,`quiz_mark` float unsigned NOT NULL,`cv_mark` float unsigned NOT NULL,`performance_mark` float unsigned NOT NULL,`total` float unsigned NOT NULL,PRIMARY KEY (`roll`,`exam_year`,`course_no`));";
		SQL[17] =  "CREATE TABLE IF NOT EXISTS `theory_first_year` (`roll` varchar(7) NOT NULL,`exam_year` int(10) unsigned NOT NULL,`course_no` varchar(10) NOT NULL,`batch` int(10) unsigned NOT NULL,`exam_type` varchar(15) NOT NULL,`m_performance` float unsigned NOT NULL,`m_section_a` float unsigned NOT NULL,`m_section_b` float unsigned NOT NULL,`total` float unsigned NOT NULL,PRIMARY KEY (`roll`,`exam_year`,`course_no`));";
		SQL[18] =  "CREATE TABLE IF NOT EXISTS `theory_second_year` AS (SELECT * FROM `theory_first_year`);";
		SQL[19] =  "CREATE TABLE IF NOT EXISTS `theory_third_year` AS (SELECT * FROM `theory_first_year`);";
		SQL[20] =  "CREATE TABLE IF NOT EXISTS `theory_fourth_year` AS (SELECT * FROM `theory_first_year`);";
		SQL[21] =  "CREATE TABLE IF NOT EXISTS `lab_second_year` AS (SELECT * FROM `lab_first_year`);";
		SQL[22] =  "CREATE TABLE IF NOT EXISTS `lab_third_year` AS (SELECT * FROM `lab_first_year`);";
		SQL[23] =  "CREATE TABLE IF NOT EXISTS `lab_fourth_year` AS (SELECT * FROM `lab_first_year`);";
		SQL[24] =  "ALTER TABLE `theory_second_year` ADD PRIMARY KEY ( `roll` , `exam_year` , `course_no` ) ;";
		SQL[25] =  "ALTER TABLE `theory_third_year` ADD PRIMARY KEY ( `roll` , `exam_year` , `course_no` ) ;";
		SQL[26] =  "ALTER TABLE `theory_fourth_year` ADD PRIMARY KEY ( `roll` , `exam_year` , `course_no` ) ;";
		SQL[27] =  "ALTER TABLE `lab_second_year` ADD PRIMARY KEY ( `roll` , `exam_year` , `course_no` ) ;";
		SQL[28] =  "ALTER TABLE `lab_third_year` ADD PRIMARY KEY ( `roll` , `exam_year` , `course_no` ) ;";
		SQL[29] =  "ALTER TABLE `lab_fourth_year` ADD PRIMARY KEY ( `roll` , `exam_year` , `course_no` ) ;";
		SQL[30] =  "DROP TABLE IF EXISTS `login_information`;";
	    SQL[31] =  "CREATE TABLE IF NOT EXISTS `login_information` (`user` VARCHAR( 100 ) NOT NULL ,`password` VARCHAR( 200 ) NOT NULL ,PRIMARY KEY ( `user` ));";
	}

	public boolean createConnection()
	{
	  try 
	    {
	   	 if(validateConnection() && validateConnectiontoDBMS())
			return true; 
	   	 validateConnectiontoDBMS();
	  	 MyConnection = DriverManager.getConnection(ConnectionString,"trc","trc");
		 return true;
	    }	  
	  catch (SQLException e) 
		{  
		   if(e.getErrorCode()==1045)
			 TempControll.DBMSPanelObject.createDBMSPanelDialog(); // always at the begining user need to provide password for Mysql DBMS
		   if(e.getErrorCode()==1049)
		     JOptionPane.showConfirmDialog(null, "Required database is missing.\nMost probably, It has been deleted recently.\nTo overcome this problem please, restart Term Result Calculator.", "Database Missing", JOptionPane.ERROR_MESSAGE);
		   if(e.getErrorCode()==0)
		      JOptionPane.showMessageDialog(null,"Can't connect to MySQL server on 'localhost'.\n Ensure MySQL Database Management System is running on your computer.", "Connection Interruption", JOptionPane.ERROR_MESSAGE);
		   return false; 
		}
	}
	
	public void setUserPass(String U,String P)
	{
		Username = U;
		Password = P;
	}
	
	public int hasGrantPreviliges()
	{
		int Code = 1;
		try
		 {
			closeRoot();
			RootConnection = DriverManager.getConnection(ConnectionStringNoDB,Username,Password);
			RootStatement = RootConnection.createStatement();
			RootStatement.executeUpdate("DELETE FROM mysql.user WHERE user = 'trc';");
			RootStatement.executeUpdate("FLUSH PRIVILEGES;");
			RootResultSet = RootStatement.executeQuery("SELECT Grant_priv FROM mysql.user WHERE user = '"+Username+"';");
			
			if(RootResultSet.next())
			  {
				if(RootResultSet.getObject(1).toString().equalsIgnoreCase("Y"))
                   createPrivateMySqlUser();
				else
	               Code = -1;
			  }
			else
			    Code = -1;
		 }
		catch(SQLException e)
		 {
			if(e.getErrorCode()==0)
			  {
			   Code = 0;
			   JOptionPane.showMessageDialog(null,"Can't connect to MySQL server on 'localhost'.\n Ensure MySQL Database Management System is running on your computer.", "Connection Interruption", JOptionPane.ERROR_MESSAGE);
			  }
			else if(e.getErrorCode()==1045)
			  {
				Code = 1045;
				JOptionPane.showMessageDialog(null,"Invalid Username & Password Combination for Mysql Database Management System.", "Unavailable Username-Password Combination", JOptionPane.ERROR_MESSAGE);
			  }
			else if(e.getErrorCode()==1227|| e.getErrorCode()==1142)
			  Code = -1;
			else
			  JOptionPane.showMessageDialog(null, e.getErrorCode()+" : "+e.getMessage());
		 }
		closeRoot();
		return Code;
	}
	
	
	private void createPrivateMySqlUser() throws SQLException
	{
		RootStatement.executeUpdate("CREATE USER 'trc'@'localhost' IDENTIFIED BY  'trc';");
		RootStatement.executeUpdate("GRANT SELECT ,INSERT ,UPDATE ,DELETE ,CREATE ,DROP ,FILE ,INDEX ,ALTER ,CREATE TEMPORARY TABLES ,CREATE VIEW ,SHOW VIEW ,CREATE ROUTINE,ALTER ROUTINE,EXECUTE ON * . * TO  'trc'@'localhost' IDENTIFIED BY  'trc' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;");
	}

	public void closeConnection()
	{
	   if(MyConnection!=null)
		 {
		  try{MyConnection.close();} 
	      catch (SQLException e) 
	          {
	    	     JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in closeConnection() method ", JOptionPane.ERROR_MESSAGE); 
	    	  }
		 }
	}
	
	private boolean validateConnection()
	{		
		if(MyConnection==null) return false;
		else
		    try {
			     if(MyConnection.isValid(0))
			        return true;
			     else 
			       {
				    return false; // it would work when created connection looses it's validity because of MySQL being turned off during the usage of this application  or any connection kill (by administrator)
				   }
		        } 
		    catch (SQLException e) 
		        {
		         return false;  // see the manual of isValid function of  
		        }
	}
	
	private boolean validateConnectiontoDBMS() throws SQLException
	{
		int NumberOfRows,Response;
		VDBMSC = DriverManager.getConnection(ConnectionStringNoDB, "trc","trc");
		VDBMSS = VDBMSC.createStatement();	
		VDBMSRS = VDBMSS.executeQuery("SHOW DATABASES LIKE 'termresultcalculator'");
		if(!VDBMSRS.next())
		   for(String sql: SQL)
		 		VDBMSS.executeUpdate(sql);
		else
		  {
			VDBMSS.executeUpdate("USE termresultcalculator;");
		    VDBMSRS = VDBMSS.executeQuery("SHOW TABLES");
		    VDBMSRS.last();
		    NumberOfRows = VDBMSRS.getRow();		    
		    if(NumberOfRows<12)
		      {
		    	if(JOptionPane.showConfirmDialog(null, "Unauthorized change has been found in database : termresultcalculator .\nProbably, one or more Tables have been dropped which may stop the application to work correctly.\nDo you want to create the database again? ", "Change in Database Table", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
		    	  {
		    		for(int i = 2; i<=31; i++)
			    	  VDBMSS.executeUpdate(SQL[i]);
		    		JOptionPane.showMessageDialog(null, "Changes have been made.","Success", JOptionPane.INFORMATION_MESSAGE);
		          }
		      }
		  }
		closeVDBMS();
		return true;
	}
	
	private void closeVDBMS()
	{
		if(VDBMSRS!=null)
		  {
			try {VDBMSRS.close();} 
		    catch (SQLException e1) {e1.printStackTrace();}
		  }
		if(VDBMSS!=null)
		  {
			try {VDBMSS.close();} 
		    catch (SQLException e1) {e1.printStackTrace();}
		  }
	    if(VDBMSC!=null)
	      {
	       try {VDBMSC.close();} 
	       catch (SQLException e) {e.printStackTrace();}
	      }
	}
	
	private void closeRoot()
	{
		try 
		  {
		   if(RootResultSet!=null)
			 RootResultSet.close();
		   if(RootStatement!=null)
			 RootStatement.close();
		   if(RootConnection!=null)
			 RootConnection.close();
		  } 
		catch (SQLException e) 
		  {
	    	 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in closeRoot() method ", JOptionPane.ERROR_MESSAGE);
		  }
	}
}