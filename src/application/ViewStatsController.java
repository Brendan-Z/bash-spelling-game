package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class ViewStatsController {

	@FXML
	private Button backButton;
	@FXML
	private Button viewStats;
	@FXML
	private TextArea textAreaStats;

	/*
	 * Checks buttons on press and when buttons are press they will call the
	 * corresponding function in order to change the scene.
	 */
	public void homePage(ActionEvent event) throws IOException {
		Main m = new Main();
		m.changeScene("homePage.fxml");
	}

	// This method will view the statistics by iterating through the files mastered,
	// faulted and failed. Counting the amount of occurrences of each word and then
	// putting them into the statistics file.

	public void viewStats(ActionEvent event) throws IOException {
		File file = new File("./words/mastered.txt");
		File file2 = new File("./words/faulted.txt");
		File file3 = new File("./words/failed.txt");
		if (file.length() < 1 && file2.length() < 1 && file3.length() < 1) {
			AlertBox.display("Spelling Wiz", "No statistics to view");
			this.homePage(event);
		} else {
			textAreaStats.clear();
			viewStats v = new viewStats();
			v.inputAllStats();
			textAreaStats.appendText("This will be in order of mastered, faulted and failed.\n");
			BufferedReader br = new BufferedReader(new FileReader("./words/statistics.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				textAreaStats.appendText(line + "\n");
			}
		}
	}
}
