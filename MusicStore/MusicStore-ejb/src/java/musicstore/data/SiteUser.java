/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "SiteUser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SiteUser.findAll", query = "SELECT s FROM SiteUser s"),
    @NamedQuery(name = "SiteUser.findByLogin", query = "SELECT s FROM SiteUser s WHERE s.login = :login"),
    @NamedQuery(name = "SiteUser.findByPassw", query = "SELECT s FROM SiteUser s WHERE s.passw = :passw")})
public class SiteUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Passw")
    private String passw;
    @JoinTable(name = "SiteUserRole", joinColumns = {
        @JoinColumn(name = "UserLogin", referencedColumnName = "Login")}, inverseJoinColumns = {
        @JoinColumn(name = "RoleName", referencedColumnName = "Name")})
    @ManyToMany
    private List<SiteRole> siteRoleList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "siteUser")
    private Customer customer;

    public SiteUser() {
    }

    public SiteUser(String login) {
        this.login = login;
    }

    public SiteUser(String login, String passw) {
        this.login = login;
        this.passw = passw;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    @XmlTransient
    public List<SiteRole> getSiteRoleList() {
        return siteRoleList;
    }

    public void setSiteRoleList(List<SiteRole> siteRoleList) {
        this.siteRoleList = siteRoleList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SiteUser)) {
            return false;
        }
        SiteUser other = (SiteUser) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "musicstore.data.SiteUser[ login=" + login + " ]";
    }
    
}
