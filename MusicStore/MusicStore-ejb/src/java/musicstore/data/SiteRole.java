/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicstore.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ivan
 */
@Entity
@Table(name = "SiteRole")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SiteRole.findAll", query = "SELECT s FROM SiteRole s"),
    @NamedQuery(name = "SiteRole.findByName", query = "SELECT s FROM SiteRole s WHERE s.name = :name")})
public class SiteRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Name")
    private String name;
    @JoinTable(name = "SiteUserRole", joinColumns = {
        @JoinColumn(name = "RoleName", referencedColumnName = "Name")}, inverseJoinColumns = {
        @JoinColumn(name = "UserLogin", referencedColumnName = "Login")})
    @ManyToMany
    private List<SiteUser> siteUserList;

    public SiteRole() {
    }

    public SiteRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<SiteUser> getSiteUserList() {
        return siteUserList;
    }

    public void setSiteUserList(List<SiteUser> siteUserList) {
        this.siteUserList = siteUserList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SiteRole)) {
            return false;
        }
        SiteRole other = (SiteRole) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "musicstore.data.SiteRole[ name=" + name + " ]";
    }
    
}
