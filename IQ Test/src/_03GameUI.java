// 기본적인 메인 화면 구상 완료

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;


public class _03GameUI extends JFrame {

	private JTextArea t_display;
	private JTextField t_operand1, t_operator1, t_operand2, t_operator2, t_result; // 산술 문제 출제란

	private JLabel timerLabel;
	private Timer timer;
	private int count = 7; // 초기 카운트 값
	private LevelBar levelBar;
	private JLabel levelLabel;


	public _03GameUI() {
		super("게임 메인 화면 구성");

		buildGUI();

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		setResizable(false);

		setVisible(true);
	}


	private void buildGUI() {
		getContentPane().setLayout(null);
		JButton levelCompleteButton = new JButton("다음->");
		levelCompleteButton.setBounds(560, 370, 150, 60);
		getContentPane().add(levelCompleteButton);
		levelCompleteButton.setEnabled(false);

		Font buttonFont = levelCompleteButton.getFont();
		levelCompleteButton.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));

		JTextField t_input = new JTextField(30);
		JButton b_send = new JButton("보내기");

		t_input.setBounds(0, 522, 692, 45);
		b_send.setBounds(690, 522, 100, 45);

		add(t_input);
		add(b_send);

		t_input.setEnabled(true);
		b_send.setEnabled(true);

		getContentPane().add(createDisplayPanel());
		getContentPane().add(createQuestionPanel());
		game9_10_CalcRandom();

	}


	private JPanel createDisplayPanel() {
		JPanel p = new JPanel();
		p.setBounds(0, 0, 0, 0);
		p.setLayout(null);

		t_display = new JTextArea();
		t_display.setEditable(false);

		timerLabel = new JLabel(Integer.toString(count));
		timerLabel.setFont(new Font("Arial", Font.BOLD, 45));
		getContentPane().add(timerLabel);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateLabelPosition();
			}
		});

		timer = new Timer(1000, e -> updateTimer());
		timer.start();

		levelBar = new LevelBar();
		levelBar.setBounds(210, 50, 370, 35);
		getContentPane().add(levelBar);

		updateLevel(4);
		return p;
	}


	private void updateTimer() {
		if (--count > 0) {
			timerLabel.setText(Integer.toString(count));
		} else {
			timer.stop();
			timerLabel.setText("X");
		}

	}


	private void updateLevel(int level) {
		levelBar.setLevel(level);
		levelBar.repaint();

		levelLabel = new JLabel("Level " + level);
		levelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		levelLabel.setBounds(5, 52, 150, 30);
		Font labelFont = levelLabel.getFont();
		levelLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
		getContentPane().add(levelLabel);
	}


	private void updateLabelPosition() {
		timerLabel.setBounds(665, 38, 450, 60);
	}


	private JPanel createQuestionPanel() {
		// 네프 계산 프로그램 참고
		JPanel p = new JPanel(null);
		p.setBounds(0, 0, 786, 562);

		t_operand1 = new JTextField(30);
		t_operator1 = new JTextField(30);
		t_operand2 = new JTextField(30);
		t_operator2 = new JTextField(30);
		t_result = new JTextField(30);

		t_operand1.setHorizontalAlignment(SwingConstants.CENTER);
		t_operator1.setHorizontalAlignment(SwingConstants.CENTER);
		t_operand2.setHorizontalAlignment(SwingConstants.CENTER);
		t_operator2.setHorizontalAlignment(SwingConstants.CENTER);
		t_result.setHorizontalAlignment(SwingConstants.CENTER);

		t_operand1.setBounds(85, 230, 90, 50);
		t_operator1.setBounds(225, 230, 60, 50);
		t_operand2.setBounds(340, 230, 90, 50);
		t_operator2.setBounds(485, 230, 60, 50);
		t_result.setBounds(600, 230, 90, 50);

		p.add(t_operand1);
		p.add(t_operator1);
		p.add(t_operand2);
		p.add(t_operator2);
		p.add(t_result);

		t_operator2.setText("=");

		t_operand1.setFont(new Font("굴림", Font.PLAIN, 30));
		t_operator1.setFont(new Font("굴림", Font.PLAIN, 30));
		t_operand2.setFont(new Font("굴림", Font.PLAIN, 30));
		t_operator2.setFont(new Font("굴림", Font.PLAIN, 30));
		t_result.setFont(new Font("굴림", Font.PLAIN, 30));

		t_operand1.setEditable(false);
		t_operator1.setEditable(false);
		t_operand2.setEditable(false);
		t_operator2.setEditable(false);
		t_result.setEditable(false);

		return p;

	}

	// 난이도 기준 설정
	// 대한민국 평균 아이큐 106
	// 일반인 아이큐 : 80 ~ 89
	// 테스트 시작시 기본 아이큐를 80으로 설정
	// 한 문제 맞출때 마다 5씩 증가
	// 난이도 별 랜덤 범위 설정

	// 1~3, 4~5, 6~8, 9~10, 4구역으로 나누기
	// 1~3은 한자리수의 + -
	// 4~5는 한자리수의 *
	// 6~8은 두자리수의 + -
	// 9~10은 두자리수의 * /


	// 레벨 1~3
	public void game1_3_CalcRandom() {
		Random rand = new Random();

		int num1 = rand.nextInt(9) + 1;
		int num2 = rand.nextInt(9) + 1;

		String[] operators = { "+", "-" };
		String operator1 = operators[rand.nextInt(operators.length)];

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator1);
		t_result.setText("?");
	}


	// 레벨 4~5
	// 1 곱하기 제외 O
	public void game4_5_CalcRandom() {
		Random rand = new Random();

		int num1 = rand.nextInt(9) + 2;
		int num2 = rand.nextInt(9) + 2;

		String operators = "*";
		String operator1 = operators;

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator1);
		t_result.setText("?");
	}


	// 레벨 6~8
	// 1의 자리수 제외, 두자리 수로 설정 O
	public void game6_8_CalcRandom() {
		Random rand = new Random();

		int num1 = rand.nextInt(20) + 10;
		int num2 = rand.nextInt(20) + 10;

		String[] operators = { "+", "-" };
		String operator1 = operators[rand.nextInt(operators.length)];

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator1);
		t_result.setText("?");
	}


	// 레벨 9~10
	// 0, 1, 2, 3, 5, 7, 10 나누기에서 제외
	// 0 ~ 3 곱하기에서 제외
	public void game9_10_CalcRandom() {
		Random rand = new Random();
		int num1 = rand.nextInt(35) + 4;
		int num2 = rand.nextInt(35) + 4;

		String[] operators = { "/" };
		String operator = operators[rand.nextInt(operators.length)];

		int result = 0;
		if (operator.equals("/")) {
			List<Integer> excludedNumbers = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 5, 7, 10));
			while (num1 <= num2 || excludedNumbers.contains(num1) || num1 != num2) {
				num1 = rand.nextInt(150) + 1;
				num2 = rand.nextInt(15) + 2;
			}

			if (num1 % num2 != 0) {
				num1 -= num1 % num2;
			}

			result = num1 / num2;
		} else {
			result = num1 * num2;
		}

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator);
		t_result.setText("?");
	}


	private class LevelBar extends JPanel {
		private int level;


		public LevelBar() {
			setPreferredSize(new Dimension(300, 20));
			level = 0;
		}


		public void setLevel(int level) {
			this.level = level;
		}


		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			int barWidth = getWidth();
			int barHeight = getHeight();

			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, barWidth, barHeight);

			g.setColor(Color.ORANGE);
			int filledWidth = (int) (barWidth * (level / 10.0));
			g.fillRect(0, 0, filledWidth, barHeight);

			g.setColor(Color.BLACK);
			for (int i = 1; i < 10; i++) {
				int x = barWidth * i / 10;
				g.drawLine(x, 0, x, barHeight);
			}

		}
	}


	public static void main(String[] args) {
		new _03GameUI();
	}
}