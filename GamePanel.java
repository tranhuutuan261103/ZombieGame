import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int GAME_HEIGHT = 800;
	static final int GAME_WIDTH = (int)(GAME_HEIGHT * (0.6));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int TANK_DIAMETER = 30 ;
	
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	MyTank myTank;
	ArrayList<Anemy> queue = new ArrayList<>();
	ArrayList<Bullet> bullet = new ArrayList<>(); 
	
	GamePanel() {
		newMyTank();
		newAnemy();
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread (this);
		gameThread.start();
	}
	
	public void newAnemy() {
		queue.clear();

	}
	
	public void createAnemy() {
		Random random = new Random();
		Anemy myAnemy = new Anemy(random.nextInt(GAME_WIDTH-TANK_DIAMETER),random.nextInt(2)*(GAME_HEIGHT-TANK_DIAMETER),TANK_DIAMETER,TANK_DIAMETER);
		queue.add(myAnemy);
	}
	
	public Anemy findAnemyBestNear() {
		Anemy temp = queue.get(0);
		double maxDis = 10000;
		for (int i=1;i<queue.size();i++) {
			int xpos = queue.get(i).x;
			int ypos = queue.get(i).y;
			double t1= Math.pow(xpos-myTank.x,2);
			double t2= Math.pow(ypos-myTank.y,2);
			double t = (double)Math.sqrt(t1+t2);
			if (t < maxDis) {
				temp = queue.get(i);
				maxDis = t;
			}
		}
		return temp;
	}
	
	public void createBullet() {
		Anemy bestAnemy = this.findAnemyBestNear();
		int vectorx=bestAnemy.x-myTank.x;
		int vectory=bestAnemy.y-myTank.y;
		Bullet myBullet = new Bullet(myTank.x+TANK_DIAMETER/2,myTank.y+TANK_DIAMETER/2,vectorx,vectory);
		bullet.add(myBullet);
	}
	
	public void newMyTank() {
		bullet.clear();
		myTank = new MyTank(GAME_WIDTH/2,GAME_HEIGHT/2,TANK_DIAMETER,TANK_DIAMETER);
	}

	public void paint(Graphics g) {
		//super.paint(g);
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}
	
	public void draw(Graphics g) {
		myTank.draw(g);
		//myTank.attack(g);
		for(int i=0 ;i< bullet.size();i++) {
		    //do something with each element
			bullet.get(i).draw(g);
		}
		for(int i=0 ;i< queue.size();i++) {
		    //do something with each element
			queue.get(i).draw(g);
		}
	}
	
	public void move() {
		myTank.move();
		for(int i=0 ;i< bullet.size();i++) {
		    //do something with each element
			bullet.get(i).move();
		}
		for(int i=0 ;i< queue.size();i++) {
		    //do something with each element
			queue.get(i).move();
		}
	}
	
	public void checkCollision() {
		
		if (myTank.y<0)
			myTank.y = 0;
		if (myTank.y>= (GAME_HEIGHT-TANK_DIAMETER))
			myTank.y = (GAME_HEIGHT-TANK_DIAMETER);
		if (myTank.x<0)
			myTank.x = 0;
		if (myTank.x>= (GAME_WIDTH-TANK_DIAMETER))
			myTank.x = (GAME_WIDTH-TANK_DIAMETER);
		
		
		
		for(int i=0; i < queue.size(); i++) {
			// kill anemy
			for (int j=0;j< bullet.size();j++) {
				if (!(queue.get(i).x >= bullet.get(j).x+10 || queue.get(i).x + TANK_DIAMETER <= bullet.get(j).x) ) {
					if (!(queue.get(i).y >= bullet.get(j).y+10 || queue.get(i).y + TANK_DIAMETER <= bullet.get(j).y))
					{
						bullet.remove(j);
						queue.remove(i);
						break;
					}
				}
			}
		}
		
		for (int i=0;i<bullet.size();i++) {
			if (bullet.get(i).x < -10)
			{
				bullet.remove(i);
				i--;
			} else
			if (bullet.get(i).x > GAME_WIDTH)
			{
				bullet.remove(i);
				i--;
			} else
			
			if (bullet.get(i).y < -10)
			{
				bullet.remove(i);
				i--;
			} else
			if (bullet.get(i).y > GAME_HEIGHT)
			{
				bullet.remove(i);
				i--;
			}
		}
		
		
		for(int i=0; i < queue.size(); i++) {/*
			if (element.x - Math.abs(element.xVelocity) > myTank.x) {
				element.setXDirection(-1);
			} else if (element.x + Math.abs(element.xVelocity) < myTank.x) {
				element.setXDirection(1);
			} else element.setXDirection(0);
			
			if (element.y - Math.abs(element.yVelocity) > myTank.y) {
				element.setYDirection(-1);
			} else if (element.y + Math.abs(element.yVelocity) < myTank.y) {
				element.setYDirection(1);
			} else element.setYDirection(0);*/
			queue.get(i).setXYDirection(myTank.x,myTank.y);
		}
		
		
		for(int i=0; i < queue.size(); i++) {
			if (!(queue.get(i).x >= myTank.x+TANK_DIAMETER || queue.get(i).x + TANK_DIAMETER <= myTank.x) ) {
				if (!(queue.get(i).y >= myTank.y+TANK_DIAMETER || queue.get(i).y + TANK_DIAMETER <= myTank.y))
				{
					newAnemy();
					newMyTank();
				}
			}
		}
		
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double delay = 0;
		double delay2 = 0;
		//this.createAnemy();
		while(true) {
			System.out.println(queue.size());
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			delay += (now-lastTime)/ns;
			delay2 += (now-lastTime)/ns;
			lastTime = now;
			if (delta>=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
			if (delay >= 30) {
				this.createAnemy();
				delay-=30;
			}
			if (delay2 >= 20) {
				if (queue.size()!=0) {
				this.createBullet();
				delay2-=30;}
			}
			
		}
	}
	
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
	}
}
