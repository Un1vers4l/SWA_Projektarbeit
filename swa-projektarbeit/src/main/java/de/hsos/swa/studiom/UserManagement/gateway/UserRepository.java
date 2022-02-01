/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-20 14:47:49
 * @modify date 2022-01-20 14:47:49
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.gateway;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.jboss.logging.Logger;

import de.hsos.swa.studiom.UserManagement.control.UserService;
import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.UserManagement.entity.User;
import de.hsos.swa.studiom.shared.algorithm.username.UsernameFactory;
import de.hsos.swa.studiom.shared.exception.UserNotExistExeption;
import de.hsos.swa.studiom.shared.exception.UsernameExistExeption;


@RequestScoped
@Transactional
public class UserRepository implements UserService {

    @Inject
    EntityManager entityManager;

    Logger log = Logger.getLogger(UserRepository.class);

    
    /** 
     * @param username - Der Username der gesucht wird
     * @return User - Das gefundene User objekt
     */
    @Override
    public User findUserByUsername(String username) {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst().orElse(null);
    }
    
    /** 
     * @param userID - Die UserID die gesucht wird
     * @return User - Das gefundene User objekt
     */
    @Override
    public User findUser(long userID) {
        return entityManager.find(User.class, userID);
    }
    
    
    /** 
     * @param userGenerator - Hier wird ein Obejekt erwartet das vom UsernameFactory gerebt hat und implementiert wurde. Wird dazu benutzt um einen Username zu erzuegen.
     * @param password
     * @return User - gibt null zurück falls die erezugung vergeschlagen ist, kann entstehen falls der Username generator kein generierten kann
     */
    @Override
    public User createUserStudent(UsernameFactory userGenerator, String password){
        Set<Role> role = new HashSet<>();
        role.add(Role.STUDENT);
        return this.createUserGenertor(userGenerator, password, role);
    }

    
    /** 
     * @param userGenerator - Hier wird ein Obejekt erwartet das vom UsernameFactory gerebt hat und implementiert wurde. Wird dazu benutzt um einen Username zu erzuegen.
     * @param password
     * @param role
     * @return User - gibt null zurück falls die erezugung vergeschlagen ist, kann entstehen falls der Username generator kein generierten kann
     */
    @Override
    public User createUserGenertor(UsernameFactory userGenerator, String password, Set<Role> role){
        String username = null;
        boolean isNameFree = false;

        while(userGenerator.hasNext() && !isNameFree){
            username = userGenerator.getUsername();
            if(username != null){
                isNameFree = this.findUserByUsername(username) == null;
            }
        }
        
        if (username == null){
            log.warn("Es konnte kein Username generiert werden");
            log.debug(userGenerator.toString());
           return null; 
        } 
        
        User user = null;
        try {
            user = this.createUser(username, password, role);
            return user;
        } catch (UsernameExistExeption e) {
            log.warn("Es konnte kein Username erzeugt werden");
            log.error(e.getMessage());
            return null;
        }
    }

    
    /** 
     * @param username
     * @param password
     * @param role
     * @return User
     * @throws UsernameExistExeption - wird ausgeloest falls es schon einen user mit dem Namen existiert
     */
    @Override
    public User createUser(String username, String password, Set<Role> role) throws UsernameExistExeption{
        if(username == null || password == null || role == null) return null;
        if(this.findUserByUsername(username) != null) throw new UsernameExistExeption();

        User user = new User(username, password, role);
        entityManager.persist(user);
        log.info("User(Username: +"+ username + " + ) wurde erzeugt");
        return user;
    }

    
    /** 
     * @param userID
     * @return boolean
     */
    @Override
    public boolean deleteUser(long userID) {
        User user = this.findUser(userID);
        if (user == null) return false;

        entityManager.remove(user);

        log.info("Remove UserID: " + userID);
        log.debug("Remove("+ user.toString() +')');
        return true;
    }
    
    
    /** 
     * @param userID
     * @param password
     * @return boolean
     * @throws UserNotExistExeption
     */
    @Override
    public boolean changePassword(long userID, String password) throws UserNotExistExeption{
        User user = this.findUser(userID);
        if(user == null) throw new UserNotExistExeption();

        //TODO add password check Constrains zum Beispiel min. 6 Zeichen lang
        user.setPassword(password);

        log.info("ChangePassword UserID: " + userID);
        log.debug("ChangePassword("+ user.toString() +')');
        return true;
    }
    
    /** 
     * @return List<User>
     */
    @Override
    public List<User> getAllUser() {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findAllUser", User.class);
        return query.getResultList();
    }
    
}
