/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-21 20:32:48
 * @modify date 2022-01-21 20:32:48
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.dto;

public class TokenDto {
    private String token;

    public TokenDto() {
    }


    public TokenDto(String token) {
        this.token = token;
    }


    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
