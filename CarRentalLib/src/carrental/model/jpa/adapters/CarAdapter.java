/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model.jpa.adapters;

import carrental.model.Car;
import javax.persistence.EntityManager;

/**
 *
 * @author Ivan
 */
public final class CarAdapter {
    private CarAdapter() {        
    }
    
    public static carrental.model.jpa.entities.Car convertCarToEntity(Car car) {
        return convertCarToEntity(car, null);
    }
    
    public static carrental.model.jpa.entities.Car convertCarToEntity(Car car, EntityManager manager) {
        carrental.model.jpa.entities.Car entity = new carrental.model.jpa.entities.Car();
        entity.setId(car.getId());
        entity.setMake(car.getMake());
        entity.setModel(car.getModel());
        entity.setYear(car.getYear());
        entity.setEngineVolume(car.getEngineVolume());
        
        if(manager != null)
            manager.merge(entity);
        
        return entity;
    }
    
    public static Car convertEntityToCar(carrental.model.jpa.entities.Car carEntity) {
        Car car = new Car();
        car.setId(carEntity.getId());
        car.setMake(carEntity.getMake());
        car.setModel(carEntity.getModel());
        car.setYear(carEntity.getYear());
        car.setEngineVolume(carEntity.getEngineVolume());
        
        return car;
    }
}
