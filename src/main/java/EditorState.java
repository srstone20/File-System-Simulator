public class EditorState implements Cloneable {
    private String text;
    private int cursorPos;
    // private Stack<String> undoStack;

    EditorState(String text, int cursorPos) {
        this.text = text;
        this.cursorPos = cursorPos; // represents the index that the cursor will be placed before
    }

    EditorState (String text) {
        this(text, 0);
    }

    EditorState() {
        this("", 0);
    }

    public int getCursorPosition() {
        return cursorPos;
    }

    public String getText() {
        return text;
    }

    public void setText(String newText) {
        text = newText;
    }

    public void setCursorPosition(int pos) {
        if (pos > text.length()) cursorPos = text.length() - 1;
        else cursorPos = pos;
    }
    
    public void moveCursorForward() {
        System.out.println("cursorpos: " + cursorPos + " length: " + text.length());
        if (cursorPos < text.length() - 2) cursorPos++;
    }

    public void moveCursorBackward() {
        if (cursorPos > 0) cursorPos--;
    }

    public EditorState clone () {
        String cloneText = new String(this.text);
        int cloneCursorPos = this.cursorPos;
        return new EditorState(cloneText, cloneCursorPos);
    }

}
