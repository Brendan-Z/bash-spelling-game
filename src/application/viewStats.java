package application;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.FileWriter;

public class viewStats {

	public int grepCountCommand(String word, String fileName) {

		int grepCount = 0;

		try {
			String command = "grep -c " + word + " ./words/" + fileName;
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			Process process = pb.start();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = stdout.readLine()) != null) {
				grepCount = Integer.parseInt(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return grepCount;
	}

	public void inputAllStats() throws IOException {
		clearStats.clearAllStats("./words/statistics.txt");
		List<String> popularWords = new ArrayList<String>();

		try (BufferedReader br2 = new BufferedReader(new FileReader("./words/popular.txt"))) {
			String linePopular;
			while ((linePopular = br2.readLine()) != null) {
				popularWords.add(linePopular);
			}
			try (FileWriter fileWriter = new FileWriter("./words/statistics.txt", true)) {
				for (String word : popularWords) {
					int masteredCount = this.grepCountCommand(word, "mastered.txt");
					int faultedCount = this.grepCountCommand(word, "faulted.txt");
					int failedCount = this.grepCountCommand(word, "failedStatistics.txt");
					if (masteredCount > 0 || faultedCount > 0 || failedCount > 0) {
						fileWriter.write(word + " " + masteredCount + " " + faultedCount + " " + failedCount + System.lineSeparator());
					}
				}
			}
		}
	}
}