package controller;

import javax.faces.bean.*;

@ManagedBean
@ViewScoped
public class LoginBean {

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
//
//    public String adminLogin() {                 // can't make generice since this fn used in xhtml and can't take paremters
//        if (AuthController.isExits(this.name) && AuthController.isExits(this.password)) {
//            return "store builder page";
//        } else {
//            return "";
//        }
//    }

    public String userLogin() {                 // can't make generice since this fn used in xhtml and can't take paremters
//        if (AuthController.isExits(this.name) && AuthController.isExits(this.password)) {
//            return "home page";
//        } else {
//            return "";
//        }
        return "";
    }
}
