/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.view;

import carrental.management.RentalManager;
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
public class RemoveCarAction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));        
            RentalManager manager = Management.getManager();
            manager.removeCar(id);            
        } catch(Exception ex) {
            throw new ServletException(ex);
        }
        
        response.sendRedirect("index.jsp");
    }
}
