package asm.lightsnakes;

import java.awt.Color;
import java.awt.Graphics;

public class Snake
{
	protected int xPos, yPos, size, spread;
	protected double angle, inertia, speed;
	protected Color color;

	protected Snake tail;

	public Snake(int x, int y, int si, int sp, double a, double i, double s, Color c)
	{
		xPos = x;
		yPos = y;
		size = si;
		spread = sp;
		angle = a;
		inertia = i;
		speed = s;
		color = c;

		tail = null;
	}

	public void addTail()
	{
		if(tail == null)
		{
			tail = new Snake(xPos, yPos, size, spread, angle, inertia, speed, color.darker().darker());
		}
		else
		{
			tail.addTail();
		}
	}

	public void passStateToTail()
	{
		if(tail != null)
		{
			tail.passStateToTail();

			tail.xPos = xPos;
			tail.yPos = yPos;
			tail.size = size;
			tail.spread = spread;
			tail.angle = angle;
			tail.inertia = inertia;
			tail.speed = speed;
			tail.color = color;
		}
	}

	public void updateDirection()
	{
		if(Math.random() >= inertia)
			angle += Math.random() * Math.toRadians(spread) - Math.toRadians(spread / 2);
		angle %= 2 * Math.PI;
		if(angle < 0)
			angle += 2 * Math.PI;
		else if(angle >= 2 * Math.PI)
			angle -= 2 * Math.PI;
	}

	public void containMovement(int minx, int miny, int maxx, int maxy)
	{
		if(xPos < minx)
		{
			xPos = minx;
			angle = Math.PI / 2 + (Math.PI / 2 - angle);
		}
		else if(xPos + size >= maxx)
		{
			xPos = maxx - size;
			angle = 3 * Math.PI / 2 + (3 * Math.PI / 2 - angle);
		}

		if(yPos < miny)
		{
			yPos = miny;
			angle = Math.PI + (Math.PI - angle);
		}
		else if(yPos + size >= maxy)
		{
			yPos = maxy - size;
			angle = -angle;
		}

		angle %= 2 * Math.PI;
		if(angle < 0)
			angle += 2 * Math.PI;
		else if(angle >= 2 * Math.PI)
			angle -= 2 * Math.PI;
	}

	public void move(int minx, int miny, int maxx, int maxy)
	{
		passStateToTail();

		xPos += (int) (Math.cos(angle) * speed);
		yPos += (int) (Math.sin(angle) * speed);

		updateDirection();
		containMovement(minx, miny, maxx, maxy);
	}

	public void draw(Graphics g)
	{
		g.setColor(color);
		g.fillRect(xPos, yPos, size, size);
		if(tail != null)
		{
			tail.draw(g);
		}
	}

	public String toString()
	{
		return xPos + " " + yPos + " " + Math.toDegrees(angle) % 360;
	}
}
