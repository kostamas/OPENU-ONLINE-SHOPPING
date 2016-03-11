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
@Table(name = "PRODUCTS_SOLD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductsSold.findAll", query = "SELECT p FROM ProductsSold p"),
    @NamedQuery(name = "ProductsSold.findByTransactionId", query = "SELECT p FROM ProductsSold p WHERE p.productsSoldPK.transactionId = :transactionId"),
    @NamedQuery(name = "ProductsSold.findByProductId", query = "SELECT p FROM ProductsSold p WHERE p.productsSoldPK.productId = :productId"),
    @NamedQuery(name = "ProductsSold.findByQuantity", query = "SELECT p FROM ProductsSold p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "ProductsSold.findByStoreId", query = "SELECT p FROM ProductsSold p WHERE p.storeId = :storeId"),
    @NamedQuery(name = "ProductsSold.findByPrice", query = "SELECT p FROM ProductsSold p WHERE p.price = :price")})
public class ProductsSold implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductsSoldPK productsSoldPK;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private int quantity;
    @Basic(optional = false)
    @Column(name = "STORE_ID")
    private int storeId;
    @Basic(optional = false)
    @Column(name = "PRICE")
    private int price;

    public ProductsSold() {
    }

    public ProductsSold(ProductsSoldPK productsSoldPK) {
        this.productsSoldPK = productsSoldPK;
    }

    public ProductsSold(ProductsSoldPK productsSoldPK, int quantity, int storeId, int price) {
        this.productsSoldPK = productsSoldPK;
        this.quantity = quantity;
        this.storeId = storeId;
        this.price = price;
    }

    public ProductsSold(int transactionId, int productId) {
        this.productsSoldPK = new ProductsSoldPK(transactionId, productId);
    }

    public ProductsSoldPK getProductsSoldPK() {
        return productsSoldPK;
    }

    public void setProductsSoldPK(ProductsSoldPK productsSoldPK) {
        this.productsSoldPK = productsSoldPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productsSoldPK != null ? productsSoldPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductsSold)) {
            return false;
        }
        ProductsSold other = (ProductsSold) object;
        if ((this.productsSoldPK == null && other.productsSoldPK != null) || (this.productsSoldPK != null && !this.productsSoldPK.equals(other.productsSoldPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ProductsSold[ productsSoldPK=" + productsSoldPK + " ]";
    }
    
}
