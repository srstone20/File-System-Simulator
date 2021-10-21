import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EditorStateTest {
    @Test
    public void testEditorStateConstruction() {
        // construction
        EditorState editorState = new EditorState();
        assertEquals("", editorState.getText());
        assertEquals(0, editorState.getCursorPosition());
        editorState = new EditorState("This is a sample text", 0);
        assertEquals("This is a sample text", editorState.getText());
        assertEquals(0, editorState.getCursorPosition());

    }

    @Test
    public void testSettingAndMovingCursor() {
        EditorState editorState = new EditorState("This is a sample text", 0);

        // set the cursor to a correct value
        editorState.setCursorPosition(5);
        assertEquals(5, editorState.getCursorPosition());

        // set the cursor to an incorrect value --> cursor at last char
        editorState.setCursorPosition(1000);
        assertEquals(editorState.getText().length() - 1, editorState.getCursorPosition());

        // move the cursor right (forward) --> already at the end of the text
        editorState.moveCursorForward();
        int newPosition = editorState.getText().length() - 1;
        assertEquals(newPosition, editorState.getCursorPosition());

        // move the cursor left (backward) --> position--
        editorState.moveCursorBackward();
        newPosition = editorState.getText().length() - 2;
        assertEquals(newPosition, editorState.getCursorPosition());
        // one more time
        editorState.moveCursorBackward();
        newPosition = editorState.getText().length() - 3;
        assertEquals(newPosition, editorState.getCursorPosition());

        // forward again.
        editorState.moveCursorForward();
        newPosition = editorState.getText().length() - 2;
        assertEquals(newPosition, editorState.getCursorPosition());

        // forward again.
        editorState.moveCursorForward();
        newPosition = editorState.getText().length() - 1;
        assertEquals(newPosition, editorState.getCursorPosition());

    }

    @Test
    public void testEditorStateClone() {
        // we are going to be pushing copies of states into a stack
        // So, we need to make sure we are doing deep copying.
        EditorState state = new EditorState("test string", 0);
        EditorState clonedState = state.clone();

        // equal fields...
        assertEquals(state.getText(), clonedState.getText());
        assertEquals(state.getCursorPosition(), clonedState.getCursorPosition());
        // but not same object...
        assertNotSame(state.getText(), clonedState.getText());
    }

}
