/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Auth;
import model.Login;

@ManagedBean(name = "login")
@ViewScoped
public class LoginController {

    private String name;
    private String password;

    private final Auth query = new Auth();

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void loginControl() {
        //        if(query.loginControl(this.username,this.password)){
        //            return "home page";
        //        }
        //        return "";
        Login login = new Login(this.name, this.password);
        
        
    }
}
