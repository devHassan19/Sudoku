# Sudoku Solver API

## Project Description

Sudoku Solver API is a Java Spring Boot application that allows users to upload a Sudoku puzzle as a `.txt` file using Postman. The application processes the uploaded file, cleans and formats the puzzle data, and solves the Sudoku puzzle automatically.

The API simplifies the solving process by accepting a text file instead of requiring users to enter the Sudoku grid manually.

## Technologies Used

- Java
- Spring Boot
- Spring Web
- Maven
- Postman
- Multipart File Upload

## Features

- Upload a Sudoku puzzle using a `.txt` file.
- Clean and process the uploaded file content.
- Extract and format the Sudoku grid.
- Solve the Sudoku puzzle automatically.
- Return the solution as an API response.

## How It Works

1. Create a Sudoku puzzle and save it in a `.txt` file.
2. Send the file to the API using Postman.
3. The application receives the file through a multipart form-data request.
4. The service processes and cleans the puzzle data.
5. The Sudoku solver calculates the solution.
6. The API returns the solved puzzle.

## API Endpoint

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/upload` | Upload a Sudoku text file and solve the puzzle |

## How to Test Using Postman

1. Run the Spring Boot application.
2. Open Postman and create a new request.
3. Select the `POST` method.
4. Enter the following URL:

   `http://localhost:8080/api/upload`

5. Navigate to the **Body** tab.
6. Select **form-data**.
7. Add a key named `file`.
8. Change the key type from **Text** to **File**.
9. Select your Sudoku `.txt` file.
10. Click **Send** to upload the puzzle and receive the result.

**Important:** The form-data key must be named `file` to match the controller's `@RequestParam MultipartFile file` parameter.

## Example Sudoku Input

Create a text file named `sudoku.txt` and enter the Sudoku puzzle in the following format:

```text
0  2  1  |  0  0  0  |  5  4  3
0  6  0  |  5  1  0  |  8  7  2
8  7  0  |  4  0  2  |  0  6  1
----------------------------------
0  0  4  |  2  8  3  |  0  1  0
0  0  0  |  6  4  5  |  3  0  9
6  0  8  |  9  7  1  |  0  0  5
----------------------------------
3  0  9  |  0  2  7  |  1  0  6
2  5  0  |  1  9  4  |  7  3  8
1  0  0  |  0  5  0  |  0  9  0
```

### Input Format

- The puzzle contains a 9 × 9 Sudoku grid.
- `0` represents an empty cell that needs to be solved.
- `|` separates the 3 × 3 sections of each row.
- Horizontal lines separate the 3 × 3 sections vertically.
- The application cleans and processes the text file before solving the puzzle.

### Upload the File Using Postman

1. Select `POST` as the HTTP method.
2. Enter `http://localhost:8080/api/upload`.
3. Open **Body → form-data**.
4. Set the key to `file` and select **File** as its type.
5. Upload `sudoku.txt` and click **Send**.

The API processes the uploaded puzzle and returns the result according to the implementation in `SudokuService`.

## Project Structure

```text
src/
└── main/
    └── java/
        └── com/
            └── ga/
                └── sudoku/
                    ├── cotroller/
                    │   └── SudokuContoller.java
                    └── service/
                        └── SudokuService.java
```

- **SudokuContoller:** Handles HTTP requests and receives the uploaded file.
- **SudokuService:** Processes the uploaded puzzle and handles the solving logic.

## Running the Application

Run the application using your IDE, or execute the following command from the project root:

```bash
./mvnw spring-boot:run
```

If your project does not contain the Maven Wrapper, use:

```bash
mvn spring-boot:run
```

## Future Improvements

- Validate the uploaded file format.
- Handle invalid or unsolvable Sudoku puzzles.
- Return the solution in a structured JSON response.
- Add unit tests for the Sudoku-solving algorithm.

## Author

Developed as a Java Spring Boot project to practice REST APIs, file uploads, text processing, and algorithm implementation.
