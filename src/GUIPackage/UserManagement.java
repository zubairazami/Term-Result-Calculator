package GUIPackage;

import MainPackage.*;     

import java.awt.BorderLayout;
import java.awt.Color;    
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import java.awt.event.*;
import java.util.Vector;


public class UserManagement implements ActionListener{
    
    JDialog UserManagementFrame;  
    private JTabbedPane ChoiceTabbedPane; 
    
    private JPanel AddPanel;
    private JPanel DeletePanel;
    private JPanel UserPanel;
    
    private JButton AddPanelBackButton = new JButton("Back");
    private JButton DeletePanelBackButton = new JButton("Back");
    private JButton AddButton = new JButton("Add");
    private JButton DeleteButton = new JButton("Delete");
	
	private JTextField Username = new JTextField(25);
    private JPasswordField Password = new JPasswordField(25);
	
    private JComboBox UserList = new JComboBox() ;
    private Vector<String> UserListVector = new Vector<String>();
    
    private Border Lowered = new BevelBorder(BevelBorder.LOWERED);
    
	private Icon PanelIcon = new ImageIcon(getClass().getResource("/Icons/4.png"));
    private Controll TempControll;
    
    private String NewUser,NewPassword,UserToBeDeleted = new String();
	private int NumberOfItemsInUserList;
	
	
	public UserManagement(Controll x,OpeningFrame y)
	{
		TempControll = x;
		UserManagementFrame = new JDialog(y, "User Management",true);
	}

	public void createUserManagementFrame()
	{	
		UserManagementFrame.setLocation(300,150);
		UserManagementFrame.setSize(840,400);
		
		
		
		UserPanel = new JPanel(new BorderLayout())
		{
			 protected void paintComponent(Graphics g)
			 {
			    super.paintComponent(g);
			    g.drawImage(new ImageIcon(getClass().getResource("/Icons/8.jpg")).getImage(), 0, 0, 840,400, null);
			 }
		};
		UserPanel.setBackground(Color.DARK_GRAY);
		
		createAddPanel();
		createDeletePanel();
		
		UIManager.put("TabbedPane.tabsOpaque", false);
		UIManager.put("TabbedPane.contentOpaque", false);
		UIManager.put("TabbedPane.tabsOverlapBorder", true);
		ChoiceTabbedPane = new JTabbedPane();
		ChoiceTabbedPane.addTab("Add User", new ImageIcon(getClass().getResource("/Icons/add.jpg")), AddPanel,"Add User");
		ChoiceTabbedPane.addTab("Delete User",new ImageIcon(getClass().getResource("/Icons/delete.jpg")), DeletePanel,"Delete User");
		ChoiceTabbedPane.setForegroundAt(0, Color.WHITE);
		ChoiceTabbedPane.setForegroundAt(1, Color.WHITE);
		
		if(TempControll.SignInCoreObject.noListedUser())
		   ChoiceTabbedPane.setEnabledAt(1, false);      // if there is no user, no need to delete :)
		
		addButtonsToActionListner();	
		UserPanel.add(ChoiceTabbedPane);
		UserManagementFrame.getContentPane().add(UserPanel);
		UserManagementFrame.setModal(true);
		UserManagementFrame.setResizable(false);
		UserManagementFrame.setVisible(true);
	}
	
	private void createAddPanel()
	{
		
		AddPanel = new JPanel()
		{
			 protected void paintComponent(Graphics g)
			 {
			    super.paintComponent(g);
			    Lowered.paintBorder(this, g,490,168,124,29);
			    Lowered.paintBorder(this, g,620,168,124,29);
			    Lowered.paintBorder(this, g, 18, 6, 287, 304);
			 }
		};
		
		AddPanel.setLayout(null);		
		
		JLabel WarningMessage = new JLabel(" Username & Password should contain only alphabatic and numeric character",SwingConstants.CENTER);
		JLabel UsernameLabel = new JLabel("Username: ",SwingConstants.LEFT);
		JLabel PasswordLabel = new JLabel("Password: ",SwingConstants.RIGHT);
		
		JLabel ICON2 = new JLabel(PanelIcon);
		ICON2.setBounds(20, 8, 283, 300);
		
	  	UsernameLabel.setBounds(382,100,80,20);
	  	UsernameLabel.setFont(new Font ("SERRIF",Font.BOLD,15));
	  	UsernameLabel.setForeground(Color.BLACK);

        PasswordLabel.setBounds(382,130,80,20);
        PasswordLabel.setFont(new Font ("SERRIF",Font.BOLD,15));
		PasswordLabel.setForeground(Color.BLACK);
        
        WarningMessage.setBounds(308,205,532,15);
		WarningMessage.setForeground(Color.RED);
		WarningMessage.setFont(new Font("SERRIF",Font.BOLD,12));
	  
		Username.setBounds(467,102,300,20);
		Username.setFont(new Font ("SERRIF",Font.BOLD,12));
		Username.setForeground(Color.BLACK);
		Username.setBorder(Lowered);
		Username.setOpaque(false);
		Username.setHorizontalAlignment(SwingConstants.CENTER);
		
		Password.setFont(new Font ("SERRIF",Font.BOLD,12));
		Password.setForeground(Color.BLACK);
		Password.setBounds(467,132,300,20);
		Password.setBorder(Lowered);
        Password.setOpaque(false); 
        Password.setHorizontalAlignment(SwingConstants.CENTER);
		
		AddButton.setBounds(492,170,120,25);
        AddButton.setFont(new Font ("SERRIF",Font.BOLD,15));
        AddButton.setForeground(Color.BLACK);
	  	AddButton.setOpaque(false);
        
        AddPanelBackButton.setBounds(622,170,120,25);
        AddPanelBackButton.setFont(new Font ("SERRIF",Font.BOLD,15));
        AddPanelBackButton.setForeground(Color.BLACK);
        AddPanelBackButton.setOpaque(false);
        
        
	  	AddPanel.add(AddPanelBackButton);
		AddPanel.add(AddButton);
		AddPanel.add(ICON2);
		AddPanel.add( UsernameLabel );
		AddPanel.add( PasswordLabel );
		AddPanel.add( WarningMessage );
		AddPanel.add( Username );
		AddPanel.add( Password );
		AddPanel.setOpaque(false);

	}
	private void createDeletePanel()
	{
		DeletePanel = new JPanel()
		{
			 protected void paintComponent(Graphics g)
			 {
			    super.paintComponent(g);
			    Lowered.paintBorder(this, g, 447,168,124,29);
			    Lowered.paintBorder(this, g, 577,168,124,29);
			    Lowered.paintBorder(this, g, 18, 6, 287, 304);
			 }
		};
		
		JLabel ICON1 = new JLabel(PanelIcon);
		ICON1.setBounds(20, 8, 283, 300);
		
		JLabel DeletePanelLabel = new JLabel("Users:",SwingConstants.CENTER);
		DeletePanelLabel.setForeground(Color.BLACK);
		DeletePanelLabel.setFont(new Font ("SERRIF",Font.BOLD,15));
		DeletePanelLabel.setBounds(308,80,532,15);
		DeletePanelLabel.setForeground(Color.BLACK);
		
		UserList.setBounds(449,103,250,20);
		UserList.setFont(new Font ("SERRIF",Font.BOLD,12));
		UserList.setBackground(Color.GRAY);
		UserList.setForeground(Color.BLACK);
		UserList.setMaximumRowCount(5);
		UserList.setBorder(Lowered);
		updateUserList();
	
	  	DeletePanel.setLayout(null);
	  	
        DeleteButton.setBounds(449,170,120,25);
        DeleteButton.setFont(new Font ("SERRIF",Font.BOLD,15));
        DeleteButton.setForeground(Color.BLACK);
        DeleteButton.setOpaque(false);
        
	  	DeletePanelBackButton.setBounds(579,170,120,25);
	  	DeletePanelBackButton.setFont(new Font ("SERRIF",Font.BOLD,15));
	  	DeletePanelBackButton.setForeground(Color.BLACK);
	  	DeletePanelBackButton.setOpaque(false);
	  	
        DeletePanel.add(ICON1);
        DeletePanel.add(DeletePanelLabel);
	  	DeletePanel.add(DeletePanelBackButton);
		DeletePanel.add(DeleteButton);
		DeletePanel.add(UserList);	
		DeletePanel.setOpaque(false);
	}
	
	private void updateUserList()
	{
		UserList.removeAllItems(); // removing all from the comboBox
		UserListVector = TempControll.SignInCoreObject.getUserList();
		for(String User : UserListVector)
	        UserList.addItem(User);
		UserListVector.clear();
	}
	private void addButtonsToActionListner()
	{
	 AddPanelBackButton.addActionListener(this);
	 DeletePanelBackButton.addActionListener(this); 
	 AddButton .addActionListener(this);
	 DeleteButton.addActionListener(this);
	}
	private void clearUP()
	{
		Username.setText("");
		Password.setText("");
	}
	
    public void actionPerformed (ActionEvent event)
    {
		if(event.getSource() == AddPanelBackButton || event.getSource() == DeletePanelBackButton  )
		    UserManagementFrame.dispose(); 
		
        if(event.getSource() == AddButton)
          {
	        NewUser = Username.getText();
	        NewPassword = Password.getText();
	     
	        if(NewUser.equals("") || NewPassword.equals("") )
	           JOptionPane.showMessageDialog(null, " Please Complete both Username & Password Field.", "Empty Field", JOptionPane.ERROR_MESSAGE );
	        else 
	          {  
	           if( TempControll.SignInCoreObject.addNewUser(NewUser, NewPassword))
	    		 {
                  ChoiceTabbedPane.setEnabledAt(1, true);  // if delete tab is not added previuously because of no listed user, then after adding a new user it has to be activated
	              updateUserList(); // updating the combobox after each and everytime of adding a new user
	              JOptionPane.showMessageDialog(UserManagementFrame, NewUser + " has been added succefully.", "Success", JOptionPane.INFORMATION_MESSAGE );
	    		 }
	    	   else  
	    		 JOptionPane.showMessageDialog(UserManagementFrame, NewUser + " is an existing username.", "Error", JOptionPane.ERROR_MESSAGE ); 
	    	  }
	    	clearUP();
          }
        if(event.getSource()==DeleteButton)
		  {
		    UserToBeDeleted = UserList.getSelectedItem().toString() ;
		    NumberOfItemsInUserList = UserList.getItemCount();
		  
		    if(NumberOfItemsInUserList<2) // if the only user is left
		      JOptionPane.showMessageDialog(UserManagementFrame, UserToBeDeleted+" is the last user remaining.\nUnable to Delete.", "Error", JOptionPane.ERROR_MESSAGE );
		    else 
		      {
		       if(TempControll.SignInCoreObject.deleteUser(UserToBeDeleted)) // deleting a user
		         { 
		           updateUserList(); // update the combobox if delete is successful
		           JOptionPane.showMessageDialog(UserManagementFrame, UserToBeDeleted+" has been successfully deleted from the User List .", "Success", JOptionPane.INFORMATION_MESSAGE );
		         }
		       else	 
				 JOptionPane.showMessageDialog(UserManagementFrame, "Unable to Delete. "+UserToBeDeleted, "Error", JOptionPane.ERROR_MESSAGE );
			  }
		    clearUP();
          }	 
    }			
}