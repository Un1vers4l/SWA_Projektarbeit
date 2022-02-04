/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-20 14:47:05
 * @modify date 2022-01-20 14:47:05
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.control;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.UserManagement.entity.User;
import de.hsos.swa.studiom.shared.algorithm.username.UsernameGenerator;
import de.hsos.swa.studiom.shared.exceptions.CanNotGeneratUserExeption;
import de.hsos.swa.studiom.shared.exceptions.UserNotExistExeption;
import de.hsos.swa.studiom.shared.exceptions.UsernameExistExeption;

public interface UserService {
    public Optional<User> findUser(long userID);
    public List<User> getAllUser();
    public boolean deleteUser(long userID);
    public boolean changePassword(long userID, String newPassword) throws UserNotExistExeption;
    public Optional<User> findUserByUsername(String username);
    public User createUserStudent(UsernameGenerator username, String password) throws CanNotGeneratUserExeption;
    public User createUserGenertor(UsernameGenerator userGenerator, String password, Set<Role> role) throws CanNotGeneratUserExeption;
    public User createUser(String username, String password, Set<Role> role) throws UsernameExistExeption;
    // TODO weiter User funktion z.B password aendern
}
