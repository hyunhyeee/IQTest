// 기본적인 메인 화면 구상 완료

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameUI extends JFrame {
	
	private JTextArea t_display;
	private JTextField t_input;
	
	public GameUI() {
		super("게임 메인 화면 구성");
		
		buildGUI();
		
		setSize(800,600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
	}
	
	private void buildGUI() {
		add(createDisplayPanel(), BorderLayout.CENTER);
		add(createInputPanel(), BorderLayout.SOUTH);
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
		JButton b_send = new JButton("보내기");
		
		p.add(t_input, BorderLayout.CENTER);
		p.add(b_send, BorderLayout.EAST);
		
//		t_input.setEnabled(true);
//		b_send.setEnabled(true);
		
		return p;
	}
	
    public static void main(String[] args) {
        new GameUI();
    }
}