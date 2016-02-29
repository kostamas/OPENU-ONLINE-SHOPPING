/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Stores;
import Entities.StoresJpaController;
import static controller.LoginBean.adminName;
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

@ManagedBean
@RequestScoped
public class StoresViewer {

    StoresJpaController storeCtrl;
    private List<Stores> stores;
    @ManagedProperty(value = "#{param.storeId}")
    private int storeId; // +setter
    static private int currentStoreId;    // hack- if it's not static at some point (when update method invoked)
    private String storeName;              // all variables values changing to zero
    private String storePhoto;
    private Part file;
    private String description;

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
        storeCtrl = new StoresJpaController(emf);
        stores = storeCtrl.findStoresEntities();
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public List<Stores> getStores() {
        return stores;
    }

    public void setStores(List<Stores> stores) {
        this.stores = stores;
    }

    public void saveStoreId() {
        currentStoreId = this.storeId;
    }

    public void update() {

        Stores storeToUpdate;
        storeToUpdate = getStoreById(this.currentStoreId);
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
         String dirPath = "C:\\onlineShopping\\" + storeToUpdate.getStoreId();
        new File(dirPath).mkdir();
        
        if (this.file != null) {
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
        for (Stores tmpStore : stores) {
            if (tmpStore.getStoreId() == id) {
                return tmpStore;
            }
        }
        return null;
    }
}
