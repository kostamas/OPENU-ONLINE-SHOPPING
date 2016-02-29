/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Stores;
import Entities.StoresJpaController;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@RequestScoped
public class StoresViewer {

    private List<Stores> stores;
    @ManagedProperty(value = "#{param.storeId}")
    private Long storeId; // +setter

    public StoresViewer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        StoresJpaController storeCtrl = new StoresJpaController(emf);
        stores = storeCtrl.findStoresEntities();
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public List<Stores> getStores() {
        return stores;
    }

    public void setStores(List<Stores> stores) {
        this.stores = stores;
    }

    public void saveStoreId() {
        String s="dfds";
    }
}
