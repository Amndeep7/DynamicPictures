package asm.gui;

import javax.swing.JFrame;

public class Driver
{
	public static int REALFRAMEX = 800;
	public static int REALFRAMEY = 800;

	public static void main(String[] args) throws Exception
	{
		JFrame frame = new JFrame("Light Snakes");
		frame.setSize(REALFRAMEX, REALFRAMEY);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel());
		frame.validate();
		frame.setVisible(true);
	}
}

/*
 * maybe create separate color pallet class that'll have stuff like this, or just two colors (think latin project), etc. maybe have "food" so that the snakes
 * can eat it to grow longer/brighter/have more "impact"s corollary: if don't eat food, lose brightness/"impact"(alpha)/length/combo of these corollary: ai
 * behind them to try to get food corollary: maybe can eat other snakes maybe make snakes not squares maybe add ui sort of stuff
 */
