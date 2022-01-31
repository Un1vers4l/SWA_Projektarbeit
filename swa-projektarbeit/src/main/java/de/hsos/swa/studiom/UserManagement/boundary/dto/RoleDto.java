/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:10:22
 * @modify date 2022-01-31 15:10:22
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.boundary.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.hsos.swa.studiom.UserManagement.entity.Role;

public class RoleDto {
    List<String> allRole;

    public RoleDto() {
        this.allRole = Arrays.stream(Role.values())
        .filter(e -> Role.USER != e)
        .map(Role::name)
        .collect(Collectors.toList());
    }

    public List<String> getAllRole() {
        return this.allRole;
    }

    public void setAllRole(List<String> allRole) {
        this.allRole = allRole;
    }

}
