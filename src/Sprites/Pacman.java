package Sprites;

import javafx.scene.image.Image;
import PacmanGame.*;

public class Pacman extends Sprite{
	private String name;


	private Image pacmanImg;

	private boolean alive;
	private int size;
	private static boolean immortal = false;
	private PowerupTimer timer;

//parameters String move, boolean hasEaten, int itemValue,
	public Pacman(String name, int x, int y){
		super(x,y);
		this.size = GameTimer.INITIAL_SIZE; //set inital size
		this.alive = true;
		this.speed = 120/this.size;
		this.pacmanImg = new Image("images/PACMAN-right.png", this.size, this.size, false, false);
		this.loadImage(this.pacmanImg);


	}

	public void sizeUp(int itemValue){
		this.size = this.size + itemValue;
		double speedCheck = (double) 120/this.size;
		System.out.println("size: " +this.size);
		System.out.println("speecheck: " + speedCheck);

		if(speedCheck < 1){
			this.speed = 1;
		}
		else
		{
			this.speed = speedCheck;
		}


	}

	/*this method add a little animation where it changes orientation depending
	 * on the main blob's movement*/
	public void rotate(String direction){

		if(direction == "A"){
			this.pacmanImg = new Image("images/PACMAN-left.png", this.size, this.size, false, false);
			this.loadImage(this.pacmanImg);
		}

		if(direction == "W"){
			this.pacmanImg = new Image("images/PACMAN-up.png", this.size, this.size, false, false);
			this.loadImage(this.pacmanImg);
		}

		if(direction == "D"){
			this.pacmanImg = new Image("images/PACMAN-right.png", this.size, this.size, false, false);
			this.loadImage(this.pacmanImg);
		}

		if(direction == "S"){
			this.pacmanImg = new Image("images/PACMAN-down.png", this.size, this.size, false, false);
			this.loadImage(this.pacmanImg);
		}

	}


	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}

	public void die(){
    	this.alive = false;
    }


	/*
	 *method called if up/down/left/right arrow key is pressed.
	 *Change the x and y position of the main player if the current x,y position
	 *is within the gamestage width and height.
	 */
	public void move() {
		if(this.x+this.dx <= MainGameStage.MAP_WIDTH-this.width && this.x+this.dx >=0 && this.y+this.dy <= MainGameStage.MAP_HEIGHT-this.width && this.y+this.dy >=0){
    		this.x += this.dx;
    		this.y += this.dy;
    	}
	}

	/*
	 * this method will be the trigger for the powerup timer to work
	 * as this call the poweruptimer
	 * */
	void upgradePowerups(){

		if(Powerup.getisUsed()==false){
			this.timer = new PowerupTimer(this);
			this.timer.start();
		}else{
			this.timer.refresh();
			Powerup.setisUsed(false);
		}
	}

	void downgradePowerup(){
		if (Powerup.getName() == Powerup.IMMUNITY){
			Pacman.setImmortality(false);
			System.out.println("Powerup Immunity deactivated.");
		}

		Powerup.setisUsed(false);
	}

	// Getters

	public int getSize(){
		return this.size;
	}

	public String getName(){
		return this.name;
	}

	public boolean getImmortality(){
		return Pacman.immortal;
	}


	//Setter

	public static void setImmortality(boolean status){
		Pacman.immortal = status;
	}


}



