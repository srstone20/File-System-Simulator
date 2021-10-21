import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EditorTest {
    @Test
    public void testFileLoadingAndTextPrinting() {

        assertDoesNotThrow(() -> {
            new Editor("file.txt");
        });
        Editor ed = new Editor("file.txt");
        EditorState editorState = ed.getEditorState();

        // compare first few characters to ensure file was loaded properly
        assertEquals(editorState.getText().substring(0, 16), "To be successful");
        assertEquals(0, editorState.getCursorPosition());

        // ed.toString() prints the text with the underscore ( _ ) as
        // as a cursor.
        // Since cursor position is zero here, an underscore should be printed
        // at position zero

        assertEquals(ed.toString().subSequence(0, 17), "_To be successful");

    }

    @Test
    public void testCursorMovingCommands() {
        Editor ed = new Editor("file.txt");
        EditorState editorState;
        // move cursor one position to the right
        ed.moveCursorForward();
        editorState = ed.getEditorState();
        assertEquals(1, editorState.getCursorPosition());
        assertEquals(ed.toString().subSequence(0, 17), "T_o be successful");

        // one more position to the right
        ed.moveCursorForward();
        editorState = ed.getEditorState();
        assertEquals(2, editorState.getCursorPosition());
        assertEquals(ed.toString().subSequence(0, 17), "To_ be successful");

        // move cursor backward
        ed.moveCursorBackward();
        editorState = ed.getEditorState();
        assertEquals(1, editorState.getCursorPosition());
        assertEquals(ed.toString().subSequence(0, 17), "T_o be successful");

        // one more time
        ed.moveCursorBackward();
        editorState = ed.getEditorState();
        assertEquals(0, editorState.getCursorPosition());
        assertEquals(ed.toString().subSequence(0, 17), "_To be successful");

    }

    @Test
    public void testInsertCommand() {
        Editor ed = new Editor("file.txt");
        // let's insert something
        // note the position of the cursor after insertion
        ed.insert("Starting...");
        assertEquals(ed.toString().substring(0, 28), "Starting..._To be successful");

        // Let's push the cursor a bit forward and then insert

        ed = new Editor("file.txt");

        ed.moveCursorForward();
        ed.moveCursorForward();
        ed.insert("inserting...");
        assertEquals(ed.toString().substring(0, 18), "Toinserting..._ be");
    }

    @Test
    public void testDeleteCommand() {
        Editor ed = new Editor("file.txt");
        // let's delete 2 characters from the current position of the cursor
        // note the position of the cursor after deletion
        ed.delete(2);
        assertEquals(ed.toString().substring(0, 15), "_ be successful");

        // Let's push the cursor a bit forward and then delete

        ed = new Editor("file.txt");

        ed.moveCursorForward();
        ed.moveCursorForward();
        ed.moveCursorForward();
        ed.delete(2);
        assertEquals(ed.toString().substring(0, 15), "To _ successful");

    }

    @Test
    public void testSearchCommand() {
        Editor ed = new Editor("file.txt");
        // let's search for the word "you" from the current position of the cursor
        // note the position of the cursor after search
        ed.search("you");
        assertEquals("To be successful, _you", ed.toString().substring(0, 22));

        // Let's push the cursor past the first "you"

        ed.moveCursorForward();
        ed.moveCursorForward();
        ed.moveCursorForward();
        assertEquals("To be successful, you_", ed.toString().substring(0, 22));
        // We should now find the next "you"
        ed.search("you");
        assertTrue(ed.toString().contains("bit closer to _your"));

        // Let's search for something that does not exist.
        // the cursor should move to the end.

        ed.search("Something that does not exist");
        assertTrue(ed.toString().contains("these are not my words!_"));
    }

    @Test
    public void testReplaceCommand() {
        Editor ed = new Editor("file.txt");
        // let's replace the next two with some text
        ed.replace(2, "If you want to");
        assertTrue(ed.toString().startsWith("_If you want to be successful"));

        // let's move the cursor towards the end.
        // we'll do it by searching for a non-existing text
        // Provide a method to move the cursor n moves instead?

        ed.search("Something that does not exist");

        ed.replace(3, "some more text");
        assertTrue(ed.toString().endsWith("these are not my words!_some more text"));

    }

    @Test
    public void testUndoRedoCommands() {
        Editor ed = new Editor("file.txt");
        ed.delete(2);
        ed.undo();
        // we should be back to the original text
        assertTrue(ed.toString().startsWith("_To be successful"));

        // let's redo the last change
        ed.redo();
        assertTrue(ed.toString().startsWith("_ be successful"));

        // undo it again
        ed.undo();
        assertTrue(ed.toString().startsWith("_To be successful"));

        // undo again, should make no effect

        ed.undo();
        assertTrue(ed.toString().startsWith("_To be successful"));

        // Let's make another change
        ed.replace(2, "In order to");
        assertTrue(ed.toString().startsWith("_In order to be successful"));

        // since we made a new change,
        // redo should not make any effect
        // (i.e., we should empty the redo stack)
        ed.redo();
        assertTrue(ed.toString().startsWith("_In order to be successful"));

        // undo again, we should be back to the original text

        ed.undo();
        assertTrue(ed.toString().startsWith("_To be successful"));

        // let's make two changes

        ed.delete(2);
        ed.insert("In order to");

        assertTrue(ed.toString().startsWith("In order to_ be successful"));

        // undo, undo --> back to original
        ed.undo();
        ed.undo();
        assertTrue(ed.toString().startsWith("_To be successful"));

        // redo one move

        ed.redo();
        assertTrue(ed.toString().startsWith("_ be successful"));

        // redo again
        ed.redo();
        assertTrue(ed.toString().startsWith("In order to_ be successful"));

    }

}
