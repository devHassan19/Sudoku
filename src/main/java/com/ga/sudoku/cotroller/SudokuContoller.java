package com.ga.sudoku.cotroller;

import com.ga.sudoku.service.SudokuService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SudokuContoller {

    private final SudokuService sudokuService;

    @PostMapping("/upload")
    public String uploadPuzzle(@RequestParam MultipartFile file)
            throws IOException {
        return sudokuService.uploadPuzzle(file);
    }


}