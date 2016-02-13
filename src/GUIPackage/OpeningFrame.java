package GUIPackage;

import java.awt.*;           
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import MainPackage.*;


public class OpeningFrame extends JFrame implements ActionListener 
{   // the class for begining frame, used also for app's authentication 
    
    JFrame OpeningFrame  = new JFrame("Term  Result  Calculator"); 
	private JButton LoginButton = new JButton("Login");
	private JButton UserManagementButton = new JButton("User Management");
	private JTextField Username = new JTextField(20);
	private JPasswordField Password = new JPasswordField(20);
	private JPanel Panel;
	private JLabel Welcomespeech1 = new JLabel("Welcome to",SwingConstants.CENTER);
	private JLabel Welcomespeech2 = new JLabel("Term Result Calculator",SwingConstants.CENTER);
	private JLabel WarningMessage = new JLabel("** Username & Password can contain only alphabatic and numeric character",SwingConstants.CENTER);
	private JLabel UsernameLabel = new JLabel("Username: ",SwingConstants.CENTER);
	private JLabel PasswordLabel = new JLabel("Password: ",SwingConstants.CENTER);
	
	private Border WelcomeBevel = new BevelBorder(BevelBorder.LOWERED,Color.LIGHT_GRAY,Color.DARK_GRAY);
	private Border AuthenticationTitled = new TitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED),"Authentication",TitledBorder.TOP,TitledBorder.TOP,new Font("SERRIF",Font.BOLD+Font.ITALIC,20));
	private Controll TempControll;
	
	public OpeningFrame(Controll x)
	{
		TempControll = x;
	}
	
	public void createOpeningFrame()
	{	
		OpeningFrame.setLocation(250,100);
		OpeningFrame.setSize(840,565);
		
		Panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			   {
				super.paintComponent(g);
				g.drawImage(new ImageIcon(getClass().getResource("/Icons/main.jpg")).getImage(),0,0,840,565,null);  // setting background wallpaper
			    // drawing four borders around Welcome speech
				WelcomeBevel.paintBorder(this, g, 122, 72, 596, 116);
				WelcomeBevel.paintBorder(this, g, 120, 70, 600, 120);
				WelcomeBevel.paintBorder(this, g, 118, 68, 604, 124);
				WelcomeBevel.paintBorder(this, g, 116, 66, 608, 128);
				((TitledBorder) AuthenticationTitled).setTitleColor(Color.WHITE);//(new Color(0,162,232));  
				AuthenticationTitled.paintBorder(this, g, 190, 230, 470, 230);  // Drawing authentication border around authentication field
			   }
		};
	  	Panel.setLayout(null);
	    
	  	Welcomespeech1.setFont(new Font ("CASTELLER",Font.BOLD,20));
		Welcomespeech1.setForeground(Color.WHITE);//(new Color(0,162,232));
		Welcomespeech1.setBounds(0,90,840,25);
		
		Welcomespeech2.setFont(new Font ("CASTELLER",Font.BOLD,50));
		Welcomespeech2.setForeground(new Color(180,180,180));
		Welcomespeech2.setBounds(0,100,840,70);
	  	
	  	UsernameLabel.setBounds(215,280,200,20);
	  	UsernameLabel.setFont(new Font ("SERRIF",Font.BOLD,15));
	  	UsernameLabel.setForeground(Color.WHITE);//new Color(200,191,231));
	  	
	  	PasswordLabel.setBounds(435,280,200,20);
		PasswordLabel.setFont(new Font ("SERRIF",Font.BOLD,15));
		PasswordLabel.setForeground(Color.WHITE);//(new Color(200,191,231));
	  	
	  	Username.setBounds(215,310,200,20);
	    Username.setFont(new Font ("SERRIF",Font.ITALIC+Font.BOLD,15));
	    Username.setBackground(new Color(200,191,231));
		Username.setForeground(Color.BLACK);
		Username.setHorizontalAlignment(SwingConstants.CENTER);
	  	
		Password.setBounds(435,310,200,20);
		Password.setFont(new Font ("SERRIF",Font.ITALIC+Font.BOLD,15));
		Password.setBackground(new Color(200,191,231));
		Password.setForeground(Color.BLACK);
		Password.setHorizontalAlignment(SwingConstants.CENTER);
		
        LoginButton.setBounds(345,340,150,25);
        LoginButton.setFont(new Font ("SERRIF",Font.BOLD,12));
        LoginButton.setForeground(Color.BLACK);

		UserManagementButton.setBounds(345,370,150,25);
        UserManagementButton.setFont(new Font ("SERRIF",Font.BOLD,12));
        UserManagementButton.setForeground(Color.BLACK);
        
        WarningMessage.setBounds(0,390,840,50);        
        WarningMessage.setForeground(new Color (253,50,70));
		WarningMessage.setFont(new Font("SERRIF",Font.BOLD,12));
       
		
		Panel.add(Welcomespeech1);
		Panel.add(Welcomespeech2);
		Panel.add(UsernameLabel);
		Panel.add(Username);
		Panel.add(PasswordLabel);
		Panel.add(Password);
		Panel.add(LoginButton);
		Panel.add(UserManagementButton);
		Panel.add(WarningMessage);
		
	
		LoginButton.addActionListener(this);
		UserManagementButton.addActionListener(this);
		
		OpeningFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		OpeningFrame.addWindowListener(new java.awt.event.WindowAdapter()  // to give user a option if sure to close the whole app
        {
 	      @Override
 	      public void windowClosing(java.awt.event.WindowEvent windowEvent) 
 	       {
 	        if( JOptionPane.showConfirmDialog(null, "Do you want to exit Term Result Calculator ?", "Comfirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION )
 	    	   {
 	        	TempControll.ConnectionManagerObject.closeConnection();  // closing all the existing connection with database management system
 	        	System.exit(0);
 	    	   }
 	       }
 	    });
		OpeningFrame.getContentPane().add(Panel);
		OpeningFrame.setResizable(false);
		OpeningFrame.setVisible(true);
		
	}
	private void clearUP()
	{
		Username.setText("");
		Password.setText("");
	}
	
	@Override
	public void actionPerformed (ActionEvent event)
		{
			String User,Pass = new String();
			
			if(event.getSource()==LoginButton )
			  {	
			     User = Username.getText().toString();
			     Pass = Password.getText().toString();
			 
			     if(TempControll.ConnectionManagerObject.createConnection()) // securing a valid connection & database
			       {  
			        if(TempControll.SignInCoreObject.noListedUser())
			           JOptionPane.showMessageDialog(OpeningFrame, "Please, press User Management", "No User", JOptionPane.INFORMATION_MESSAGE);    
			        else if( User.equals("") || Pass.equals("") )
			           JOptionPane.showMessageDialog(null, " Please Complete both Username & Password Field.", "Empty Field", JOptionPane.WARNING_MESSAGE );
			        else if( TempControll.SignInCoreObject.verification(User,Pass) )
		              {
				       OpeningFrame.dispose();
				       TempControll.MenuObject.createMenuFrame(); // entering main menu of the app
		              }	
			        else
				      JOptionPane.showMessageDialog(null, "Wrong Username & Password combination.", "Error", JOptionPane.ERROR_MESSAGE );
			        clearUP();
			       }
			  }
			if(event.getSource()==UserManagementButton )
			  {	
			     User = Username.getText().toString();
			     Pass = Password.getText().toString();
			
			     if(TempControll.ConnectionManagerObject.createConnection())  // securing a valid connection & database
			       { 
			          if(TempControll.SignInCoreObject.noListedUser())
			        	 TempControll.UserManagementObject.createUserManagementFrame();
			          else if( User.equals("") || Pass.equals("") )
				         JOptionPane.showMessageDialog(null, " Please Complete both Username & Password Field.", "Empty Field", JOptionPane.WARNING_MESSAGE );
			          else if( TempControll.SignInCoreObject.verification(User,Pass) )
		                 TempControll.UserManagementObject.createUserManagementFrame();	
			          else
				         JOptionPane.showMessageDialog(null, "Wrong Username & Password combination.", "Error", JOptionPane.ERROR_MESSAGE );
			          clearUP();
			       }
		     }
		}
}