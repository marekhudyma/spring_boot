package mh.springboot.controller.error;

import java.util.List;

public class ErrorResponse {

    private Integer httpCode;

    private List<Error> errors;

    public ErrorResponse(Integer httpCode, List<Error> errors) {
        this.httpCode = httpCode;
        this.errors = errors;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
