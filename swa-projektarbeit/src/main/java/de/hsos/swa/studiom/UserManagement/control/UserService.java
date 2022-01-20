/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-20 14:47:05
 * @modify date 2022-01-20 14:47:05
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.control;

import de.hsos.swa.studiom.UserManagement.entity.User;

public interface UserService {
    public User findUser(String username);
    public boolean createUserStudent(String username, String password);
    // TODO weiter User funktion z.B password aendern
}
