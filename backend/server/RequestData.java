package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This stores the request data sent by an HTTP request.
 */
public class RequestData {
    private String method;
    private String body;
    private URI uri;
    private boolean isJson;
    private HttpExchange exchange;
    private boolean responseSent;

    /**
     * This constructs a new RequestData object from an HttpExchange object.
     * @param exchange The exchange created by the API route.
     * @throws IOException if an exception occurs while reading the request body.
     */
    public RequestData(HttpExchange exchange) throws IOException {
        this.exchange = exchange;
        this.method = exchange.getRequestMethod();
        this.uri = exchange.getRequestURI();
        this.responseSent = false;

        // Get the request body as a string.
        BufferedReader bodyReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        this.body = "";
        String line = "";
        do {
            this.body += line + "\n";
            line = bodyReader.readLine();
        } while(line != null);


        // Determine whether this request is a JSON or not.
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        this.isJson = (contentType != null && contentType.equals("application/json"));
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    /**
     * This sends the response back to the client.
     * @param responseCode The response code to send to the client.
     * @param response
     */
    public void sendResponse(int responseCode, byte[] response) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            System.out.println("Sending response -- " + dateFormat.format(date));
            this.exchange.sendResponseHeaders(responseCode, response.length);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.responseSent = true;
    }

    /**
     * Gets the HTTP response headers so you can edit them.
     * @return An editable HTTP response header object.
     */
    public Headers getResponseHeaders() {
        return this.exchange.getResponseHeaders();
    }


    public URI getUri() {
        return uri;
    }
}
