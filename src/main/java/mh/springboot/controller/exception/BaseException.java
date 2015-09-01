package mh.springboot.controller.exception;


import mh.springboot.controller.error.ErrorCode;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base exception.
 */
public abstract class BaseException extends RuntimeException {
    private ErrorCode code;
    private String message;
    private Map<String, Object> attributes;

    public BaseException(String message, ErrorCode code) {
        this.message = checkNotNull(message, "message");
        this.code = checkNotNull(code, "code");
    }

    public BaseException(String message, ErrorCode code, Map<String, Object> attributes) {
        this.message = checkNotNull(message, "message");
        this.code = checkNotNull(code, "code");
        this.attributes = checkNotNull(attributes, "attributes");
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
