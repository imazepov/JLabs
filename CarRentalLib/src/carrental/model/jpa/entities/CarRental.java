/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Ivan
 */
@Entity
@Table(name = "carrental")
@NamedQueries({
    @NamedQuery(name = "CarRental.findAll", query = "SELECT c FROM CarRental c")})
public class CarRental implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "StartDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "Days")
    private Integer days;
    @JoinColumn(name = "CustomerID", referencedColumnName = "ID")
    @ManyToOne
    private Customer customerID;
    @JoinColumn(name = "CarID", referencedColumnName = "ID")
    @ManyToOne
    private Car carID;

    public CarRental() {
    }

    public CarRental(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }

    public Car getCarID() {
        return carID;
    }

    public void setCarID(Car carID) {
        this.carID = carID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarRental)) {
            return false;
        }
        CarRental other = (CarRental) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "carrental.model.jpa.CarRental[ id=" + id + " ]";
    }
    
}
