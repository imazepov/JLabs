/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.view;

import carrental.management.RentalManager;
import carrental.model.Customer;
import carrental.management.Management;
import java.io.IOException;
import java.util.GregorianCalendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ivan
 */
public class AddCustomerAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Customer customer = (Customer)request.getAttribute("data");
        prepareCustomer(customer);
        
        try {
            RentalManager manager = Management.getManager();
            manager.addCustomer(customer);
        } catch(Exception ex) {
            throw new ServletException(ex);
        }
        
        response.sendRedirect("index.jsp");
    }

    private void prepareCustomer(Customer customer) {
        customer.setRegisterDate(new GregorianCalendar());
        
        String idCode = customer.getIdCode();
        if(idCode.length() > 20) {
            idCode = idCode.substring(0, 20);
            customer.setIdCode(idCode);
        } else if(idCode.length() < 20) {
            // Pad with zeroes
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<20-idCode.length(); i++) {
                sb.append('0');                
            }
            
            sb.append(idCode);
            customer.setIdCode(sb.toString());
        }
    }
}
