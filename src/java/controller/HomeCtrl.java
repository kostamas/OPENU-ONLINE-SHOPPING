/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.ProductsSold;
import Entities.Stores;
import static controller.BuildStoreBean.currentStoreId;
import static controller.BuildStoreBean.currentStoreName;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.StoreQueary;
import model.TransactionQueary;

@ManagedBean
@RequestScoped
public class HomeCtrl {

    @ManagedProperty(value = "#{param.selectedStoreId}")
    private int selectedStoreId;

    @ManagedProperty(value = "#{param.selectedStoreName}")
    private String selectedStoreName;

    public static int currentStoreId;
    public static String currentStoreName;
    static public String userName;

    private List<Stores> storesList;
    private boolean isLoggedIn;
    private String title;
    private List<ProductsSold> userHistoryList;

    public HomeCtrl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        if (UserBean.userName != null) {
            isLoggedIn = true;
            this.userName = UserBean.userName;
        } else {
            isLoggedIn = false;
        }

        StoreQueary storeQueary = new StoreQueary();
        storesList = storeQueary.getAllStores();   // get

        if (this.isLoggedIn) {
            this.title = "Welcom " + this.userName;
        } else {
            this.title = "Welcome to ONLINE SHOPPING";
        }

        if (this.isLoggedIn) {               // user history
            // implement - user unlogged;
            TransactionQueary transactionDB = new TransactionQueary();
            this.userHistoryList = transactionDB.getUserHistory(this.userName);

//            this.userHistoryList.remove(0);    ?????????
//            this.userHistoryList.remove(0);
        }

    }

    // ****************** setters & getters   ********************//
    public List<ProductsSold> getUserHistoryList() {
        return userHistoryList;
    }

    public void setUserHistoryList(List<ProductsSold> userHistoryList) {
        this.userHistoryList = userHistoryList;
    }

    public String getUserName() {
        return userName;
    }

    public List<Stores> getStoresList() {
        return storesList;
    }

    public void setStoresList(List<Stores> storesList) {
        this.storesList = storesList;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSelectedStoreId() {
        return selectedStoreId;
    }

    public void setSelectedStoreId(int selectedStoreId) {
        this.selectedStoreId = selectedStoreId;
    }

    public String getSelectedStoreName() {
        return selectedStoreName;
    }

    public void setSelectedStoreName(String selectedStoreName) {
        this.selectedStoreName = selectedStoreName;
    }

    // ****************** setters & getters   ********************//
    public String viewStoreProduts() {
        currentStoreId = this.selectedStoreId;       // static ...
        BuildStoreBean.currentStoreId = currentStoreId;    // go to products page with this selected store...

        currentStoreName = this.selectedStoreName;       // static ...
        BuildStoreBean.currentStoreName = currentStoreName;    // go to pro
        return "home products page";
    }

    public void userHistory() {

    }

}
