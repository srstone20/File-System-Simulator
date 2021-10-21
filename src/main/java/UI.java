import java.util.Scanner;

public class UI {

    public static void main(String[] args) {
        Editor editor = new Editor("file.txt");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to my simple, yet wonderful, command-line text editor");
        System.out.println("p/n to move cursor left/right");
        System.out.println("type exit to terminate");
        System.out.println(editor.toString());
        String command = "";
        while (!command.equals("exit")) {
            command = scanner.next();
            if (command.equals("n")) {
                editor.moveCursorForward();
            } else if (command.equals("p")) {
                editor.moveCursorBackward();
            } else if (command.equals("d")) {
                int numChar = scanner.nextInt();
                editor.delete(numChar);
            } else if (command.equals("r")) {
                int numChar = scanner.nextInt();
                String replaceText = scanner.next();
                editor.replace(numChar, replaceText);
            } else if (command.equals("s")) {
                String key = scanner.next();
                editor.search(key);
            } else if (command.equals("i")) {
                String textToInsert = scanner.next();
                editor.insert(textToInsert);
            } else if (command.equals("undo")) {
                editor.undo();
            } else if (command.equals("redo")) {
                editor.redo();
            } else if (command.equals("exit")) {
                System.out.println("bye!");
                break;
            } else {
                command = "";
                System.out.println("?");
            }
            if (!command.equals("")) {
                System.out.println(editor.toString());
            }
        }
        scanner.close();
    }

}
