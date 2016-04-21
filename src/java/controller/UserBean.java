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
public class UserBean {

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

    public String userLogin() {                 // can't make generice since this fn used in xhtml and can't take paremters
        String queryName = "Users.login";
        String firstQueryParameterName = "userName";
        String firstParameterValue = this.name;
        String secondQueryParameterName = "password";
        String secondParameterValue = this.password;
        this.errorMessage = "";
        
        if (this.name.length() < 4 || this.password.length() < 4) {
            this.errorMessage = "user name and password must contain at least 4 letters!";
            return "";
        }

        Auth auth = new Auth();
        if (auth.loginControl(queryName, firstQueryParameterName, firstParameterValue, secondQueryParameterName, secondParameterValue)) {
            // login seccuedde
            this.userName = this.name;  // static variable
            return "home page";
        } else {
            this.errorMessage = "wrong password or user name";
            return "";
        }
    }

    public String userRegister() throws IOException {                 // can't make generice since this fn used in xhtml and can't take paremters
        Users user = new Users(this.name, this.password);
        String queryName = "Users.findByUserName";
        String queryParameterName = "userName";
        String parameterValue = this.name;
        this.errorMessage = "";

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
