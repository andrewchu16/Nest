import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import server.MethodHandler;
import server.RequestData;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;

public class SearchMethodHandler implements MethodHandler {
    /**
     *
     * @param data The data sent by the request.
     */
    @Override
    public void handleMethod(RequestData data) {
        URI uri = data.getUri();
        String searchQuery = "";

        try {
            searchQuery = this.getSearchQuery(uri);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Can't get search query.");
        }

        // idk how you get the sql connection
        // then add it to the response body.
//        Organization resultOrganization = Organization.searchOrganization(searchQuery, sqlConnection);
        Organization resultOrganization = null;
        if (resultOrganization == null) {
            // send a 402 or 406 response idk https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#client_error_responses
            data.sendResponse(402, 0);

        } else {
            // set the headers
            Headers headers = data.getResponseHeaders();
            headers.add("Content-Type", "text/plain");


            byte[] response = resultOrganization.getName().getBytes();

            try {
                // then add it to the response body.
                OutputStream responseStream = data.getResponseBodyStream();

                responseStream.write(response);
                responseStream.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public String getSearchQuery(URI uri) throws UnsupportedEncodingException {
        String query = uri.getQuery();
        String[] parameters = query.split("&");
        String searchString = parameters[0];
        int index = searchString.indexOf("=");
        return URLDecoder.decode(searchString.substring(index + 1), "UTF-8");
    }
}
