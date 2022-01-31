/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-24 18:38:13
 * @modify date 2022-01-24 18:38:13
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.UserManagement.entity.User;

public class UserDto {
    private long userID;
    @NotBlank(message="Bitte geben sie ihr Username ein")
    private String username;
    @NotBlank(message="Bitte geben sie ihr Password ein")
    private String password;
    @NotEmpty(message="weahlen Sie eine Role")
    private List<String> role;

    public UserDto() {
    }

    public UserDto(long userID, String username, String password, List<String> role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
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

    public List<String> getRole() {
        return this.role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public long getUserID() {
        return this.userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public static class Converter {
        public static User DtoToUser(long userid, UserDto userDto){
            User user = new User(userDto.username, userDto.password, roleAsEnum(userDto.role));
            user.setUserId(userid);
            return user;
        }
        public static UserDto SimpleDto(User user){
            UserDto uDto = new UserDto();
            uDto.setUserID(user.getUserId());
            uDto.setUsername(user.getUsername());
            return uDto;
        }
        public static UserDto UserToDto(User user){
            UserDto uDto = new UserDto();
            uDto.setUserID(user.getUserId());
            uDto.setUsername(user.getUsername());
            List<String> role = user.getRole()
                .stream()
                .map(Role::name)
                .collect(Collectors.toList());
            uDto.setRole(role);
            return uDto;
        }
        public static Set<Role> roleAsEnum(List<String> role) {
            return Arrays.stream(Role.values())
                .filter(e -> role.contains(e.toString()))
                .collect(Collectors.toSet());
        }
    }
}
