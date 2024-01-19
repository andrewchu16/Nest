package server;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;

/**
 * This stores the request data sent by an HTTP request.
 */
public class RequestData {
    private String method;
    private String body;
    private URI uri;
    private boolean isJson;
    private HttpExchange exchange;

    /**
     * This constructs a new RequestData object from an HttpExchange object.
     * @param exchange The exchange created by the API route.
     * @throws IOException if an exception occurs while reading the request body.
     */
    public RequestData(HttpExchange exchange) throws IOException {
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
     * Gets a file output stream that can be written to to write the repsonse body.
     * @return The response body output stream.
     */
    public OutputStream getResponseBodyStream() {
        return this.exchange.getResponseBody();
    }

    /**
     * Gets the HTTP response headers so you can edit them.
     * @return An editable HTTP response header object.
     */
    public Headers getResponseHeaders() {
        return this.exchange.getResponseHeaders();
    }


    /**
     * This returns the request body converted into JSON.
     * @return JSON if the request body can be converted, null otherwise.
     */
    public JsonObject getJson() {
        if (!this.isJson) {
            return null;
        }

        JsonElement element = JsonParser.parseString(this.body);
        JsonObject json = element.getAsJsonObject();
        return json;
    }


    public URI getUri() {
        return uri;
    }
}
