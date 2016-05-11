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
public class EditProfilUserBean extends Profile{
    public String address;
    UsersJpaController userJpaCtrl;

    public EditProfilUserBean() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        userJpaCtrl = new UsersJpaController(emf);
        this.name = UserBean.userName;
        if (this.name != null && this.name.length() > 3) {
            init();
        }

    }

    public void init() {
        Auth auth = new Auth();
        Users userProfile = auth.getUserByUserName(this.name).get(0);
        this.password = userProfile.getPassword();
        this.firstName = userProfile.getFirstName();
        this.lastName = userProfile.getLastName();
        this.email = userProfile.getMail();
        this.credit = userProfile.getCredit();
        this.address = userProfile.getAddress();
    }

   
    public String getAddress() {
        return address;
    }

  
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void update() {
        if (this.name.length() < 4) {
            return;
        }

        Auth auth = new Auth();
        Users userToUpdate = auth.getUserByUserName(this.name).get(0);

        if (this.firstName.length() > 0) {
            userToUpdate.setFirstName(this.firstName);
        }

        if (this.lastName.length() > 0) {
            userToUpdate.setLastName(this.lastName);
        }

        if (this.email.length() > 0) {
            userToUpdate.setMail(this.email);
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
