/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:10:18
 * @modify date 2022-01-31 15:10:18
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.dto;

import java.util.List;

public class DataDto {
    private List<UserDto> userData;

    public DataDto(List<UserDto> userData) {
        this.userData = userData;
    }

    public DataDto() {
    }

    public List<UserDto> getUserData() {
        return this.userData;
    }

    public void setUserData(List<UserDto> userData) {
        this.userData = userData;
    }

}
