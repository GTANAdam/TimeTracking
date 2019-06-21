package com.timetracking.auth.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.timetracking.user.dao.IUserDAO;
import com.timetracking.user.dao.UserDAO;
import com.timetracking.user.model.User;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/reg")
public class Register extends HttpServlet 
{
	IUserDAO userDAO = new UserDAO();
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/WEB-INF/view/reg.jsp").forward(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
    	String name = request.getParameter("name");
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	
    	User user = new User();
    	user.setName(name);
    	user.setEmail(email);
    	user.setPassword(password);
    	user.setRole("user");
    	
		if (userDAO.add(user) > 0) 
		{
			response.sendRedirect("/login");
			return;
		}
		
		PrintWriter out = response.getWriter();  
		out.print("Error, wrong input data!");  
		out.close();  
    }
}