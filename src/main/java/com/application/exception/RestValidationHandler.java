package com.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FieldValidationErrorDetails> handleValidationError(
            MethodArgumentNotValidException mNotValidException, HttpServletRequest request) {

        FieldValidationErrorDetails fErrorDetails = new FieldValidationErrorDetails();
        fErrorDetails.setError_timeStamp(new Date().getTime());
        fErrorDetails.setError_status(HttpStatus.BAD_REQUEST.value());
        fErrorDetails.setError_title("Field Validation Error");
        fErrorDetails.setError_detail("Inut Field Validation Failed");
        fErrorDetails.setError_developer_Message(mNotValidException.getClass().getName());
        fErrorDetails.setError_path(request.getRequestURI());

        BindingResult result = mNotValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError error : fieldErrors) {
            FieldValidationError fError = processFieldError(error);
            List<FieldValidationError> fValidationErrorsList =
                    fErrorDetails.getErrors().get(error.getField());
            if (fValidationErrorsList == null) {
                fValidationErrorsList = new ArrayList<FieldValidationError>();
            }
            fValidationErrorsList.add(fError);
            fErrorDetails.getErrors().put(error.getField(), fValidationErrorsList);
        }
        return new ResponseEntity<FieldValidationErrorDetails>(fErrorDetails, HttpStatus.BAD_REQUEST);
    }

    private FieldValidationError processFieldError(final FieldError error) {
        FieldValidationError fieldValidationError = new FieldValidationError();
        if (error != null) {
            fieldValidationError.setField(error.getField());
            fieldValidationError.setType(MessageType.ERROR);
            fieldValidationError.setMessage(error.getDefaultMessage());
        }
        return fieldValidationError;
    }
}
