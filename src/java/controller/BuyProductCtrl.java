/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Products;
import Entities.ProductsJpaController;
import Entities.UsersCart;
import Entities.UsersCartJpaController;
import Entities.UsersCartPK;
import Entities.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.ProductQueary;
import model.UserCartQuary;


@ManagedBean
@RequestScoped
public class BuyProductCtrl {

    @ManagedProperty(value = "#{param.selectedProductId}")
    private int selectedProductId;
    private static int currentProductId;    // static - patch...
    private List<Products> productsList;
    private int storeId;
    private String storeName;
    private UsersCart userCart;
    static public int cost;
    UsersCartJpaController userCartCtrl;
    ProductsJpaController productCtrl;
     

    public BuyProductCtrl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        userCartCtrl = new UsersCartJpaController(emf);
        productCtrl = new ProductsJpaController(emf);
        this.storeId = BuildStoreBean.currentStoreId;
        this.storeName = BuildStoreBean.currentStoreName;
        ProductQueary productQueary = new ProductQueary();
        productsList = productQueary.getProductsByStoreId(this.storeId);
    }

    /**
     * ***************** setters & getters ********************
     */
    public int getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(int selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * ***************** setters & getters ********************
     */
    
    
    public void addToCart() throws NonexistentEntityException, Exception{
        int _selectedProdId = this.selectedProductId;
        List<UsersCart> cartList = null;
        UserCartQuary userCartDB = new UserCartQuary();
                             // return product list that must contain one element
        Products product = new ProductQueary().getProductByProductId(_selectedProdId).get(0);   // get product object
        if(HomeCtrl.userName != null){                                        // user logged in...
           cartList = userCartDB.getUserCart(HomeCtrl.userName, _selectedProdId);  
        }else{
            // popup message- user please login in!!!
            return;
        }
        
        if(cartList.size() > 0) {     // the user already buyed this product
            cartList.get(0).setQuantity(cartList.get(0).getQuantity() + 1);    // quantitiy++
            try {
                userCartCtrl.edit(cartList.get(0));
            } catch (Exception ex) {
                Logger.getLogger(BuyProductCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else {    
            UsersCartPK userCartPk = new UsersCartPK(HomeCtrl.userName, _selectedProdId);
            try {
                userCartCtrl.create(new UsersCart(userCartPk, 1));
            } catch (Exception ex) {
                Logger.getLogger(BuyProductCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.cost += product.getPrice();
        int newStock = product.getStock() - 1;
        if(newStock == 0){
            productCtrl.destroy(product.getProductId());
            productsList.remove(product);
        }else{
         product.setStock(newStock);
         productCtrl.edit(product);
        }  
    }
    
//      private Products getProducteById(int id) {
//        for (Products tmpProduct : productsList) {
//            if (tmpProduct.getProductId() == id) {
//                return tmpProduct;
//            }
//        }
//        return null;
//    }
     
    public String buyProduct(){
        return "";
    }
    
    
}
