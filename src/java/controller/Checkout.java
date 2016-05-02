/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Administrators;
import Entities.AdministratorsJpaController;
import Entities.Products;
import Entities.ProductsJpaController;
import Entities.Users;
import Entities.UsersCart;
import Entities.UsersCartJpaController;
import Entities.UsersJpaController;
import Entities.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Auth;
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
    private int userCredit;
    private int cost;
    private String errorMessage;

    public Checkout() {
        if (UserBean.userName != null && UserBean.userName.length() > 3) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
            cartList = UserCartQuary.getUserCart(UserBean.userName);
            userCartrl = new UsersCartJpaController(emf);
            productsCtrl = new ProductsJpaController(emf);
            calcTotalCost();
        }
    }

    // ****************** setters & getters   ********************//
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(int userCredit) {
        this.userCredit = userCredit;
    }

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
        this.errorMessage = "no user credit";
        if (this.userCredit > 9999999 && this.userCredit < 100000000) {
            TransactionCtrl transactionCtrl = new TransactionCtrl();
            Date date = new Date();

            transactionCtrl.transactionHandler(this.cartList, this.cost, HomeCtrl.userName, date.toString());
            transactionCtrl.emptyUserCart(this.cartList, userCartrl);
            this.cartList.clear();
            this.cost = 0;
        } else  {
            this.errorMessage = "no user credit";
        }
    }

    public void updateCreditCard() throws Exception {

        Auth authDB = new Auth();
        if (UserBean.userName == null) {
            this.errorMessage = "unknown error accur";
            return;
        }
        if (this.userCredit < 999999 || this.userCredit > 99999999) {
            this.errorMessage = "credit card must be 8 digits.";
            return;
        }

        this.errorMessage = "valid credit";
        Users user = authDB.getUserByUserName(UserBean.userName).get(0);
        user.setCredit(this.userCredit);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        UsersJpaController userJpaCtrl = new UsersJpaController(emf);

        userJpaCtrl.edit(user);
    }

    public void calcTotalCost() {
        this.cost = 0;
        for (UsersCart product : this.cartList) {
            this.cost += product.getQuantity() * product.getProductPrice();
        }
    }
}
