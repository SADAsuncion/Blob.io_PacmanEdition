package PacmanGame;

import javafx.application.Application;
import javafx.stage.Stage;

public class PacmanGame extends Application {

	public static void main(String[] args){
		launch(args);
	}

	public void start(Stage stage) throws Exception{
	       MainGameStage gameStage = new MainGameStage();
	       gameStage.setStage(stage);

		}

}
