// 03. 게임 진행 화면
// 산술 문제

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;


public class _03_0GameUI extends JFrame {

	private JTextArea t_display;
	private JTextField t_operand1, t_operator1, t_operand2, t_operator2, t_result, t_input;

	private JLabel timerLabel;
	private JLabel levelLabel;
	private JLabel startLabel;

	private GameTimer gameTimer;
	private Timer timer;

	private JButton finalResultButton;
	private JButton levelCompleteButton;

	private int count = 7;
	private static int currentLevel = 1;

	private LevelBar levelBar;

	public BackgroundMusicPlayer musicPlayer;
	public JButton musicButton;


	public _03_0GameUI() {
		// JFrame 설정
		super("게임 메인 화면 구성");
		musicPlayer = new BackgroundMusicPlayer();
		musicPlayer.playBackgroundMusic();
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		// 시작을 나타내는 레이블 설정, 2초 후 게임 시작을 담당하는 GameTimer 초기화
		startLabel = new JLabel("게임이 잠시 후 시작됩니다");
		startLabel.setBounds(160, 230, 500, 50);
		startLabel.setFont(new Font("맑은고딕", Font.BOLD, 40));
		getContentPane().setLayout(null);
		getContentPane().add(startLabel);

		gameTimer = new GameTimer();
		gameTimer.start();

		// 최종 결과 보기 버튼 초기화
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
		ImageIcon soundOnIcon = new ImageIcon(getClass().getResource("/Game_pic/soundOn.png"));
		ImageIcon soundOffIcon = new ImageIcon(getClass().getResource("/Game_pic/soundOff.png"));

		// 이미지 크기 고정
		Image imgOn = soundOnIcon.getImage();
		Image imgOff = soundOffIcon.getImage();
		Image scaledImgOn = imgOn.getScaledInstance(65, 40, Image.SCALE_SMOOTH);
		Image scaledImgOff = imgOff.getScaledInstance(65, 40, Image.SCALE_SMOOTH);

		// 이미지 아이콘 크기에 맞춰 버튼 크기 조정
		ImageIcon scaledSoundOnIcon = new ImageIcon(scaledImgOn);
		ImageIcon scaledSoundOffIcon = new ImageIcon(scaledImgOff);

		musicButton = new JButton(scaledSoundOnIcon); // 초기 이미지 아이콘 설정
		musicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				musicPlayer.toggleMusic();
				if (musicPlayer.isMusicOn()) {

					musicButton.setIcon(scaledSoundOnIcon); // 음악이 꺼진 상태의 이미지로 변경
				} else {

					musicButton.setIcon(scaledSoundOffIcon); // 음악이 켜진 상태의 이미지로 변경
				}

			}
		});
		musicButton.setBounds(720, 0, 65, 40);
		musicButton.setContentAreaFilled(false); // 배경색 제거
		musicButton.setBorderPainted(false); // 테두리 없애기
		musicButton.setFocusPainted(false); // 글씨 테두리 없애기

		add(musicButton);

	}


	public void startGame() {
		buildGUI(); // 메서드 호출
		currentLevel = 1; // 초기 레벨 설정
		updateLevel(currentLevel);
		levelTable(currentLevel);
		timer.start(); // 타이머 시작 설정
	}


	// UI 구성 메소드
	private void buildGUI() {
		getContentPane().setLayout(null);

		// "다음->" 버튼 생성 및 설정
		levelCompleteButton = new JButton("다음->");
		levelCompleteButton.setBounds(560, 370, 150, 60);
		getContentPane().add(levelCompleteButton);
		levelCompleteButton.setEnabled(false);

		levelCompleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// "디음->" 버튼 동작 기능 설정
				currentLevel++;
				updateLevel(currentLevel);
				levelTable(currentLevel);
				levelCompleteButton.setEnabled(false);
				timer.restart();
				resetTimerLabel();
				t_input.setEnabled(true);
				t_input.setText("");
				t_input.requestFocusInWindow();
			}
		});

		Font buttonFont = levelCompleteButton.getFont();
		levelCompleteButton.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));

		t_input = new JTextField(30); // 보내기 입력창 설정
		JButton b_send = new JButton("보내기"); // "보내기" 버튼 설정

		ActionListener sendActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 보내기 버튼 동작 기능 설정
				checkAnswer();
				timer.stop();
			}
		};

		b_send.addActionListener(sendActionListener);
		t_input.addActionListener(sendActionListener); // 엔터 키 입력 처리

		// 위치 설정
		t_input.setBounds(0, 522, 692, 45);
		b_send.setBounds(690, 522, 100, 45);

		// 화면에 추가
		add(t_input);
		add(b_send);

		// 기능 황성화
		t_input.setEnabled(true);
		b_send.setEnabled(true);

		t_input.requestFocusInWindow();
		// 화면 구성 메소드 호출
		getContentPane().add(createDisplayPanel());
		getContentPane().add(createQuestionPanel());

		// 레벨 난이도 문제 초기화
		levelTable(1);
	}


	private JPanel createDisplayPanel() {
		// 디스플레이를 담당하는 패널 생성
		JPanel p = new JPanel();
		p.setBounds(0, 0, 0, 0); // 패널의 위치, 크기 설정
		p.setLayout(null); // 레이아웃 null로 설정

		t_display = new JTextArea();
		t_display.setEditable(false); // 편집 불가능으로 설정

		timerLabel = new JLabel(Integer.toString(count));
		timerLabel.setBounds(665, 38, 450, 60); // 위치 크기 설정
		timerLabel.setFont(new Font("Arial", Font.BOLD, 45)); // 폰트 설정
		getContentPane().add(timerLabel); // 프레임에 추가

		timer = new Timer(1000, e -> updateTimer());
		levelBar = new LevelBar();
		levelBar.setBounds(210, 50, 370, 35); // 레벨 바의 위치와 크기를 설정
		getContentPane().add(levelBar); // 레벨 바를 프레임에 추가

		updateLevel(1); // 메서드 호출, 초기 레벨 표시

		return p;
	}


	// 게임 UI와 로직을 담당
	private class GameTimer extends Thread {
		@Override
		public void run() {
			try {
				sleep(2000); // 게임 시작 2초 대기
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 2초 대기 후 게임 시작
			startLabel.setVisible(false); // 시작 레이블 숨김
			startGame(); // 게임 시작
		}
	}


	// 타이머 업데이트 메서드
	private void updateTimer() {
		if (--count > 0) {
			timerLabel.setText(Integer.toString(count)); // 타이머 감소 및 표시
		} else {
			timer.stop(); // 타이머 정지
			timerLabel.setText("X"); // 시간 초과 표시
			gameOver(); // 게임 오버 처리
		}

	}


	// 타이머 라벨 초기화 메서드
	private void resetTimerLabel() {
		count = 7;
		timerLabel.setText(Integer.toString(count)); // 타이머 초기화
	}


	// 레벨 표시, 업데이트 메서드
	private void updateLevel(int level) {
		if (levelLabel == null) {
			// 레벨 라벨 생성
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

		levelBar.setLevel(level); // 레벨 바 업데이트
		levelBar.repaint(); // 레벨 바 다시 그리기

		repaint(); // 화면 갱신
	}


	// 레벨을 나타내는 바를 그리는 클래스
	private class LevelBar extends JPanel {
		private int level;


		public LevelBar() {
			setPreferredSize(new Dimension(300, 20)); // 사이즈 설정
			level = 0;
		}


		public void setLevel(int level) {
			this.level = level; // 레벨 설정
		}


		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// 레벨 바 그리기
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


	// 문제 패널 생성 메서드
	private JPanel createQuestionPanel() {
		JPanel p = new JPanel(null);
		p.setBounds(0, 0, 786, 562);

		// 각각 숫자와 연산자 입력 필드 생성 및 설정
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


	// 레벨에 따라 다른 종류의 문제 생성
	private void levelTable(int level) {
		Random rand = new Random();
		int num1 = 0;
		int num2 = 0;

		String operator = " ";

		switch (level) {
		// 레벨 1~3
		case 1:
		case 2:
		case 3:
			num1 = rand.nextInt(10) + 5;
			num2 = rand.nextInt(6) + 1;
			String[] operators1 = { "+", "-" };
			operator = operators1[rand.nextInt(operators1.length)];
			break;
		// 레벨 4~5
		case 4:
		case 5:
			num1 = rand.nextInt(10) + 2;
			num2 = rand.nextInt(9) + 2;
			operator = "*";
			break;
		// 레벨 6~8
		case 6:
		case 7:
		case 8:
			num1 = rand.nextInt(30) + 20;
			num2 = rand.nextInt(20) + 10;
			String[] operators2 = { "+", "-" };
			operator = operators2[rand.nextInt(operators2.length)];
			break;
		// 레벨 9~10
		case 9:
		case 10:
			num1 = rand.nextInt(20) + 10;
			num2 = rand.nextInt(10) + 1;
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


	// 사용자의 답 체크 메서드
	private void checkAnswer() {
		int userAnswer = Integer.parseInt(t_input.getText()); // 사용자 입력 값 가져오기
		int correctAnswer = calculateResult(); // 정답 계산

		if (userAnswer == correctAnswer) { // 10단계 클리어시 최종화면 버튼 활성화
			if (getCurrentLevel() == 10) {
				gameOver();
			} else { // 오답시 최종화면 버튼 활성화
				levelCompleteButton.setEnabled(true);
				t_input.setEnabled(false);
			}

		} else {
			gameOver();
		}

	}


	// 계산 결과 메서드
	private int calculateResult() {
		int num1 = Integer.parseInt(t_operand1.getText()); // 첫 번째 피연산자
		int num2 = Integer.parseInt(t_operand2.getText()); // 두 번째 피연산자
		String operator = t_operator1.getText(); // 연산자
		int result = 0; // 결과 값 초기화

		// 연산자에 따른 계산 수행
		switch (operator) {
		case "+":
			result = num1 + num2; // 더하기
			break;
		case "-":
			result = num1 - num2; // 뺴기
			break;
		case "*":
			result = num1 * num2; // 곱하기
			break;
		case "/":
			result = num1 / num2; // 나누기
			break;
		}

		return result;
	}


	// 게임 종료 처리 메서드
	private void gameOver() {
		timer.stop(); // 타이머 정지
		startLabel.setVisible(false); // 시작 레이블 숨기기
		t_input.setEnabled(false); // 입력 필드 비활성화

		// 버튼 기능 동작 추가
		finalResultButton.setVisible(true);
		finalResultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_04GameOverUI nextClassFrame = new _04GameOverUI();
				nextClassFrame.setVisible(true); // 보이게 표시
				dispose(); // 현재 창 닫기
			}
		});
	}


	// 현재 레벨을 반환하는 메서드
	public static int getCurrentLevel() {
		return currentLevel; // 현재 레벨 번환
	}


	public static void main(String[] args) {
		new _03_0GameUI(); // 객체 생성
	}
}