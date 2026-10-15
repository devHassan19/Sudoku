package com.ga.sudoku.service;

import com.ga.sudoku.SudokuSolver;
import com.ga.sudoku.exception.InvalidCharacterException;
import com.ga.sudoku.exception.SudokuFileNotFoundException;
import com.ga.sudoku.model.Sudoku;
import com.ga.sudoku.model.SudokuCell;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class SudokuService {

    private final String UPLOAD_DIR = "uploads/sudoku/";

    // To Read the Puzzle file
    public String uploadPuzzle(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please upload a puzzle file"
            );
        }

        String filePath = savePuzzleFile(file);
        String puzzleContent = readPuzzleFile(filePath);
        validatePuzzle(puzzleContent);
        System.out.println(puzzleContent);

        Sudoku sudoku = new Sudoku(puzzleContent);

        System.out.println("Original Sudoku:");
        sudoku.printBoard();

        SudokuSolver solver = new SudokuSolver();

        if (!solver.solvePuzzle(sudoku)) {
            return "No solution exists for this Sudoku puzzle";
        }

        System.out.println("Solved Sudoku:");
        sudoku.printBoard();

        saveSolution(sudoku);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            sudoku.printBoard();
        } finally {
            System.setOut(originalOut);
        }

        return "Puzzle solved and solution saved successfully\n" + buffer;

    }

    private String savePuzzleFile(MultipartFile file) {

        try {

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String originalFileName = file.getOriginalFilename();

            String extension = "";
            if (originalFileName != null &&
                    originalFileName.contains(".")) {
                extension = originalFileName.substring(
                        originalFileName.lastIndexOf(".")
                );
            }

            String fileName = UUID.randomUUID() + extension;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Puzzle file saved: " + filePath);
            return UPLOAD_DIR + fileName;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not save puzzle file", e);
        }
    }

    private String readPuzzleFile(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new SudokuFileNotFoundException(
                    "Sudoku file not found: " + filePath);
        }

        try {
            String content = Files.readString(path);
            for (char ch : content.toCharArray()) {
                if (!Character.isDigit(ch) && !Character.isWhitespace(ch) && ch != '|'
                        && ch != '-') {
                    throw new InvalidCharacterException(
                            "Invalid character in Sudoku file: " + ch);
                }
            }
            return content;

        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not read puzzle file", e);
        }
    }

    private void validatePuzzle(String content) {
        String numbersOnly = content.replaceAll("[\\s|\\-]", "");

        if (!numbersOnly.matches("[0-9]{81}")) {
            throw new IllegalArgumentException(
                    "Sudoku puzzle must contain exactly 81 digits"
            );
        }
    }

    private void saveSolution(Sudoku sudoku) {
        StringBuilder content = new StringBuilder();
        SudokuCell[][] board = sudoku.getBoard();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                content.append(board[row][col].getValue());

                if (col == 2 || col == 5) {
                    content.append(" | ");
                } else if (col < 8) {
                    content.append(" ");
                }
            }

            content.append(System.lineSeparator());

            if (row == 2 || row == 5) {
                content.append("----------------------------------")
                        .append(System.lineSeparator());
            }
        }

        try {
            Path solutionPath = Paths.get("uploads/sudoku/puzzle1.solution.txt");

            Files.createDirectories(solutionPath.getParent());
            Files.writeString(solutionPath, content.toString());

            System.out.println("Solution saved to: " + solutionPath.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Could not save Sudoku solution", e);
        }
    }

}
