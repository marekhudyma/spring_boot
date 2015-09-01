package mh.springboot.controller.exception;


import mh.springboot.controller.error.ErrorCode;

import java.util.Map;

/**
 * 404 error
 */
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message, ErrorCode code) {
        super(message, code);
    }

    public ResourceNotFoundException(String message, ErrorCode code, Map<String, Object> attributes) {
        super(message, code, attributes);
    }

}
