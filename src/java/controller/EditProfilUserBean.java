/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Administrators;
import Entities.AdministratorsJpaController;
import Entities.Stores;
import Entities.Users;
import Entities.UsersCartJpaController;
import Entities.UsersJpaController;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Auth;

@ManagedBean
@ViewScoped
public class EditProfilUserBean {

    public String userName;
    public String firstName;
    public String lastName;
    public String mail;
    public String address;
    public String password;
    public int credit;
    UsersJpaController userJpaCtrl;

    public EditProfilUserBean() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        userJpaCtrl = new UsersJpaController(emf);
        this.userName = UserBean.userName;
        if (this.userName != null && this.userName.length() > 3) {
            init();
        }

    }

    public void init() {
        Auth auth = new Auth();
        Users userProfile = auth.getUserByUserName(this.userName).get(0);
        this.password = userProfile.getPassword();
        this.firstName = userProfile.getFirstName();
        this.lastName = userProfile.getLastName();
        this.mail = userProfile.getMail();
        this.credit = userProfile.getCredit();
        this.address = userProfile.getAddress();
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void update() {
        if (this.userName.length() < 4) {
            return;
        }

        Auth auth = new Auth();
        Users userToUpdate = auth.getUserByUserName(this.userName).get(0);

        if (this.firstName.length() > 0) {
            userToUpdate.setFirstName(this.firstName);
        }

        if (this.lastName.length() > 0) {
            userToUpdate.setLastName(this.lastName);
        }

        if (this.mail.length() > 0) {
            userToUpdate.setMail(this.mail);
        }

        if (this.password.length() > 3) {
            userToUpdate.setPassword(this.password);
        }

        if (this.address.length() > 4) {
            userToUpdate.setAddress(this.address);
        }

        if (this.credit > 99999999 && this.credit < 100000000) {  // bug - convert to integer
            userToUpdate.setCredit(this.credit);
        }

        try {
            userJpaCtrl.edit(userToUpdate);
            init();
        } catch (Exception ex) {
            Logger.getLogger(EditProfilAdminBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
