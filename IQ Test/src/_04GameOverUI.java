// 기본적인 게임 종료 화면 구상 완료

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class _04GameOverUI extends JFrame {

	private JTextArea t_display;
	private JTextField t_input;
	private JLabel resultNum, resultStr;


	public _04GameOverUI() {
		super("게임 종료 화면 구성");

		buildGUI();

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		setResizable(false);

		setVisible(true);
	}


	private void buildGUI() {
		add(createDisplayPanel());
		add(createResultPanel());
		add(createInputPanel(), BorderLayout.SOUTH);
	}


	private JPanel createDisplayPanel() {
		JPanel p = new JPanel(null);

		t_display = new JTextArea();
		t_display.setEditable(false);

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


	private JPanel createResultPanel() {
		JPanel p = new JPanel(null);
		resultNum = new JLabel("최종 점수 \" \" 점");
		resultStr = new JLabel("오!! 당신은 범고래와 똑같은 지능이군요");

		resultNum.setFont(new Font("굴림", Font.BOLD, 35));
		resultStr.setFont(new Font("굴림", Font.BOLD, 23));

		resultNum.setHorizontalAlignment(SwingConstants.CENTER);
		resultStr.setHorizontalAlignment(SwingConstants.CENTER);

		resultNum.setBounds(149, 209, 471, 87);
		resultStr.setBounds(159, 288, 453, 63);

		p.add(resultNum);
		p.add(resultStr);

		return p;
	}


	public static void main(String[] args) {
		new _04GameOverUI();
	}
}