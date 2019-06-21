package com.timetracking.auth.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.timetracking.user.model.User;
import com.timetracking.auth.dao.AuthDAO;
import com.timetracking.auth.dao.IAuthDAO;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private IAuthDAO authDAO = new AuthDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	
    	User user = authDAO.getAccount(email, password);
    	
    	if (user == null)
    	{
    		PrintWriter out = response.getWriter();  
    		out.print("Error, incorrect email or password!");  
    		out.close();  
    		return;
    	}
    	
    	HttpSession session = request.getSession();
		session.setAttribute("email", email);
    	session.setAttribute("role", user.getRole());
		session.setAttribute("userId", user.getId());
		
    	response.sendRedirect("/activity/user/list");
    }
}