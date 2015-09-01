package mh.springboot.controller.exception;

import mh.springboot.controller.error.ErrorCode;

import java.util.Map;

/**
 *  400 http error
 */
public class TypeMismatchCustomException extends BaseException {

    public TypeMismatchCustomException(String message, ErrorCode code) {
        super(message, code);
    }

    public TypeMismatchCustomException(String message, ErrorCode code, Map<String, Object> attributes) {
        super(message, code, attributes);
    }

}
