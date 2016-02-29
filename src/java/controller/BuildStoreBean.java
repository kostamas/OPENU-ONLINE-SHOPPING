/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Stores;
import static java.awt.PageAttributes.MediaType.C;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.faces.bean.*;
import javax.servlet.http.Part;
import model.StoreBuilder;

@ManagedBean
@ViewScoped
public class BuildStoreBean {

    public static int storeId;
    private String storeName;
    private String storePhoto;
    private String storeAdmin;
    private String description;
    private Part file;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStorePhoto() {
        return storePhoto;
    }

    public void setStorePhoto(String storePhoto) {
        this.storePhoto = storePhoto;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAdmin() {
        return storeAdmin;
    }

    public void setStoreAdmin(String storeAdmin) {
        this.storeAdmin = storeAdmin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String createNewStore() {
        if (this.description == null || this.storeName == null || this.file == null) {
            return null;
        }

        StoreBuilder storeDB = new StoreBuilder();

        this.storeId = storeDB.createStoreId() + 1;

        String adminName;
        if (RegisterBean.adminName != null) {
            adminName = RegisterBean.adminName;
        } else {
            adminName = LoginBean.adminName;
        }

        String dirPath = "C:\\onlineShopping\\" + this.storeId;
        new File(dirPath).mkdir();
        this.storePhoto = this.storeId + "store.jpg";

        Stores newStore = new Stores(this.storeId, this.storeName, adminName, this.description, this.storePhoto);
        storeDB.save(newStore);
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, new File(dirPath, this.storePhoto).toPath());
        } catch (IOException e) {
            // Show faces message?
        }

        return "build products page";
    }

}
