package server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Server class
 */
public class Server {
    public static String DEFAULT_ADDRESS = "localhost";
    public static int DEFAULT_PORT = 8080;

    private String address;
    private int port;
    private HttpServer server;
    private TreeMap<String, HttpContext> routeContexts;

    /**
     * This creates a new Server at the default address and port.
     * Default address: localhost
     * Default port: 8080
     * @throws IOException if the server cannot be created at the address and port.
     */
    public Server() throws IOException {
        this(DEFAULT_ADDRESS, DEFAULT_PORT);
    }

    /**
     * This creates a new Server at the given address and port.
     * @param address The address the server will run on.
     * @param port The port the server will run on.
     * @throws IOException if the server cannot be created at the given address and port.
     */
    public Server(String address, int port) throws IOException {
        // hostname, port, backlogging port
        this.address = address;
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(this.address, this.port), 0);

        this.routeContexts = new TreeMap<String, HttpContext>();
    }

    /**
     * This starts the server at the set address and port.
     */
    public void run() {
        System.out.println("Serving at http://" + this.getAddress() + ":" + this.getPort());
        this.server.start();
    }

    /**
     * This stops the server right away.
     */
    public void stop() {
        System.out.println("Stopping server");
        this.server.stop(0);
    }

    /**
     * This sets the handler for the given API path and HTTP method.
     * @param path The API path e.g. "/login"
     * @param method The HTTP method to handle.
     * @param methodHandler The handler for when the request is made with the specified method.
     * @return The http context for this path.
     * @throws  InvalidHttpMethodException If the HTTP method is invalid.
     */
    private HttpContext setMethod(String path, String method, MethodHandler methodHandler) throws InvalidHttpMethodException {
        if (this.routeContexts.containsKey(path)) {
            // Add method handler to existing route handler.
            HttpContext context = this.routeContexts.get(path);
            RouteHandler routeHandler = (RouteHandler) context.getHandler();
            routeHandler.setHandler(method, methodHandler);
            return context;
        } else {
            // Create a new route handler.
            RouteHandler routeHandler = new RouteHandler();
            routeHandler.setHandler(method, methodHandler);
            HttpContext context = this.server.createContext(path, routeHandler);
            return context;
        }
    }

    /**
     * This sets the GET method for the given API path.
     * @param path The API path e.g. "/login"
     * @param methodHandler The handler for when a GET request is made.
     * @return The http context for this path.
     */
    public HttpContext setGetMethod(String path, MethodHandler methodHandler) throws InvalidHttpMethodException {
        return this.setMethod(path, RouteHandler.GET_METHOD, methodHandler);
    }

    /**
     * This sets the POST method for the given API path.
     * @param path The API path e.g. "/login"
     * @param methodHandler The handler for when a POST request is made.
     * @return The http context for this path.
     */
    public HttpContext setPostMethod(String path, MethodHandler methodHandler) throws InvalidHttpMethodException {
        return this.setMethod(path, RouteHandler.POST_METHOD, methodHandler);
    }

    /**
     * This sets the PUT method for the given API path.
     * @param path The API path e.g. "/login"
     * @param methodHandler The handler for when a PUT request is made.
     * @return The http context for this path.
     */
    public HttpContext setPutMethod(String path, MethodHandler methodHandler) throws InvalidHttpMethodException {
        return this.setMethod(path, RouteHandler.PUT_METHOD, methodHandler);
    }

    /**
     * This sets the DELETE method for the given API path.
     * @param path The API path e.g. "/login"
     * @param methodHandler The handler for when a DELETE request is made.
     * @return The http context for this path.
     */
    public HttpContext setDeleteMethod(String path, MethodHandler methodHandler) throws InvalidHttpMethodException {
        return this.setMethod(path, RouteHandler.DELETE_METHOD, methodHandler);
    }

    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }
}