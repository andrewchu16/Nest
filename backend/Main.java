import com.sun.net.httpserver.Headers;
import server.InvalidHttpMethodException;
import server.MethodHandler;
import server.Request;
import server.Server;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static final char QUIT_KEY = 'q';
    public static void main(String[] args) throws IOException, InvalidHttpMethodException {
        System.out.println("Initializing server");
        Server server = new Server();

//        server.setGetMethod("/search", new SearchMethodHandler());
        // Proof the server works.
        server.setGetMethod("/", new MethodHandler() {
            @Override
            public void handleMethod(Request data) {
                Headers headers = data.getResponseHeaders();
                headers.add("Content-Type", "text/plain");

                byte[] response = "Hello World".getBytes();
                data.sendResponse(200, response);
            }
        });

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