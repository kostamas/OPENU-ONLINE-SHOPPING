package controller;

import Entities.Administrators;
import Entities.Users;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import model.Auth;

@ManagedBean
@ViewScoped
public class AdminBean {

    public String name;
    public String password;
    public static String userName;
    public static String adminName;
    public String errorMessage;

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

    public String adminLogin() throws IOException {     // can't make generice since this fn used in xhtml and can't take paremters
        this.errorMessage = "";
        String queryName = "Administrators.login";
        String firstQueryParameterName = "adminName";
        String firstParameterValue = this.name;
        String secondQueryParameterName = "password";
        String secondParameterValue = this.password;

         if (this.name.length() < 4 || this.password.length() < 4) {
            this.errorMessage = "user name and password must contain at least 4 letters!";
            return "";
        }
         
        Auth auth = new Auth();
        if (auth.loginControl(queryName, firstQueryParameterName, firstParameterValue, secondQueryParameterName, secondParameterValue)) {
            this.adminName = this.name;
            return "build store page";
        } else {
            this.errorMessage = "wrong password or user name";
            return "";
        }
    }

  public String adminRegister() {        // can't make generice since this fn used in xhtml and can't take paremters
        this.errorMessage = "";
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
}
