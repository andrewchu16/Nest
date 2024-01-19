package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.TreeMap;

/**
 * This is a wrapper class for an HttpHandler.
 */
public class RouteHandler implements HttpHandler {
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";

    /**
     * This represents the methods that can be handled by a RouteHandler.
     */
    public static final String[] VALID_METHODS = {GET_METHOD, POST_METHOD, PUT_METHOD, DELETE_METHOD};

    /**
     * This checks if a method is supported by a RouteHandler.
     * @param method The method to check.
     * @return True if it is supported, false otherwise.
     */
    public static boolean checkMethodIsValid(String method) {
        method = method.toUpperCase().strip(); // .strip() might cause an error, replace with .trim() if it does.
        for (String validMethod: VALID_METHODS) {
            if (validMethod.equals(method)) {
                return true;
            }
        }

        return false;
    }

    private TreeMap<String, MethodHandler> methodHandlers;

    /**
     * This constructs a new route handler which handlers every route with an empty method handler.
     * @see EmptyMethodHandler
     */
    public RouteHandler() {
        this.methodHandlers = new TreeMap<String, MethodHandler>();

        for (String method: VALID_METHODS) {
            this.methodHandlers.put(method, new EmptyMethodHandler(true));
        }
    }

    public void setHandler(String method, MethodHandler handler) throws InvalidHttpMethodException {
        if (!checkMethodIsValid(method)) {
            throw new InvalidHttpMethodException(method);
        }

        this.methodHandlers.put(method, handler);
    }

    /**
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RequestData requestData = new RequestData(exchange);

        MethodHandler methodHandler = this.methodHandlers.get(requestData.getMethod());

        if (methodHandler != null) {
            methodHandler.handleMethod(requestData);
        }
    }
}
