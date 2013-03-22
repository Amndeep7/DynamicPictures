package asm.lightsnakes;

import java.awt.Color;

public class ControlledSnake extends Snake
{
	protected boolean isBeingTracked;

	protected int mxPos, myPos;

	public ControlledSnake(int x, int y, int si, int sp, double a, double i, double s, Color c)
	{
		super(x, y, si, sp, a, i, s, c);
		isBeingTracked = true;
		mxPos = myPos = 0;
	}

	public double getSpeed()
	{
		return speed;
	}

	public boolean getIsBeingTracked()
	{
		return isBeingTracked;
	}

	public void setAngle(double a)
	{
		angle = a;
	}

	public void setSpeed(double s)
	{
		speed = s;
	}

	public void setIsBeingTracked(boolean b)
	{
		isBeingTracked = b;
	}

	public void setMouseXPosition(int x)
	{
		mxPos = x;
	}

	public void setMouseYPosition(int y)
	{
		myPos = y;
	}

	public double constrainedAngle(double a)
	{
		a %= 2 * Math.PI;
		if(a < 0)
			a += 2 * Math.PI;
		return a;
	}

	public double angleDiff(double a, double b)
	{
		double dif = (b - a + Math.PI) % (2 * Math.PI);
		if(dif < 0)
			dif += 2 * Math.PI;
		return dif - Math.PI;
	}

	public double bisectAngle(double a, double b)
	{
		return constrainedAngle(a + angleDiff(a, b) * 0.5);
	}

	public void updateDirection()
	{
		super.updateDirection();

		if(isBeingTracked)
		{
			angle = bisectAngle(angle, constrainedAngle(Math.atan2(myPos - yPos, mxPos - xPos)));
		}
	}
}
