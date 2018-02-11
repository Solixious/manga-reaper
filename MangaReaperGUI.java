import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class MangaReaperGUI extends JFrame {
	private static final String TITLE = "Manga Reaper";

	private MangaReaper mangaReaper;
	private JLabel nameLabel;
	private JTextField nameField;
	private JButton checkButton, downloadButton;

	public MangaReaperGUI() {
		super(TITLE);
		mangaReaper = new MangaReaper();
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 200);

		nameLabel = new JLabel("Manga Name");
		nameField = new JTextField();
		checkButton = new JButton("Check");
		downloadButton = new JButton("Download Manga");

		downloadButton.setEnabled(false);

		nameLabel.setBounds(20, 20, 100, 30);
		nameField.setBounds(120, 20, 250, 30);
		checkButton.setBounds(390, 20, 100, 30);
		downloadButton.setBounds(120, 70, 180, 30);

		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Checking...");
				String name = nameField.getText();
				if(name != null && name.length() > 0) {
					try {
						int response = mangaReaper.checkExistence(name);
					
						if(response == 200) {
							downloadButton.setEnabled(true);
						}
					} catch(Exception e1) {}
				}
			}
		});

		downloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mangaReaper.downloadManga();
				} catch(Exception e1) {e1.printStackTrace();}
			}
		});

		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});

		add(nameLabel);
		add(nameField);
		add(checkButton);
		add(downloadButton);
	}	

	public void display() {
		setVisible(true);
	}
}
