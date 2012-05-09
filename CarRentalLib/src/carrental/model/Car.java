/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model;

/**
 *
 * @author ivan
 */
public class Car extends Model {    
    private String make;
    private String model;
    private int year;
    private float engineVolume;
    
    public float getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(float engineVolume) {
        this.engineVolume = engineVolume;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    public String toString() {
        return String.format("%s %s %4d", make, model, year);
    }
}
