package com.ComputerSquad.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Token {
	private static final String TOKENPATH = "user/configuration";

	public static String readToken() {
		File file = null;
		try {
			file = new File(TOKENPATH);
		} catch (NullPointerException e) {
			System.out.println("Please create a file named 'TOKEN' in the user folder. " +
					"If it doesn't exist, create it at the same location than the program");
			System.exit(1);
		}

		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Please create a file named 'TOKEN' in the user folder. " +
					"If it doesn't exist, create it at the same location than the program");
			System.exit(1);
		}

		// Count the lines. The configuration should be only in one line
		int lines = 0;
		String data = null;
		while (scanner.hasNextLine()) {
			data = scanner.nextLine();
			lines++;
		}

		if (lines > 1) {
			System.out.println("Error reading configuration. The configuration should be only in one line");
			System.exit(1);
		} else if (lines == 0) {
			System.out.println("The files doesn't have any lines");
		}

		return data;
	}
}
