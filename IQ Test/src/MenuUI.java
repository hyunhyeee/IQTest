// 객지 메뉴 화면 구성 완료

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MenuUI extends JFrame {

	public MenuUI() {

		buildGUI();

		setTitle("메뉴 화면 구성");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);

		JLabel title = new JLabel("게임을 선택하세요");
		title.setBounds(100, 50, 300, 100);
		title.setForeground(Color.BLACK);

		Font labelFont = title.getFont();
		title.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		add(title);

		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/java_pic.jpg");
		backgroundPanel.setSize(new Dimension(800, 600));
		add(backgroundPanel);

		setVisible(true);
	}


	private void buildGUI() {
		JButton btn1 = new JButton("산술문제");
		JButton btn2 = new JButton("속담 맞추기");
		JButton btn3 = new JButton("상식 테스트");

		btn1.setBounds(150, 150, 500, 60);
		add(btn1);
		btn2.setBounds(150, 220, 500, 60);
		add(btn2);
		btn3.setBounds(150, 290, 500, 60);
		add(btn3);

		Font buttonFont = btn1.getFont();
		btn1.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
		btn2.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
		btn3.setFont(new Font(buttonFont.getName(), Font.BOLD, 15));
	}


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
		new MenuUI();
	}
}
