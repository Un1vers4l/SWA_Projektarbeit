/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-21 15:40:25
 * @modify date 2022-01-21 15:40:25
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.dto;

import javax.validation.constraints.NotBlank;

public class AuthDto {
    @NotBlank(message="Bitte geben sie ihr Username ein")
    private String username;
    @NotBlank(message="Bitte geben sie ihr Password ein")
    private String password;

    public AuthDto() {
    }

    public AuthDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
