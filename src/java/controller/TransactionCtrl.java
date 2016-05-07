/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.ProductsSold;
import Entities.ProductsSoldPK;
import Entities.Transactions;
import Entities.UsersCart;
import Entities.UsersCartJpaController;
import Entities.exceptions.NonexistentEntityException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import model.TransactionQueary;
import model.UserCartQuary;

@ManagedBean
@RequestScoped
public class TransactionCtrl {

    private List<UsersCart> userCart;

    public void transactionHandler(List<UsersCart> userCart, int cost, String userName, String transactionDate) {
        // save transactions
        TransactionQueary transactionDB = new TransactionQueary();
        int transactionId = transactionDB.createTransactionId();
        Transactions transaction = new Transactions(transactionId, userName, cost, transactionDate);
        transactionDB.saveTransaction(transaction);
        // save solds products
        for (UsersCart product : userCart) {
            ProductsSoldPK productsSoldPK = new ProductsSoldPK(transactionId, product.getUsersCartPK().getProductId());
            ProductsSold productSold;
            UserCartQuary userCartDB = new UserCartQuary();
            String adminName = userCartDB.getAdminName(product.getStoreId());
            productSold = new ProductsSold(productsSoldPK, product.getQuantity(), product.getStoreName(), product.getProductPrice(), adminName, product.getProductName());
            transactionDB.savSoldProduct(productSold);
        }
    }

    public void emptyUserCart(List<UsersCart> userCart, UsersCartJpaController userCartrl) throws NonexistentEntityException {
        for (UsersCart product : userCart) {
            userCartrl.destroy(product.getUsersCartPK());
        }
        userCart.clear();
    }

}
