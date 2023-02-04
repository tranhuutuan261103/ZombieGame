import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MyTank extends Rectangle  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage image;
	int xVelocity;
	int yVelocity;
	int speed = 4;
	MyTank(int x,int y, int width,int height) {
		super(x,y, width,height);
		try {
			image = ImageIO.read(new File(".\\data\\myTank3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public int getxVelocity() {
		return xVelocity;
	}



	public void setxVelocity(int xVelocity) {
		this.xVelocity = xVelocity;
	}



	public int getyVelocity() {
		return yVelocity;
	}



	public void setyVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}



	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode()==KeyEvent.VK_W) {
			setYDirection(-speed);
			move();
		}
		if (e.getKeyCode()==KeyEvent.VK_S) {
			setYDirection(speed);
			move();
		}
		if (e.getKeyCode()==KeyEvent.VK_A) {
			setXDirection(-speed);
			move();
		}
		if (e.getKeyCode()==KeyEvent.VK_D) {
			setXDirection(speed);
			move();
		}
	}
	
    public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_W) {
			setYDirection(0);
			move();
		}
		if (e.getKeyCode()==KeyEvent.VK_S) {
			setYDirection(0);
			move();
		}
		if (e.getKeyCode()==KeyEvent.VK_A) {
			setXDirection(0);
			move();
		}
		if (e.getKeyCode()==KeyEvent.VK_D) {
			setXDirection(0);
			move();
		}
	}
    
    public void setXDirection(int xDirection) {
    	xVelocity = xDirection ;
    }
    
    public void setYDirection(int yDirection) {
    	yVelocity = yDirection ;
    }
    
    public void move() {
    	y = y + yVelocity ;
    	x = x + xVelocity ;
    }
    
    public void attack(Graphics g) {
    	g.setColor(Color.green);
    	g.fillOval(x+40, y, 10, 10);
    	
    }
    
    public void draw(Graphics g) {
    	g.setColor(Color.blue);
    	g.fillOval(x,y,width,height);
    	
    	//Graphics2D g2d = (Graphics2D) g;
    	g.drawImage(image,x,y,null);
    }
}
