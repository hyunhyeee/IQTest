// 03. 게임 진행 화면
// 단어 암기 테스트

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
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


public class _03_Memory_Game extends JFrame {

	private JTextArea t_display;
	private JTextField word01, word02, word03;
	private JButton b_result, b_levelComplete, b_music, b_send;
	private JLabel timerLabel, levelLabel, startLabel;

	private GameTimer gameTimer;
	private Timer timer, wordTimer;
	private LevelBar levelBar;
	private int[] usedWordIndices = new int[3]; // 최대 3개의 단어 인덱스를 저장하는 배열

	private HashSet<Integer> usedWord = new HashSet<>(); // 이미 사용된 단어의 인덱스를 추적할 HashSet
	private String[] words = { "사과", "포도", "나라", "고향", "도마", "과자", "손주", "아들", "부모", "이불", "침대",
	                "안경", "비행기", "자동차", "효자손", "옷걸이", "손잡이", "충전기", "비둘기", "손수건", "선풍기", "귀성길",
	                "음료수", "항아리", "제기차기", "가족사진", "대형창고", "강강술래", "사자성어", "대한민국", "비밀번호", "호랑나비",
	                "하모니카", "정월대보름", "지구온난화", "광개토대왕", "공기청정기", "장수하늘소", "종이비행기", "국제우주정거장",
	                "공동경비구역", "고속버스터미널", "주택청약통장", "아이스아메리카노", "동대문역사문화공원역" };

	private int count = 15;
	private static int currentLevel = 1;

	public BackgroundMusicPlayer musicPlayer;
	private boolean isCorrect;


	public _03_Memory_Game() {
		// JFrame 설정
		super("기억 능력 검사");
		musicPlayer = new BackgroundMusicPlayer();
		musicPlayer.playBackgroundMusic();
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		// 시작을 나타내는 레이블 설정, 2초 후 게임 시작을 담당하는 GameTimer 초기화
		startLabel = new JLabel("테스트가 잠시 후 시작됩니다");
		startLabel.setBounds(130, 230, 600, 50);
		startLabel.setFont(new Font("맑은고딕", Font.BOLD, 40));
		getContentPane().setLayout(null);
		getContentPane().add(startLabel);

		gameTimer = new GameTimer();
		gameTimer.start();

		// 최종 결과 보기 버튼 초기화
		b_result = new JButton("최종 결과 보기");
		b_result.setBounds(560, 380, 150, 60);
		getContentPane().add(b_result);
		b_result.setVisible(false); // 처음에는 보이지 않도록 설정

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

		b_music = new JButton(scaledSoundOnIcon); // 초기 이미지 아이콘 설정
		b_music.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				musicPlayer.toggleMusic();
				if (musicPlayer.isMusicOn()) {

					b_music.setIcon(scaledSoundOnIcon); // 음악이 꺼진 상태의 이미지로 변경
				} else {

					b_music.setIcon(scaledSoundOffIcon); // 음악이 켜진 상태의 이미지로 변경
				}

			}
		});
		b_music.setBounds(720, 0, 65, 40);
		b_music.setContentAreaFilled(false); // 배경색 제거
		b_music.setBorderPainted(false); // 테두리 없애기
		b_music.setFocusPainted(false); // 글씨 테두리 없애기

		add(b_music);

	}


	public void startGame() {
		buildGUI(); // 메서드 호출
		currentLevel = 1; // 초기 레벨 설정
		updateLevel(currentLevel);
		levelTable(currentLevel);
		displayWord(); // 타이머 시작 설정
	}


	// UI 구성 메소드
	private void buildGUI() {
		getContentPane().setLayout(null);

		// "다음->" 버튼 생성 및 설정
		b_levelComplete = new JButton("다음->");
		b_levelComplete.setBounds(560, 380, 150, 60);
		b_levelComplete.setVisible(false); // 처음에는 보이지 않도록 설정
		getContentPane().add(b_levelComplete);
		b_levelComplete.setEnabled(false);

		b_levelComplete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// "디음->" 버튼 동작 기능 설정
				currentLevel++;
				updateLevel(currentLevel);
				levelTable(currentLevel);
				// b_levelComplete.setEnabled(false);
				b_levelComplete.setVisible(false);
				wordTimer.restart();
				resetTimerLabel();

				b_send.setEnabled(false);// 기능 활성화
			}
		});

		Font buttonFont = b_levelComplete.getFont();
		b_levelComplete.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));

		b_send = new JButton("보내기"); // "보내기" 버튼 설정
		ActionListener sendActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 보내기 버튼 동작 기능 설정
				checkAnswer();
				timer.stop();

			}
		};

		b_send.addActionListener(sendActionListener);
		b_send.setBounds(560, 380, 150, 60);// 위치 설정
		b_send.setEnabled(false);// 기능 활성화
		add(b_send);// 화면에 추가

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


	// 문제 패널 생성 메서드
	private JPanel createQuestionPanel() {
		JPanel p = new JPanel(null);
		p.setBounds(0, 0, 786, 562);

		// 각각 숫자와 연산자 입력 필드 생성 및 설정
		word01 = new JTextField(30);
		word02 = new JTextField(30);
		word03 = new JTextField(30);

		word01.setHorizontalAlignment(SwingConstants.LEFT);
		word02.setHorizontalAlignment(SwingConstants.LEFT);
		word03.setHorizontalAlignment(SwingConstants.LEFT);

		word01.setBounds(85, 180, 350, 60);
		word02.setBounds(85, 280, 350, 60);
		word03.setBounds(85, 380, 350, 60);

		p.add(word01);
		p.add(word02);
		p.add(word03);

		word01.setFont(new Font("굴림", Font.PLAIN, 30));
		word02.setFont(new Font("굴림", Font.PLAIN, 30));
		word03.setFont(new Font("굴림", Font.PLAIN, 30));

		word01.setEditable(false);
		word02.setEditable(false);
		word03.setEditable(false);

		return p;
	}


	// 레벨에 따라 다른 종류의 문제 생성
	private void levelTable(int level) {
		switch (level) {
		// 레벨 1~2
		case 1:
		case 2:
			setWordIndicesForEasyLevel();
			break;
		// 레벨 3~5
		case 3:
		case 4:
		case 5:
			setWordIndicesForMediumLevel();
			break;
		// 레벨 6~7
		case 6:
		case 7:
			setWordIndicesForHardLevel();
			break;
		// 레벨 8~10
		case 8:
		case 9:
		case 10:
			setWordIndicesForVeryHardLevel();
			break;
		default:
			break;
		}

	}


	private void setWordIndicesForEasyLevel() {
		// 레벨 1~3의 경우, 2글자 단어를 랜덤하게 선택하여 사용
		Random rand = new Random();
		usedWordIndices[0] = getRandomIndexByLength(rand, 2);
		usedWordIndices[1] = getRandomIndexByLength(rand, 2);
		usedWordIndices[2] = getRandomIndexByLength(rand, 2);
		displayWord();
	}


	private void setWordIndicesForMediumLevel() {
		// 레벨 4~5의 경우, 3글자 단어를 랜덤하게 선택하여 사용
		Random rand = new Random();
		usedWordIndices[0] = getRandomIndexByLength(rand, 3);
		usedWordIndices[1] = getRandomIndexByLength(rand, 3);
		usedWordIndices[2] = getRandomIndexByLength(rand, 3);
		displayWord();
	}


	private void setWordIndicesForHardLevel() {
		// 레벨 6~8의 경우, 4글자 단어를 랜덤하게 선택하여 사용
		Random rand = new Random();
		usedWordIndices[0] = getRandomIndexByLength(rand, 4);
		usedWordIndices[1] = getRandomIndexByLength(rand, 4);
		usedWordIndices[2] = getRandomIndexByLength(rand, 4);
		displayWord();
	}


	private void setWordIndicesForVeryHardLevel() {
		// 레벨 9~10의 경우, 5글자 이상 단어를 랜덤하게 선택하여 사용
		Random rand = new Random();
		usedWordIndices[0] = getRandomIndexByLengthGreaterThan(rand, 4);
		usedWordIndices[1] = getRandomIndexByLengthGreaterThan(rand, 4);
		usedWordIndices[2] = getRandomIndexByLengthGreaterThan(rand, 4);
		displayWord();
	}


	private int getRandomIndexByLength(Random rand, int length) {
		int randomIndex;
		do {
			randomIndex = rand.nextInt(words.length);
		} while (!(words[randomIndex].length() == length) || usedWord.contains(randomIndex));

		usedWord.add(randomIndex);
		return randomIndex;
	}


	private int getRandomIndexByLengthGreaterThan(Random rand, int length) {
		int randomIndex;
		do {
			randomIndex = rand.nextInt(words.length);
		} while (!(words[randomIndex].length() > length) || usedWord.contains(randomIndex));

		usedWord.add(randomIndex);
		return randomIndex;
	}


	private void displayWord() {
		// Random rand = new Random();
		// usedWordIndices[0] = getRandomIndex(rand);
		// usedWordIndices[1] = getRandomIndex(rand);
		// usedWordIndices[2] = getRandomIndex(rand);

		word01.setText(words[usedWordIndices[0]]);
		word02.setText(words[usedWordIndices[1]]);
		word03.setText(words[usedWordIndices[2]]);

		wordTimer = new Timer(2000, e -> {
			hideWord();
			enableInputFields(); // 입력란 활성화
			timer.start();
		});
		wordTimer.setRepeats(false);
		wordTimer.start();
	}


	private int getRandomIndex(Random rand) {
		int randomIndex;
		do {
			randomIndex = rand.nextInt(words.length);
		} while (usedWord.size() < words.length && usedWord.contains(randomIndex)); // 이미 선택된 인덱스인지
		                                                                            // 확인

		if (usedWord.size() >= words.length) {
			usedWord.clear(); // 모든 단어가 선택되었으면 초기화
		}

		usedWord.add(randomIndex); // 선택된 인덱스 기록

		return randomIndex;
	}


	private void hideWord() {
		word01.setText(""); // 단어를 화면에서 지움
		word02.setText("");
		word03.setText("");
	}


	private void enableInputFields() {
		word01.setEditable(true); // 입력란 활성화
		word02.setEditable(true);
		word03.setEditable(true);

		b_send.setEnabled(true);
		word01.requestFocusInWindow(); // 포커스 설정
		word01.addActionListener(e -> word02.requestFocusInWindow());
		word02.addActionListener(e -> word03.requestFocusInWindow());
		word03.addActionListener(e -> b_send.requestFocusInWindow());
	}


	private boolean checkIfCorrect(String answer1, String answer2, String answer3) {
		// // 사용자가 입력한 값들을 HashSet에 담기
		// HashSet<String> userAnswers = new HashSet<>();
		// userAnswers.add(answer1);
		// userAnswers.add(answer2);
		// userAnswers.add(answer3);
		//
		// // 정답들을 HashSet에 담기
		// HashSet<String> correctAnswers = new HashSet<>();
		// correctAnswers.add(words[usedWord.toArray(new Integer[0])[0]]);
		// correctAnswers.add(words[usedWord.toArray(new Integer[0])[1]]);
		// correctAnswers.add(words[usedWord.toArray(new Integer[0])[2]]);
		//
		// // 정답들과 사용자 입력 값들을 비교하여 일치 여부 반환
		// return correctAnswers.equals(userAnswers);

		// 사용자가 입력한 값들을 HashSet에 담기
		HashSet<String> userAnswers = new HashSet<>();
		userAnswers.add(answer1);
		userAnswers.add(answer2);
		userAnswers.add(answer3);

		// 정답들을 HashSet에 담기
		HashSet<String> correctAnswers = new HashSet<>();
		correctAnswers.add(words[usedWordIndices[0]]);
		correctAnswers.add(words[usedWordIndices[1]]);
		correctAnswers.add(words[usedWordIndices[2]]);

		// 정답들과 사용자 입력 값들을 비교하여 일치 여부 반환
		return correctAnswers.equals(userAnswers);

		// String correctAnswer1 = word01.getText();
		// String correctAnswer2 = word02.getText();
		// String correctAnswer3 = word03.getText();
		//
		// return answer1.equals(correctAnswer1)
		// && answer2.equals(correctAnswer2)
		// && answer3.equals(correctAnswer3);
	}


	private void checkAnswer() {
		// 사용자 입력 값 가져오기
		String answer1 = word01.getText();
		String answer2 = word02.getText();
		String answer3 = word03.getText();

		// 정답 체크 로직을 checkIfCorrect 메서드로 대체
		isCorrect = checkIfCorrect(answer1, answer2, answer3);

		if (isCorrect) {
			// 정답일 때
			b_levelComplete.setEnabled(true);
			b_levelComplete.setVisible(true);
			b_result.setEnabled(false);
			b_result.setVisible(false);

			word01.setEditable(false);
			word02.setEditable(false);
			word03.setEditable(false);

			if (getCurrentLevel() == 10) {
				// 마지막 레벨이면 게임 오버 처리
				gameOver();
			}

		} else {
			// 오답일 때
			gameOver();
		}

	}


	// 게임 종료 처리 메서드
	private void gameOver() {
		startLabel.setVisible(false); // 시작 레이블 숨기기
		timer.stop(); // 타이머 정지
		word01.setEnabled(false); // 입력 필드 비활성화
		word02.setEnabled(false);
		word03.setEnabled(false);
		b_result.setVisible(true);
		b_result.setEnabled(true);
		b_send.setEnabled(false);
		b_send.setVisible(false);
		b_levelComplete.setEnabled(false);
		b_levelComplete.setVisible(false);
		b_result.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_04_Memory_Gameover nextClassFrame = new _04_Memory_Gameover();
				// nextClassFrame.setVisible(true); // 보이게 표시
				dispose(); // 현재 창 닫기
			}
		});
	}


	// 현재 레벨을 반환하는 메서드
	public static int getCurrentLevel() {
		return currentLevel; // 현재 레벨 번환
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
		count = 15;
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

			g.setColor(Color.GREEN);
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

		new _03_Memory_Game(); // 객체 생성
	}
}