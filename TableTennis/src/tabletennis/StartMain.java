package tabletennis;

import java.awt.BorderLayout;

import javax.swing.JFrame;


public class StartMain extends JFrame {
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Table Tennis v1.0");

		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(600, 315);
		frame.setLocation(10, 10);
		
		MainWindow mw = new MainWindow();
		frame.add(mw, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
}
