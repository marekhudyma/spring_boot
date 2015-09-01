package mh.springboot.controller.error;

import com.google.common.collect.ImmutableList;
import mh.springboot.controller.exception.BadRequestException;
import mh.springboot.controller.exception.BaseException;
import mh.springboot.controller.exception.ResourceNotFoundException;
import mh.springboot.controller.exception.ResourceServiceUnavailableException;
import mh.springboot.controller.exception.TypeMismatchCustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exceptions translator to http status codes and correct response format.
 */
@ControllerAdvice
public class ExceptionsTranslator {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsTranslator.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidParamsException(BadRequestException e) {
        return createErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleTypeMismatchException(TypeMismatchException e) {
        TypeMismatchCustomException exception = new TypeMismatchCustomException(e.getMessage(), ErrorCode.TYPE_MISMATCH);
        return createErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceServiceUnavailableException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorResponse handleResourceServiceUnavailableException(ResourceServiceUnavailableException e) {
        return createErrorResponse(e, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleError(Throwable t) {
        logger.error("Internal Server Error[500]: ", t);
        Error error = new Error(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error!");
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ImmutableList.of(error));
    }

    private ErrorResponse createErrorResponse(BaseException e, HttpStatus httpStatus) {
        Error error = null;
        if (e.getAttributes() == null) {
            error = new Error(e.getCode(), e.getMessage());
        } else {
            error = new Error(e.getCode(), e.getMessage(), e.getAttributes());
        }
        return new ErrorResponse(httpStatus.value(), ImmutableList.of(error));
    }
}
