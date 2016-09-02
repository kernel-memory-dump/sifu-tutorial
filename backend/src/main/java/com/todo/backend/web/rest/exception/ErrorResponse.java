/**
* Copyright 2016 dryTools doo
* Email: contact@drytools.co
* 
* This file is part of todo.
* 
* todo is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* todo is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with todo. If not, see <http://www.gnu.org/licenses/>.*
**/
package com.todo.backend.web.rest.exception;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;


public class ErrorResponse {

    private final List<String> errorCodes;
    private final List<FieldError> fieldErrors;
    private final String message;

    public ErrorResponse(@Nonnull List<FieldError> fieldErrors, String message) {
        this.fieldErrors = fieldErrors;
        this.errorCodes = null;
        this.message = message;
    }

    public ErrorResponse(@Nonnull String code, String message) {
        this.fieldErrors = null;
        errorCodes = new LinkedList<String>();
        errorCodes.add(code);
        this.message = message;
    }

    public ErrorResponse(String message, @Nonnull List<String> errorCodes) {
        this.fieldErrors = null;
        this.errorCodes = errorCodes;
        this.message = message;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public String getMessage() {
        return message;
    }

}
