// 객지 게임 시작화면 구현 완료


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//setLocation((int)((dmen.width - getSize().width)/2), 
//(int)((dmen.height - getSize().height))/2);
// 어떤 환경이든 위치를 정중앙에 배치


// 시작화면
public class StartUI extends JFrame {

	public StartUI() {
		setLayout(null);

		JLabel title = new JLabel("당신의 지능은 몇살인가요?");
		title.setBounds(190, 150, 1000, 100);
		title.setForeground(Color.BLACK);

		Font labelFont = title.getFont();
		title.setFont(new Font(labelFont.getName(), Font.PLAIN, 50));
		add(title);

		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/java_pic.jpg");
		backgroundPanel.setSize(new Dimension(1000, 700));
		add(backgroundPanel);

		
		
		JButton s_Button = new JButton("시작하기");
		s_Button.setBounds(650, 370, 150, 50);
		add(s_Button);
		
		Font buttonFont = title.getFont();
		s_Button.setFont(new Font(buttonFont.getName(), Font.BOLD, 20));

		
		setTitle("지능테스트 첫 화면");
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}


	// Custom JPanel to display an image
	class ImagePanel extends JPanel {
		private ImageIcon imageIcon;
		
		public ImagePanel(String imagePath) {
			imageIcon = new ImageIcon(getClass().getResource(imagePath));
		}


		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}


	public static void main(String[] args) {
		new StartUI();
	}

}
