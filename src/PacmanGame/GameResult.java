package PacmanGame;

import Sprites.Pacman;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GameResult {

	private Scene scene;
	private Pane root;
	private Canvas bgCanvas;

	//Parameters: int score, int time, int numEnemies, int size
	GameResult(GameTimer gameTimer){
		// create root
		this.root = new Pane();

		this.bgCanvas = new Canvas(MainGameStage.WINDOW_WIDTH, MainGameStage.WINDOW_HEIGHT);
		GraphicsContext gc = bgCanvas.getGraphicsContext2D();

		//Add background
		Image bg = new Image("images/WelcomeBG.jpg", MainGameStage.WINDOW_WIDTH, MainGameStage.WINDOW_HEIGHT, false, false);
		gc.drawImage(bg, 0, 0);

		// Create vbos for the title image
		VBox vbox = new VBox();

		//Add Image
		ImageView title = new ImageView(new Image("images/Gameover Title.png"));
		title.setFitHeight(125);
		title.setFitWidth(440);

		//position vbox
		vbox.setLayoutX(175);
		vbox.setLayoutY(-120);

		//Create animation to the title
		TranslateTransition characAnimation = new TranslateTransition();
        characAnimation.setDuration(Duration.seconds(3));
        characAnimation.setToY(170);
        characAnimation.setNode(vbox);
        characAnimation.play();

		// Add rectangle
		Canvas rectCanvas = new Canvas(MainGameStage.WINDOW_WIDTH, MainGameStage.WINDOW_HEIGHT);
		GraphicsContext gc2 = rectCanvas.getGraphicsContext2D();

		Image rectangle = new Image("images/Rectangle1.png", MainGameStage.WINDOW_WIDTH, MainGameStage.WINDOW_HEIGHT, false, false);
		gc2.drawImage(rectangle, 25 ,175, 750, 450);


		//Create Text for result information
		Font theFont = Font.loadFont("file:resources/fonts/PixeloidSans-Bold.ttf",35);
		gc2.setFont(theFont);
		gc2.setFill(Color.ANTIQUEWHITE);
		gc2.setFill(Color.ANTIQUEWHITE);
		gc2.fillText("" + gameTimer.getFoodEaten(), 230, 310);
		gc2.fillText("" + gameTimer.getGhostEaten(), 570, 310);
		gc2.fillText("" + gameTimer.getPacmanSize(), 390, 430);
		gc2.fillText("" + gameTimer.getTimeAlive(), 390, 540);

		//Create "buttons"

		//create hbox for home and back buttons
		HBox hbox = new HBox(300);

        ImageView exit = new ImageView(new Image("images/Exit Red.png"));
        exit.setFitHeight(60);
        exit.setPreserveRatio(true);
        this.buttonHandler(exit);
		hbox.setLayoutY(650);
		hbox.setLayoutX(270);

        hbox.getChildren().addAll(exit);

		vbox.getChildren().add(title);
		root.getChildren().add(bgCanvas);
		root.getChildren().add(vbox);
		root.getChildren().add(rectCanvas);
		root.getChildren().add(hbox);


		this.scene = new Scene (root,MainGameStage.WINDOW_WIDTH, MainGameStage.WINDOW_HEIGHT);


	}


	public Scene getScene(){
		return this.scene;
	}

	//event handler for button
	private void buttonHandler(ImageView imageView){
        imageView.setOnMouseClicked(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
            	System.exit(0);
            }

        });
	}
}
