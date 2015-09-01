package mh.springboot.controller.exception;


import mh.springboot.controller.error.ErrorCode;

import java.util.Map;

/**
 * 503 error
 */
public class ResourceServiceUnavailableException extends BaseException {

    public ResourceServiceUnavailableException(String message, ErrorCode code) {
        super(message, code);
    }

    public ResourceServiceUnavailableException(String message, ErrorCode code, Map<String, Object> attributes) {
        super(message, code, attributes);
    }

}
