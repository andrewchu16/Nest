package server;

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

        // idk search for it
        // then add it to the response body.
    }

    public String getSearchQuery(URI uri) throws UnsupportedEncodingException {
        String query = uri.getQuery();
        String[] parameters = query.split("&");
        String searchString = parameters[0];
        int index = searchString.indexOf("=");
        return URLDecoder.decode(searchString.substring(index + 1), "UTF-8");
retu
    }
}
