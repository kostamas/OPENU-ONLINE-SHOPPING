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
import Entities.exceptions.NonexistentEntityException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.ProductQueary;
import model.UserCartQuary;

@ManagedBean
@RequestScoped
public class Checkout {

    @ManagedProperty(value = "#{param.selectedProductId}")
    private int selectedProductId;
    List<UsersCart> cartList;
    String userName;
    UsersCartJpaController userCartrl;
    ProductsJpaController productsCtrl;
    private int cost;

    public Checkout() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        cartList = UserCartQuary.getUserCart(HomeCtrl.userName);
        userCartrl = new UsersCartJpaController(emf);
        productsCtrl = new ProductsJpaController(emf);

        calcTotalCost();

    }

    // ****************** setters & getters   ********************//
    public List<UsersCart> getCartList() {
        return cartList;
    }

    public void setCartList(List<UsersCart> cartList) {
        this.cartList = cartList;
    }

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

    // ****************** setters & getters   ********************//
    public void deleteOrder() throws Exception {
        Products product = new ProductQueary().getProductByProductId(this.selectedProductId).get(0);   // get product object
        product.setStock(product.getStock() + 1); // stock++;
        productsCtrl.edit(product);                        // updateDb

        UsersCart currentProduct = null;
        boolean productRemove = false;

        for (UsersCart productTemp : this.cartList) {
            if (this.selectedProductId == productTemp.getUsersCartPK().getProductId()) {
                currentProduct
                        = currentProduct = productTemp;

                if (currentProduct.getQuantity() < 2) {
                    productRemove = true;
                } else {
                    productRemove = false;
                }
            }
        }
        if (productRemove) {
            userCartrl.destroy(currentProduct.getUsersCartPK());       // update DB
            this.cartList.remove(currentProduct);
        } else {
            currentProduct.setQuantity(currentProduct.getQuantity() - 1);
            userCartrl.edit(currentProduct);
        }
        this.cartList = UserCartQuary.getUserCart(HomeCtrl.userName);   // get updated list
        calcTotalCost();
    }

    public void userPay() throws NonexistentEntityException {
        TransactionCtrl transactionCtrl = new TransactionCtrl();
        transactionCtrl.transactionHandler(this.cartList, this.cost, HomeCtrl.userName);
        transactionCtrl.emptyUserCart(this.cartList, userCartrl);
        this.cartList.clear();
        this.cost = 0;
    }

    public void calcTotalCost() {
        this.cost = 0;
        for (UsersCart product : this.cartList) {
            this.cost += product.getQuantity() * product.getProductPrice();
        }
    }
}
