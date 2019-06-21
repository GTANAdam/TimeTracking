package com.timetracking.activity.servlet.admin;

import com.timetracking.activity.dao.ActivityDAO;
import com.timetracking.activity.dao.IActivityDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/activity/delete")
public class Delete extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private IActivityDAO activityDAO = new ActivityDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html");  
        PrintWriter out = response.getWriter();  
        
        HttpSession session = request.getSession(false);  
        if (session == null || session.getAttribute("email") == null)
        {  
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").include(request, response);  
            out.close();  
            return;
        }
        
        if (!session.getAttribute("role").toString().equals("admin"))
        {
            out.print("Insufficient permissions!");  
            out.close();  
            return;
        }
        
    	// Get activity id
        String id = request.getParameter("activityId");
        
        // Delete activity from databse
        activityDAO.delete(Integer.parseInt(id));
        
        // Redirect to listing
        response.sendRedirect("/activity/list");
    }
}
