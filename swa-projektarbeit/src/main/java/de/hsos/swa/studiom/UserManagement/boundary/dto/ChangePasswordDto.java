/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-24 18:38:03
 * @modify date 2022-01-24 18:38:03
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.dto;

import javax.validation.constraints.NotBlank;

public class ChangePasswordDto {
    
    @NotBlank(message="Bitte geben sie ihr Password ein")
    private String password;

    public ChangePasswordDto() {
    }


    public ChangePasswordDto(String password) {
        this.password = password;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
