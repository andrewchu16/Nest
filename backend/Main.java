import server.InvalidHttpMethodException;
import server.Server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static final char QUIT_KEY = 'q';
    public static void main(String[] args) throws IOException, InvalidHttpMethodException {
        System.out.println("Initializing server");
        Server server = new Server();

        server.setGetMethod("/search", new SearchMethodHandler());

        server.run();

        System.out.println("Press '" + QUIT_KEY + "' to stop server.");
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        int chr;
        do {
            chr = keyboard.read();
        } while (chr != -1 && chr != QUIT_KEY);

        keyboard.close();
        server.stop();
    }
}