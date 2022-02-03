/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-20 14:46:53
 * @modify date 2022-01-20 14:46:53
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.control;

import java.util.Optional;

import de.hsos.swa.studiom.shared.exceptions.WrongUserDataExeption;

public interface AuthService {
    public Optional<String> userLogin(String username, String password) throws WrongUserDataExeption;
}
