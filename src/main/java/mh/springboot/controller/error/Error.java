package mh.springboot.controller.error;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Error with variable response attributes.
 */
public class Error extends HashMap<String, Object> {

    private static final String CODE = "code";
    private static final String MESSAGE = "message";

    /**
     * Constructor that takes variable number of arguments and puts them in the response.
     *
     * @param code    Unique error code.
     * @param message Response message.
     */
    public Error(ErrorCode code, String message) {
        put(CODE, checkNotNull(code));
        put(MESSAGE, checkNotNull(message));
    }

    /**
     * Constructor that takes variable number of arguments and puts them in the response.
     *
     * @param code       Unique error code.
     * @param message    Response message.
     * @param attributes Variable list of attributes to add to the response.
     */
    public Error(ErrorCode code, String message, Map<String, Object> attributes) {
        put(CODE,  checkNotNull(code));
        put(MESSAGE, checkNotNull(message));
        putAll(attributes);
    }

    public ErrorCode getCode() {
        return ErrorCode.valueOf(get(CODE).toString());
    }

    public String getMessage() {
        return (String) get(MESSAGE);
    }
}
