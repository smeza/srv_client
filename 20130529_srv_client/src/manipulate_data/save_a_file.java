package manipulate_data;

import java.io.File;
import java.io.IOException;



public class save_a_file {
	/**
	 * Creates a txt document and stores it
	 */

	public void createFile(String content) 
	{
		File file = new File("data/data/your package name/test.txt");
		if (!file.exists()) {
		        try {
		            file.createNewFile();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		}
	}

}
