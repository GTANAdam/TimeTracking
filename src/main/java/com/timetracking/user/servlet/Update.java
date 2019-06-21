package com.timetracking.user.servlet;

import com.timetracking.user.dao.IUserDAO;
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

@WebServlet("/user/update")
public class Update extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private IUserDAO userDAO = new UserDAO();
	
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
        
    	String id = request.getParameter("userId");
        User user = userDAO.getById(Integer.parseInt(id));
        
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/view/user/update.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
        String id = request.getParameter("userId");
        User user = userDAO.getById(Integer.parseInt(id));
        
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        user.setRole(request.getParameter("role"));
        
        userDAO.update(user);
        response.sendRedirect("/user/list");
    }
}
