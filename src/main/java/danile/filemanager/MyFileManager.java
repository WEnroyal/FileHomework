package danile.filemanager;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.AncestorListener;

public class MyFileManager {

	private JFrame frame;
	private JTextField textField;
	private File file;
	private File files [] ;
	private JButton btnConfirm = new JButton("Confirm");
	private JButton btnDelete = new JButton("Delete");
	private JButton btnBack = new JButton("<");
	private JTextPane textPane;
	private JPanel panel = new JPanel();



	private MyFileManager()
	{
		initialize();
	}

	public static MyFileManager newInstance(){
		return new MyFileManager();
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
		textField.setText(System.getProperty("os.name").toLowerCase().equals("linux")?"/home":"C:");
		
		btnConfirm.setBounds(341, 0, 94, 20);
		frame.getContentPane().add(btnConfirm);

		panelOne();
		frame.getContentPane().add(scrollPane());
		frame.getContentPane().add(panelTwo());
		
		
		btnDelete.setBounds(248, 0, 94, 20);
		frame.getContentPane().add(btnDelete);
		
		
		btnBack.setBounds(0, 0, 41, 23);
		frame.getContentPane().add(btnBack);
		frame.setVisible(true);
	}
	private JScrollPane scrollPane() 
	{
		JScrollPane scrollPane = new JScrollPane(this.panel);
		scrollPane.setBounds(0, 21, 200, 240);
		return scrollPane;
	}
	
	private void panelOne()
	{

		this.panel.setBounds(0, 21, 200, 240);
		this.panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		file = new File(textField.getText());
		File[] arrs = file.listFiles();
		
		buttonActionComplete();
		this.btnConfirm.addActionListener(listenerConfirm(textField));

	}
	private JPanel panelTwo()
	{
		JPanel panel2 = new JPanel();
		panel2.setBounds(226, 21, 198, 238);
		panel2.setLayout(null);

		textPane = new JTextPane();
		textPane.setBounds(10, 11, 300, 300);
		panel2.add(textPane);
		return panel2;

	}

	private ActionListener listenerConfirm(JTextField path) {
		return arg -> {
			System.out.println("HELLO");
			this.panel.setVisible(false);
			this.panel.removeAll();
			file = new File(path.getText());
			List<File> fileList = Arrays.asList(file.listFiles().length == 0 ? null : file.listFiles());
			if (fileList.size() != 0) {
				this.panel.setVisible(false);
				fileList.stream().forEach(file -> {
					if (file.isDirectory()) {
						JButton button = new JButton(file.getName());
						this.panel.add(button);
						button.addActionListener((arg0) -> {
							path.setText(path.getText() + "/" + button.getText());
						});
					} else if (file.isFile()) {
						JButton button = new JButton(file.getName());
						if (file.getName().endsWith(".txt")) {
							this.panel.add(button);
							button.addActionListener(arg0 -> {
								try (BufferedReader br = new BufferedReader(new FileReader(path.getText() + "/" + button.getText()))) {
									String strLine = null;
									while ((strLine = br.readLine()) != null) {
										textPane.setText(strLine);
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							});
						} else if (file.getName().endsWith(".jpg")) {
							this.panel.add(button);
							button.addActionListener(arg0 -> {
								textPane.setVisible(false);
								textPane.setText("");
								String newPath = path.getText() + "/" + file.getName();
								System.out.println(path);
								textPane.insertIcon(new ImageIcon(newPath));
								textPane.setVisible(true);
							});
						}
					}

				});
				this.panel.setVisible(true);
			}
		};
	}
	private ActionListener listenerDelete()
	{
		return arg->{
			File file = new File(textField.getText());
			List<File> fileList = Arrays.asList(file.listFiles().length == 0?null:file.listFiles());
			if(fileList.size() != 0)
			{
				fileList.stream().forEach(fileInner -> fileInner.delete());
			}
			file.delete();
			this.textField.setText(textField.getText().substring(0,textField.getText().lastIndexOf("/")));
			this.btnConfirm.doClick();
		};
	}
	private ActionListener listenerBack()
	{
		return arg->{
			String str = textField.getText();

			if(str.lastIndexOf("/") != 0)
			{
				this.textField.setText(str.substring(0,str.lastIndexOf("/")));
			}
			this.btnConfirm.doClick();
		};
	}
	private void buttonActionComplete()
	{
		this.btnBack.addActionListener(listenerBack());
		this.btnDelete.addActionListener(listenerDelete());

	}
}
