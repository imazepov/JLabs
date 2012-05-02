/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.view;

import carrental.management.RentalManager;
import carrental.model.Car;
import carrental.model.Customer;
import carrental.management.Management;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ivan
 */
public class AddRentalAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int carId = Integer.valueOf(request.getParameter("car"));
            int customerId = Integer.valueOf(request.getParameter("customer"));
            int days = Integer.valueOf(request.getParameter("days"));
            
            RentalManager manager = Management.getManager();
            
            Car car = manager.getCarById(carId);
            Customer customer = manager.getCustomerById(customerId);
            
            manager.rent(customer, car, days);
        } catch(Exception ex) {
            throw new ServletException(ex);
        }
        
        response.sendRedirect("index.jsp");
    }
}
