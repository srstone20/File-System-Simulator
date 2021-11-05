import java.io.IOException;
import java.nio.file.*;
import java.util.Stack;

class Editor {
    private EditorState edState = new EditorState();
    private Stack<EditorState> undoStack;
    private Stack<EditorState> redoStack;

    Editor(String file) {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(file)));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        text.trim();
        edState.setText(text);
        undoStack = new Stack<EditorState>();
        redoStack = new Stack<EditorState>();
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
        // System.out.println("THE TEXT: " + edState.getText());
        // System.out.println("THE CURSOR POSITION: " + edState.getCursorPosition());
        // System.out.println("THE TEXT LENGTH: " + edState.getText().length());

        String before = edState.getText().substring(0, edState.getCursorPosition());
        String after = edState.getText().substring(edState.getCursorPosition(), edState.getText().length());

        // The strings before and after print perfectly fine by themselves, but when combined into one string the cursor is all messed up\
        // this only happens when the cursor is on a newline character
        // System.out.println("THE STRING BEFORE: " + before);
        // System.out.println("THE STRING AFTER: " + after);

        // StringBuilder sb = new StringBuilder();
        // sb.append(before);
        // sb.append("_");
        // sb.append(after);

        // System.out.println("STRINGBUILDER: " + sb.toString());

        return before + "_" + after;

        // return stringBefore + "_" + stringAfter;
        // String before = "";
        // String after = "";

        // if (edState.getCursorPosition() > 1 && (edState.getText().charAt(edState.getCursorPosition() - 1) == '\n' || edState.getText().charAt(edState.getCursorPosition() - 1) == '\r')) {
        //     before = edState.getText().substring(0, edState.getCursorPosition() - 1);
        //     after = edState.getText().substring(edState.getCursorPosition(), edState.getText().length());    
        // }
        // else {
        //     before = edState.getText().substring(0, edState.getCursorPosition());
        //     after = edState.getText().substring(edState.getCursorPosition(), edState.getText().length());
        // }
    }

}
