/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model.jpa.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Ivan
 */
@Entity
@Table(name = "car")
@NamedQueries({
    @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c")})
public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Make")
    private String make;
    @Column(name = "Model")
    private String model;
    @Column(name = "Year")
    private Integer year;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EngineVolume")
    private Float engineVolume;
    @OneToMany(mappedBy = "carID")
    private List<CarRental> carRentalList;

    public Car() {
    }

    public Car(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(Float engineVolume) {
        this.engineVolume = engineVolume;
    }

    public List<CarRental> getCarRentalList() {
        return carRentalList;
    }

    public void setCarRentalList(List<CarRental> carRentalList) {
        this.carRentalList = carRentalList;
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
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "carrental.model.jpa.Car[ id=" + id + " ]";
    }
    
}
