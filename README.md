[![Points badge](../../blob/badges/.github/badges/points.svg)](../../actions)

# Project 1: Simple command-line text editor

## Project Outcomes:

- Develop a basic command-line text editor with Undo/Redo features.

## Preparatory Readings:

- ZyBook Unit 4

## This project

In this project, you are going to develop a basic command-line text editor where the user enters commands to edit the text. An example of real-life text editor of this type is **vi**.

The text editor will read the input text from a file. Your program will then ask the user for commands to edit the text. Our text editor supports the following commands:

1. `d n` delete the next n characters.
1. `r n new_text` replace the next n characters with `new_text`
1. `s key` search for the first occurrence of `key` in the text. This commands moves the cursor to the first character of `key`, or to the end of the text if `key` is not found in the text.
1. `i text` insert text.
1. `undo` undo the effect of the last command.
1. `redo` redo the last undone command.

Your program should support undoing/redoing an arbitrary number of commands. Please refer to the test suit to find out the exact behavior of these commands.

### Project Requirements:

You are provided with a test suite in folder [src/test/java](src/test/java).
Your implementation must pass all tests provided in this test suit.
The test suite is broken into a number of sets for grading purposes as well as to help you with incremental development.

1. Individual tests can be run as follows:
   1. gradle test_editor_state_construction
   1. gradle test_editor_state_cursor_move
   1. gradle test_editor_state_clone
   1. gradle test_editor_print
   1. gradle test_editor_cursor_move_commands
   1. gradle test_editor_insert_commands
   1. gradle test_editor_delete_commands
   1. gradle test_editor_search_commands
   1. gradle test_editor_replace_commands
   1. gradle test_editor_undo_redo_commands
1. Running all of these tests must be possible by running `gradle test`.

### Input format

The file [file.txt](file.txt) contains the text that our editor will be editing.

### Submission Requirements:

1. All code must be added and committed to your local git repository.
1. All code must be pushed to the GitHub repository created when you "accepted" the assignment.
   1. After pushing, with `git push origin main`, visit the web URL of your repository to verify that your code is there.
      If you don't see the code there, then we can't see it either.
1. Your code must compile and run.
   The auto-grading tests will indicate your score for your submission.
   1. The auto-grading build should begin automatically when you push your code to GitHub.
   1. If your program will not compile, the graders will not be responsible for trying to test it.
   1. You should get an email regarding the status of your build, if it does not pass, keep trying.
1. Important: this is a 2.5-weeks assignment. It's required that you pass at least **30%** of the test cases every week.

## Important Notes:

- Projects will be graded on whether they correctly solve the problem, and whether they adhere to good programming practices.
- Projects must be received by the time specified on the due date. Projects received after that time will get a grade of zero.
- Please review the academic honesty policy.
  - Note that viewing another student's solution, whether in whole or in part, is considered academic dishonesty.
  - Also note that submitting code obtained through the Internet or other sources, whether in whole or in part, is considered academic dishonesty.
  - All programs submitted will be reviewed for evidence of academic dishonesty, and all violations will be handled accordingly.

## Grading

- View on GitHub:
  1.  On your GitHub repo page, click the :arrow_forward: **Actions** tab to see your graded results.
  1.  If it isn't a green check mark (:heavy_check_mark:) then at least part of the testing failed.
  1.  Click the commit message for the failing version then click "Autograding" on the left side of the page.
  1.  Follow the :x: path and expand things to see what errors exist.
  1.  At the bottom of the _education/autograding_ section, you can view the score for the auto-grading portion of the rubric.
      It will look something like **_90/100_**.
