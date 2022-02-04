/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:09:57
 * @modify date 2022-01-31 15:09:57
 * @desc [description]
 */
package de.hsos.swa.studiom.shared.algorithm.username;

import java.util.Optional;

public class SimpleUsernameAlgo extends UsernameGenerator {
    private String vorname;
    private String nachname;

    private int index; 

    public SimpleUsernameAlgo(String vorname, String nachname) {
        super(vorname.length() - 1 + nachname.length() - 1);
        this.vorname = vorname.substring(0, vorname.length() - 1);
        this.nachname = nachname.substring(0, nachname.length() - 1);
        index = vorname.length() - 1;
    }


    @Override
    public Optional<String> getUsername() {
        if(!this.hasNext()) return Optional.ofNullable(null);;
        
        String username = vorname.substring(0, index) + nachname;
        index--;
        if(index == 0){
            String tmp = vorname;
            vorname = nachname;
            nachname = tmp;
            index = vorname.length();
        }
        super.getUsername();
        return Optional.ofNullable(username);
    }
    @Override
    public boolean hasNext(){
        return super.hasNext() && index > 0;
    }


    @Override
    public String toString() {
        return "{" + 
            super.toString() +
            "{" +
            " vorname='" + vorname + "'" +
            ", nachname='" + nachname + "'" +
            ", index='" + index + "'" +
            "}}";
    }
    
}
