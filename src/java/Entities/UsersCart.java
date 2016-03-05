/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "USERS_CART")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersCart.findUserCart", query = "SELECT u FROM UsersCart u WHERE u.usersCartPK.userName = :userName and u.usersCartPK.productId = :productId"),
    @NamedQuery(name = "UsersCart.findAll", query = "SELECT u FROM UsersCart u"),
    @NamedQuery(name = "UsersCart.findByUserName", query = "SELECT u FROM UsersCart u WHERE u.usersCartPK.userName = :userName"),
    @NamedQuery(name = "UsersCart.findByProductId", query = "SELECT u FROM UsersCart u WHERE u.usersCartPK.productId = :productId"),
    @NamedQuery(name = "UsersCart.findByQuantity", query = "SELECT u FROM UsersCart u WHERE u.quantity = :quantity"),
    @NamedQuery(name = "UsersCart.findByProductPrice", query = "SELECT u FROM UsersCart u WHERE u.productPrice = :productPrice"),
    @NamedQuery(name = "UsersCart.findByStoreId", query = "SELECT u FROM UsersCart u WHERE u.storeId = :storeId"),
    @NamedQuery(name = "UsersCart.findByProductName", query = "SELECT u FROM UsersCart u WHERE u.productName = :productName"),
    @NamedQuery(name = "UsersCart.findByStoreName", query = "SELECT u FROM UsersCart u WHERE u.storeName = :storeName")})
public class UsersCart implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsersCartPK usersCartPK;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "PRODUCT_PRICE")
    private Integer productPrice;
    @Column(name = "STORE_ID")
    private Integer storeId;
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Column(name = "STORE_NAME")
    private String storeName;

    public UsersCart() {
    }

    public UsersCart(UsersCartPK usersCartPK) {
        this.usersCartPK = usersCartPK;
    }

    public UsersCart(UsersCartPK usersCartPK, int price, int storeId, String prodName, String storeName) {
        this.usersCartPK = usersCartPK;
        this.quantity = 1;
        this.productPrice = price;
        this.storeId = storeId;
        this.productName = prodName;
        this.storeName = storeName;
    }

    public UsersCart(String userName, int productId) {
        this.usersCartPK = new UsersCartPK(userName, productId);
    }

    public UsersCartPK getUsersCartPK() {
        return usersCartPK;
    }

    public void setUsersCartPK(UsersCartPK usersCartPK) {
        this.usersCartPK = usersCartPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usersCartPK != null ? usersCartPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersCart)) {
            return false;
        }
        UsersCart other = (UsersCart) object;
        if ((this.usersCartPK == null && other.usersCartPK != null) || (this.usersCartPK != null && !this.usersCartPK.equals(other.usersCartPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.UsersCart[ usersCartPK=" + usersCartPK + " ]";
    }

}
