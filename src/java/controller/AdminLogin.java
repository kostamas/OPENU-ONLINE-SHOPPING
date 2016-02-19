/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.bean.*;

@ManagedBean
@ViewScoped
public class AdminLogin {
    private String adminName;
    private String password;

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
     public String checkAdminAuth(){
        if(this.adminName.equals("tr") && this.password.equals("123")){
            return "home page";
        }else{
            return "";
        }
    }
}
