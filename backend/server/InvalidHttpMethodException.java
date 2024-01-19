package server;

/**
 * This represents an error that occurs when trying to use an HTTP method that cannot be handled by a RouteHandler.
 * @see RouteHandler
 */
public class InvalidHttpMethodException extends Exception {
    /**
     * This constructs the exception without referencing the wrong HTTP method used.
     */
    public InvalidHttpMethodException() {
        super("Invalid HTTP method used.");

    }

    /**
     * This constructs the exception with reference to the wrong HTTP method used.
     * @param method The invalid method used.
     */
    public InvalidHttpMethodException(String method) {
        super("Invalid HTTP method used: \"" + method + "\".");
    }
}
