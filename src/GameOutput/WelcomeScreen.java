package GameOutput;


import PacmanGame.MainGameStage;
import PacmanGame.*;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class WelcomeScreen{
	//Attributes
	private Scene scene;
	private Pane root;
	private GameTimer gametimer;
	private MainGameStage gamestage;
	private Canvas canvas1;
	private GraphicsContext gc;
	private Canvas textCanvas;
	public static MediaPlayer mediaPlayer;


	//
	public WelcomeScreen(MainGameStage gamestage, GameTimer gametimer){
		//initialize
		this.root = new Pane();
		this.scene = new Scene(this.root, MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT);
		this.textCanvas = new Canvas(MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_WIDTH);
		this.canvas1 = new Canvas(MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_WIDTH);							//create a 400x400screen canvas for "drawing" elements
		this.gc = canvas1.getGraphicsContext2D();
		this.gametimer = gametimer;
		this.gamestage = gamestage;
		this.BGMusic();
		this.start();
	}


	public void start(){

		this.addComponents();
		VBox vbox = this.addButtons();
		HBox hbox = this.addIcons();

		this.root.getChildren().add(vbox);
		this.root.getChildren().add(hbox);


	}

	//this functions adds the components for the welcome page
	private void addComponents(){

		// Add background to the canvas
		Canvas canvas = new Canvas(MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT);
		GraphicsContext gc1 = canvas.getGraphicsContext2D();
		Image bg = new Image("images/WelcomeBG.jpg",MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT,false, false);
		gc1.drawImage(bg,0, 0);

		this.gc = this.canvas1.getGraphicsContext2D();
		Image title = new Image("images/PacmanTitle.png",MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT,false, false);
		this.gc.drawImage(title, 150,20, 500, 250);

		//Add rectangle
		this.gc = this.canvas1.getGraphicsContext2D();
		Image rec = new Image("images/Rectangle.png",MainGameStage.WINDOW_WIDTH,MainGameStage.WINDOW_HEIGHT,false, false);
		this.gc.drawImage(rec,0, 275, 800,250);

		//add text
		//Font font = new Font("Pixeloid Sans", 30);
		GraphicsContext textGc = this.textCanvas.getGraphicsContext2D();
		textGc.setFont(Font.loadFont("file:resources/fonts/PixeloidSans-Bold.ttf", 30));
        textGc.setFill(Color.WHITE);
        textGc.fillText("Press Start to Play",210,550);

        //text animation
        //guide from: https://youtu.be/MgD2FxMr7AA
        TranslateTransition textAnimation = new TranslateTransition();
        textAnimation.setDuration(Duration.seconds(1));
        textAnimation.setToY(10);
        textAnimation.setAutoReverse(true);
        textAnimation.setCycleCount(Animation.INDEFINITE);
        textAnimation.setNode(this.textCanvas);
        textAnimation.play();

        //add to root
        this.root.getChildren().add(canvas);
		this.root.getChildren().add(this.canvas1);
		this.root.getChildren().add(this.textCanvas);


	}

	private void BGMusic(){
		Media music = new Media(getClass().getResource("/music/mainbgmusic.mp3").toExternalForm());
		mediaPlayer = new MediaPlayer(music);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
		mediaPlayer.setVolume(0.5);
	}

	//add characters and animation
	private HBox addIcons(){

		HBox hbox = new HBox(10);

		ImageView pacman = new ImageView(new Image("images/Pacman.gif"));
        pacman.setFitHeight(100);
        pacman.setPreserveRatio(true);



        ImageView ghost1 = new ImageView(new Image("images/Ghost1.gif"));
        ghost1.setFitHeight(100);
        ghost1.setPreserveRatio(true);


        ImageView ghost2 = new ImageView(new Image("images/Ghost2.gif"));
        ghost2.setFitHeight(100);
        ghost2.setPreserveRatio(true);


        hbox.getChildren().addAll(pacman,ghost1,ghost2);
        hbox.setLayoutY(350);
        hbox.setLayoutX(-500);

        TranslateTransition characAnimation = new TranslateTransition();
        characAnimation.setDuration(Duration.seconds(4));
        characAnimation.setToX(1500);
        //characAnimation.setAutoReverse(true);
        characAnimation.setCycleCount(Animation.INDEFINITE);
        characAnimation.setNode(hbox);
        characAnimation.play();

		return hbox;
	}

	//Create button using images with functionalities
	private VBox addButtons(){
		 //Create imageView with background image
        ImageView start = new ImageView(new Image("images/Start Button.png"));
        start.setFitHeight(60);
        start.setPreserveRatio(true);
        this.buttonHandler(start,1, this.gamestage, this.gametimer);

        ImageView about = new ImageView(new Image("images/about Button.png"));
        about.setFitHeight(40);
        about.setPreserveRatio(true);
        this.buttonHandler(about,2, this.gamestage, this.gametimer);

        ImageView info = new ImageView(new Image("images/Info Button.png"));
        info.setFitHeight(35);
        info.setPreserveRatio(true);
        this.buttonHandler(info,3,this.gamestage, this.gametimer);

        ImageView exit = new ImageView(new Image("images/Exit Button.png"));
        exit.setFitHeight(40);
        exit.setPreserveRatio(true);
        this.buttonHandler(exit,4,this.gamestage, this.gametimer);

        VBox vbox = new VBox(8);
        vbox.setAlignment(Pos.BASELINE_CENTER);

        vbox.getChildren().addAll(start,about,info,exit);
        vbox.setLayoutY(575);
        vbox.setLayoutX(285);

        return vbox;
	}


	//event listeners for buttons
	private void buttonHandler(ImageView imageView, int num, MainGameStage gamestage, GameTimer gametimer){
        imageView.setOnMouseClicked(new EventHandler <MouseEvent>() {

			public void handle(MouseEvent event) {
            	switch(num){
            	case 1:
            		//Go to start game
            		System.out.println("start");
            		gamestage.startGame(gametimer);
            		break;
            	case 2:
            		//Go to about page
            		System.out.println("about");
            		mediaPlayer.setMute(true);
            		gamestage.displayAbout(gamestage);
            		break;
            	case 3:
            		//Go to about page info page
            		System.out.println("info");
            		mediaPlayer.setMute(true);
            		gamestage.displayInfo(gamestage, gametimer);
            		break;
            	case 4:
            		// Exit game
            		System.out.println("exit");
            		System.exit(0);

            	}

            }

        });
	}

	public Scene getScene(){
		return this.scene;
	}


}
