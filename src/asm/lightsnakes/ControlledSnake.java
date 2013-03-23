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

	public double constrainAngle(double angle)
	{
		angle %= 2 * Math.PI;
		if(angle < 0)
			angle += 2 * Math.PI;
		return angle;
	}

	public double angleDifference(double angle1, double angle2)
	{
		double dif = (angle2 - angle1 + Math.PI) % (2 * Math.PI);
		if(dif < 0)
			dif += 2 * Math.PI;
		return dif - Math.PI;
	}

	public double divideAngle(double angle1, double angle2, double fraction)
	{
		return constrainAngle(angle1 + angleDifference(angle1, angle2) * fraction);
	}

	public void updateDirection()
	{
		super.updateDirection();

		if(isBeingTracked)
		{
			angle = divideAngle(angle, constrainAngle(Math.atan2(myPos - yPos, mxPos - xPos)), 0.15);
		}
	}
}
