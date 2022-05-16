package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz {

	/*
	 * Initializing an ArrayList to store specific words to be used for each quiz
	 * which includes the new game and review quizzes.
	 */

	List<String> popularWords = new ArrayList<String>();
	List<String> quizWords = new ArrayList<String>();
	List<String> reviewTestTemp = new ArrayList<String>();
	List<String> reviewWords = new ArrayList<String>();
	ArrayList<Integer> wordPositions = new ArrayList<Integer>();

	List<String> mastered = new ArrayList<String>();
	List<String> faulted = new ArrayList<String>();
	List<String> failed = new ArrayList<String>();

	int wordNumber = -1;
	int wordCount = 1;

	private boolean checkFirstAttempt = true;

	public Quiz() {

	}

	public boolean getWords(String selection) {
		try {
			if (selection.equals("new")) {
				// Clear arrays
				popularWords.clear();

				// Append all the words from the file into an ArrayList
				BufferedReader br2 = new BufferedReader(new FileReader("./words/popular.txt"));
				String linePopular;
				while ((linePopular = br2.readLine()) != null) {
					popularWords.add(linePopular);
				}

				// Get all indexes
				for (int i = 0; i < popularWords.size(); i++) {
					wordPositions.add(i);
				}

				// Shuffles 3 random words into array
				Collections.shuffle(wordPositions);
				for (int i = 0; i < 3; i++) {
					quizWords.add(popularWords.get(wordPositions.get(i)));
				}
				quizWords.add("done");
				
			} else {
				// Clear all the current arrays
				popularWords.clear();
				quizWords.clear();
				wordPositions.clear();
				reviewWords.clear();
				
				
				BufferedReader br1 = new BufferedReader(new FileReader("./words/failed.txt"));
				String lineReviewTest1;
				while ((lineReviewTest1 = br1.readLine()) != null) {
					reviewTestTemp.add(lineReviewTest1);
				}
				// Append all the words in the review file into an array.
				BufferedReader br3 = new BufferedReader(new FileReader("./words/review.txt"));
				String lineReviewTest;
				while ((lineReviewTest = br3.readLine()) != null) {
					reviewTestTemp.add(lineReviewTest);
				}
				
				// Checks for contents present inside the review file, if there is nothing then return false.
				if (reviewTestTemp.size() <  1) {
					return false;
				}
				
				// Get all indexes
				for (int i = 0; i < reviewTestTemp.size(); i++) {
					wordPositions.add(i);
				}
				
				if (reviewTestTemp.size() > 2) { 
					Collections.shuffle(wordPositions);
					for (int i = 0; i < 3; i++) {
						quizWords.add(reviewTestTemp.get(wordPositions.get(i)));
						reviewWords.remove(reviewTestTemp.get(wordPositions.get(i)));
					}
				} else {
					for (int i = 0; i < reviewTestTemp.size(); i++) {
						quizWords.add(reviewTestTemp.get(i));
						reviewWords.remove(reviewTestTemp.get(i));
					}	
				}
				quizWords.add("done");
			}
			return true;
		}
		
		catch (IOException ex) {

		}

		return true;
	}

	// Gets the next word of the quiz and returns it
	public String getNextWord() {
		wordNumber++;
		return quizWords.get(wordNumber);
	}

	// Gets the current word of the quiz and returns it
	public String getCurrentWord() {
		return quizWords.get(wordNumber);
	}

	// Gets the number of words in the quiz and returns it
	public int getNumOfWords() {
		return quizWords.size() - 1;
	}

	// Gets the word count of the quiz and returns it
	public int getCurrentCount() {
		return wordCount;
	}

	// Increments the wordCount
	public void updateCurrentCount() {
		wordCount++;
	}

	// This method will check whether the answer is correct and return a specific
	// integer for
	// depending on what case it is 0: correct, 1: incorrect try again, 2: failed.
	public int checkAnswer(String spellingWord) throws IOException, InterruptedException {
		if (this.checkFirstAttempt) {
			if (spellingWord.equalsIgnoreCase(this.getCurrentWord())) {
				this.addMastered();
				this.festival("Correct");
				return 0;
			} else {
				this.festival("Incorrect, try once more ");
				Timer(1250);
				this.festival(this.getCurrentWord());
				Timer(1250);
				this.festival(this.getCurrentWord());
				this.checkFirstAttempt = false;

				return 1;
			}
		} else {
			if (spellingWord.equalsIgnoreCase(this.getCurrentWord())) {
				this.addFaulted();
				this.festival("Correct");
				this.checkFirstAttempt = true;

				return 0;
			} else {
				this.addFailed();
				this.festival("Incorrect");
				this.checkFirstAttempt = true;

				return 2;

			}
		}
	}

	public void addMastered() throws IOException {
		mastered.clear();
		mastered.add(getCurrentWord());
		try (FileWriter writer = new FileWriter("./words/mastered.txt", true)) {
			for (String word : mastered) {
				writer.write(word + System.lineSeparator());
			}
			writer.close();
		}
	}

	public void addFaulted() throws IOException {
		faulted.clear();
		faulted.add(getCurrentWord());
		try (FileWriter writer = new FileWriter("./words/faulted.txt", true)) {
			for (String word : faulted) {
				writer.write(word + System.lineSeparator());
			}
			writer.close();
		}
	}

	public void addFailed() throws IOException {
		failed.clear();
		failed.add(getCurrentWord());
		try (FileWriter writer = new FileWriter("./words/failed.txt", true)) {
			for (String word : failed) {
				writer.write(word + System.lineSeparator());
			}
			writer.close();
		}
		try (FileWriter writer1 = new FileWriter("./words/failedStatistics.txt", true)) {
			for (String word : failed) {
				writer1.write(word + System.lineSeparator());
			}
			writer1.close();
			
		}
	}

	public void addToReview() {
		reviewWords.add(getCurrentWord());
	}

	public String getQuestionText() {
		int num = getCurrentCount();
		String number = "" + num;
		String questionText = "Spell Word " + number + " of " + getNumOfWords() + ":";
		return questionText;
	}

	public void removeFromFile(String string) {
		try {
			String command = "sed -i /" + string +  "/d ./words/failed.txt";
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void festival(String string) {
		try {
			String command = "echo " + string + " | festival --tts";
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			pb.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* This method will add a delay between different outputs. */
	public void Timer(int num) {
		try {
			Thread.sleep(num);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
