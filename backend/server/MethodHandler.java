package server;

/**
 * This handles a method given request data.
 */
public interface MethodHandler {
    /**
     * Handles the request via the given method.
     * @param data The data sent by the request.
     */
    public void handleMethod(RequestData data);
}
