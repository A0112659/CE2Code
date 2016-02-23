import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is used for a user to do many operations. A user can add one line
 * comment into a given text file. A user can delete one entry by providing the
 * specified index in the text file. A user can display all the entries which
 * are added before. A user can clear all the entries which are added before. A
 * user can exit this application. This program will be saved after entering
 * each user operation into text file. This program will also handle all the
 * cases which contain invalid inputs
 * 
 * @author Guo Xiang A0112659A
 */

public class TextBuddy {
	// for checking no argument of the input
	private static final int NO_ARGUMENTS_SIZE = 0;
	private static final String MESSAGE_NO_ARGUMENT = "";
	// for checking invalid input
	private static final int EXIT_WITH_INVALID_INPUT = 1;
	private static final int EXIT_WITHOUT_INVALID_INPUT = 0;
	// for checking invalid delete function
	private static final int EMPTY_STORAGE_SIZE = 0;
	// for printing out the searching function
	private static final String MESSAGE_WITHOUT_INFORMATION = "";

	public static ArrayList<String> storage = new ArrayList<String>();
	public static Scanner sc = new Scanner(System.in);
	private static String fileName = "textFile.txt";

	// messages for commenting, validation and checking errors
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ADD = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETE = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_EMPTY = "%1$s is empty";
	private static final String MESSAGE_INVALID = "Invalid command!";
	private static final String MESSAGE_SORT = "%1$s is sorted:";
	private static final String MESSAGE_WORD_NOT_FOUND = " \"%1$s\" is not found in %2$s";
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_SORT = "sort";
	private static final String COMMAND_SEARCH = "search";

	public static void main(String args[]) throws IOException {
		exitIfIncorrectArguments(args);
		setInitialization(args);
		printWelcomeMessage();
		executeCommandUntilExitCommand();
	}

	private static void exitIfIncorrectArguments(String[] args) throws IOException {
		exitIfNoArguments(args);

	}

	// check whether the input contains argument
	private static void exitIfNoArguments(String[] args) throws IOException {
		if (args.length == NO_ARGUMENTS_SIZE) {
			stopWithErrorMessage("No arguments");
		}
	}

	private static void stopWithErrorMessage(String message) {
		System.out.println(message);
		System.exit(EXIT_WITH_INVALID_INPUT);

	}

	private static void setInitialization(String[] args) throws IOException {
		fileName = args[0];
	}

	public static void printWelcomeMessage() {
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
	}

	public static void executeCommandUntilExitCommand() throws IOException {
		getCommand();
	}

	public static void getCommand() throws IOException {
		while (true) {
			executeCommand();
		}
	}

	/*
	 * This method analyzes user's command and do the corresponding operation to
	 * the text file.
	 */
	public static void executeCommand() throws IOException {
		System.out.print("command: ");
		String command = sc.nextLine();
		String[] content = command.split(" ");
		// get the first part of the command after splitting
		String operation = command.trim().split("\\s+")[0];
		// get the second part of the command after splitting
		String theRestText = command.replace(operation, "").trim();
		// pass the command to test
		isCommandValid(operation, theRestText);

		if (operation.equals(COMMAND_ADD)) {
			doAddingText(command, operation, theRestText);
		}

		else if (operation.equals(COMMAND_DELETE)) {
			doDeletingText(command, operation, theRestText);
		}

		else if (operation.equals(COMMAND_DISPLAY)) {
			doDisplayingText();
		}

		else if (operation.equals(COMMAND_CLEAR)) {
			doClearingText();
		}

		else if (operation.equals(COMMAND_EXIT)) {
			doExitingText();
		}

		else if (operation.equals(COMMAND_SORT)) {
			doSortingText();
		}

		else if (operation.equals(COMMAND_SEARCH)) {
			doSearchingText(content);
		} else {
			printInvalidCommand();
		}
	}

	public static String doAddingText(String command, String operation, String theRestText) throws IOException {
		// check whether the add function contains content
		if (theRestText.equals(MESSAGE_NO_ARGUMENT)) {
		} else {
			storage.add(theRestText);
			writeToFile();
			return printAddedMessage(theRestText);
		}
		return printInvalidCommand();
	}

	public static String doDeletingText(String command, String operation, String theRestText) throws IOException {
		// check whether the delete function contains content
		if (theRestText.equals(MESSAGE_NO_ARGUMENT)) {
			return printInvalidCommand();
		} else {
			Integer number = Integer.valueOf(theRestText);
			// check whether the delete function is with the correct index
			if (number > storage.size() || number <= EMPTY_STORAGE_SIZE) {
				return printInvalidCommand();
			} else {
				String content = storage.remove(number - 1);
				writeToFile();
				return printDeletedMessage(content);
			}
		}
	}

	public static String doDisplayingText() throws IOException {
		if (storage.size() == EMPTY_STORAGE_SIZE) {
		} else {

			return printDisplayMessage();
		}

		return printEmptyMessage();
	}

	public static String doClearingText() throws IOException {
		storage.clear();
		writeToFile();
		return printClearMessage();
	}

	public static void doExitingText() throws IOException {
		sc.close();
		System.exit(EXIT_WITHOUT_INVALID_INPUT);
	}

	// this method is used for sorting lines of information alphabetically
	public static String doSortingText() throws IOException {
		Collections.sort(storage);
		writeToFile();
		printSortedMessage();
		return doDisplayingText();
	}

	// This method is used to o search for a word and return the lines
	// containing that word.
	public static String doSearchingText(String[] commandWords) {
		ArrayList<String> foundContent = new ArrayList<String>();

		if (storage.size() == EMPTY_STORAGE_SIZE) {
		} else {
			String target = commandWords[1];
			for (String text : storage) {
				String[] contentWords = text.split(" ");
				for (String contentWord : contentWords) {
					if (contentWord.equalsIgnoreCase(target)) {
						foundContent.add(text);
					}
				}
			}
			return displaySearchingResult(foundContent, target);
		}
		return printEmptyMessage();

	}

	public static String displaySearchingResult(ArrayList<String> foundSentences, String target) {
		//// check whether the searching component can be found or not
		if (foundSentences.isEmpty()) {
			String printedContent = String.format(MESSAGE_WORD_NOT_FOUND, target, fileName);
			System.out.println(printedContent);
			return printedContent;
		} else {
			return showTheDetails(foundSentences);
		}
	}

	// This method is used to print all the contents after searching
	public static String showTheDetails(ArrayList<String> messages) {
		int index = 1;
		String printedMessage = MESSAGE_WITHOUT_INFORMATION;
		for (String message : messages) {
			printedMessage = printSearchingMessage(index, message, printedMessage);
			index++;
		}
		return printedMessage;
	}

	public static String printAddedMessage(String content) {
		System.out.println(String.format(MESSAGE_ADD, fileName, content));
		return String.format(MESSAGE_ADD, fileName, content);
	}

	public static String printDeletedMessage(String content) {
		System.out.println(String.format(MESSAGE_DELETE, fileName, content));
		return String.format(MESSAGE_DELETE, fileName, content);
	}

	public static String printEmptyMessage() {
		String output = String.format(MESSAGE_EMPTY, fileName);
		System.out.println(output);
		return output;
	}

	public static String printDisplayMessage() {
		String output = "";
		for (int i = 0; i < storage.size(); i++) {
			int number = i + 1;
		    System.out.println(number + "." + storage.get(i));
		    output += number + "." + storage.get(i) + System.lineSeparator();
		}	
		return output;
	}

	public static String printClearMessage() {
		System.out.println(String.format(MESSAGE_CLEAR, fileName));
		return String.format(MESSAGE_CLEAR, fileName);
	}

	public static String printInvalidCommand() {
		System.out.println(MESSAGE_INVALID);
		return MESSAGE_INVALID;
	}

	public static void printSortedMessage() {
		System.out.println(String.format(MESSAGE_SORT, fileName));
	}

	public static String printSearchingMessage(int index, String message, String printedMessage) {
		String sentence = index + "." + message;
		System.out.println(sentence);
		return printedMessage + sentence + System.lineSeparator();
	}

	/*
	 * This function is used to write every operation which is valid to the text
	 * file. The file will be saved after every valid insertion
	 */
	public static void writeToFile() throws IOException {
		try {
			File myFile = new File(fileName);
			FileWriter fw = new FileWriter(myFile);
			int num = 1;

			for (String sentence : storage) {
				fw.write(num + ".");
				fw.write(sentence + System.lineSeparator());
				num++;
			}
			fw.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean isCommandValid(String operation, String theRestText) {
		if (operation.equals("exit") || operation.equals("display") || operation.equals("clear")) {
			return true;
		} else if (operation.equals("add")) {
			if (!theRestText.trim().equals("")) {
				return true;
			}
		}
		return false;
	}

}
