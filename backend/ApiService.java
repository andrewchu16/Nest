import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ApiService {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public HttpResponse<String> searchForSponsors(String query) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api/sponsors/search?q=" + query))
                .GET()
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> login(String username, String password) throws Exception {
        // Assuming JSON payload for login
        String json = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> logout() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api/auth/logout"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> addSponsorToProject(String projectId, String sponsorId) throws Exception {
        String json = "{\"sponsorId\":\"" + sponsorId + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api/projects/" + projectId + "/sponsors"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> renameProject(String projectId, String newName) throws Exception {
        String json = "{\"newName\":\"" + newName + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api/projects/" + projectId + "/rename"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> createProject(String projectName) throws Exception {
        String json = "{\"projectName\":\"" + projectName + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api/projects"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> deleteProject(String projectId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api/projects/" + projectId))
                .DELETE()
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> removeSponsor(String sponsorId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api/sponsors/" + sponsorId))
                .DELETE()
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> viewPastSponsors() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/api/sponsors/past"))
                .GET()
                .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> getSponsorContactInfo(String sponsorId) throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://example.com/api/sponsors/" + sponsorId + "/contact"))
            .GET()
            .build();

        return httpClient.send(request, BodyHandlers.ofString());
    }
}