package datatypewrappers;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class FileUtility {

	public static void deleteFile(String sFileName) {
		File file = new File(sFileName);
		file.delete();
	}

	public static String readFile(File file) {

		try {
			return readFile(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String readFile(InputStream fin) {

		StringBuilder strContent = new StringBuilder("");
		int ch;
		try {
			while ((ch = fin.read()) != -1) {
				strContent.append((char) ch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(fin);
		}
		return strContent.toString();
	}

	public static String readFile(String sFilePath) {
		File file = new File(sFilePath);
		return readFile(file);
	}

	public static void writeTotFile(String fileName, String content) {
		FileWriter output = null;
		BufferedWriter writer = null;
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			output = new FileWriter(fileName, true);
			writer = new BufferedWriter(output);
			writer.write(content);
			writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(writer);
			close(output);
		}
	}
	
	public static void close(Closeable closeIt) {
		if (null != closeIt) {
			try {
				closeIt.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}