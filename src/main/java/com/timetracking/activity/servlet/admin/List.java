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

@WebServlet("/activity/list")
public class List extends HttpServlet 
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

        // Get the number of rows
        int rowsPerPage = 5;
        int numberOfRows = activityDAO.getNumberOfRows();
        int numberOfPages = numberOfRows / rowsPerPage;

        if (numberOfPages % rowsPerPage > 0) {
            numberOfPages++;
        }

        int currentPage = 1;
        if (request.getParameter("currentPage") != null && !request.getParameter("currentPage").isEmpty()) 
        {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }

        // Set the number of pages available
        request.setAttribute("pages", numberOfPages);
        request.setAttribute("currentPage", currentPage);

        // Get activity list from databse and render to forward page
        request.setAttribute("activities", activityDAO.getActivities(currentPage, rowsPerPage));

        request.getRequestDispatcher("/WEB-INF/view/activity/admin/list.jsp").forward(request,response);
    }
}
