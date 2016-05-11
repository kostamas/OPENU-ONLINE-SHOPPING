package controller;

import Entities.Administrators;
import Entities.Stores;
import controller.EditProfilAdminBean;
import model.Auth;
import Entities.AdministratorsJpaController;
import static controller.AdminBean.adminName;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@ViewScoped
public class EditProfilAdminBean extends Profile{
    private AdministratorsJpaController adminJpaCtrl;
    
    //constructor
    public EditProfilAdminBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        adminJpaCtrl = new AdministratorsJpaController(emf);
        this.name = AdminBean.adminName;
        if(this.name.length() > 3){
            init();
        }
    }
    
    public void init(){
         Auth auth = new Auth();
         Administrators adminProfile = auth.getAdminByAdminName(this.name).get(0);
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