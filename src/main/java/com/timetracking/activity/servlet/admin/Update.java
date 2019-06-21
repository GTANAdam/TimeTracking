package com.timetracking.activity.servlet.admin;

import com.timetracking.activity.dao.IActivityDAO;
import com.timetracking.activity.dao.ActivityDAO;
import com.timetracking.activity.model.Activity;
import com.timetracking.dao.MainDAO;
import com.timetracking.user.dao.UserDAO;
import com.timetracking.user.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/activity/update")
public class Update extends HttpServlet 
{
	final static Logger logger = Logger.getLogger(MainDAO.class);
	
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
        
    	String id = request.getParameter("activityId");
        Activity activity = activityDAO.getById(Integer.parseInt(id));
        
        request.setAttribute("users", new UserDAO().getUsers());
        
        request.setAttribute("activity", activity);
        request.getRequestDispatcher("/WEB-INF/view/activity/admin/update.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
    	// Get activity id param
        String id = request.getParameter("activityId");
        
        // Get activity from database
        Activity activity = activityDAO.getById(Integer.parseInt(id));
        
        logger.debug(activity.toString());
        
        // Set activity data
        activity.setName(request.getParameter("name"));
        activity.setStatus(request.getParameter("status"));

        if (!request.getParameter("startDate").isEmpty()) 
        {
            activity.setStartDate(Date.valueOf(request.getParameter("startDate")));
        }

        if (!request.getParameter("endDate").isEmpty()) 
        {
            activity.setEndDate(Date.valueOf(request.getParameter("endDate")));
        }
        
		// Get user by id from database
		User user = new UserDAO().getById(Integer.parseInt(request.getParameter("userId")));
        activity.setUser(user);
        
        // Update activity in databse
        activityDAO.update(activity);
        
        // Redirect to listing
        response.sendRedirect("/activity/list");
    }
}
