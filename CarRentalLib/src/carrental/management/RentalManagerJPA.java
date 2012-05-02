/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.management;

import carrental.model.Car;
import carrental.model.CarRental;
import carrental.model.Customer;
import carrental.model.jpa.adapters.CarAdapter;
import carrental.model.jpa.adapters.CarRentalAdapter;
import carrental.model.jpa.adapters.CustomerAdapter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Ivan
 */
public class RentalManagerJPA implements RentalManager {

    private static EntityManagerFactory factory;
    
    private EntityManager getEntityManager() {
        if(factory == null)
            factory = Persistence.createEntityManagerFactory("CarRental");
        
        return factory.createEntityManager();
    }
    
    @Override
    public void addCar(Car car) throws RentalManagerException {
        carrental.model.jpa.entities.Car carEntity = CarAdapter.convertCarToEntity(car);
        
        persistObject(carEntity);
    }

    @Override
    public void addCustomer(Customer customer) throws RentalManagerException {
        carrental.model.jpa.entities.Customer customerEntity = CustomerAdapter.convertCustomerToEntity(customer);
        
        persistObject(customerEntity);
    }

    @Override
    public void closeRental(CarRental rental) throws RentalManagerException {
        carrental.model.jpa.entities.CarRental carRentalEntity = CarRentalAdapter.convertCarRentalToEntity(rental);
        
        removeObject(carRentalEntity);
    }

    @Override
    public void closeRentalById(int id) throws RentalManagerException {
        carrental.model.jpa.entities.CarRental entity = fetchObjectById(carrental.model.jpa.entities.CarRental.class, id);
        
        removeObject(entity);
    }

    @Override
    public Car getCarById(int id) throws RentalManagerException {
        carrental.model.jpa.entities.Car entity = fetchObjectById(carrental.model.jpa.entities.Car.class, id);
        
        return CarAdapter.convertEntityToCar(entity);
    }

    @Override
    public Customer getCustomerById(int id) throws RentalManagerException {
        carrental.model.jpa.entities.Customer entity = fetchObjectById(carrental.model.jpa.entities.Customer.class, id);
        
        return CustomerAdapter.convertEntityToCustomer(entity);
    }

    @Override
    public List<Car> listCars() throws RentalManagerException {         
        ArrayList<Car> cars = new ArrayList<Car>();
        
        List<carrental.model.jpa.entities.Car> carEntities = fetchAllObjects(carrental.model.jpa.entities.Car.class);
        for(carrental.model.jpa.entities.Car entity : carEntities) {
            cars.add(CarAdapter.convertEntityToCar(entity));
        }
        
        return cars;
    }

    @Override
    public List<Customer> listCustomers() throws RentalManagerException {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        
        List<carrental.model.jpa.entities.Customer> customerEntities = fetchAllObjects(carrental.model.jpa.entities.Customer.class);
        for(carrental.model.jpa.entities.Customer entity : customerEntities) {
            customers.add(CustomerAdapter.convertEntityToCustomer(entity));
        }
        
        return customers;
    }

    @Override
    public List<CarRental> listRentals() throws RentalManagerException {
        ArrayList<CarRental> carRentals = new ArrayList<CarRental>();
        
        List<carrental.model.jpa.entities.CarRental> rentalEntities = fetchAllObjects(carrental.model.jpa.entities.CarRental.class);
        for(carrental.model.jpa.entities.CarRental entity : rentalEntities) {
            carRentals.add(CarRentalAdapter.convertEntityToCarRental(entity));
        }
        
        return carRentals;        
    }

    @Override
    public void removeCar(Car car) throws RentalManagerException {
        carrental.model.jpa.entities.Car entity = CarAdapter.convertCarToEntity(car);
        
        removeObject(entity);
    }

    @Override
    public void removeCar(int id) throws RentalManagerException {
        carrental.model.jpa.entities.Car entity = fetchObjectById(carrental.model.jpa.entities.Car.class, id);
        
        removeObject(entity);
    }

    @Override
    public void removeCustomer(Customer customer) throws RentalManagerException {
        carrental.model.jpa.entities.Customer entity = CustomerAdapter.convertCustomerToEntity(customer);
        
        removeObject(entity);
    }

    @Override
    public void removeCustomer(int id) throws RentalManagerException {
        carrental.model.jpa.entities.Customer entity = fetchObjectById(carrental.model.jpa.entities.Customer.class, id);
        
        removeObject(entity);
    }

    @Override
    public CarRental rent(Customer customer, Car car, int days) throws RentalManagerException {
        carrental.model.jpa.entities.CarRental entity = new carrental.model.jpa.entities.CarRental();
        entity.setCustomerID(CustomerAdapter.convertCustomerToEntity(customer));
        entity.setCarID(CarAdapter.convertCarToEntity(car));
        entity.setDays(days);
        entity.setStartDate(new GregorianCalendar().getTime());
        
        persistObject(entity);
        
        return CarRentalAdapter.convertEntityToCarRental(entity);
    }

    private <T> List<T> fetchAllObjects(Class<T> type) {
        EntityManager entityManager = null;
        
        try{
            entityManager = getEntityManager();
            
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> q = cb.createQuery(type);
            TypedQuery<T> tq = entityManager.createQuery(q);
            
            return tq.getResultList();
        } finally {
            if(entityManager != null)
                entityManager.close();
        }       
    }
    
    private <T> T fetchObjectById(Class<T> type, int id) {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            return entityManager.find(type, id);
        } finally {
            if(entityManager != null)
                entityManager.close();
        }        
    }
    
    private void persistObject(Object obj) throws RentalManagerException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        
        try {
            entityManager = getEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            
            entityManager.persist(obj);
            
            transaction.commit();
        } catch (Exception ex) {
            if(transaction != null)
                transaction.rollback();
            throw new RentalManagerException(ex);            
        } finally {
            if(entityManager != null)
                entityManager.close();
        }
    }
    
    private void removeObject(Object obj) throws RentalManagerException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        
        try {
            entityManager = getEntityManager();
            
            transaction = entityManager.getTransaction();
            transaction.begin();
            
            entityManager.remove(entityManager.merge(obj));
            
            transaction.commit();
        } catch (Exception ex) {
            if(transaction != null)
                transaction.rollback();
            throw new RentalManagerException(ex);
        } finally {
            if(entityManager != null)
                entityManager.close();
        }
    }
    
}