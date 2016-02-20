
package controller;

import javax.faces.bean.*;

@ManagedBean
@ViewScoped
public class RegisterBean {
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
    
    public String adminRegister(){                 // can't make generice since this fn used in xhtml and can't take paremters
        if(DataBaseControoler.isExits(this.name) && DataBaseControoler.isExits(this.password)){
            return "admin area page";
        }else{
            return "";
        }
    }
    
    public String userRegister(){                 // can't make generice since this fn used in xhtml and can't take paremters
        if(DataBaseControoler.isExits(this.name) && DataBaseControoler.isExits(this.password)){
            return "home page";
        }else{
            return "";
        }
    }   
}
