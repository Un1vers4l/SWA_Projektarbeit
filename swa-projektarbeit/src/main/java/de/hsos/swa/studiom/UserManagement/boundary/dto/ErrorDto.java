/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-21 15:43:08
 * @modify date 2022-01-21 15:43:08
 * @desc [description]
 * @Quelle https://quarkus.io/guides/validation
 */
package de.hsos.swa.studiom.UserManagement.boundary.dto;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

public class ErrorDto {
    private boolean success;
    private String message;

    public ErrorDto() {
    }

    public ErrorDto(String message) {
        this.message = message;
        success = false;
    }

    public ErrorDto(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ErrorDto(Exception e) {
        this.message = e.getMessage();
        this.success = false;
    }
    public ErrorDto(Set<? extends ConstraintViolation<?>> violations) {
        this.success = false;
        this.message = violations.stream()
             .map(cv -> cv.getMessage())
             .collect(Collectors.joining('\n' + " "));
    }

    public String getmessage() {
        return this.message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public boolean getsuccess() {
        return this.success;
    }

    public void setsuccess(boolean success) {
        this.success = success;
    }

    

    
}
