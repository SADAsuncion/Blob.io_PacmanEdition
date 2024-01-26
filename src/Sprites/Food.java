package Sprites;

import javafx.scene.image.Image;
import java.util.Random;
import PacmanGame.GameTimer;


public class Food extends Items{
	//Constants
	public final static Image FOOD_IMAGE = new  Image("images/food1.png",Food.FOOD_WIDTH,Food.FOOD_WIDTH,false,false);
	public final static Image FOOD_IMAGE2 = new Image ("images/food2.png",Food.FOOD_WIDTH,Food.FOOD_WIDTH,false,false);
	public final static Image FOOD_IMAGE3 = new Image ("images/food3.png",Food.FOOD_WIDTH,Food.FOOD_WIDTH,false,false);
	public final static int FOOD_WIDTH = 20;
	public final static int FOOD_TOTAL = 50;

	//Attributes
	private Image foodImage;
	private boolean alive;
	private int value;

	public Food (int x, int y){
		super(x,y);

		this.alive = true;

		Random r = new Random();
		int imgnum = r.nextInt(3);    //will count for 0 to 2

		if(imgnum==0){
			this.foodImage = FOOD_IMAGE;
			this.loadImage(FOOD_IMAGE);
		}else if(imgnum==1){
			this.foodImage = FOOD_IMAGE2;
			this.loadImage(FOOD_IMAGE2);
		}else{
			this.foodImage = FOOD_IMAGE3;
			this.loadImage(FOOD_IMAGE3);
		}

		value = 10;
	}

	/*
	 *method for checking if food and Pacman collide
	* the result of this method: food will vanish & Pacman's size will increase
	*/
	public void checkCollision(Pacman pacman){
		if(this.collidesWith(pacman)){
			System.out.println("Food is collected!");
			this.vanish();
			this.alive = false;
			pacman.sizeUp(value);
			GameTimer.foodCount-=1;
			GameTimer.foodeaten+=1;

		}

	}

	/*method that will check if a ghost eats a food
	*the result of this method: food will vanish and ghost will grow
	*/
	public void checkGhostFoodCollision(Ghost g){
		if(this.collidesWith(g)){
			this.vanish();
			this.alive = false;
			g.sizeUp(value);
			GameTimer.foodCount-=1;

		}
	}


	//getters
	public Image getImage(){
		return this.foodImage;
	}

	public boolean isAlive(){
		return this.alive;
	}

//	//to delete
//		public void setY(double y){
//			this.y += y;
//		}
//
//		public void setX(double x2){
//			this.x += x2;
//		}

}


