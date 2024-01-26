package GameOutput;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import PacmanGame.MainGameStage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;

import PacmanGame.*;

public class Instructions {
	//attributes
	private Scene scene;
	private Pane root;
	private MainGameStage gamestage;
	private Canvas canvas;
	private static GraphicsContext gc;
	private GameTimer gametimer;


	private static int page = 1; //this will be used to track the current page number

	//declaring the images that will be used
	private final Image Instruct1 = new Image("images/Instructions1.jpg",800,800,false,false);
	private final Image Instruct2 = new Image("images/Instructions2.jpg",800,800,false,false);
	private final Image Instruct3 = new Image("images/Instructions3.jpg",800,800,false,false);
	private final Image Instruct4 = new Image("images/Instructions4.jpg",800,800,false,false);
	private final Image Instruct5 = new Image("images/Instructions5.jpg",800,800,false,false);
	private final Image Instruct6 = new Image("images/Instructions6.jpg",800,800,false,false);

	private final Image bg = Instruct1; 	//Setting the background

	private final int MIN_PAGE = 1;
	private final int MAX_PAGE = 6;

	//constructors
	public Instructions(MainGameStage gamestage, GameTimer gametimer){

		this.root = new Pane();
		this.scene = new Scene (this.root,MainGameStage.WINDOW_HEIGHT,MainGameStage.WINDOW_WIDTH);
		this.gamestage = gamestage;
		this.gametimer = gametimer;
		canvas = new Canvas(MainGameStage.WINDOW_HEIGHT,MainGameStage.WINDOW_WIDTH);
		Instructions.gc = canvas.getGraphicsContext2D();

		this.start();

	}


	public void start(){

		Instructions.gc.drawImage(this.bg,0,0);
		this.root.getChildren().add(canvas);

		HBox hbox = this.addButtons();
		this.root.getChildren().add(hbox);

		updatePage(page);
	}


	//Home Event Handler
	private void HomeEventHandler(ImageView btn, MainGameStage gamestage, GameTimer gametimer){

		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				System.out.println("Home");
				gamestage.displayWelcomeScene(gamestage, gametimer);

			}
		});
	}


	//Next Event Handler
	private void NextEventHandler(ImageView btn){

		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				if(Instructions.page < MAX_PAGE){
					System.out.println("Next");
					Instructions.page = (Instructions.page+1);
					MoveToPage(Instructions.page);
					updatePage(Instructions.page);
				}
			}
		});
	}

	//Back Event Handler
	private void BackEventHandler(ImageView btn){

		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				if(Instructions.page > MIN_PAGE){
					Instructions.page = (Instructions.page-1);
					MoveToPage(Instructions.page);
					System.out.println("Back");
					updatePage(Instructions.page);
				}
			}
		});
	}


	private void MoveToPage(int page){  		//This will be used to move the page
		switch(page){
		case 1:
			Instructions.gc.drawImage(this.Instruct1,0,0);
			break;
		case 2:
			Instructions.gc.drawImage(this.Instruct2,0,0);;
			break;
		case 3:
			Instructions.gc.drawImage(this.Instruct3,0,0);
			break;
		case 4:
			Instructions.gc.drawImage(this.Instruct4,0,0);
			break;
		case 5:
			Instructions.gc.drawImage(this.Instruct5,0,0);
			break;
		case 6:
			Instructions.gc.drawImage(this.Instruct6,0,0);
			break;
		}
	}

	/*this methods updates page number*/
	private void updatePage(int page){
		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setFont(Font.font("Times New Roman", FontWeight.LIGHT, 15));
		gc.setFill(Color.WHITE);
		gc.fillText("Page "+(page)+" out of "+MAX_PAGE+" pages", 350, 780);
	}

	/*This methods creates images as buttons with functionalities*/
	private HBox addButtons(){
		//Create imageView with background image

		ImageView back = new ImageView (new Image("images/back button.png"));
		back.setFitHeight(40);
		back.setPreserveRatio(true);
		this.BackEventHandler(back);

		ImageView home = new ImageView (new Image("images/home button.png"));
		home.setFitHeight(40);
		home.setPreserveRatio(true);
		this.HomeEventHandler(home, this.gamestage, this.gametimer);

		ImageView next = new ImageView (new Image("images/next button.png"));
		next.setFitHeight(42);
		next.setPreserveRatio(true);
		this.NextEventHandler(next);


		HBox hbox = new HBox(8);
		hbox.setAlignment(Pos.BASELINE_CENTER);

		hbox.getChildren().addAll(back,home,next);
		hbox.setLayoutX(200);
		hbox.setLayoutY(680);
		hbox.setSpacing(10);

		return hbox;
	}

	public Scene getScene(){
		return this.scene;
	}

}

