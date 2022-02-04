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
import java.util.Optional;
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
import de.hsos.swa.studiom.shared.algorithm.username.UsernameGenerator;
import de.hsos.swa.studiom.shared.exceptions.CanNotGeneratUserExeption;
import de.hsos.swa.studiom.shared.exceptions.UserNotExistExeption;
import de.hsos.swa.studiom.shared.exceptions.UsernameExistExeption;


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
    public Optional<User> findUserByUsername(String username) {
        if(username == null) throw new IllegalArgumentException();
        TypedQuery<User> query = entityManager.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }
    
    /** 
     * @param userID - Die UserID die gesucht wird
     * @return User - Das gefundene User objekt
     */
    @Override
    public Optional<User> findUser(long userID) {
        return Optional.ofNullable(entityManager.find(User.class, userID));
    }
    
    
    /** 
     * @param userGenerator - Hier wird ein Obejekt erwartet das vom UsernameFactory gerebt hat und implementiert wurde. Wird dazu benutzt um einen Username zu erzuegen.
     * @param password
     * @return User - gibt den erzuegten User zurueck
     * @throws CanNotGeneratUserExeption - wird geschmissen falls es keinen User erzeuegen koennte
     */
    @Override
    public User createUserStudent(UsernameGenerator userGenerator, String password) throws CanNotGeneratUserExeption{
        Set<Role> role = new HashSet<>();
        role.add(Role.STUDENT);
        return this.createUserGenertor(userGenerator, password, role);
    }

    
    /** 
     * @param userGenerator - Bekommt ein Obejkt das von der Abstracte Klasse "UsernameFactory" erbt und damit erzeugt es ein Username
     * @param password
     * @param role
     * @return User - gibt den erzuegten User zurueck
     * @throws CanNotGeneratUserExeption - wird geschmissen falls es keinen User erzeuegen koennte
     */
    //ToDo add password generator
    @Override
    public User createUserGenertor(UsernameGenerator userGenerator, String password, Set<Role> role) throws CanNotGeneratUserExeption{
        if(userGenerator == null || password == null || role == null) throw new IllegalArgumentException(); 
        Optional<String> username = Optional.ofNullable(null);
        boolean isNameFree = false;

        while(userGenerator.hasNext() && !isNameFree){
            username =  userGenerator.getUsername();
            if(username.isPresent()){
                isNameFree = !this.findUserByUsername(username.get()).isPresent();
            }
        }
        
        while(!isNameFree){
            String usernameTmp = username.orElse("MaxMusterman") + this.getRandomNumber(0, 1000);
            isNameFree = !this.findUserByUsername(usernameTmp).isPresent();
            if(isNameFree)  username = Optional.ofNullable(usernameTmp);
        }

        try {
            User user = this.createUser(username.get(), password, role);
            return user;
        } catch (UsernameExistExeption e) {
            log.warn("Es konnte kein Username erzeugt werden");
            log.error(e.getMessage());
            log.debug(userGenerator.toString());
            throw new CanNotGeneratUserExeption();
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
        if(username == null || password == null || role == null) throw new IllegalArgumentException();
        if(this.findUserByUsername(username).isPresent()) throw new UsernameExistExeption();

        User user = new User(username, password, role);
        entityManager.persist(user);
        log.info("User(Username: "+ username + ") wurde erzeugt");
        return user;
    }

    
    /** 
     * @param userID
     * @return boolean gibt false zurueck fals der user nicht existiert
     */
    @Override
    public boolean deleteUser(long userID) {
        Optional<User> user = this.findUser(userID);
        if (!user.isPresent()) return false;

        entityManager.remove(user.get());

        log.info("Remove UserID: " + userID);
        log.debug("Remove("+ user.get().toString() +')');
        return true;
    }
    
    
    /** 
     * @param userID
     * @param password
     * @return boolean gibt false zurueck falls, das neue Password nicht der Bedingungen der Passwoerter ueber ein stimmt
     * @throws UserNotExistExeption
     */
    @Override
    public boolean changePassword(long userID, String password) throws UserNotExistExeption{
        if(password == null) throw new IllegalArgumentException();

        Optional<User> user = this.findUser(userID);
        if(!user.isPresent()) throw new UserNotExistExeption();

        //TODO add password check Constrains zum Beispiel min. 6 Zeichen lang
        user.get().setPassword(password);

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
    //Quelle: https://www.baeldung.com/java-generating-random-numbers-in-range
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    
}
