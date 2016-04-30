package controller;

import Entities.Administrators;
import Entities.Stores;
import controller.EditProfilAdminBean;
import model.Auth;
import Entities.AdministratorsJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@ViewScoped
public class EditProfilAdminBean {

    private String adminName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int credit;
    private AdministratorsJpaController adminJpaCtrl;

    public String getAdminName() {
        return adminName;
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

    public void setAdminName(String adminName) {
        this.adminName = adminName;
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

    //constructor
    public EditProfilAdminBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        adminJpaCtrl = new AdministratorsJpaController(emf);
        this.adminName = AdminBean.adminName;
        if(this.adminName.length() > 3){
            init();
        }
    }
    
    public void init(){
         Auth auth = new Auth();
         Administrators adminProfile = auth.getAdminByAdminName(adminName).get(0);
         this.password = adminProfile.getPassword();
         this.firstName = adminProfile.getFirstName();
         this.lastName = adminProfile.getLastName();
         this.email = adminProfile.getEmail();
         this.credit = adminProfile.getCredit();
    }

    public void update() {

        Auth auth = new Auth();
        Administrators adminToUpdate = auth.getAdminByAdminName(adminName).get(0);

        if (this.firstName.length() > 0) {
            adminToUpdate.setFirstName(this.firstName);
        }

        if (this.lastName.length() > 0) {
            adminToUpdate.setLastName(this.lastName);
        }

        if (this.password.length() > 4) {
            adminToUpdate.setPassword(this.password);
        }

        if (this.credit > 99999999 && this.credit < 100000000) {
            adminToUpdate.setCredit(this.credit);
        }

        try {
            adminJpaCtrl.edit(adminToUpdate);
        } catch (Exception ex) {
            Logger.getLogger(EditProfilAdminBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
