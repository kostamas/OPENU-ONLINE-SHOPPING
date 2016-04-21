/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Stores;
import Entities.StoresJpaController;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Part;
import model.StoreQueary;

@ManagedBean
@RequestScoped
public class StoresViewer {

    StoresJpaController storeCtrl;
    private List<Stores> storesList;
    @ManagedProperty(value = "#{param.selectedStoreId}")
    private int selectedStoreId;
    static private int currentStoreId;    // patch- if it's not static we goona lose store id becuase
    @ManagedProperty(value = "#{param.selectedStoreName}")
    private String selectedStoreName;
    static private String currentStoreName;    // patch- if it's not static we goona lose store id becuase
    private String storeName;              // at some point (when update method invoked) all variables values changing to zero
    private String storePhoto;
    private Part file;
    private String description;
    private String adminName;                         // get admin name

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhoto() {
        return storePhoto;
    }

    public void setStorePhoto(String storePhoto) {
        this.storePhoto = storePhoto;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StoresViewer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        storeCtrl = new StoresJpaController(emf);            // crud
        if (UserBean.adminName != null) {
            this.adminName = UserBean.adminName;
        }
        StoreQueary storeQueary = new StoreQueary();
        storesList = storeQueary.getStoresByAdmin(this.adminName);   // get all admins stores
    }

    public int getSelectedStoreId() {
        return selectedStoreId;
    }

    public void setSelectedStoreId(int selectedStoreId) {
        this.selectedStoreId = selectedStoreId;
    }

    public List<Stores> getStores() {
        return storesList;
    }

    public void setStores(List<Stores> stores) {
        this.storesList = stores;
    }

    public void saveCurrentStore() {            // invoked from client...
        currentStoreId = this.selectedStoreId;
        currentStoreName = this.selectedStoreName;

    }

    public String getSelectedStoreName() {
        return selectedStoreName;
    }

    public void setSelectedStoreName(String selectedStoreName) {
        this.selectedStoreName = selectedStoreName;
    }

    public void update() {

        Stores storeToUpdate = getStoreById(this.currentStoreId);

        if (this.description.length() > 0) {
            storeToUpdate.setDescription(this.description);
        }
        if (this.storeName.length() > 0) {
            storeToUpdate.setStoreName(this.storeName);
        }
        try {
            storeCtrl.edit(storeToUpdate);
        } catch (Exception ex) {
            Logger.getLogger(StoresViewer.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (this.file != null) {
            String dirPath = "C:\\onlineShopping\\" + storeToUpdate.getStoreId();
            new File(dirPath).mkdir();
            File oldImage = new File(dirPath + "\\" + storeToUpdate.getStorePhoto());     // deleting old image
            oldImage.delete();

            try (InputStream input = this.file.getInputStream()) {
                Files.copy(input, new File(dirPath, storeToUpdate.getStorePhoto()).toPath());
            } catch (IOException e) {
                // Show faces message?
            }
        }
    }

    private Stores getStoreById(int id) {
        for (Stores tmpStore : storesList) {
            if (tmpStore.getStoreId() == id) {
                return tmpStore;
            }
        }
        return null;
    }

    public String viewStoreProduts() {
        currentStoreId = this.selectedStoreId;       // static ...
        BuildStoreBean.currentStoreId = currentStoreId;    // go to products page with this selected store...

        currentStoreName = this.selectedStoreName;       // static ...
        BuildStoreBean.currentStoreName = currentStoreName;    // go to pro
        return "build products page";
    }
}
