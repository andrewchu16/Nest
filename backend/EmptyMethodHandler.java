import server.MethodHandler;
import server.RequestData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This represents a method handler that does nothing.
 */
public class EmptyMethodHandler implements MethodHandler {
    private boolean alertWhenTriggered;

    /**
     * Constructs an empty method handler that does not alert when it gets triggered.
     */
    public EmptyMethodHandler() {
        this.alertWhenTriggered = false;
    }

    /**
     * Constructs an empty method handler that alerts when it gets triggered if specified.
     * @param alertWhenTriggered Whether to alert when the handler gets triggered or not.
     */
    public EmptyMethodHandler(boolean alertWhenTriggered) {
        this.alertWhenTriggered = alertWhenTriggered;
    }

    /**
     * This does nothing. If set to alert when triggered, it will print a message with the HTTP method, the timestamp, and the API path.
     * @param data The request data.
     */
    @Override
    public void handleMethod(RequestData data) {
        // do nothing.
        if (this.alertWhenTriggered) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            System.out.println("-- " + data.getMethod() + " " + dateFormat.format(date) + " -- " + data.getUri().toString());
        }
    }
}
