package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import java.io.IOException;

public class ReviewMistakesController {

	@FXML
	private Button backButton;
	@FXML
	private Button submitButton;
	@FXML
	private Button startButton;
	@FXML
	private Label labelText;
	@FXML
	private Label labelType;
	@FXML
	private TextField userInput;

	public void homePage(ActionEvent event) throws IOException {
		changeToHomePage();
	}

	/* When the start button is press the game will begin */
	public void startGame(ActionEvent event) throws IOException {
		Quiz q = new Quiz();
		q.getWords("review");
		if (q.getNumOfWords() < 1) {
			AlertBox.display("Spelling Wiz", "There are no words to review.");
			changeToHomePage();
		} else {
			labelText.setText(q.getQuestionText());
			q.getNextWord();
			q.festival(q.getCurrentWord());
			submitButton.setOnAction(e -> {
				String answer = userInput.getText();
				userInput.clear();
				try {
					int number = q.checkAnswer(answer);
					if (number == 0) {
						labelType.setText("Correct!");
						q.removeFromFile(q.getCurrentWord());
						String checkNext = q.getNextWord();
						if (checkNext.equals("done")) {
							labelText.setText("");
							changeToHomePage();
							AlertBox.display("Spelling Wiz", "Select the your next option!");
						} else {
							q.updateCurrentCount();
							labelText.setText(q.getQuestionText());
							Timer(1250);
							q.festival(q.getCurrentWord());
						}
					} else if (number == 2) {
						labelType.setText("Incorrect!");
						String checkNext = q.getNextWord();
						if (checkNext.equals("done")) {
							labelText.setText("");
							changeToHomePage();
							AlertBox.display("Spelling Wiz", "Select the your next option!");
						} else {
							q.updateCurrentCount();
							labelText.setText(q.getQuestionText());
							Timer(1250);
							q.festival(q.getCurrentWord());
						}
					} else {
						labelType.setText("Incorrect, try again!");
					}
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
	}

	public void changeToHomePage() throws IOException {
		Main m = new Main();
		m.changeScene("homePage.fxml");
	}

	public void Timer(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
