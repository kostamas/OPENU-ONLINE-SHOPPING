package controller;

import Entities.Administrators;
import Entities.Users;
import java.util.Timer;
import java.util.TimerTask;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import model.Auth;

@ManagedBean
@ViewScoped
public class LoginBean {

    public static String userName;
    public static String adminName;

    private String name;
    private String password;

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

    public String adminLogin() {                 // can't make generice since this fn used in xhtml and can't take paremters
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String queryName = "Administrators.login";
        String firstQueryParameterName = "adminName";
        String firstParameterValue = this.name;
        String secondQueryParameterName = "password";
        String secondParameterValue = this.password;

        this.adminName = this.name;

        Auth auth = new Auth();
        if (auth.loginControl(queryName, firstQueryParameterName, firstParameterValue, secondQueryParameterName, secondParameterValue)) {
            return "store builder page";
        } else {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("wrong password or user name"));
            return "";
        }
    }

    public String userLogin() {                 // can't make generice since this fn used in xhtml and can't take paremters
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String queryName = "Users.login";
        String firstQueryParameterName = "userName";
        String firstParameterValue = this.name;
        String secondQueryParameterName = "password";
        String secondParameterValue = this.password;
        this.userName = this.name;

        Auth auth = new Auth();
        if (auth.loginControl(queryName, firstQueryParameterName, firstParameterValue, secondQueryParameterName, secondParameterValue)) {
            return "home page";
        } else {
            String errorMessage = "wrong password or user name";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMessage));
            return "";
        }
    }
}
