import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
	int x;
	int y;
	double xTemp,yTemp;
	double a;
	double b;
	Bullet(int x0,int y0,double a,double b) {
		double rateValue = 6/Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
		this.a=a*rateValue;
		this.b=b*rateValue;
		xTemp=0;
		yTemp=0;
		x = x0;
		y = y0;
	}
	public int getXVelocity() {
		return x;
	}
	public void setXVelocity(int x) {
		this.x = x;
	}
	public int getYVelocity() {
		return y;
	}
	public void setYVelocity(int y) {
		this.y = y;
	}
	
	public void move() {
		xTemp+=a;
		yTemp+=b;
		if (xTemp<=-1 || xTemp>=1) {
			x+=(int)xTemp;
			xTemp-=(int)xTemp;
		}
		if (yTemp<=-1 || yTemp>=1) {
			y+=(int)yTemp;
			yTemp-=(int)yTemp;
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
    	g.fillOval(x, y, 10, 10);
	}
	
}
