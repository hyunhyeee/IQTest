import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class _03_Knowledge_Game extends JFrame {
	private static int currentLevel = 1;
	private JLabel levelLabel;
	
	private JLabel startLabel;
	private GameTimer gameTimer;
	
	private JButton finalResultButton;
	private JButton nextButton;
	
	private LevelBar levelBar;
	
	private JTextArea t_display;
    private JLabel question, question2;
    private JCheckBox option1, option2, option3, option4;
    private ButtonGroup buttonGroup;
    
	public BackgroundMusicPlayer musicPlayer;
	public JButton musicButton;
    
    private static int userScore = 1;
    
    public _03_Knowledge_Game() {
		musicPlayer = new BackgroundMusicPlayer();
		musicPlayer.playBackgroundMusic();
		
		
		setLayout(null); 
		setTitle("게임 메인 화면 구성");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		
		// 시작을 나타내는 레이블 설정, 2초 후 게임 시작을 담당하는 GameTimer 초기화
		startLabel = new JLabel("게임이 잠시 후 시작됩니다");
		startLabel.setBounds(160, 230, 500, 50);
		startLabel.setFont(new Font("맑은고딕", Font.BOLD, 40));
		getContentPane().setLayout(null);
		getContentPane().add(startLabel);

		gameTimer = new GameTimer();
		gameTimer.start();
			
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
		
		setVisible(true);
    }
    
	// 프로그램 이미지 바탕화면 클래스
	public class ImagePanel extends JPanel {
	    private ImageIcon imageIcon;

	    public ImagePanel(String imagePath) {
	        imageIcon = new ImageIcon(getClass().getResource(imagePath));
	        setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight())); // 이미지 크기에 맞게 Panel 크기 설정
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
	    }
	}
    
	public void startGame() {
		buildGUI(); // 메서드 호출
	    createDisplayPanel();
		updateLevel(currentLevel);
		levelTable(currentLevel);
	}
	
	// UI 구성 메소드
	private void buildGUI() {
		getContentPane().setLayout(null);

		// "다음->" 버튼 생성 및 설정
		nextButton = new JButton("다음->");
		nextButton.setBounds(560, 370, 150, 60);
		getContentPane().add(nextButton);
		nextButton.setEnabled(true);

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkAnswer();
				currentLevel++;
				updateLevel(currentLevel);
				levelTable(currentLevel);
				nextButton.setEnabled(true);
			}
		});
		Font buttonFont = nextButton.getFont();
		nextButton.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
		
		
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
	    // 레벨 난이도 문제 초기화
	    levelTable(1);
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
			startGame();
		}
	}
	
	// 현재 레벨을 반환하는 메서드
	public static int getCurrentLevel() {
		return currentLevel; // 현재 레벨 번환
	}
	
    public static int getUserScore() {
        return userScore;
    }
	
	private JPanel createDisplayPanel() {
		// 디스플레이를 담당하는 패널 생성
		JPanel p = new JPanel();
		p.setBounds(0, 0, 0, 0); // 패널의 위치, 크기 설정
		p.setLayout(null); // 레이아웃 null로 설정

		t_display = new JTextArea();
		t_display.setEditable(false); // 편집 불가능으로 설정


		levelBar = new LevelBar();
		levelBar.setBounds(210, 50, 370, 35); // 레벨 바의 위치와 크기를 설정
		getContentPane().add(levelBar); // 레벨 바를 프레임에 추가

		updateLevel(1); // 메서드 호출, 초기 레벨 표시
		
	    getContentPane().add(p); // 패널을 프레임에 추가
		return p;
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
			level = 1;
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
	
	private void createQuestionPanel() {
	    if (question != null) {
	        getContentPane().remove(question);
	        getContentPane().remove(question2);
	        getContentPane().remove(option1);
	        getContentPane().remove(option2);
	        getContentPane().remove(option3);
	        getContentPane().remove(option4);
	    }

        // 문제를 나타내는 레이블
        question = new JLabel();
        question.setBounds(50, 160, 600, 30);
        question.setFont(new Font("맑은고딕", Font.PLAIN, 20));
        
        // 소제목 문제 나타내는 레이블
        question2 = new JLabel();
        question2.setBounds(50, 195, 600, 30);
        question2.setFont(new Font("맑은고딕", Font.BOLD, 17));
        question2.setForeground(Color.BLUE);

        // 체크박스 생성
        option1 = new JCheckBox();
        option2 = new JCheckBox();
        option3 = new JCheckBox();
        option4 = new JCheckBox();

        // 체크박스의 위치를 고정시키기
        option1.setBounds(50, 230, 500, 50);
        option2.setBounds(50, 300, 500, 50);
        option3.setBounds(50, 370, 500, 50);
        option4.setBounds(50, 440, 500, 50);
      
        // 폰트 설정
        option1.setFont(new Font("맑은고딕", Font.PLAIN, 20));
        option2.setFont(new Font("맑은고딕", Font.PLAIN, 20));
        option3.setFont(new Font("맑은고딕", Font.PLAIN, 20));
        option4.setFont(new Font("맑은고딕", Font.PLAIN, 20));

        option1.setBorderPainted(true);
        option2.setBorderPainted(true);
        option3.setBorderPainted(true);
        option4.setBorderPainted(true);
        
        // 라디오 버튼을 그룹으로 묶기
        buttonGroup = new ButtonGroup();
        buttonGroup.add(option1);
        buttonGroup.add(option2);
        buttonGroup.add(option3);
        buttonGroup.add(option4);
      
        // 프레임에 추가
        add(question);
        add(question2);
        add(option1);
        add(option2);
        add(option3);
        add(option4);
		
	}
	
	//문제 생성 메소드
	private void levelTable(int level) {
		createQuestionPanel();

	    switch (level) {
	        case 1:
	            question.setText("문제 1: 올해는 몇 년도인가요?");
	            option1.setText("2022");
	            option2.setText("2023");
	            option3.setText("2024");
	            option4.setText("2025");
	            break;

	        case 2:
	            question.setText("문제 2: 대한민국의 국보 1호는 무엇인가요?");
	            option1.setText("숭례문");
	            option2.setText("돈의문");
	            option3.setText("흥인지문");
	            option4.setText("숙정문");
	            break;

	        case 3:
	            question.setText("문제 3: 대한민국의 수도는 어디인가요?");
	            option1.setText("인천");
	            option2.setText("부산");
	            option3.setText("세종");
	            option4.setText("서울");
	            break;
	            
	        case 4:
	            question.setText("문제 4: 다음 뜻에 맞는 속담은 무엇일까요?");
	            question2.setText("      잘 아는 일이어도 꼼꼼하게 확인하고 조심해서 하라");
	            option1.setText("돌다리도 두들겨 보고 건너라.");
	            option2.setText("사공이 많으면 배가 산으로 간다.");
	            option3.setText("지렁이도 밟으면 꿈틀한다.");
	            option4.setText("낫 놓고 기역 자도 모른다.");
	            break;
	            
	        case 5:
	            question.setText("문제 5: 동그라미 두 개를 붙였을 때 연상되는 물체는 무엇일까요?");
	            option1.setText("컴퓨터");
	            option2.setText("눈사람");
	            option3.setText("냉장고");
	            option4.setText("지하철");
	            break;
	            
	        case 6:
	            question.setText("문제 6: [오후 5시]와 같은 단어를 고르세요.");
	            option1.setText("13시");
	            option2.setText("15시");
	            option3.setText("17시");
	            option4.setText("19시");
	            break;
	            
	        case 7:
	            question.setText("문제 7: 책을 사려고 할 때, 가야 하는 곳은 어디일까요?");
	            option1.setText("서점");
	            option2.setText("전자상가");
	            option3.setText("편의점");
	            option4.setText("음식점");
	            break;
	            
	        case 8:
	            question.setText("문제 8: [ 6+3+4=? ]");
	            option1.setText("10");
	            option2.setText("11");
	            option3.setText("12");
	            option4.setText("13");
	            break;
	            
	        case 9:
	            question.setText("문제 9: 6.25전쟁이 발발한 연도는 언제일까요?");
	            option1.setText("1945년");
	            option2.setText("1948년");
	            option3.setText("1950년");
	            option4.setText("1953년");
	            break;
	            
	        case 10:
	            question.setText("문제 10: 같은 동물로 묶인 것이 아닌 것은 무엇일까요?");
	            option1.setText("개-강아지");
	            option2.setText("소-망아지");
	            option3.setText("닭-병아리");
	            option4.setText("개구리-올챙이");
	            break;
	    }
	    checkAnswer();
	}
	
	// 사용자의 답 체크 메서드
    private void checkAnswer() {
        int correctAnswer = 1;

        switch (getCurrentLevel()) {
            case 1:
                correctAnswer = 2;
                break;
            case 2:
                correctAnswer = 1;
                break;
            case 3:
                correctAnswer = 4;
                break;
            case 4:
                correctAnswer = 1;
                break;
            case 5:
                correctAnswer = 2;
                break;
            case 6:
                correctAnswer = 3;
                break;
            case 7:
                correctAnswer = 1; 
                break;
            case 8:
                correctAnswer = 4;
                break;
            case 9:
                correctAnswer = 3; 
                break;
            case 10:
                correctAnswer = 2;
                break;
        }

        
        if (option1.isSelected() && correctAnswer == 1 ||
            option2.isSelected() && correctAnswer == 2 ||
            option3.isSelected() && correctAnswer == 3 ||
            option4.isSelected() && correctAnswer == 4) {
            userScore++;
         
        }
        if (getCurrentLevel() == 10) {
            gameOver();
        }
    }
	
    
	// 게임 종료 처리 메서드
	private void gameOver() {
		nextButton.setVisible(false);

		// 버튼 기능 동작 추가
		finalResultButton.setVisible(true);
		finalResultButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_04_Knowledge_Gameover nextClassFrame = new _04_Knowledge_Gameover();
				nextClassFrame.setVisible(true); // 보이게 표시
				dispose(); // 현재 창 닫기
			}
		});
	}
	
	
	public static void main(String[] args) {
        new _03_Knowledge_Game();
	}
}
