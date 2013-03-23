package asm.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;

import asm.lightsnakes.ControlledSnake;
import asm.lightsnakes.Grid;
import asm.lightsnakes.Snake;

public class Panel extends JPanel
{
	private static final long serialVersionUID = 1187924516205302028L;

	Random random = new Random();

	public final int FRAMEX = 1000;
	public final int FRAMEY = 1000;

	BufferedImage myImage;
	Graphics myBuffer;

	Timer view;
	Timer tick;

	int granularity = 20;
	Grid grid;
	ArrayList<Snake> snakes;
	ControlledSnake controlled;

	public Panel()
	{
		Mouse m = new Mouse();
		addMouseListener(m);
		addMouseMotionListener(m);

		myImage = new BufferedImage(FRAMEX, FRAMEY, BufferedImage.TYPE_INT_ARGB);
		myBuffer = myImage.getGraphics();

		myBuffer.setColor(Color.BLACK);
		myBuffer.fillRect(0, 0, FRAMEX, FRAMEY);

		grid = new Grid(FRAMEX / granularity, FRAMEY / granularity, new Color(0, 0, 0, 20));
		snakes = new ArrayList<Snake>();

		float[] hsv = new float[3];
		hsv[0] = (float) Math.random();
		hsv[1] = (float) (Math.random() * 0.2 + 0.8);
		hsv[2] = (float) (Math.random() * 0.3 + 0.7);
		float golden_ratio = (float) 0.61803398875;
		for(int x = 0; x < 50; x++)
		{
			hsv[0] += golden_ratio;
			hsv[0] %= 1;

			snakes.add(new Snake(random.nextInt(FRAMEX), random.nextInt(FRAMEY), random.nextInt(100), random.nextInt(100), Math.random() * 2 * Math.PI, Math
			          .random() * 0.8, Math.random() * 75, HSVtoRGBA(hsv, (float) (Math.random() * 0.3))));
			int limit = random.nextInt(10);
			for(int y = 0; y < limit; y++)
				snakes.get(snakes.size() - 1).addTail();
		}

		controlled = new ControlledSnake(random.nextInt(FRAMEX), random.nextInt(FRAMEX), random.nextInt(100), 50, 0, 0.3, 30, new Color(255, 255, 0, 200));

		tick = new Timer(10, new TickTimer());
		tick.start();

		view = new Timer(1, new ViewTimer());
		view.start();
	}

	public static Color HSVtoRGBA(float[] hsv, float alpha)
	{
		float chroma = hsv[2] * hsv[1];
		float hscaled = hsv[0] * 6;
		float intermediate = chroma * (1 - Math.abs(hscaled % 2 - 1));

		float[] rgb1 = new float[3];
		if(hscaled < 1)
		{
			rgb1[0] = chroma;
			rgb1[1] = intermediate;
			rgb1[2] = 0;
		}
		else if(hscaled < 2)
		{
			rgb1[0] = intermediate;
			rgb1[1] = chroma;
			rgb1[2] = 0;
		}
		else if(hscaled < 3)
		{
			rgb1[0] = 0;
			rgb1[1] = chroma;
			rgb1[2] = intermediate;
		}
		else if(hscaled < 4)
		{
			rgb1[0] = 0;
			rgb1[1] = intermediate;
			rgb1[2] = chroma;
		}
		else if(hscaled < 5)
		{
			rgb1[0] = intermediate;
			rgb1[1] = 0;
			rgb1[2] = chroma;
		}
		else if(hscaled < 6)
		{
			rgb1[0] = chroma;
			rgb1[1] = 0;
			rgb1[2] = intermediate;
		}

		float match = hsv[2] - chroma;

		float[] retRGB = {rgb1[0] + match, rgb1[1] + match, rgb1[2] + match};

		return new Color(retRGB[0], retRGB[1], retRGB[2], alpha);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		grid.draw(myBuffer, granularity);

		for(Snake s : snakes)
		{
			s.draw(myBuffer);
		}

		controlled.draw(myBuffer);

		g.drawImage(myImage, 0, 0, getWidth(), getHeight(), 0, 0, FRAMEX, FRAMEY, null);
	}

	private class ViewTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			repaint();
		}
	}

	private class TickTimer implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			for(Snake s : snakes)
			{
				s.move(0, 0, FRAMEX, FRAMEY);
			}
			controlled.move(0, 0, FRAMEX, FRAMEY);
		}
	}

	private class Mouse extends MouseInputAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			switch(e.getButton())
			{
				case 1:
				{
					controlled.setSpeed(controlled.getSpeed() + 5);
					break;
				}
				case 2:
				{
					controlled.setIsBeingTracked(!controlled.getIsBeingTracked());
					break;
				}
				case 3:
				{
					controlled.setSpeed(controlled.getSpeed() - 5);
					break;
				}
				default:
				{
					// nothing cause something is seriously messed up if this case comes about
				}
			}
		}

		public void mouseMoved(MouseEvent e)
		{
			controlled.setMouseXPosition((int) ((e.getX() / (double) Driver.REALFRAMEX) * FRAMEX));
			controlled.setMouseYPosition((int) ((e.getY() / (double) Driver.REALFRAMEY) * FRAMEY));
		}
	}
}
