import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

public class TextBuddyTest {
	@Test
	// tests doAddingText() method
	public void testAddMethod() throws IOException {
		// test addSentence method
		TextBuddy.doClearingText();
		assertEquals("added to textFile.txt: \"drink tea\"",
				TextBuddy.doAddingText("add drink tea", "add", "drink tea"));
		assertEquals("added to textFile.txt: \"have dinner\"",
				TextBuddy.doAddingText("add have dinner", "add", "have dinner"));
		assertEquals("added to textFile.txt: \"have lunch\"",
				TextBuddy.doAddingText("add have lunch", "add", "have lunch"));
		assertEquals(3, TextBuddy.storage.size());
		String resultAdd = "1.drink tea" + System.lineSeparator() + "2.have dinner" + System.lineSeparator()
				+ "3.have lunch" + System.lineSeparator();
		assertEquals(resultAdd, getFileContent("textFile.txt"));
	}

	@Test
	// tests doDeletingText() method
	public void testDeleteMethod() throws IOException {
		TextBuddy.doClearingText();
		TextBuddy.doAddingText("add go shopping", "add", "go shopping");
		TextBuddy.doAddingText("add swimming", "add", "swimming");
		TextBuddy.doAddingText("add play basketball", "add", "play basketball");

		assertEquals("deleted from textFile.txt: \"swimming\"", TextBuddy.doDeletingText("delete 2", "delete", "2"));
		assertEquals(2, TextBuddy.storage.size());
		// test the delete's index is out of boundary , it is too large
		assertEquals("Invalid command!", TextBuddy.doDeletingText("delete 999", "delete", "999"));
		assertEquals(2, TextBuddy.storage.size());
		// test the delete's index is 0
		assertEquals("Invalid command!", TextBuddy.doDeletingText("delete 0", "delete", "0"));
		assertEquals(2, TextBuddy.storage.size());
		// test the delete's index is a negative number
		assertEquals("Invalid command!", TextBuddy.doDeletingText("delete -65", "delete", "-65"));
		assertEquals(2, TextBuddy.storage.size());
	}

	@Test
	// tests doClearingText() method
	public void testClearMethod() throws IOException {
		TextBuddy.doClearingText();
		TextBuddy.doAddingText("add go fishing", "add", "go fishing");
		TextBuddy.doAddingText("add study", "add", "study");
		TextBuddy.doAddingText("add play tennis", "add", "play tennis");
		assertEquals("all content deleted from textFile.txt", TextBuddy.doClearingText());
		assertEquals(0, TextBuddy.storage.size());
		assertEquals(0, countFileLines("textFile.txt"));
	}

	@Test
	// tests doDisplayingText() method
	public void testDisplayMethod() throws IOException {
		TextBuddy.doClearingText();
		TextBuddy.doAddingText("add apple juice", "add", "apple juice");
		TextBuddy.doAddingText("add boy", "add", "boy");
		TextBuddy.doAddingText("add go fishing", "add", "go fishing");
		TextBuddy.doAddingText("add play tennis", "add", "play tennis");
		TextBuddy.doAddingText("add study", "add", "study");
		String testResult = "";
		testResult = "1.apple juice" + System.lineSeparator() + "2.boy" + System.lineSeparator() + "3.go fishing"
				+ System.lineSeparator() + "4.play tennis" + System.lineSeparator() + "5.study"
				+ System.lineSeparator();
		assertEquals(testResult, TextBuddy.doDisplayingText());
		assertEquals(testResult, getFileContent("textFile.txt"));
		assertEquals(5, countFileLines("textFile.txt"));
	}

	@Test
	// tests doSortingText() method
	public void testSortMethod() throws IOException {
		TextBuddy.doClearingText();
		TextBuddy.doAddingText("add go fishing", "add", "go fishing");
		TextBuddy.doAddingText("add study", "add", "study");
		TextBuddy.doAddingText("add play tennis", "add", "play tennis");
		TextBuddy.doAddingText("add apple juice", "add", "apple juice");
		TextBuddy.doAddingText("add boy", "add", "boy");
		String result = "";
		result = "1.apple juice" + System.lineSeparator() + "2.boy" + System.lineSeparator() + "3.go fishing"
				+ System.lineSeparator() + "4.play tennis" + System.lineSeparator() + "5.study" + System.lineSeparator();
		assertEquals(result, TextBuddy.doSortingText());
		assertEquals(result, getFileContent("textFile.txt"));
		assertEquals(5, countFileLines("textFile.txt"));
	}

	@Test
	// This method tests doSearchingText() method
	public void testSearchMethod() throws IOException {
		TextBuddy.doClearingText();
		TextBuddy.doAddingText("add go fishing", "add", "go fishing");
		TextBuddy.doAddingText("add go studying", "add", "go studying");
		TextBuddy.doAddingText("add play tennis", "add", "play tennis");
		TextBuddy.doAddingText("add play soccer", "add", "play soccer");
		TextBuddy.doAddingText("add boy", "add", "boy");
		assertEquals(TextBuddy.storage.size(), 5);
		assertEquals(countFileLines("textFile.txt"), 5);
		String[] testCase1 = { "search", "go" };
		String resultCase1 = "1.go fishing" + System.lineSeparator() + "2.go studying" + System.lineSeparator();
		assertEquals(TextBuddy.doSearchingText(testCase1), resultCase1);
		String[] testCase2 = { "search", "play" };
		String resultCase2 = "1.play tennis" + System.lineSeparator() + "2.play soccer" + System.lineSeparator();
		assertEquals(TextBuddy.doSearchingText(testCase2), resultCase2);
		String[] testCase3 = { "search", "boy" };
		String resultCase3 = "1.boy" + System.lineSeparator();
		assertEquals(TextBuddy.doSearchingText(testCase3), resultCase3);
	}

	// This method counts the number of lines in the text file
	public int countFileLines(String fileName) throws IOException {
		FileReader r = new FileReader(fileName);
		BufferedReader br = new BufferedReader(r);
		int counter = 1;
		if (br.readLine() == null) {
			br.close();
			return 0;
		} else {
			while (br.readLine() != null) {
				counter++;
			}
		}
		br.close();
		return counter;
	}

	// This file get the content of the file and return the content as a string
	public String getFileContent(String fileName) throws IOException {
		FileReader r = new FileReader(fileName);
		BufferedReader br = new BufferedReader(r);
		String str;
		String text = "";
		str = br.readLine();
		while (str != null) {
			text = text + str + System.lineSeparator();
			str = br.readLine();
		}
		br.close();
		return text;
	}
}
