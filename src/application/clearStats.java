package application;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class clearStats {
	
	public static void clearAllStats(String file) throws FileNotFoundException {
		PrintWriter writer= new PrintWriter(file);
		writer.print("");
		writer.close();
	}
}
