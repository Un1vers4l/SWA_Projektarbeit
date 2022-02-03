/**
 * @author Marcel Sauer(886022)
 * @email marcel.sauer@hs-osanbrueck.de
 * @create date 2022-01-20 14:45:16
 * @modify date 2022-01-20 14:45:16
 * @desc [description]
 */
package de.hsos.swa.studiom.UserManagement.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import de.hsos.swa.studiom.StudentsManagement.entity.Student;

@Vetoed
@Entity
@Table(name = "Account")
@NamedQuery(name = "User.findByUsername", query = "SELECT f from User f where f.username = :username", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@NamedQuery(name = "User.findAllUser", query = "SELECT f from User f", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class User {

    @Id
    @SequenceGenerator(name = "userIdSequence", sequenceName = "Users_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "userIdSequence")
    private long userId;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @ElementCollection(targetClass = Role.class)
    @JoinTable(name = "User_Roles", joinColumns = @JoinColumn(name = "userID"))
    @Fetch(FetchMode.JOIN)
    private Set<Role> role = new HashSet<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Student student;

    public User(String username, String password, Set<Role> role) {
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(role);
        this.addRole(Role.USER);
    }

    public User() {
    }

    public Optional<Student> getStudent() {
        return Optional.ofNullable(this.student);
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = this.passwordDecoder(password);
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }

    public boolean addRole(Role role){
        return this.role.add(role);
    }
    public boolean hasRole(Role role){
        return this.role.contains(role);
    }

    public boolean isMyPassword(String password) {
        return this.password.equals(this.passwordDecoder(password));
    }
    
    private String passwordDecoder(String password){
        return DigestUtils.sha256Hex(password);
    }
    public void changeMyData(User other){
        if(other.username != null) this.username = other.username;
        if(other.password != null) this.password = other.password;
        if(other.role != null) this.role = other.role;
    }
}
