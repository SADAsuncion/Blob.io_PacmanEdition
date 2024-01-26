package PacmanGame;

import GameOutput.*;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import Sprites.*;
import PacmanGame.MainGameStage;
import java.util.concurrent.TimeUnit;

public class GameTimer extends AnimationTimer {

	public final static int INITIAL_SIZE = 40;
	public static final int INITIAL_NUM_ENEMIES = 10;
	public static final int NUM_ENEMIES = 3;
	public static final int POWERUP_TIME = 5;
	public static final int POWERUP_PAUSE = 10;
	public static final int POWERUP_DISAPPEAR = 5;


	private GraphicsContext gc;
	private Scene theScene;
	private MainGameStage gameStage;
	private Pacman pacman;
	private Camera camera;


	private ArrayList<Ghost> enemies;
	public static int ghostCount = 0;
	public static int ghostEaten = 0;

	private ArrayList<Food> foodList;
	public static int foodeaten = 0;
	public static int foodCount = 0;

	private ArrayList <Powerup> powerUps;
	public static int PowerCounter = 0;

	public static long timeAlive;
	public static int finalSize;


	//Camera
	private int camMaxX;
	private int camMaxY;
	private int camMinX;
	private int camMinY;

	//text
	private int statusPosX;
	private int statusPosY;

	//powerUp texts ad needed attributes
	private int textX;
	private int textY;


//	private long startSpawn;
//	private long startTime;


	private Image background = new Image("images/Final Main Game Background.jpg",MainGameStage.MAP_WIDTH,MainGameStage.MAP_HEIGHT,false,false);


	GameTimer(GraphicsContext gc, Scene theScene, MainGameStage gameStage){

		this.gc = gc;
		this.gc.drawImage(this.background, MainGameStage.MAP_WIDTH, MainGameStage.MAP_HEIGHT);
		this.theScene = theScene;
		this.gameStage = gameStage;

		//Spawn sprites
		this.pacman = new Pacman("Pacman", 100,100);
		this.foodList = new ArrayList<Food>();
		this.enemies = new ArrayList<Ghost>();
		this.powerUps = new ArrayList <Powerup>();

		this.spawnGhost();

		//Camera attributes initializations
		this.camMaxX = MainGameStage.MAP_WIDTH - MainGameStage.WINDOW_WIDTH;
		this.camMaxY = MainGameStage.MAP_HEIGHT - MainGameStage.WINDOW_HEIGHT;
		this.camMinX = 0;
		this.camMinY = 0;

		//Initialized camera
		this.camera = new ParallelCamera();
		this.camera.setTranslateX(MainGameStage.WINDOW_WIDTH/2);
		this.camera.setTranslateY(MainGameStage.WINDOW_HEIGHT/2);
		theScene.setCamera(this.camera);

		//Text Position
			//For Status bar
		this.statusPosX = 0;
		this.statusPosY = 0;
			//For PowerUp text Indicator
		this.textX = 0 ;
		this.textY = 0;

		this.handleKeyPressEvent();

	}

	@Override
	public void handle(long currentNanoTime) {
		//Draw Background
		this.gc.clearRect(0, 0, MainGameStage.MAP_WIDTH,MainGameStage.MAP_HEIGHT);
		this.gc.drawImage(background, 0, 0);

		//Spawn/Render sprites
		this.spawnPacman();
		this.renderFoods();
		this.renderGhost();
		this.renderPower();
		this.spawnFood();

		//makes sprites movement
		this.pacman.move();
		this.moveGhost();
		this.updateGhostMovement();

		//Check sprites Collision
		this.checkFoodCollision();
		this.checkGhostCollision();

		//other Sprite Updates
		this.updateGhostImage();
		this.powerUpCounter();

		//Timer
		long currentTime =  TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
		long startTime = TimeUnit.NANOSECONDS.toSeconds(MainGameStage.startGame);
		long SecondsTimer = currentTime-startTime;

		this.statusBar(SecondsTimer);
		this.checkPowerupsCollision(SecondsTimer);
		this.autoPowerupSpawn(SecondsTimer);

		//camera
		this.updateCamera();

		/*If pacman is dead
		 * Display game results */
		if(!pacman.isAlive()){
			gameStage.displayGameResults();
			this.stop();
			timeAlive = SecondsTimer;
			WelcomeScreen.mediaPlayer.setMute(true);

		}
	}


	// Method that will listen and handlee the key press events
	private void handleKeyPressEvent(){
		theScene.setOnKeyPressed(new EventHandler <KeyEvent>(){
			public void handle(KeyEvent e){
				KeyCode code = e.getCode();
				movePacman(code);
			}
		});

		theScene.setOnKeyReleased(new EventHandler <KeyEvent>(){
			public void handle(KeyEvent e)
			{
				KeyCode code = e.getCode();
				stopPacman(code);
			}
		});
	}

	//this method will show the status bar in the screen
	private void statusBar(long SecondsTimer){
		this.gc.setFill(javafx.scene.paint.Color.WHITE);
		Font theFont = Font.font("Tahoma",FontWeight.BOLD,20);//set font type, style and size
		this.gc.setFont(theFont);

		this.gc.fillText("FOODS EATEN:"+foodeaten,this.statusPosX, this.statusPosY);
		this.gc.fillText("GHOSTS EATEN:"+ghostEaten,this.statusPosX + 210, this.statusPosY);
		this.gc.fillText("SIZE:"+ this.pacman.getSize(), this.statusPosX + 430, this.statusPosY);
		this.gc.fillText("TIME:"+(SecondsTimer)+ "", this.statusPosX + 580,  this.statusPosY);
	}


	/*
	 * This method is responsible for pacman's movement
	 * also, if speed up is used then speed will be doubled
	 * */
	private void movePacman(KeyCode key){
		if(key == KeyCode.W) //Up
		{
			if(Powerup.getisUsed() == true && Powerup.getName() == Powerup.BOOST)
			{
				this.pacman.setDY(-pacman.getSpeed() *2);
				this.pacman.rotate("W");
			}
			else
			{
				this.pacman.setDY(-pacman.getSpeed());
				this.pacman.rotate("W");
			}


		}
		if(key == KeyCode.S){

			if(Powerup.getisUsed() == true && Powerup.getName() == Powerup.BOOST)
			{
				this.pacman.setDY(pacman.getSpeed() *2);
				this.pacman.rotate("S");
//				System.out.println(pacman.getSpeed());

			}
			else
			{
				this.pacman.setDY(pacman.getSpeed());
				this.pacman.rotate("S");
//				System.out.println(pacman.getSpeed());
			}
		}
		if(key == KeyCode.A){

			if(Powerup.getisUsed() == true && Powerup.getName() == Powerup.BOOST)
			{
				this.pacman.setDX(-pacman.getSpeed()*2);
				this.pacman.rotate("A");

			}
			else
			{
				this.pacman.setDX(-pacman.getSpeed());
				this.pacman.rotate("A");

			}

		}
		if(key == KeyCode.D){

			if(Powerup.getisUsed() == true && Powerup.getName() == Powerup.BOOST)
			{
				this.pacman.setDX(pacman.getSpeed()*2);
				this.pacman.rotate("D");
			}
			else
			{
				this.pacman.setDX(pacman.getSpeed());
				this.pacman.rotate("D");

			}


		}
	}

	private void stopPacman(KeyCode key){
		this.pacman.setDX(0);
		this.pacman.setDY(0);

	}


	//method for generating random position of food in map
	public void spawnFood(){
		Random r = new Random();

		for(int i=foodCount;i<Food.FOOD_TOTAL;i++){

			int x = r.nextInt(MainGameStage.MAP_HEIGHT);
			int y = r.nextInt(MainGameStage.MAP_HEIGHT);
			this.foodList.add(new Food(x,y));
			foodCount+=1;

		}
	}


	//method for generating random position of powerups in the window
	public void spawnPowerups(){
		Random r = new Random();

		for(int i=PowerCounter; i<1;i++){
			int x = (int) (r.nextInt(MainGameStage.WINDOW_HEIGHT) + this.camera.getTranslateX());
			int y = (int)(r.nextInt(MainGameStage.WINDOW_HEIGHT)+ this.camera.getTranslateY());

			if(0<=x && x<MainGameStage.MAP_WIDTH && 0<=y && y<MainGameStage.MAP_HEIGHT){
				this.powerUps.add(new Powerup(x,y));
				PowerCounter+=1;
//				System.out.println(PowerCounter);
//				System.out.println("A powerup has spawned.");
			}
		}

	}

	//This method is of spawning ghost at random positions
	public void spawnGhost(){
		Random r = new Random();

		for(int i=0; i<GameTimer.INITIAL_NUM_ENEMIES; i++ ){
			int x = r.nextInt(MainGameStage.MAP_HEIGHT);
			int y = r.nextInt(MainGameStage.MAP_HEIGHT);
			this.enemies.add(new Ghost(x,y));
			ghostCount += 1;

		}
	}

	//method for deploying the ghosts
	public void renderGhost(){
		for(Ghost g: enemies){
			g.render(this.gc);
		}
	}

	//method for deploying the food
	public void renderFoods(){

		for(Food f:foodList){
			f.render(this.gc);
		}
	}

	//method for deploying powerups
	public void renderPower(){
		for(Powerup P: powerUps){
			P.render(this.gc);

		}
	}

	//method for spawning the main blob
	void spawnPacman(){
		this.pacman.render(this.gc);
	}


	//method for checking pacman and food collision
	void checkFoodCollision(){

		for(int i = 0; i < this.foodList.size(); i++){
			Food f = this.foodList.get(i);
			if(f.isVisible()){
				f.checkCollision(this.pacman);
			}
			else{
				this.foodList.remove(i);
			}
		}
	}

	//checker for powerup
	void checkPowerupsCollision(long SecondsTimer){
		for(int i = 0; i<this.powerUps.size();i++){
			Powerup p = this.powerUps.get(i);
			if(p.isVisible()){
				p.checkCollision(this.pacman);
			}
			else{
				this.powerUps.remove(i);
			}

			//this condition will be used to check if the powerup is used or not after 5 seconds
			if(p.isVisible() && SecondsTimer % GameTimer.POWERUP_DISAPPEAR == 0 && SecondsTimer % GameTimer.POWERUP_PAUSE != 0){
				if(Powerup.getisUsed()==false){
					p.vanish();
					p.setAlive(false);
					GameTimer.PowerCounter-=1;
				}
			}
		}
	}


	//method for checking ghosts and pacman collision
	void checkGhostCollision(){
		for(int i = 0; i< this.enemies.size(); i++){
			Ghost g = this.enemies.get(i);
			if(g.isAlive())
			{
				g.checkCollision(this.pacman);

			}
			else
			{
				this.enemies.remove(i);
			}

		}
	}

	//method for ghosts and food collision
	void checkGhostEatsFood(Ghost g){
		for(int j= 0; j < this.foodList.size(); j++){
		Food f = this.foodList.get(j);
		if(f.isVisible()){
			f.checkGhostFoodCollision(g);

		}
		else{
			this.foodList.remove(j);

		}
	}
}

	//method checks ghosts and other ghosts collision
	void checkGhostsCollision(Ghost g1){
		for(int i=0; i < this.enemies.size(); i++){
			Ghost g2 = this.enemies.get(i);

			if(g2 !=g1){
				if(g2.isAlive())
				{
					g2.checkGhostCollision(g1);
				}
				else
				{
					this.enemies.remove(i);
				}
			}
		}
	}

	//This methos updates ghosts X and Ys
	private void updateGhostMovement(){
		for(int i = 0; i<this.enemies.size(); i++){
			Ghost g = this.enemies.get(i);
			g.move();

		}
	}

	/*This methods updates Ghosts image*/
	private void updateGhostImage(){
		//If pacman has immunityh
		if(this.pacman.getImmortality() == true)
		{
			// ghosts will update its images
			for(int i = 0; i<this.enemies.size(); i++){
				Ghost g = this.enemies.get(i);
				g.changeGhostImage();

			}
		}
		else
		{
			// retrun to normal state
			for(int i = 0; i<this.enemies.size(); i++){
				Ghost g = this.enemies.get(i);
				g.returnGhostNormalState();

			}
		}
	}

	/*This function is responsible for the random movement of the ghosts*/
	public void moveGhost(){

		// Array of string movements (it is also based on how pacman is being moved)
		String[] moveList = {"A", "W","S","D"};

		Random rt = new Random();

		// Randomized seconds
		int sec = rt.nextInt(100);
		//System.out.println(sec);

		//loop ghost existing in the array
		for(int i = 0; i<this.enemies.size(); i++){
			Ghost g = this.enemies.get(i);

			int r = new Random().nextInt(moveList.length); // randomized moved in the array of moves
			String rMove = moveList[r];	//store random move in a variable

			//reference: https://docs.oracle.com/javafx/2/animations/basics.htm
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds((double) (sec)), event ->{

				/*if the ghost is positioned on the right edge of the map then
				 * the ghost can only move up, down left*/
				if(g.getX()+g.getDX() >= MainGameStage.MAP_WIDTH-g.getSize()){
					if(rMove == "S"){
						g.setDX(g.getSpeed());
					}
					else if(rMove == "A"){
						//speed dapat
						 g.setDX(-g.getSpeed());
					}
					else if(rMove == "W"){
						g.setDY(-g.getSpeed());

					}

				}
				/*if the ghost is positioned on the left edge of the map then
				 * the ghost can only move up, down right*/
				else if(g.getX()+g.getDX()<=0){
					if(rMove == "D"){
						g.setDX(g.getSpeed());
					}
					else if(rMove == "W"){
						g.setDY(-g.getSpeed());

					}
					else if(rMove == "S"){
						g.setDX(g.getSpeed());
					}
				}
				/*if the ghost is positioned on the bottom edge of the map then
				 * the ghost can only move up, right and left*/
				else if(g.getY()+g.getDY() >= MainGameStage.MAP_HEIGHT-g.getSize()){
					if(rMove == "A"){
						//speed dapat
						 g.setDX(-g.getSpeed());
					}
					else if(rMove == "D"){
						g.setDX(g.getSpeed());
					}

					else if(rMove == "W"){
						g.setDY(-g.getSpeed());

					}
				/*if the ghost is positioned on the upper edge of the map then
				 * the ghost can only move down, right and left*/
				}else if(g.getY()+g.getDY() <=0){
					if(rMove == "A"){
						//speed dapat
						 g.setDX(-g.getSpeed());
					}
					else if(rMove == "S"){
						g.setDY(g.getSpeed());

					}
					else if(rMove == "D"){
						g.setDX(g.getSpeed());
					}
				}
				/*if the ghost is positioned on the inside the map, it can freely move*/
				else{
					if(rMove == "A"){
						//speed dapat
						 g.setDX(-g.getSpeed());
					}
					else if(rMove == "D"){
						g.setDX(g.getSpeed());
					}
					else if(rMove == "W"){
						g.setDY(-g.getSpeed());

					}
					else if(rMove == "S"){
						g.setDY(g.getSpeed());

					}
				}
			}));

			timeline.play();
			this.checkGhostEatsFood(g);
			this.checkGhostsCollision(g);

		}
	}

	//method for automatic spawning of powerups
	void autoPowerupSpawn(long SecondsTimer){
		if(SecondsTimer % GameTimer.POWERUP_PAUSE == 0){   //this is to ensure that it will pop every 10 seconds
			this.spawnPowerups();
		}
	}


	//this method updates the camera view
		private void updateCamera(){

			int camX = (int)this.pacman.getX() + this.pacman.getSize()/2 - MainGameStage.WINDOW_WIDTH/2;
			int camY = (int)this.pacman.getY() + this.pacman.getSize()/2 - MainGameStage.WINDOW_HEIGHT/2;

			int statX = camX + 40;
			int statY = camY + 40;

			int textX = camX + MainGameStage.WINDOW_WIDTH/4;
			int textY = camY + MainGameStage.WINDOW_HEIGHT/4;


			//set boundaties
			if(camX > this.camMaxX)
			{
				camX = this.camMaxX;
				statX = MainGameStage.MAP_WIDTH -  MainGameStage.WINDOW_WIDTH + 30;
				textX = MainGameStage.MAP_WIDTH - MainGameStage.WINDOW_WIDTH/2 - 200;
			}
			else if(camX < this.camMinX)
			{
				camX = this.camMinX;
				statX = 20;
				textX = MainGameStage.WINDOW_WIDTH/4;
			}
			if(camY > this.camMaxY)
			{
				camY = this.camMaxY;
				statY = MainGameStage.MAP_HEIGHT -  MainGameStage.WINDOW_HEIGHT + 50;
				textY = MainGameStage.MAP_HEIGHT - MainGameStage.WINDOW_HEIGHT/2 - 200;
			}
			else if(camY < this.camMinY)
			{
				camY = this.camMinY;
				statY = 40;
				textY = MainGameStage.WINDOW_HEIGHT/4;
			}

			// set cameras position
			camera.setTranslateY(camY);
			camera.setTranslateX(camX);

			//the status position
			this.statusPosX = statX;
			this.statusPosY = statY;

			//set text position
			this.textX = textX;
			this.textY = textY;


		}

		//This method shows that the power up is activated and its timer
		private void powerUpCounter(){
			if(Powerup.getisUsed() == true)
			{
				this.gc.setFont(Font.loadFont("file:resources/fonts/PixeloidSans-Bold.ttf", 30));
				if(Powerup.getName() == Powerup.IMMUNITY)
				{
					this.gc.fillText("Immunity Activated",  this.textX, this.textY);
				}else
				{
					this.gc.fillText("Speed Up Activated", this.textX, this.textY);
				}
				this.gc.setFill(Color.WHITE);
				this.gc.fillText(PowerupTimer.getCounter() + "s",  this.textX + MainGameStage.WINDOW_WIDTH/4 -20, this.textY + 50);
			}

		}


		//getters
		public Scene getScene(){
			return this.theScene;
		}

		public int getFoodEaten(){
			return GameTimer.foodeaten;
		}

		public int getGhostEaten(){
			return GameTimer.ghostEaten;
		}

		public int getPacmanSize(){
			return this.pacman.getSize();
		}


		public long getTimeAlive(){
			return GameTimer.timeAlive;
		}



}
