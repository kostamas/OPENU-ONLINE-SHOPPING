package controller;

import Entities.Administrators;
import Entities.Users;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import model.Auth;

@ManagedBean
@ViewScoped
public class RegisterBean {

    private String name;
    private String password;

    public static String userName;
    public static String adminName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String adminRegister() {                 // can't make generice since this fn used in xhtml and can't take paremters
        Administrators admin = new Administrators(this.name, this.password);
        String queryName = "Administrators.findByAdminName";
        String queryParameterName = "adminName";
        String parameterValue = this.name;

        this.adminName = this.name;

        if (this.name.length() < 4 || this.password.length() < 4) {
            String errorMessage = "user name and password must contain at least 4 letters!";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMessage));
            return "";
        }

        Auth authController = new Auth();
        if (authController.registerControl(admin, queryName, queryParameterName, parameterValue)) {    // return true if registration succeded

            return "store builder page";
        } else {
            String errorMessage = "user name already exists";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMessage));
            return "";
        }
    }

    public String userRegister() {                 // can't make generice since this fn used in xhtml and can't take paremters
        Users user = new Users(this.name, this.password);
        String queryName = "Users.findByUserName";
        String queryParameterName = "userName";
        String parameterValue = this.name;

        this.userName = this.name;

        if (this.name.length() < 4 || this.password.length() < 4) {
            String errorMessage = "user name and password must contain at least 4 letters!";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMessage));
            return "";
        }

        Auth authController = new Auth();
        if (authController.registerControl(user, queryName, queryParameterName, parameterValue)) {    // return true if registration succeded

            return "home page";
        } else {
            String errorMessage = "user name already exists";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMessage));
            return "";
        }
    }
}
