// 기본적인 게임 종료 화면 구상 완료

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GameOverUI extends JFrame {

	private JTextArea t_display;
	private JTextField t_input;
	private JLabel resultNum, resultStr;


	public GameOverUI() {
		super("게임 종료 화면 구성");

		buildGUI();

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		setResizable(false);

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

		b_restart.setPreferredSize(new Dimension(b_restart.getPreferredSize().width, 50));
		b_exit.setPreferredSize(new Dimension(b_exit.getPreferredSize().width, 50));

		p.add(b_restart);
		p.add(b_exit);

		Font buttonFont = b_restart.getFont();
		b_restart.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
		b_exit.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));

		return p;
	}

	// private JPanel createResultPanel() {
	// JPanel p = new JPanel();
	//
	// resultNum = new JLabel();
	// resultStr = new JLabel();
	//
	// // resultNum.setBounds()
	// }


	public static void main(String[] args) {
		new GameOverUI();
	}
}