import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JTextField t_operand1, t_operator1, t_operand2, t_operator2, t_result, t_input;
	private JLabel timerLabel;
	private Timer timer;
	private int count = 7;
	private LevelBar levelBar;
	private JLabel levelLabel;
	private JLabel startLabel;
	private Timer startTimer;
	private JButton finalResultButton;

	private JButton levelCompleteButton;
	private int currentLevel = 1;


	public _03GameUI() {
		super("게임 메인 화면 구성");

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		startLabel = new JLabel("테스트가 3초 뒤 시작됩니다");
		startLabel.setBounds(170, 230, 500, 50);
		startLabel.setFont(new Font("맑은고딕", Font.BOLD, 40));
		getContentPane().setLayout(null);
		getContentPane().add(startLabel);

		startTimer = new Timer(3000, e -> {
			startLabel.setVisible(false);
			startGame();
		});
		startTimer.setRepeats(false);
		startTimer.start();

		finalResultButton = new JButton("최종 결과 보기");
		finalResultButton.setBounds(560, 370, 150, 60);
		getContentPane().add(finalResultButton);
		finalResultButton.setVisible(false); // 처음에는 보이지 않도록 설정

		finalResultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOver();
			}
		});

		setVisible(true);
	}


	private void startGame() {
		buildGUI();
		updateLevel(currentLevel);
		generateRandomProblem(currentLevel);
		timer.start();
	}


	private void buildGUI() {
		getContentPane().setLayout(null);

		levelCompleteButton = new JButton("다음->");
		levelCompleteButton.setBounds(560, 370, 150, 60);
		getContentPane().add(levelCompleteButton);
		levelCompleteButton.setEnabled(false);

		levelCompleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentLevel++;
				updateLevel(currentLevel);
				generateRandomProblem(currentLevel);
				levelCompleteButton.setEnabled(false);
				timer.restart();
				resetTimerLabel();
				t_input.setEnabled(true);
				t_input.setText("");
			}
		});

		Font buttonFont = levelCompleteButton.getFont();
		levelCompleteButton.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));

		t_input = new JTextField(30);
		JButton b_send = new JButton("보내기");

		ActionListener sendActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkAnswer();
				timer.stop();
			}
		};

		b_send.addActionListener(sendActionListener);
		t_input.addActionListener(sendActionListener); // 엔터 키 입력 처리

		t_input.setBounds(0, 522, 692, 45);
		b_send.setBounds(690, 522, 100, 45);

		add(t_input);
		add(b_send);

		t_input.setEnabled(true);
		b_send.setEnabled(true);

		getContentPane().add(createDisplayPanel());
		getContentPane().add(createQuestionPanel());

		generateRandomProblem(4);
	}


	private JPanel createDisplayPanel() {
		JPanel p = new JPanel();
		p.setBounds(0, 0, 0, 0);
		p.setLayout(null);

		t_display = new JTextArea();
		t_display.setEditable(false);

		timerLabel = new JLabel(Integer.toString(count));
		timerLabel.setBounds(665, 38, 450, 60);
		timerLabel.setFont(new Font("Arial", Font.BOLD, 45));
		getContentPane().add(timerLabel);

		timer = new Timer(1000, e -> updateTimer());
		levelBar = new LevelBar();
		levelBar.setBounds(210, 50, 370, 35);
		getContentPane().add(levelBar);

		updateLevel(1);
		return p;
	}


	private void updateTimer() {
		if (--count > 0) {
			timerLabel.setText(Integer.toString(count));
		} else {
			timer.stop();
			timerLabel.setText("X");
			gameOver();
		}

	}


	private void resetTimerLabel() {
		count = 7;
		timerLabel.setText(Integer.toString(count));
	}


	private void updateLevel(int level) {
		if (levelLabel == null) {
			levelLabel = new JLabel("Level " + level);
			levelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			levelLabel.setBounds(5, 52, 150, 30);
			Font labelFont = levelLabel.getFont();
			levelLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 25));
			getContentPane().add(levelLabel);
		} else {
			// 이미 추가된 레벨 라벨이 있다면 내용만 업데이트
			levelLabel.setText("Level " + level);
		}

		levelBar.setLevel(level);
		levelBar.repaint();

		repaint();
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


	private JPanel createQuestionPanel() {
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


	private void generateRandomProblem(int level) {
		Random rand = new Random();
		int num1 = 0;
		int num2 = 0;

		String operator = " ";

		switch (level) {
		case 1:
		case 2:
		case 3: // 레벨 1~3
			num1 = rand.nextInt(15) + 1;
			num2 = rand.nextInt(15) + 1;
			String[] operators1 = { "+", "-" };
			operator = operators1[rand.nextInt(operators1.length)];
			break;
		case 4:
		case 5: // 레벨 4~5
			num1 = rand.nextInt(15) + 2;
			num2 = rand.nextInt(15) + 4;
			operator = "*";
			break;
		case 6:
		case 7:
		case 8: // 레벨 6~8
			num1 = rand.nextInt(500) + 60;
			num2 = rand.nextInt(500) + 60;
			String[] operators2 = { "+", "-" };
			operator = operators2[rand.nextInt(operators2.length)];
			break;
		case 9:
		case 10: // 레벨 9~10
			num1 = rand.nextInt(45) + 4;
			num2 = rand.nextInt(45) + 4;
			String[] operators3 = { "*", "/" };
			operator = operators3[rand.nextInt(operators3.length)];
			List<Integer> excludedNumbers = Arrays.asList(0, 1, 2, 3, 5, 7, 10);
			while (operator.equals("/")
			    && (num1 <= num2 || excludedNumbers.contains(num2) || num1 == num2
			        || num1 % num2 != 0)) {
				num1 = rand.nextInt(200) + 10;
				num2 = rand.nextInt(15) + 2;
			}
			if (operator.equals("/")) {
				num1 -= num1 % num2;
			}
			break;
		default:
			break;
		}

		t_operand1.setText(String.valueOf(num1));
		t_operand2.setText(String.valueOf(num2));
		t_operator1.setText(operator);
		t_result.setText("?");

	}


	private void checkAnswer() {
		try {
			int userAnswer = Integer.parseInt(t_input.getText());
			int correctAnswer = calculateResult();

			if (userAnswer == correctAnswer) {
				levelCompleteButton.setEnabled(true);
				t_input.setEnabled(false);
			} else {
				gameOver();
			}

		} catch (NumberFormatException ex) {}

	}


	private int calculateResult() {
		int num1 = Integer.parseInt(t_operand1.getText());
		int num2 = Integer.parseInt(t_operand2.getText());
		String operator = t_operator1.getText();
		int result = 0;

		switch (operator) {
		case "+":
			result = num1 + num2;
			break;
		case "-":
			result = num1 - num2;
			break;
		case "*":
			result = num1 * num2;
			break;
		case "/":
			if (num2 != 0) {
				result = num1 / num2;
			} else {

			}
			break;
		}

		return result;
	}


	private void gameOver() {
		timer.stop();
		startLabel.setVisible(false);
		t_input.setEnabled(false);

		finalResultButton.setVisible(true);
		finalResultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_04GameOverUI nextClassFrame = new _04GameOverUI();
				nextClassFrame.setVisible(true);
				dispose();
			}
		});
	}


	public void finalScore(int level) {

	}


	public static void main(String[] args) {
		new _03GameUI();
	}
}
