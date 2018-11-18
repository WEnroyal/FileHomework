package danile.filemanager;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class MyFileManager {

	private JFrame frame;
	private JTextField textField;
	private File file;
	private File files [] ;
	JButton btnConfirm = new JButton("Confirm");
	JButton btnNewButton = new JButton("Delete");
	JButton btnNewButton1 = new JButton("<");
	JTextPane textPane;
	
	public MyFileManager() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 444, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.BOLD, 14));
		textField.setBounds(41, 0, 206, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText("C:");
		
		btnConfirm.setBounds(341, 0, 94, 20);
		frame.getContentPane().add(btnConfirm);
		
		frame.getContentPane().add(scrollPane());
		frame.getContentPane().add(panel2());
		
		
		btnNewButton.setBounds(248, 0, 94, 20);
		frame.getContentPane().add(btnNewButton);
		
		
		btnNewButton1.setBounds(0, 0, 41, 23);
		frame.getContentPane().add(btnNewButton1);
		frame.setVisible(true);
	}
	private JScrollPane scrollPane() 
	{
		JScrollPane scrollPane = new JScrollPane(panel1());
		scrollPane.setBounds(0, 21, 200, 240);
		return scrollPane;
	}
	
	private JPanel panel1() 
	{
		final JPanel panel = new JPanel();
		panel.setBounds(0, 21, 200, 240);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		file = new File(textField.getText());
		File[] arrs = file.listFiles();
		
		btnNewButton1.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				String str = textField.getText();
				if(str.indexOf("/") > 0) 
				{
					String str2 = str.substring(0, str.lastIndexOf("/"));
					textField.setText(str2);	
				} else
					try {
						throw new Exception();
					} catch (Exception e1) {
						System.out.println("No path back");
					}
				
				
				
			}});
		btnNewButton.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				
				File file = new File(textField.getText());
				File[] files = file.listFiles();
				if(files != null) 
				{
					for(File f : files) 
					{
						f.delete();
					}
					System.out.println("Папка была удалена");
				}else
					System.out.println("Не найдено");
				file.delete();
				
			}});
		btnConfirm.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent arg0) {
				panel.setVisible(false);
				panel.removeAll();
				file = new File(textField.getText());
				File[] arrs = file.listFiles();
				if(file.exists()) {
				for(int i = 0; i < arrs.length;i++) 
				{
					 
					if(arrs[i].isDirectory()) 
					{
						final JButton button = new JButton(arrs[i].getName());
						panel.add(button);
						panel.setVisible(true);
						button.addActionListener(new ActionListener() {

							
							public void actionPerformed(ActionEvent arg0) {
								textField.setText(textField.getText() + "/" + button.getText());
								panel.setVisible(true);
								
							}});
					}else
						if(arrs[i].isFile())
						{
							final JButton button = new JButton(arrs[i].getName());
							panel.add(button);
							panel.setVisible(true);
							 if(arrs[i].getName().endsWith(".txt")) 
							 {
								 button.addActionListener(new ActionListener() {

										
										public void actionPerformed(ActionEvent arg0) {
										    try{
										        FileInputStream fstream = new FileInputStream(textField.getText() + "/" + button.getText());
										        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
										        String strLine;
									
										        while ((strLine = br.readLine()) != null){
										           textPane.setText(strLine);
										        }
										     }catch (IOException e){
										        System.out.println("ERROR");
										     }
											
										}}); 
							 }else
								 
								 if(arrs[i].getName().endsWith(".jpg")) 
								 {
									 
									 final String name = new String( arrs[i].getName());
									 button.addActionListener(new ActionListener() {

											
											public void actionPerformed(ActionEvent arg0) {
												textPane.setVisible(false);
												textPane.setText("");
												String path = textField.getText() + "/" + name; 
												System.out.println(path);
												textPane.insertIcon(new ImageIcon(path));
												textPane.setVisible(true);
												
											}}); 
								 }
							
						}
				
					}
			} else
					try {
						throw new Exception();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("WRONG PATH");
					}
				
				
				
			}});
		
		
		return panel;
	}
	
	private JPanel panel2() 
	{
		JPanel panel2 = new JPanel();
		panel2.setBounds(226, 21, 198, 238);
		panel2.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(10, 11, 178, 216);
		panel2.add(textPane);
		return panel2;
	}
}
