package controller;

import Entities.Administrators;
import Entities.Users;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import model.Auth;

@ManagedBean
@ViewScoped
public class RegisterBean {

    public String name;
    public String password;
    public String errorMessage;
    public static String userName;
    public static String adminName;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

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

        if (this.name.length() < 4 || this.password.length() < 4) {
            this.errorMessage = "user name and password must contain at least 4 letters!";
            return "";
        }

        Auth authController = new Auth();
        if (authController.registerControl(admin, queryName, queryParameterName, parameterValue)) { 
            // return true if registration succeded
            this.adminName = this.name;
            return "build store page";
        } else {
            this.errorMessage = "user name already exists";
            return "";
        }
    }

    public String userRegister() throws IOException {                 // can't make generice since this fn used in xhtml and can't take paremters
        Users user = new Users(this.name, this.password);
        String queryName = "Users.findByUserName";
        String queryParameterName = "userName";
        String parameterValue = this.name;

        if (this.name.length() < 4 || this.password.length() < 4) {
            this.errorMessage = "user name and password must contain at least 4 letters!";
            return "";
        }

        Auth authController = new Auth();
        if (authController.registerControl(user, queryName, queryParameterName, parameterValue)) {
            // return true if registration succeded
            this.userName = this.name; // static variable
            return "home page";
        } else {
            this.errorMessage = "user name already exists";
            return "";
        }
    }
}
