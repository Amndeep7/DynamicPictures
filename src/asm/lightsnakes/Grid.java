package asm.lightsnakes;

import java.awt.Color;
import java.awt.Graphics;

public class Grid
{
	private int width, height;

	private Color[][] grid;

	public Grid(int w, int h, Color c)
	{
		width = w;
		height = h;

		grid = new Color[height][width];

		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				grid[y][x] = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
			}
		}
	}

	public void draw(Graphics g, int granularity)
	{
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				g.setColor(grid[y][x]);
				g.fillRect(x * granularity, y * granularity, granularity, granularity);
			}
		}
	}
}
