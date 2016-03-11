/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "PRODUCTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Products.findByProductIdWithQuantity", 
            query = "SELECT p FROM Products p WHERE p.storeId = :storeId and p.stock > 0"),
    @NamedQuery(name = "Products.findAll", query = "SELECT p FROM Products p"),
    @NamedQuery(name = "Products.findByProductId", query = "SELECT p FROM Products p WHERE p.productId = :productId"),
    @NamedQuery(name = "Products.findByProductName", query = "SELECT p FROM Products p WHERE p.productName = :productName"),
    @NamedQuery(name = "Products.findByPrice", query = "SELECT p FROM Products p WHERE p.price = :price"),
    @NamedQuery(name = "Products.findByStock", query = "SELECT p FROM Products p WHERE p.stock = :stock"),
    @NamedQuery(name = "Products.findByPhoto", query = "SELECT p FROM Products p WHERE p.photo = :photo"),
    @NamedQuery(name = "Products.findByStoreId", query = "SELECT p FROM Products p WHERE p.storeId = :storeId"),
    @NamedQuery(name = "Products.findByDescription", query = "SELECT p FROM Products p WHERE p.description = :description")})
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PRODUCT_ID")
    private Integer productId;
    @Basic(optional = false)
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Basic(optional = false)
    @Column(name = "PRICE")
    private int price;
    @Basic(optional = false)
    @Column(name = "STOCK")
    private int stock;
    @Basic(optional = false)
    @Column(name = "PHOTO")
    private String photo;
    @Basic(optional = false)
    @Column(name = "STORE_ID")
    private int storeId;
    @Column(name = "DESCRIPTION")
    private String description;

    public Products() {
    }

    public Products(Integer productId) {
        this.productId = productId;
    }

    public Products(Integer productId, String productName, int price, int stock, String photo, int storeId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.photo = photo;
        this.storeId = storeId;
    }
    
      public Products(Integer productId, String productName, int price, int stock, String photo, int storeId, String description) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.photo = photo;
        this.storeId = storeId;
        this.description = description;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Products)) {
            return false;
        }
        Products other = (Products) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Products[ productId=" + productId + " ]";
    }
    
}
