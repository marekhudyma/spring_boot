package mh.springboot.controller.error;

import com.google.common.collect.ImmutableList;
import mh.springboot.controller.ApplicationController;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * General exceptions translator to http status codes and correct response format.
 */
@ControllerAdvice
public class ExceptionsTranslator {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsTranslator.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ModelAndView handleInvalidParamsException(BadRequestException e) {
        return createErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ModelAndView handleTypeMismatchException(TypeMismatchException e) {
        TypeMismatchCustomException exception = new TypeMismatchCustomException(e.getMessage(), ErrorCode.TYPE_MISMATCH);
        return createErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ModelAndView handleResourceNotFoundException(ResourceNotFoundException e) {
        return createErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceServiceUnavailableException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ModelAndView handleResourceServiceUnavailableException(ResourceServiceUnavailableException e) {
        return createErrorResponse(e, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ModelAndView handleError(Throwable t) {
        logger.error("Internal Server Error", t);
        Error error = new Error(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                                              ImmutableList.of(error)));
        modelAndView.setViewName(ApplicationController.ERROR_PATH);
        return modelAndView;
    }

    private ModelAndView createErrorResponse(BaseException e, HttpStatus httpStatus) {
        logger.error("Error ", e);
        Error error;
        if (e.getAttributes() == null) {
            error = new Error(e.getCode(), e.getMessage());
        } else {
            error = new Error(e.getCode(), e.getMessage(), e.getAttributes());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", new ErrorResponse(httpStatus.value(), ImmutableList.of(error)));
        modelAndView.setViewName(ApplicationController.ERROR_PATH);
        return modelAndView;
    }
}
