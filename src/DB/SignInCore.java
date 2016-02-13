package DB;

import DB.ConnectionManager;  
import MainPackage.Controll;
import java.security.MessageDigest;    
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;


public class SignInCore 
{ 	
	private String User,Pass, TempUser,TempPass;
	private String UP[] = new String[2];
	Vector<String> UserList = new Vector<String>();
	private Controll TempControll;
	private ConnectionManager TempCM ;
	private Statement SICStatement;
	private ResultSet SICResultSet;
	
	public SignInCore(Controll x)
	{
		TempControll = x;
		TempCM = x.ConnectionManagerObject;
	}
	
	public boolean noListedUser() 
	{
		TempCM.createConnection();
		try 
		  {
		   SICStatement = TempCM.MyConnection.createStatement(); // using ConnectionManager's object & MyConnection so that only one connection exists through the whole app
		   SICResultSet = SICStatement.executeQuery("SELECT user FROM login_information");
		   if(!SICResultSet.next())  //means no user is in the table 
		     {
		      closeAll();
		      return true;
		     }
			  else 
			    {
				 closeAll();
				 return false;
			    }
		   } 
		catch (SQLException e) 
		   {
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in createConnection() method", JOptionPane.ERROR_MESSAGE);
		    return false;
		   }
	}
	
	
	public Vector<String> getUserList()
	{
		UserList.clear();
		TempCM.createConnection();
		try 
		   {
			SICStatement = TempCM.MyConnection.createStatement();
			SICResultSet = SICStatement.executeQuery("SELECT user FROM login_information");
			while(SICResultSet.next())
			    UserList.add(encryptionOWN(SICResultSet.getObject(1).toString()));
			closeAll();
			return UserList;
		   } 
		catch (SQLException e) 
		   {
			JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in getUserList() method", JOptionPane.ERROR_MESSAGE);
			return UserList;
		   }
	}
	
	
	public boolean verification(String Username, String Password)
	 {
	   User = encryptionOWN(Username);
	   Pass = encryptionMD5(Password);
	   TempCM.createConnection();
	   try 
	   {
		SICStatement = TempCM.MyConnection.createStatement();
		SICResultSet = SICStatement.executeQuery( "SELECT * FROM login_information WHERE user='"+User+"';");
	    if(!SICResultSet.next())
	      {
	    	closeAll();
	    	return false;
	      }
	    else
	      {
	    	if(Pass.equals(SICResultSet.getObject(2).toString()))
	    	  {
	    		closeAll();
	    		return true;
	    	  }
	    	else
	    	  {
	    		closeAll();
	    	    return false;
	    	  }
	      }
	   } 
	   catch (SQLException e) 
	   {
		
		   JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in verification() method", JOptionPane.ERROR_MESSAGE);
	       return false;
	   }
	 }
	 
	 public boolean addNewUser( String NewUser, String NewPassword) 
	 {	   
		 NewUser = 	encryptionOWN(NewUser);
		 NewPassword = encryptionMD5(NewPassword);
		 
		 TempCM.createConnection();
		 
		 try 
		  {
			SICStatement = TempCM.MyConnection.createStatement();
		    SICResultSet = SICStatement.executeQuery("SELECT * FROM login_information WHERE user='"+NewUser+"';");
		    if(SICResultSet.next())
		      {
		    	closeAll();
		    	return false;
		      }
		    else 
		      {
		    	SICStatement.executeUpdate("INSERT INTO `login_information` (`user` ,`password`)VALUES ('"+NewUser+"', '"+NewPassword+"');");
		        closeAll();
		        return true;
		      }
		    	
		  } 
		 catch (SQLException e) 
		  {
			 JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in addNewUser() method", JOptionPane.ERROR_MESSAGE);
			 return false;
		  }
	 }
	 
	 public boolean deleteUser(String UserToBeDeleted)
	 {
		 UserToBeDeleted = encryptionOWN(UserToBeDeleted);
		 TempCM.createConnection();
		 try
	      {
			 SICStatement = TempCM.MyConnection.createStatement();
			 SICStatement.executeUpdate("DELETE FROM `login_information` WHERE `user` = '"+UserToBeDeleted+"';"); 
			 closeAll();
			 return true;
	      }
	    catch(SQLException e)
	       {
	    	JOptionPane.showMessageDialog(null, e.getErrorCode() + ":" +e.getMessage()+"\n"+"This could occur because of unauthorized change in database.\nPlease Restore the database 'termresultcalculator' or contact service provider. " ,"Error in deleteUser() method", JOptionPane.ERROR_MESSAGE);
	    	return false;
	       }
	 }
 
   private String encryptionOWN(String GivenString)
	 {
	 
	  String Encrypted;	 
	  char ListOfGivenString[] =  GivenString.toCharArray();
	  int AsciiValue;
	  
	  for(int i= 0;i<ListOfGivenString.length;i++ )
	     {
		  AsciiValue = ListOfGivenString[i];
		  
		  if(ListOfGivenString[i]>='A' && ListOfGivenString[i] <='Z')
		     ListOfGivenString[i] = (char)('Z'-AsciiValue+'A');
		   
		  else if(ListOfGivenString[i]>='a' && ListOfGivenString[i] <='z')
		         ListOfGivenString[i] = (char)('z'-AsciiValue+'a');
		        
		  else if(ListOfGivenString[i]>='0' && ListOfGivenString[i] <='9')
	             ListOfGivenString[i] = (char)('9'-AsciiValue+'0');
	             
		  else 
			  ListOfGivenString[i] = ListOfGivenString[i];
	     }
	      
	  	Encrypted = new String(ListOfGivenString);
	  	return Encrypted;  
	 }
   
   public static String encryptionMD5(String Received)
     {
       try 
          {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(Received.getBytes("UTF-8"));
          
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash)
              sb.append(String.format("%02x", b&0xff));
            Received = sb.toString();
          } 
       catch (UnsupportedEncodingException ex) 
          {
           //Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
          } 
       catch (NoSuchAlgorithmException ex) 
          {
           //Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
          }
       return Received;
   }
   private void  closeAll()
	{
		if(SICResultSet!=null)
		   SICResultSet = null;
		if(SICStatement!=null)
		   SICStatement = null;
	}
}