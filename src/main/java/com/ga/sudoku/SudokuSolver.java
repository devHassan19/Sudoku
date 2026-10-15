package com.ga.sudoku;

import com.ga.sudoku.model.Sudoku;
import com.ga.sudoku.model.SudokuCell;

public class SudokuSolver {

    public boolean solvePuzzle(Sudoku sudoku) {
        SudokuCell[][] board = sudoku.getBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col].getValue() == 0) {
                    for (int number = 1; number <= 9; number++) {
                        if (isValid(board, row, col, number)) {
                            board[row][col].setValue(number);
                            if (solvePuzzle(sudoku)) {
                                return true;
                            }
                            board[row][col].setValue(0);
                        }
                    }
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isValid(
            SudokuCell[][] board, int row, int col, int number) {

        for (int c = 0; c < 9; c++) {
            if (board[row][c].getValue() == number) {
                return false;
            }
        }

        for (int r = 0; r < 9; r++) {
            if (board[r][col].getValue() == number) {
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board[r][c].getValue() == number) {
                    return false;
                }
            }
        }

        return true;
    }
}