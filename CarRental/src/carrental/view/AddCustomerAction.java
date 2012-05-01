/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.view;

import carrental.model.ConnectionPool;
import carrental.model.Customer;
import carrental.model.adapters.CustomerAdapter;
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
        
        try {
            CustomerAdapter adapter = new CustomerAdapter(ConnectionPool.getConnection());
            adapter.addObject(customer);
        } catch(Exception ex) {
            throw new ServletException(ex);
        }
        
        response.sendRedirect("index.jsp");
    }
}
