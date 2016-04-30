/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductsSoldPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "TRANSACTION_ID")
    private int transactionId;
    @Basic(optional = false)
    @Column(name = "PRODUCT_ID")
    private int productId;

    public ProductsSoldPK() {
    }

    public ProductsSoldPK(int transactionId, int productId) {
        this.transactionId = transactionId;
        this.productId = productId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) transactionId;
        hash += (int) productId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductsSoldPK)) {
            return false;
        }
        ProductsSoldPK other = (ProductsSoldPK) object;
        if (this.transactionId != other.transactionId) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ProductsSoldPK[ transactionId=" + transactionId + ", productId=" + productId + " ]";
    }
    
}
