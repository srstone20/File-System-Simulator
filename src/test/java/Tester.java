
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tester {
    public static void main(String[] args) throws Exception{
        int cursorPos = 425;
        String s = new String(Files.readAllBytes(Paths.get("file.txt"))); 
        String before;
        String after;


        if (cursorPos > 1 && (s.charAt(cursorPos - 1) == '\n' || s.charAt(cursorPos - 1) == '\r')) {
            before = s.substring(0, cursorPos - 1);
            after = s.substring(cursorPos, s.length());    
        }
        else {
            before = s.substring(0, cursorPos);
            after = s.substring(cursorPos, s.length());
        }

        System.out.println(before + '_' + after);
    }
}
