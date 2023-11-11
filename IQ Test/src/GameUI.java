import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameUI {
	
	private JTextArea t_display;
	private JTextField t_input;
	private JButton b_send;
	
	public GameUI() {
		
    	JFrame f = new JFrame();
    	
		f.setTitle("Object Talk");
		
		f.setSize(500, 300);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	private JPanel createDisplayPanel() {
		JPanel p = new JPanel(new BorderLayout());
		
		t_display = new JTextArea();
		t_display.setEditable(false);
		
		p.add(new JScrollPane(t_display), BorderLayout.CENTER);
		
		return p;
	}
	
	private JPanel createInputPanel() {
		JPanel p = new JPanel(new BorderLayout());
		
		t_input = new JTextField(30);
		b_send = new JButton("보내기");
		
		p.add(t_input, BorderLayout.CENTER);
		p.add(b_send, BorderLayout.EAST);
		
		t_input.setEnabled(true);
		b_send.setEnabled(true);
		
		return p;
	}
	
    public static void main(String[] args) {
        new GameUI();
    }
}