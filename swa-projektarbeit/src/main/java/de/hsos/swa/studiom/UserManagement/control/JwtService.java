/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-20 14:47:00
 * @modify date 2022-01-20 14:47:00
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.control;


import javax.enterprise.context.RequestScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import de.hsos.swa.studiom.UserManagement.entity.Role;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.smallrye.jwt.build.JwtSignatureException;

@RequestScoped
public class JwtService {

    @ConfigProperty(name = "mp.jwt.verify.issuer") public String issuer;

    public String generateToken(String username, Role roles, Long duration) {

        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        long currentTimeInSecs = currentTimeInSecs();

        String group = roles.toString();

        claimsBuilder.issuer(this.issuer);
        claimsBuilder.subject(username);
        claimsBuilder.issuedAt(currentTimeInSecs);
        claimsBuilder.expiresIn(duration);
        claimsBuilder.groups(group);
        claimsBuilder.claim("matNr", 886022);
        String token = null;
        try {
            token = claimsBuilder.jws().sign();
        } catch (JwtSignatureException e) {
            //TODO: handle exception
        }

        return token;
    }

    private static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }
}
