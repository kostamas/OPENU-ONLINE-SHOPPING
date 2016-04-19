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
@RequestScoped
public class LoginBean {

    @ManagedProperty(value = "#{param.name}")
    private String name;
    @ManagedProperty(value = "#{param.password}")
    private String password;
    
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

    public void adminLogin() throws IOException {                 // can't make generice since this fn used in xhtml and can't take paremters
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String queryName = "Administrators.login";
        String firstQueryParameterName = "adminName";
        String firstParameterValue = this.name;
        String secondQueryParameterName = "password";
        String secondParameterValue = this.password;

        this.adminName = this.name;

        Auth auth = new Auth();
        if (auth.loginControl(queryName, firstQueryParameterName, firstParameterValue, secondQueryParameterName, secondParameterValue)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        } else {
            this.errorMessage = "wrong password or user name";
            return;
        }
    }

    public void userLogin() throws IOException {                 // can't make generice since this fn used in xhtml and can't take paremters
        String queryName = "Users.login";
        String firstQueryParameterName = "userName";
        String firstParameterValue = this.name;
        String secondQueryParameterName = "password";
        String secondParameterValue = this.password;
        this.userName = this.name;

        Auth auth = new Auth();
        if (auth.loginControl(queryName, firstQueryParameterName, firstParameterValue, secondQueryParameterName, secondParameterValue)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
        } else {
            this.errorMessage = "wrong password or user name";
            return;
        }
    }
}
