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
@Table(name = "ADMINISTRATORS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrators.login", query = "SELECT a FROM Administrators a WHERE a.adminName = :adminName and a.password = :password"),
    @NamedQuery(name = "Administrators.findAll", query = "SELECT a FROM Administrators a"),
    @NamedQuery(name = "Administrators.findByAdminName", query = "SELECT a FROM Administrators a WHERE a.adminName = :adminName"),
    @NamedQuery(name = "Administrators.findByPassword", query = "SELECT a FROM Administrators a WHERE a.password = :password"),
    @NamedQuery(name = "Administrators.findByFirstName", query = "SELECT a FROM Administrators a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Administrators.findByLastName", query = "SELECT a FROM Administrators a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "Administrators.findByCredit", query = "SELECT a FROM Administrators a WHERE a.credit = :credit")})
public class Administrators implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ADMIN_NAME")
    private String adminName;
    @Basic(optional = false)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "CREDIT")
    private Boolean credit;

    public Administrators() {
    }

    public Administrators(String adminName) {
        this.adminName = adminName;
    }

    public Administrators(String adminName, String password) {
        this.adminName = adminName;
        this.password = password;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getCredit() {
        return credit;
    }

    public void setCredit(Boolean credit) {
        this.credit = credit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adminName != null ? adminName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrators)) {
            return false;
        }
        Administrators other = (Administrators) object;
        if ((this.adminName == null && other.adminName != null) || (this.adminName != null && !this.adminName.equals(other.adminName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Administrators[ adminName=" + adminName + " ]";
    }
    
}
