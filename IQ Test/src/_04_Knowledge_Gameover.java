import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class _04_Knowledge_Gameover extends JFrame {
	
	private JLabel scoreLabel, explainLabel, explainLabel2;
	
	public BackgroundMusicPlayer musicPlayer;
	public JButton musicButton;


    public _04_Knowledge_Gameover() {
    	
		musicPlayer = new BackgroundMusicPlayer();
		musicPlayer.playBackgroundMusic();
		
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
    	
		
    	setTitle("종료화면 구성");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        buildGUI(_03_Knowledge_Game.getUserScore());
        
		// 이미지를 표시할 패널 생성
		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/knowledge_Gameover.png");
		backgroundPanel.setSize(new Dimension(800, 600)); // 크기 설정
		add(backgroundPanel); // 프레임에 추가
		
		

        setVisible(true);
    }
    
	// 프로그램 이미지 바탕화면 클래스
	public class ImagePanel extends JPanel {
	    private ImageIcon imageIcon;

	    public ImagePanel(String imagePath) {
	        imageIcon = new ImageIcon(getClass().getResource(imagePath));
	        setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
	    }
	}
    
    private void buildGUI(int userScore) {
    	buildFinishUI(userScore);
	    createInputPanel(); // 입력 패널 생성 및 하단에 추가
    }
    
	// 입력 버튼 메서드
	private void createInputPanel() {
		JButton b_restart = new JButton("재시작하기");
		b_restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_03_Knowledge_Game newGameUI = new _03_Knowledge_Game();
				newGameUI.setVisible(true);
				dispose();
			}
		});

		JButton b_exit = new JButton("나가기");
		b_exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_02MenuUI firstFrame = new _02MenuUI();
				firstFrame.setVisible(true);
				dispose();
			}
		});
		
		
		// 버튼 위치 설정
		b_restart.setBounds(190, 400, 203, 108);
		b_restart.setFont(new Font("굴림", Font.BOLD, 20));
		b_restart.setContentAreaFilled(false); // 배경색 제거
		b_restart.setBorderPainted(false); // 테두리 없애기
		b_restart.setFocusPainted(false); // 글씨 테두리 없애기
		add(b_restart);
		
		b_exit.setBounds(477, 400, 203, 108);
		b_exit.setFont(new Font("굴림", Font.BOLD, 20));
		b_exit.setContentAreaFilled(false); // 배경색 제거
		b_exit.setBorderPainted(false); // 테두리 없애기
		b_exit.setFocusPainted(false); // 글씨 테두리 없애기
		add(b_exit);
	}
    
    
    
	private void buildFinishUI(int userScore) {
		
		explainLabel= new JLabel("기본 상식 능력 검사 결과는");
		explainLabel.setFont(new Font("맑은고딕", Font.BOLD, 30));
		explainLabel.setBounds(300, 150, 500, 50);

        scoreLabel = new JLabel(userScore + " / 10");
        scoreLabel.setFont(new Font("굴림", Font.BOLD, 40));
        scoreLabel.setBounds(300, 210, 500, 50);
        
		explainLabel2= new JLabel("나가기를 눌러 다른 검사도 진행해보세요!");
		explainLabel2.setFont(new Font("맑은고딕", Font.BOLD, 20));
        explainLabel2.setBounds(230, 350, 500, 50);

        add(explainLabel);
        add(scoreLabel);
        add(explainLabel2);

    }
	
	
	public static void main(String[] args) {
		new _04_Knowledge_Gameover(); // 객체 호출
	}

}
