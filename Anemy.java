import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Anemy extends Rectangle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage image;
	Random random;
	double xVelocity,xTemp;
	double yVelocity,yTemp;
	int initialSpeed = 1 ;
	
	Anemy(int x, int y, int width,int height) {
		super(x,y,width,height);
		xVelocity = 0;
		yVelocity = 0;
		xTemp = 0;
		yTemp = 0;
		try {
			image = ImageIO.read(new File("C:\\Users\\TUAN\\eclipse-workspace\\MyGame\\src\\zombie2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public double getxVelocity() {
		return xVelocity;
	}



	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
		this.xTemp+=xVelocity;
	}



	public double getyVelocity() {
		return yVelocity;
	}



	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
		this.yTemp+=yVelocity;
	}



	public void setXDirection(double randomXDirection) {
		xVelocity = randomXDirection*initialSpeed;
	}
	
	public void setYDirection(double randomYDirection) {
		yVelocity = randomYDirection*initialSpeed;
	}
	
	public void move() {
		if (xTemp>=1 || xTemp<=-1) {
			x+=(int)xTemp;;
			if (xTemp>=1)
				xTemp--;
			else xTemp++;
		}
		if (yTemp>=1 || yTemp<=-1) {
			y+=(int)yTemp;;
			if (yTemp>=1)
				yTemp--;
			else yTemp++;
		}
		//x+=xVelocity;
		//y+=yVelocity;
		//System.out.println(x +" "+y);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, height, width);
		g.drawImage(image, x, y, null);
	}



	public void setXYDirection(int myTankx, int myTanky) {
		// TODO Auto-generated method stub
		if (Math.abs(y-myTanky)!=0) {//System.out.println("gg:"+(double)Math.abs(x-myTankx)/Math.abs(y-myTanky));
//			double tempValue = (double)Math.abs(x-myTankx)/Math.abs(y-myTanky);
		    if ((double)Math.abs(x-myTankx)/(double)Math.abs(y-myTanky) >=1 ) {
				if (x - Math.abs(xVelocity) > myTankx) {
					setxVelocity(-1);
				} else if (x + Math.abs(xVelocity) < myTankx) {
					setxVelocity(1);
				} else setxVelocity(0);
				
				if (y - Math.abs(yVelocity) > myTanky) {
					setyVelocity(-(double)Math.abs(y-myTanky)/Math.abs(x-myTankx));
				} else if (y + Math.abs(yVelocity) < myTanky) {
					setyVelocity((double)Math.abs(y-myTanky)/Math.abs(x-myTankx));
				} else setyVelocity(0);
			} else {
				if (x - Math.abs(xVelocity) > myTankx) {
					setxVelocity(-(double)Math.abs(x-myTankx)/(double)Math.abs(y-myTanky));
				} else if (x + Math.abs(xVelocity) < myTankx) {
					setxVelocity((double)Math.abs(x-myTankx)/(double)Math.abs(y-myTanky));
				} else setxVelocity(0);
				
				if (y - Math.abs(yVelocity) > myTanky) {
					setyVelocity(-1);
				} else if (y + Math.abs(yVelocity) < myTanky) {
					setyVelocity(1);
				} else setyVelocity(0);
			}
		} else {
			if (x - Math.abs(xVelocity) > myTankx) {
				setxVelocity(-1);
			} else if (x + Math.abs(xVelocity) < myTankx) {
				setxVelocity(1);
			} else setxVelocity(0);
		}
	}
	
}
