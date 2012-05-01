/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package carrental.view;

import carrental.management.RentalManager;
import carrental.model.ConnectionPool;
import carrental.model.Management;
import carrental.model.adapters.CarRentalAdapter;
import carrental.model.adapters.CustomerAdapter;
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
public class RemoveRentalAction extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.valueOf(request.getParameter("id"));        
            RentalManager manager = Management.getManager();
            
            manager.closeRentalById(id);
        } catch(Exception ex) {
            throw new ServletException(ex);
        }
        
        response.sendRedirect("index.jsp");
    }
}
