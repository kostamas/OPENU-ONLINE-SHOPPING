/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.ProductsSold;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class TransactionsHistory {

    private List<ProductsSold> productSoldList;
    private String transactionDate;
    private String userName;

    public TransactionsHistory() {
    }

    public TransactionsHistory(List<ProductsSold> productSoldList, String transactionDate, String userName) {
        this.productSoldList = productSoldList;
        this.transactionDate = transactionDate;
        this.userName = userName;
    }

    public List<ProductsSold> getProductSoldList() {
        return productSoldList;
    }

    public void setProductSoldList(List<ProductsSold> productSoldList) {
        this.productSoldList = productSoldList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public void addProductSold(ProductsSold productSold){
        this.productSoldList.add(productSold);
    }
}
