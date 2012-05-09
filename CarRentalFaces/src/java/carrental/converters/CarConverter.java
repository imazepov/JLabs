/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.converters;

import carrental.management.Management;
import carrental.management.ManagementException;
import carrental.management.RentalManager;
import carrental.model.Car;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ivan
 */
@FacesConverter(forClass=Car.class)
public class CarConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
            
        try {
            RentalManager manager = Management.getManager();
            return manager.getCarById(Integer.valueOf(value));
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(!(value instanceof Car)) {
            return null;
        }
        
        return Integer.toString(((Car)value).getId());
    }    
}
