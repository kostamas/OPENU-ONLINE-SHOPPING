package controller;

import Entities.Administrators;
import Entities.Users;
import javax.faces.bean.*;
import model.Auth;

@ManagedBean
@ViewScoped
public class LoginBean {

    public static String userName ;
    public static String adminName ;
    
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
        String queryName = "Administrators.login";
        String firstQueryParameterName = "adminName";
        String firstParameterValue = this.name;
        String secondQueryParameterName = "password";
        String secondParameterValue = this.password;
        
        this.adminName = this.name;
        
        Auth auth = new Auth();
        if (auth.loginControl(queryName,firstQueryParameterName,firstParameterValue,secondQueryParameterName,secondParameterValue)) {
            return "store builder page";
        } else {
            return "";
        }
    }

    public String userLogin() {                 // can't make generice since this fn used in xhtml and can't take paremters
    String queryName = "Users.login";
        String firstQueryParameterName = "userName";
        String firstParameterValue = this.name;
        String secondQueryParameterName = "password";
        String secondParameterValue = this.password;
        
        this.userName = this.name;
        
        Auth auth = new Auth();
        if (auth.loginControl(queryName,firstQueryParameterName,firstParameterValue,secondQueryParameterName,secondParameterValue)) {
            return "home page";
        } else {
            return "";
        }
    }
}
