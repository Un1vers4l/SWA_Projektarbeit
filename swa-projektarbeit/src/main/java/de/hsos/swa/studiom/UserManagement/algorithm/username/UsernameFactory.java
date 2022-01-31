/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-31 15:10:06
 * @modify date 2022-01-31 15:10:06
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.algorithm.username;

public abstract class UsernameFactory implements UsernameAlgorithm {
    private final int maxCount;
    private int count;

    public UsernameFactory(int maxCount) {
        this.maxCount = maxCount + 1;
        this.count = 0;
    }
    public UsernameFactory() {
        this.maxCount = 500;
        this.count = 0;
    }

    @Override
    public String getUsername() {
        this.count++;
        return null;
    }

    public boolean hasNext(){
        return this.maxCount > this.count;
    }


    @Override
    public String toString() {
        return "{" +
            " maxCount='" + maxCount + "'" +
            ", count='" + count + "'" +
            "}";
    }

    
}