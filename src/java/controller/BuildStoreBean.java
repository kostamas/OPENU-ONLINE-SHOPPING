/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Products;
import Entities.ProductsJpaController;
import Entities.Stores;
import Entities.StoresJpaController;
import Entities.exceptions.NonexistentEntityException;
import static java.awt.PageAttributes.MediaType.C;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Part;
import model.StoreBuilder;
import model.StoreQueary;

@ManagedBean
@RequestScoped

public class BuildStoreBean {

    @ManagedProperty(value = "#{param.selectedStoreId}")
    private int selectedStoreId;

    @ManagedProperty(value = "#{param.selectedStoreName}")
    private String selectedStoreName;

    public static int currentStoreId;
    public static String currentStoreName;
    StoresJpaController storeCtrl;
    private List<Stores> storesList;
    private int storeId;
    private String storeName;
    private String storePhoto;
    private String storeAdmin;
    private String description;
    private Part file;

    public BuildStoreBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        storeCtrl = new StoresJpaController(emf);            // crud

        StoreQueary storeQueary = new StoreQueary();
        storesList = storeQueary.getStoresByAdmin(this.currentStoreName);   // get all admins stores
    }

    /**
     * ******************** seeters & getters ********************
     */
    public int getSelectedStoreId() {
        return selectedStoreId;
    }

    public List<Stores> getStoresList() {
        return storesList;
    }

    public void setStoresList(List<Stores> storesList) {
        this.storesList = storesList;
    }

    public void setSelectedStoreId(int selectedStoreId) {
        this.selectedStoreId = selectedStoreId;
    }

    public int getStoreId() {
        return currentStoreId;
    }

    public void setStoreId(int storeId) {
        this.currentStoreId = storeId;
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

    public void saveCurrentStore() {            // invoked from client...
        currentStoreId = this.selectedStoreId;
        currentStoreName = this.selectedStoreName;

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

    public void deleteStore() {
        try {
            Stores storeToDelete = getStoreById(this.selectedStoreId);
            deleteAllStoreProds(this.selectedStoreId);
            storesList.remove(storeToDelete);
            storeCtrl.destroy(this.selectedStoreId);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BuildStoreBean.class.getName()).log(Level.SEVERE, null, ex);
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

    private void deleteAllStoreProds(int storeIdToDelete) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        EntityManager em;
        ProductsJpaController productsJpaCtrl = new ProductsJpaController(emf);
        em = emf.createEntityManager();

        List<Products> prodList = em.createNamedQuery("Products.findByStoreId")
                .setParameter("storeId", storeIdToDelete)
                .getResultList();

        for (int i = 0; i < prodList.size(); i++) {
            try {
                productsJpaCtrl.destroy(prodList.get(i).getProductId());
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(BuildStoreBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
