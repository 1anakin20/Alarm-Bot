package com.ComputerSquad.helpers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class FileOperations {
	public static String readFile(String filePath) throws IOException {
		return Files.lines(Paths.get(filePath), StandardCharsets.UTF_8).collect(Collectors.joining("\n"));
	}

	public static String readFile(String filePath, Charset charset) throws IOException {
		return Files.lines(Paths.get(filePath), charset).collect(Collectors.joining("\n"));
	}
}
