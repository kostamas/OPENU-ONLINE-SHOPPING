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
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import model.TransactionQueary;

@ManagedBean
@RequestScoped
public class TransactionCtrl {
    
    private List<UsersCart> userCart;
    
    public void transactionHandler(List<UsersCart> userCart, int cost, String userName) {
        TransactionQueary transactionDB = new TransactionQueary();
        int transactionId = transactionDB.createTransactionId();
        Transactions transaction = new Transactions(transactionId, userName, cost);
        transactionDB.saveTransaction(transaction);
        for (UsersCart product : userCart) {
            ProductsSoldPK productsSoldPK = new ProductsSoldPK(transactionId, product.getUsersCartPK().getProductId());
            ProductsSold productSold;
            productSold = new ProductsSold(productsSoldPK, product.getQuantity(), product.getStoreId(), product.getProductPrice());
            transactionDB.savSoldProduct(productSold);
        }
    }
    
}
