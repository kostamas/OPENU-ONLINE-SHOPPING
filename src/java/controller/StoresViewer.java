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
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ManagedBean
@ViewScoped
public class StoresViewer {

    private List<Stores> stores;

    public StoresViewer() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        StoresJpaController storeCtrl = new StoresJpaController(emf);
        stores = storeCtrl.findStoresEntities();
    }

    public List<Stores> getStores() {
        return stores;
    }

    public void setStores(List<Stores> stores) {
        this.stores = stores;
    }
}
