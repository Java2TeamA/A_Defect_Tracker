package java2.ateam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUser extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	ArrayList<User> userList;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String relativeWebPath = "/WEB-INF/repository/";
		String dbPath = getServletContext().getRealPath(relativeWebPath);
		
		DatabaseAccess db = new DatabaseAccess(dbPath);
		
		//get connection to database
        db.getConnection();
        
		//save defect id if exists in URL parameter
		//process the AddUser form
	    if (request.getParameter("firstName") != null && request.getParameter("lastName") != null && request.getParameter("email") != null){
	        
	      //create a new defect record in the database
			Integer rowsInserted = db.insertUser(request.getParameter("firstName"), 
		    		request.getParameter("lastName"), 
		    		request.getParameter("email"));
		    
			if (rowsInserted.equals(1))
				request.setAttribute("message", "add_user_success");
			else
				request.setAttribute("message", "add_user_failure");
	    } 	    
	    	
		userList = db.getUsers();
		Collections.sort(userList);
		request.setAttribute("userList", userList);
	    
	    //close db connection
		db.closeConnection();
		
	    RequestDispatcher dispatcher = getServletConfig().getServletContext().getRequestDispatcher("/jsp/AddUser.jsp");
	    dispatcher.forward(request, response);
		
	}

}
