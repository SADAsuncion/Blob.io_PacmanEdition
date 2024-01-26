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
import PacmanGame.GameTimer;
import PacmanGame.MainGameStage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;

public class About {
	//attributes
	private Scene scene;
	private Pane root;
	private Canvas canvas;
	private static GraphicsContext gc;


	private static int page = 1; //this will be used to track the current page number

	//declaring the images that will be used
	private final Image About1 = new Image("images/About1.jpg",800,800,false,false);
	private final Image About2 = new Image("images/About2.jpg",800,800,false,false);
	private final Image About3 = new Image("images/About3.jpg",800,800,false,false);
	private final Image About4 = new Image("images/About4.jpg",800,800,false,false);
	private final Image About5 = new Image("images/About5.jpg",800,800,false,false);
	private final Image About6 = new Image("images/About6.jpg",800,800,false,false);
	private final Image About7 = new Image("images/About7.jpg",800,800,false,false);


	private final Image bg = About1;  //Setting the background

	private final int MIN_PAGE = 1;
	private final int MAX_PAGE = 7;

	private MainGameStage gamestage;
	private GameTimer gametimer;


	//Constructor
	public About(MainGameStage gamestage, GameTimer gametimer){

		//Initialized
		this.root = new Pane();
		this.scene = new Scene (this.root,MainGameStage.WINDOW_HEIGHT,MainGameStage.WINDOW_WIDTH);
		this.gamestage = gamestage;
		this.gametimer = gametimer;
		canvas = new Canvas(MainGameStage.WINDOW_HEIGHT,MainGameStage.WINDOW_WIDTH);
		About.gc = canvas.getGraphicsContext2D();
		this.start();


	}
	public void start(){

		About.gc.drawImage(this.bg,0,0);
		this.root.getChildren().add(canvas);

		HBox hbox = this.addButtons();
		this.root.getChildren().add(hbox);

		updatePage(page);
	}


	//Home Event Handler - will lead to the main home screen
	private void HomeEventHandler(ImageView btn,MainGameStage gamestage, GameTimer gametimer){

		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				System.out.println("Home");
				gamestage.displayWelcomeScene(gamestage, gametimer);
			}
		});
	}


	//Next Event Handler - will proceed to the next page
	private void NextEventHandler(ImageView btn){

		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				if(About.page < MAX_PAGE){     // condition to ensure that it will not go beyond
					System.out.println("Next");
					About.page = (About.page+1);
					MoveToPage(About.page);
					updatePage(About.page);
				}
			}
		});
	}

	//Back Event Handler - will proceed to the previous page
	private void BackEventHandler(ImageView btn){

		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent e) {
				if(About.page > MIN_PAGE){      // condition to ensure that it will not go beyond
					System.out.println("Back");
					About.page = (About.page-1);
					MoveToPage(About.page);
					updatePage(About.page);
				}
			}
		});
	}

	public void MoveToPage(int page){  		//This will be used to move the page
		switch(page){
		case 1:
			About.gc.drawImage(this.About1,0,0);
			break;
		case 2:
			About.gc.drawImage(this.About2,0,0);;
			break;
		case 3:
			About.gc.drawImage(this.About3,0,0);
			break;
		case 4:
			About.gc.drawImage(this.About4,0,0);
			break;
		case 5:
			About.gc.drawImage(this.About5,0,0);
			break;
		case 6:
			About.gc.drawImage(this.About6,0,0);
			break;
		case 7:
			About.gc.drawImage(this.About7,0,0);
			break;
		}
	}

	//this method will print the page number in the about us interface
	public void updatePage(int page){
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

