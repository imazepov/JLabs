/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.model;

import java.security.InvalidParameterException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author ivan
 */
public class Customer extends Model {
    private String firstName;
    private String lastName;
    private String idCode;
    private Date registerDate;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
//        if(idCode.length() != 20) {
//            throw new InvalidParameterException();
//        }
        this.idCode = idCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getRegisterDate() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(registerDate.getTime());        
        return cal;
    }

    public void setRegisterDate(Calendar calendar) {
        this.registerDate = new Date(calendar.getTimeInMillis());
    }
    
    public String getFormattedRegisterDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
        return sdf.format(registerDate.getTime());
    }
}
