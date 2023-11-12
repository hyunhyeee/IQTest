// 메뉴 화면 구성 완료

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
	private JButton btn1, btn2, btn3;
	
    public MenuUI() {
		
    	setTitle("메뉴 화면 구성");
    	setSize(800, 600);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(null);
    	
		JLabel title = new JLabel("게임을 선택하세요");
		title.setBounds(100, 50, 300, 100);
		title.setForeground(Color.BLACK);

		Font labelFont = title.getFont();
		title.setFont(new Font(labelFont.getName(), Font.PLAIN, 30));
		add(title);
		
		ImagePanel backgroundPanel = new ImagePanel("/Game_pic/mountain.png");
		backgroundPanel.setSize(new Dimension(1000, 700));
		add(backgroundPanel);
    	
    	buildGUI();
    	setVisible(true);
    }
    
    private void buildGUI() {
    	
    	JButton btn1 = new JButton("산술문제");
    	JButton btn2 = new JButton("속담 맞추기");
    	JButton btn3 = new JButton("상식 테스트");
    	
    	btn1.setBounds(150, 150, 500, 60);
    	btn2.setBounds(150, 220,500, 60);
    	btn3.setBounds(150, 290, 500, 60);
    	
    	add(btn1);
    	add(btn2);
    	add(btn3);
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





//// 메뉴 화면 구성
//
//import java.awt.Container;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//
//public class MenuUI extends JFrame {
//	
//    public MenuUI() {
//    	
//    	JFrame f = new JFrame();
//    	
//    	JButton btn1 = new JButton();
//    	JButton btn2 = new JButton();
//    	JButton btn3 = new JButton();
//    	Container c = f.getContentPane();
//    	
//    	f.setTitle("메뉴 화면 구성");
//    	f.setSize(800, 600);
//    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	c.setLayout(null);
//    	
//    	btn1.setText("산술문제");
//    	btn2.setText("속담 맞추기");
//    	btn3.setText("상식 테스트");
//    	
//    	btn1.setBounds(150, 50, 500, 60);
//    	btn2.setBounds(150, 120,500, 60);
//    	btn3.setBounds(150, 190, 500, 60);
//    	
//    	c.add(btn1);
//    	c.add(btn2);
//    	c.add(btn3);
//    	
//    	f.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        new MenuUI();
//    }
//}