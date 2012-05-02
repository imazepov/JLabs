/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.view;

import carrental.management.RentalManager;
import carrental.model.Car;
import carrental.management.Management;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ivan
 */
public class AddCarAction extends HttpServlet {
    
    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Car car = (Car)request.getAttribute("data");
        
        try {
            RentalManager manager = Management.getManager();
            manager.addCar(car);
        } catch(Exception ex) {
            throw new ServletException(ex);
        }
        
        response.sendRedirect("index.jsp");
    }
}
