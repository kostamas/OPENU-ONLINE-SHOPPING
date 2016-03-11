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
@Table(name = "TRANSACTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t"),
    @NamedQuery(name = "Transactions.findByTransactionId", query = "SELECT t FROM Transactions t WHERE t.transactionId = :transactionId"),
    @NamedQuery(name = "Transactions.findByTotalCost", query = "SELECT t FROM Transactions t WHERE t.totalCost = :totalCost"),
    @NamedQuery(name = "Transactions.findByUserName", query = "SELECT t FROM Transactions t WHERE t.userName = :userName")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TRANSACTION_ID")
    private Integer transactionId;
    @Basic(optional = false)
    @Column(name = "TOTAL_COST")
    private int totalCost;
    @Column(name = "USER_NAME")
    private String userName;

    public Transactions() {
    }

    public Transactions(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Transactions(Integer transactionId, int totalCost) {
        this.transactionId = transactionId;
        this.totalCost = totalCost;
    }
    
     public Transactions(Integer transactionId, String userName, int totalCost) {
        this.transactionId = transactionId;
        this.totalCost = totalCost;
        this.userName = userName;
    }


    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Transactions[ transactionId=" + transactionId + " ]";
    }
    
}
