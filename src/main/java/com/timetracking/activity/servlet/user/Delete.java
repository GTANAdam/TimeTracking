package com.timetracking.activity.servlet.user;

import com.timetracking.activity.dao.ActivityDAO;
import com.timetracking.activity.dao.IActivityDAO;
import com.timetracking.activity.model.Activity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/activity/user/delete")
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
        
    	// Get activity id
        String id = request.getParameter("activityId");
        
        // Get activity from database
        Activity activity = activityDAO.getById(Integer.parseInt(id));

        // Set Status to awaiting deletion
        activity.setStatus("Awaiting Deletion");

        // Update activity in databse
        activityDAO.update(activity);
        
        // Redirect to listing
        response.sendRedirect("/activity/user/list");
    }
}
