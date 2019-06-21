package com.timetracking.activity.servlet.admin;

import com.timetracking.activity.dao.ActivityDAO;
import com.timetracking.activity.dao.IActivityDAO;
import com.timetracking.activity.model.Activity;
import com.timetracking.user.dao.UserDAO;
import com.timetracking.user.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/activity/add")
public class Add extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private IActivityDAO acitvityDAO = new ActivityDAO();

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
        
    	request.setAttribute("users", new UserDAO().getUsers());
        request.getRequestDispatcher("/WEB-INF/view/activity/admin/add.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
    	// Instantiate new activity object
        Activity activity = new Activity();

        // Set activity data
        activity.setName(request.getParameter("name"));
        //activity.setStartDate(Date.valueOf(request.getParameter("startDate")));
        //activity.setEndDate(Date.valueOf(request.getParameter("endDate")));
        //activity.setStatus(request.getParameter("status"));

        if (!request.getParameter("startDate").isEmpty()) 
        {
            activity.setStartDate(Date.valueOf(request.getParameter("startDate")));
        }
        
        activity.setStatus("Approved");
        
		// Get user by id from database
		User user = new UserDAO().getById(Integer.parseInt(request.getParameter("userId")));
        activity.setUser(user);
        
        // Add activity to databse
        acitvityDAO.add(activity);
        
        // Redirect to listing
        response.sendRedirect("/activity/list");
    }
}
