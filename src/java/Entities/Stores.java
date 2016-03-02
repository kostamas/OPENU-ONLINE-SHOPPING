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
@Table(name = "STORES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stores.findByAdmin", query = "SELECT s FROM Stores s WHERE s.storeAdmin = :storeAdmin"),  // opssss... alrady exists!!!
    @NamedQuery(name = "Stores.findAll", query = "SELECT s FROM Stores s"),
    @NamedQuery(name = "Stores.findByStoreId", query = "SELECT s FROM Stores s WHERE s.storeId = :storeId"),
    @NamedQuery(name = "Stores.findByStoreName", query = "SELECT s FROM Stores s WHERE s.storeName = :storeName"),
    @NamedQuery(name = "Stores.findByStoreAdmin", query = "SELECT s FROM Stores s WHERE s.storeAdmin = :storeAdmin"),
    @NamedQuery(name = "Stores.findByDescription", query = "SELECT s FROM Stores s WHERE s.description = :description"),
    @NamedQuery(name = "Stores.findByStorePhoto", query = "SELECT s FROM Stores s WHERE s.storePhoto = :storePhoto")})
public class Stores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "STORE_ID")
    private Integer storeId;
    @Basic(optional = false)
    @Column(name = "STORE_NAME")
    private String storeName;
    @Basic(optional = false)
    @Column(name = "STORE_ADMIN")
    private String storeAdmin;
    @Basic(optional = false)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @Column(name = "STORE_PHOTO")
    private String storePhoto;

    public Stores() {
    }

    public Stores(Integer storeId) {
        this.storeId = storeId;
    }

    public Stores(Integer storeId, String storeName, String storeAdmin, String description, String storePhoto) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAdmin = storeAdmin;
        this.description = description;
        this.storePhoto = storePhoto;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAdmin() {
        return storeAdmin;
    }

    public void setStoreAdmin(String storeAdmin) {
        this.storeAdmin = storeAdmin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStorePhoto() {
        return storePhoto;
    }

    public void setStorePhoto(String storePhoto) {
        this.storePhoto = storePhoto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeId != null ? storeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stores)) {
            return false;
        }
        Stores other = (Stores) object;
        if ((this.storeId == null && other.storeId != null) || (this.storeId != null && !this.storeId.equals(other.storeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Stores[ storeId=" + storeId + " ]";
    }

}
