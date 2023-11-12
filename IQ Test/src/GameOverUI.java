// 기본적인 게임 종료 화면 구상 완료

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameOverUI extends JFrame {
	
	private JTextArea t_display;
	private JTextField t_input;
	
	public GameOverUI() {
		super("게임 종료 화면 구성");
		
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
		JPanel p = new JPanel(new GridLayout());
		
		JButton b_restart = new JButton("재시작하기");
		JButton b_exit = new JButton("나가기");
		
		p.add(b_restart);
		p.add(b_exit);
		
		return p;
			}
	
    public static void main(String[] args) {
        new GameOverUI();
    }
}