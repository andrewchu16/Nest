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
public class Request {
    private String method;
    private String body;
    private URI uri;
    private HttpExchange exchange;

    /**
     * This constructs a new RequestData object from an HttpExchange object.
     * 
     * @param exchange The exchange created by the API route.
     * @throws IOException if an exception occurs while reading the request body.
     */
    public Request(HttpExchange exchange) throws IOException {
        this.exchange = exchange;
        this.method = exchange.getRequestMethod();
        this.uri = exchange.getRequestURI();

        // Get the request body as a string.
        BufferedReader bodyReader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        this.body = "";
        String line = "";
        do {
            this.body += line + "\n";
            line = bodyReader.readLine();
        } while (line != null);
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    /**
     * This sends the response back to the client.
     * 
     * @param responseCode The response code to send to the client.
     * @param response     The response in bytes to send. If it is null, it sends no response body.
     */
    public void sendResponse(int responseCode, byte[] response) {
        try {
            // Output the timestamp of the response.
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            System.out.println("Sending response " + responseCode + " -- " + dateFormat.format(date));

            // Send -1 if no response is provided.
            if (response == null) {
                this.exchange.sendResponseHeaders(responseCode, -1);
                return;
            }

            this.exchange.sendResponseHeaders(responseCode, response.length);

            // Write the response body.
            OutputStream responseStream = this.exchange.getResponseBody();
            responseStream.write(response);
            responseStream.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Gets the HTTP response headers so you can edit them.
     * 
     * @return An editable HTTP response header object.
     */
    public Headers getResponseHeaders() {
        return this.exchange.getResponseHeaders();
    }

    public URI getUri() {
        return uri;
    }
}
