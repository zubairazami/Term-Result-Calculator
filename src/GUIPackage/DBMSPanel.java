package GUIPackage;

import MainPackage.Controll;     
import javax.swing.*;         
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DBMSPanel implements ActionListener{

	    private String MySQLUsername = new String("");
	    private String User, Pass;
	    private JButton ValidityCheckButton = new JButton("Submit");
		private JLabel UsernameLabel = new JLabel("Username for MySQL: ");
		private JLabel PasswordLabel = new JLabel("Password for MySQL: ");
		private JLabel MessageLabel = new JLabel("Provide a user with grant option in your MySql Database Management System");;
		private JPanel DBMSPanel;
		private JDialog DBMSDialog;
		private JTextField Username = new JTextField(20);
		private JPasswordField Password = new JPasswordField(20); 
        private Border MyBorder = new BevelBorder(BevelBorder.LOWERED,Color.BLACK,Color.DARK_GRAY);
		private Controll TempControll;
        private Menu TempMenuObject;
        private Font MyFont1 = new Font ("SERRIF",Font.BOLD,13);
        private Font MyFont2 = new Font ("SERRIF",Font.BOLD,11);
	  
		public DBMSPanel(Controll x) 
		{
			 TempControll = x;
			 User ="";
		     Pass ="";
		}
		
		public void createDBMSPanelDialog()
		{
		    DBMSDialog = new JDialog(); 
			DBMSPanel =new JPanel()
			 {
			  protected void paintComponent(Graphics g)
				{
				 super.paintComponent(g);
				 g.drawImage(new ImageIcon(getClass().getResource("/Icons/9.jpeg")).getImage(),0,0,840,300,null);
				 g.setColor(Color.BLACK);
			     MyBorder.paintBorder(this,g,30,90,400,120);
			     new BevelBorder(BevelBorder.RAISED,Color.BLACK,Color.DARK_GRAY).paintBorder(this,g,154,164,152,32);
				}
			 };
			 DBMSPanel.setLayout(null);
			 
			 MessageLabel.setBounds(10,50,500,15);
			 MessageLabel.setForeground(Color.BLACK);
			 MessageLabel.setFont(MyFont2);
			 
			 
			 UsernameLabel.setBounds(50,105,150,15);
			 UsernameLabel.setForeground(Color.BLACK);
			 UsernameLabel.setFont(MyFont1);
			
			 PasswordLabel.setBounds(50,130,150,15);
			 PasswordLabel.setForeground(Color.BLACK);
			 PasswordLabel.setFont(MyFont1);
			 
			 Username.setBounds(210,103,200,18);
			 Username.setForeground(Color.BLACK);
			 Username.setBackground(Color.WHITE);
			 Username.setText("");
			 Username.setOpaque(false);
			 Username.setBorder(new BevelBorder(BevelBorder.RAISED,Color.BLACK,Color.BLACK));
			 Username.setFont(MyFont1);
			 Username.setHorizontalAlignment(SwingConstants.CENTER);
			 
			 Password.setBounds(210,128,200,18);
			 Password.setForeground(Color.BLACK);
			 Password.setBackground(Color.WHITE);
			 Password.setText("");
			 Password.setOpaque(false);
			 Password.setBorder(new BevelBorder(BevelBorder.RAISED,Color.BLACK,Color.BLACK));
			 Password.setFont(MyFont1);
			 Password.setHorizontalAlignment(SwingConstants.CENTER);
			 
			 ValidityCheckButton.setForeground(Color.BLACK);
			 ValidityCheckButton.setBounds(155,165,150,30);
			 ValidityCheckButton.setContentAreaFilled(false);
			 ValidityCheckButton.addActionListener(this);
			 
			 DBMSPanel.add(MessageLabel);
			 DBMSPanel.add(Username);
			 DBMSPanel.add(UsernameLabel);
			 DBMSPanel.add(PasswordLabel);
			 DBMSPanel.add(Password);
			 DBMSPanel.add(ValidityCheckButton);
			 
			 DBMSDialog.setSize(460,300);
			 DBMSDialog.setResizable (false);
			 DBMSDialog.setLocation(445,280);
			 DBMSDialog.setModal(true);
			 DBMSDialog.add (DBMSPanel);
			 DBMSDialog.setTitle("MySQL Authentication");
			 DBMSDialog.setVisible(true);	 
		}
		public void actionPerformed(ActionEvent AE) 
	    {
		 User = Username.getText();
		 Pass = Password.getText();
		
		 if(User.equals("")&&Pass.equals(""))
		    JOptionPane.showMessageDialog(null,"Please complete both Username & Password field.","Blank Field",JOptionPane.ERROR_MESSAGE);
		 else
		   {
			TempControll.ConnectionManagerObject.setUserPass(User,Pass);  // setting the  user password for mysql throughout the each execution of the program  
			int x = TempControll.ConnectionManagerObject.hasGrantPreviliges();
			if(x==1)
			   DBMSDialog.dispose();
			if(x==-1)
			  {
			   JOptionPane.showMessageDialog(DBMSDialog, "The user is not privileged with grant option.\n Please submit root user & password or any other user privileged with grant option. ","Not Sufficiently Privileged User", JOptionPane.ERROR_MESSAGE);  
			   Username.setText("");
			   Password.setText("");
			  }
		   }
		}
}