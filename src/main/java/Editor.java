import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

class Editor {
    private EditorState edState = new EditorState();
    private Stack<EditorState> undoStack;
    private Stack<EditorState> redoStack;

    Editor(File file) {
        try (Scanner sc = new Scanner(file)){
            sc.useDelimiter("\\Z");
            edState.setText(sc.next());
        }
        catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        }
        undoStack = new Stack<EditorState>();
        redoStack = new Stack<EditorState>();
    }

    Editor(String file) {
        this(new File(file));
    }

    EditorState getEditorState() {
        return edState;
    }

    void moveCursorForward() {
        edState.moveCursorForward();
    }

    void moveCursorBackward() {
        edState.moveCursorBackward();
    }

    private void newCommand() {
        undoStack.push(edState.clone());
        redoStack.clear();
    }

    void delete(int numChars) {
        newCommand();
        if (edState.getCursorPosition() + numChars > edState.getText().length()) {
            edState.setText(edState.getText().substring(0, edState.getCursorPosition()));
        }
        else  {
            String stringBefore = edState.getText().substring(0, edState.getCursorPosition());
            String stringAfter = edState.getText().substring(edState.getCursorPosition() + numChars, edState.getText().length());
            edState.setText(stringBefore + stringAfter);
        }
    }

    void replace(int numChars, String insertedText) {
        newCommand();
        delete(numChars);
        if (edState.getCursorPosition() + numChars > edState.getText().length()) {
            edState.setText(edState.getText().substring(0, edState.getCursorPosition()) + insertedText);
        }
        else {
            String stringBefore = edState.getText().substring(0, edState.getCursorPosition());
            String stringAfter = edState.getText().substring(edState.getCursorPosition(), edState.getText().length());
            edState.setText(stringBefore + insertedText + stringAfter);
        }
    }

    void insert(String insertedText) {
        newCommand();
        String stringBefore = edState.getText().substring(0, edState.getCursorPosition());
        String stringAfter = edState.getText().substring(edState.getCursorPosition(), edState.getText().length());
        edState.setText(stringBefore + insertedText + stringAfter);
        // update cursorPos to be after the inserted text
        edState.setCursorPosition(stringBefore.length() + insertedText.length());
    }

    boolean search(String key) {
        // create copy of text starting at cursorPos to search from
        String searchedString = edState.getText().substring(edState.getCursorPosition(), edState.getText().length());
        if (searchedString.contains(key)) {
            edState.setCursorPosition(searchedString.indexOf(key) + edState.getCursorPosition());
            return true;
        }
        edState.setCursorPosition(edState.getText().length());
        return false;
    }


    void undo() {
        if (!undoStack.empty()) {
            redoStack.push(edState.clone());
            edState = undoStack.pop();
        }
    }

    void redo() {
        if (!redoStack.empty()) {
            undoStack.push(edState.clone());
            edState = redoStack.pop();
        }
    }

    public String toString() {
        String stringBefore = edState.getText().substring(0, edState.getCursorPosition());
        String stringAfter = edState.getText().substring(edState.getCursorPosition(), edState.getText().length());
        return stringBefore + "_" + stringAfter;
    }

}
