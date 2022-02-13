package de.hsos.swa.studiom.UserManagement.boundary.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class PostUserDto {
    @NotBlank(message="Bitte geben sie ihr Username ein")
    private String username;
    @NotBlank(message="Bitte geben sie ihr Password ein")
    private String password;
    @NotEmpty(message="weahlen Sie eine Role")
    private List<String> role;

    public PostUserDto() {
    }

    public PostUserDto(String username, String password, List<String> role) {
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

}
