package Sprites;

import java.util.Random;

import PacmanGame.GameTimer;
import PacmanGame.MainGameStage;
import javafx.scene.image.Image;

public class Ghost extends Sprite {
	private Image ghostImage;
	private Image immuneGhost;
	private boolean alive;
	private int size;
	private int num;


	public Ghost(int x, int y){
		super(x,y);

		this.alive = true;
		this.size = GameTimer.INITIAL_SIZE;
		this.speed = 120/this.size;

		Random r = new Random();
		int imgNum = r.nextInt(3);

		this.num = imgNum;

		if(imgNum == 0){
			this.ghostImage = new Image("images/ghost1.png",this.size,this.size,false,false);
			this.loadImage(this.ghostImage);
		}else if(imgNum==1){
			this.ghostImage = new Image("images/ghost2.png",this.size,this.size,false,false);
			this.loadImage(this.ghostImage);
		}else{
			this.ghostImage = new Image("images/ghost3.png",this.size,this.size,false,false);
			this.loadImage(this.ghostImage);
		}

		this.immuneGhost = new Image("images/ImmuneGhost.png",this.size,this.size,false,false);
	}



	public void sizeUp(int itemValue){
		//update size
		this.size = this.size + itemValue;

		//if the blob sized up, its speed will be slower
		this.speed = 120/this.size;
		this.size = this.size + itemValue;
		double speedCheck = (double) 120/this.size;

		//if the blob sized up, its speed will be slower
		if(speedCheck < 1)
		{
			this.speed = 1;
		}
		else
		{
			this.speed = speedCheck;
		}

		this.updateImage();

	}

	private void updateImage(){
		if(this.num == 0)
		{
			this.ghostImage = new Image("images/ghost1.png",this.size,this.size,false,false);
			this.loadImage(this.ghostImage);
		}
		else if(this.num == 1)
		{
			this.ghostImage = new Image("images/ghost2.png",this.size,this.size,false,false);
			this.loadImage(this.ghostImage);
		}
		else if(this.num == 2)
		{
			this.ghostImage = new Image("images/ghost3.png",this.size,this.size,false,false);
			this.loadImage(this.ghostImage);
		}

		Image newGhostImg = new Image("images/ImmuneGhost.png",this.size,this.size,false,false);
		this.immuneGhost = newGhostImg;
	}


	public void move(){
		if(this.x+this.dx <= MainGameStage.MAP_WIDTH-this.width && this.x+this.dx >=0 && this.y+this.dy <= MainGameStage.MAP_HEIGHT-this.width && this.y+this.dy >=0){
    		this.x += this.dx;
    		this.y += this.dy;
		}

	}

	public void die(){
		this.alive = false;
	}

	//this method checks collision of this instance of ghosts and pacman
	public void checkCollision(Pacman pacman){
		if(this.collidesWith(pacman)){
			if(pacman.getImmortality() == true)
			{

				pacman.sizeUp(this.size);
				GameTimer.ghostEaten +=1;
				GameTimer.ghostCount-=1;
				this.die();
				this.alive = false;
			}else if(this.size > pacman.getSize()){
				pacman.die();
			}
			else
			{
				pacman.sizeUp(this.size);
				GameTimer.ghostEaten +=1;
				GameTimer.ghostCount-=1;
				this.die();
				this.alive = false;
			}
		}
	}

	//this Method loads ghosts' immune image
	public void changeGhostImage(){
		this.loadImage(this.immuneGhost);

	}

	//this methods loads ghosts' original image
	public void returnGhostNormalState(){
		this.loadImage(this.ghostImage);
	}




	// this method checks collision of ghosts
	public void checkGhostCollision(Ghost ghost){
		if(this.collidesWith(ghost))
		{
			if(this.size > ghost.getSize()){
				ghost.die();
			}
			else
			{
				ghost.sizeUp(this.size);
				GameTimer.ghostCount-=1;
				this.die();
				this.alive = false;
			}
		}

	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}

	//getters
	public int getSize(){
		return this.size;

	}

	public Image getImage(){
		return this.ghostImage;
	}

}