package Sprites;

import javafx.scene.image.Image;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import PacmanGame.GameTimer;

public class Powerup extends Items{

	public final static Image BOOST_IMAGE = new Image("images/powerup1.png",Food.FOOD_WIDTH,Food.FOOD_WIDTH,false,false);
	public final static Image IMMUNITY_IMAGE = new Image("images/powerup2.png",Food.FOOD_WIDTH,Food.FOOD_WIDTH,false,false);

	public final static String BOOST = "boost";
	public final static String IMMUNITY = "immunity";
	public final static int POWERUPS_TOTAL = 2;
	private static boolean isUsed;

	private static String name;
	private static boolean alive;


	public Powerup(int x, int y){
		super(x,y);

		Random r = new Random();
		Powerup.alive = true;
		Powerup.isUsed = false;

		int powernum = r.nextInt(Powerup.POWERUPS_TOTAL);    //will count for 0 to 1

		if(powernum==0){   //this is for boost
			name = Powerup.BOOST;
			this.loadImage(BOOST_IMAGE);
		}else{            //this is for immunity
			name = Powerup.IMMUNITY;
			this.loadImage(IMMUNITY_IMAGE);
		}

	}
	/*
	 * this method will check if a powerup collides with pacman
	 * and it will call the necessary action to have the effect for immunity
	 * */
	public void checkCollision(Pacman pacman){
		if(this.collidesWith(pacman)){
			System.out.println("Pacman collected a powerup!!");
			if (name == Powerup.IMMUNITY){
				Pacman.setImmortality(true);
				System.out.println("Immunity Powerup activated");
			}
			pacman.upgradePowerups();
			this.vanish();
			alive = false;
			Powerup.setisUsed(true);
			GameTimer.PowerCounter-=1;

		}
	}

	//getters

	/*
	 * This will give the name of the powerup which is
	 * essential in identifying the type of powerup
	 * */
	public static String getName(){
		return name;
	}
	public static boolean getisUsed(){
		return Powerup.isUsed;
	}


	public static boolean getisAlive(){
		return Powerup.alive;
	}

	//setters

	public  static void setisUsed(boolean status){
		Powerup.isUsed = status;
	}

	public void setAlive(boolean status) {
		Powerup.alive = status;
	}




}
