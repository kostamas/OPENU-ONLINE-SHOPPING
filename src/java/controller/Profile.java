
package controller;

import Entities.Administrators;
import Entities.Users;
import static java.awt.PageAttributes.MediaType.E;
import static javafx.scene.input.KeyCode.T;
import javax.persistence.EntityManagerFactory;

public class Profile {
    String name;
    String password;
    String firstName;
    String lastName;
    String email;
    int credit;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCredit() {
        return credit;
    }

    public void setAdminName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}
