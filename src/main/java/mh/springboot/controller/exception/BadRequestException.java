package mh.springboot.controller.exception;


import mh.springboot.controller.error.ErrorCode;

import java.util.Map;

/**
 *  400 http error
 */
public class BadRequestException extends BaseException {

    public BadRequestException(String message, ErrorCode code) {
        super(message, code);
    }

    public BadRequestException(String message, ErrorCode code, Map<String, Object> attributes) {
        super(message, code, attributes);
    }

}
