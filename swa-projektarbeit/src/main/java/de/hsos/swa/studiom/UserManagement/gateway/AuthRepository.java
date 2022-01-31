/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-25 18:35:14
 * @modify date 2022-01-25 18:35:14
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.gateway;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import de.hsos.swa.studiom.UserManagement.control.AuthService;
import de.hsos.swa.studiom.UserManagement.control.UserService;
import de.hsos.swa.studiom.UserManagement.entity.Role;
import de.hsos.swa.studiom.UserManagement.entity.User;
import de.hsos.swa.studiom.UserManagement.exception.WrongUserDataExeption;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.smallrye.jwt.build.JwtSignatureException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.eclipse.microprofile.config.ConfigProvider;


@RequestScoped
@Transactional
public class AuthRepository implements AuthService {

    @Inject
    EntityManager entityManager;

    @Inject
    UserService userService;

    Logger log = Logger.getLogger(AuthRepository.class);

    private final long defaultDuration = 1800;

    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue="http://example.com") 
    private String issuer;

    @Override
    public String userLogin(String username, String password) throws WrongUserDataExeption {
        User user = userService.findUserByUsername(username);
        
        if(user == null) throw new WrongUserDataExeption();
        if(!user.isMyPassword(password)) throw new WrongUserDataExeption();

        long duration = this.getDuration(user.getRole());
        
        String token = this.generateToken(user.getUserId(), user.getRole(), duration);
        return token;
    }


    private String generateToken(long userID, Set<Role> roles, Long duration) {

        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        long currentTimeInSecs = this.currentTimeInSecs();

        Set<String> groups = new HashSet<>();
        for(Role role: roles) groups.add(role.toString());

        claimsBuilder.issuer(this.issuer);
        claimsBuilder.subject(Long.toString(userID));
        claimsBuilder.issuedAt(currentTimeInSecs);
        claimsBuilder.expiresIn(duration);
        claimsBuilder.groups(groups);

        String token = null;
        try {
            token = claimsBuilder.jws().sign();
        } catch (JwtSignatureException e) {
            log.error("Bitte pruefen Sie den privateKey.pem oder starten sie den Server neu");
            log.error(e.toString());
        }

        return token;
    }

    private int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    } 
    
    private long getDuration(Set<Role> role){

        long duration = Long.MAX_VALUE;

        for(Role userRole : role){
            Optional<Long> durationConfig = ConfigProvider.getConfig().getOptionalValue("studyOM.jwt.duration." + userRole.toString(), Long.class);
            if(durationConfig.isPresent() && durationConfig.get() < duration)
            {
                duration = durationConfig.get();                
            }
        }
        if(duration != Long.MAX_VALUE) return duration; 

        Optional<Long> defaultConfig = ConfigProvider.getConfig().getOptionalValue("studyOM.jwt.duration", Long.class);

        return defaultConfig.isPresent() ? defaultConfig.get() : this.defaultDuration;
    }
}
