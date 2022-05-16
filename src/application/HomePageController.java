package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.io.IOException;

public class HomePageController {
	
	@FXML
	private Button newGameButton;
	@FXML
	private Button reviewMistakesButton;
	@FXML
	private Button viewStatsButton;
	@FXML
	private Button clearStatsButton;
	
	/* Checks buttons on press and when buttons are press they will call
     * the corresponding function in order to change the scene.
	*/
	
	public void newGame(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("newGame.fxml");
	}
	
	public void reviewMistakes(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("reviewMistakes.fxml");
	}
	
	public void viewStats(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("viewStats.fxml");
	}
	
	public void clearStats(ActionEvent event) throws IOException, FileNotFoundException {
		clearStats.clearAllStats("./words/failed.txt");
		clearStats.clearAllStats("./words/faulted.txt");
		clearStats.clearAllStats("./words/mastered.txt");
		clearStats.clearAllStats("./words/statistics.txt");
		clearStats.clearAllStats("./words/review.txt");
		clearStats.clearAllStats("./words/failedStatistics.txt");
		AlertBox.display("Spelling Wiz", "Successfully cleared statistics");
	}
	
}
