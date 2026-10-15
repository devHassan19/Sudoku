package com.ga.sudoku.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Sudoku {

    private SudokuCell[][] board;

    public Sudoku(String content) {
        board = new SudokuCell[9][9];

        String numbersOnly = content.replaceAll("[\\s|\\-]", "");
        for (int i = 0; i < 81; i++) {
            int row = i / 9;
            int col = i % 9;

            int value = Character.getNumericValue(numbersOnly.charAt(i));
            board[row][col] = new SudokuCell(value);
        }
    }

    public void printBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col].getValue() + " ");

                if (col == 2 || col == 5) {
                    System.out.print("| ");
                }
            }

            System.out.println();

            if (row == 2 || row == 5) {
                System.out.println("---------------------");
            }
        }
    }
}