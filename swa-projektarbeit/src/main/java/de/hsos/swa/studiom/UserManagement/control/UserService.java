/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-20 14:47:05
 * @modify date 2022-01-20 14:47:05
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.control;

import java.util.List;
import java.util.Set;

import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.UserManagement.entity.User;
import de.hsos.swa.studiom.shared.algorithm.username.UsernameFactory;
import de.hsos.swa.studiom.shared.exception.UserNotExistExeption;
import de.hsos.swa.studiom.shared.exception.UsernameExistExeption;

public interface UserService {
    public User findUser(long userID);
    public List<User> getAllUser();
    public boolean deleteUser(long userID);
    public boolean changePassword(long userID, String newPassword) throws UserNotExistExeption;
    public User findUserByUsername(String username);
    public User createUserStudent(UsernameFactory username, String password);
    User createUserGenertor(UsernameFactory userGenerator, String password, Set<Role> role);
    public User createUser(String username, String password, Set<Role> role) throws UsernameExistExeption;
    // TODO weiter User funktion z.B password aendern
}
