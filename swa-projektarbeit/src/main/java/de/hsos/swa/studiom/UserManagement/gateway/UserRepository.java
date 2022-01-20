/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-20 14:47:49
 * @modify date 2022-01-20 14:47:49
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.gateway;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.hsos.swa.studiom.UserManagement.control.AuthService;
import de.hsos.swa.studiom.UserManagement.control.JwtService;
import de.hsos.swa.studiom.UserManagement.control.UserService;
import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.UserManagement.entity.User;


@RequestScoped
@Transactional
public class UserRepository implements AuthService, UserService {

    @Inject
    EntityManager entityManager;

    @Inject
    JwtService jwtService;

    @Inject
    Durations durations;
   

    @Override
    public User findUser(String username) {
        return entityManager.find(User.class, username);
    }

    @Override
    public String userLogin(String username, String password) {
        User user = this.findUser(username);
        if(user == null) return null;
        if(!user.getPassword().equals(DigestUtils.sha256Hex(password))) return null;

        long duration = durations.normal;
        if(user.getRole() == Role.STUDENT) duration = durations.student;
        else if(user.getRole() == Role.SEKT) duration = durations.sekt;
        
        String token = jwtService.generateToken(username, user.getRole(), duration);
        return token;
    }

    @Override
    public boolean createUserStudent(String username, String password) {
        if(username == null) return false;
        if(password == null) return false;

        String passwordHash = DigestUtils.sha256Hex(password);

        User user = new User(username, passwordHash, Role.STUDENT);
        entityManager.persist(user);
        return true;
    }
    
}

@RequestScoped
class Durations{
    @ConfigProperty(name = "studyOM.jwt.duration") public Long normal;
    @ConfigProperty(name = "studyOM.student.jwt.duration") public Long student;
    @ConfigProperty(name = "studyOM.sekt.jwt.duration") public Long sekt;
}
