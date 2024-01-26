package PacmanGame;
import GameOutput.*;


import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainGameStage {
	// Constants of the sizes
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 800;
	public static final int MAP_HEIGHT = 2400;
	public static final int MAP_WIDTH = 2400;

	private final Image ICON = new Image ("images/Pacman_Icon.png",300,300,false,false);

	//Attributes
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	static long startGame;


	//constructor
	public MainGameStage(){
		this.root = new Group();
		this.scene = new Scene(root, MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(MainGameStage.MAP_WIDTH,MainGameStage.MAP_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gametimer = new GameTimer(this.gc, this.scene, this);

	}

	/*This method sets up the stage*/
	public void setStage(Stage stage){

		this.stage = stage;
		stage.getIcons().add(ICON);
		this.displayWelcomeScene(this, this.gametimer);
		this.root.getChildren().add(canvas);
		this.stage.setTitle("PACMAN.IO");
		this.stage.setScene(this.scene);
		this.stage.show();
	}


	/*This method Display the welcome scene*/
	public void displayWelcomeScene(MainGameStage gamestage, GameTimer gametimer){
		PauseTransition transition = new PauseTransition(Duration.seconds(0.001));
		transition.play();

		transition.setOnFinished(new EventHandler <ActionEvent>() {
			public void handle(ActionEvent event) {
				WelcomeScreen welcome = new WelcomeScreen(gamestage, gametimer);
				stage.setScene(welcome.getScene());

			}

		});
	}

	/*This method displays the game results
	 * aka. game over scene*/
	public void displayGameResults(){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler <ActionEvent>(){

			public void handle(ActionEvent event){
				GameResult gameResults = new GameResult(gametimer);
				stage.setScene(gameResults.getScene());
			}

		});
	}

	/*This method display the instructions and mechanics of the game*/
	public void displayInfo(MainGameStage gamestage, GameTimer gametimer){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler <ActionEvent>(){

			public void handle(ActionEvent event){
				Instructions instruction = new Instructions(gamestage, gametimer);
				stage.setScene(instruction.getScene());
			}

		});
	}

	/*This method display the information about the developers of the games*/
	public void displayAbout(MainGameStage gamestage){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();

		transition.setOnFinished(new EventHandler <ActionEvent>(){

			public void handle(ActionEvent event){
				About about = new About(gamestage,gametimer);
				stage.setScene(about.getScene());
			}

		});
	}

	/*This method starts the main game*/
	public void startGame(GameTimer gametimer){
		PauseTransition transition = new PauseTransition(Duration.seconds(1));
		transition.play();
		startGame= System.nanoTime(); // determines the start of game time

		transition.setOnFinished(new EventHandler <ActionEvent>(){

		public void handle(ActionEvent arg0) {
				GameTimer gameTimer = gametimer;
				stage.setScene(gameTimer.getScene());
				gameTimer.start();
			}

		});
	}

}

