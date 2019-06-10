

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bank.beans.*;
import com.bank.controller.*;
import com.bank.dao.impl.*;
import com.bank.data.*;
import com.bank.services.impl.*;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Database data;
	private Properties properties;
	private UserDaoImpl userDao;
	private CustomerDaoImpl customerDao;
	private EmployeeDaoImpl employeeDao;
	private UserServiceImpl userService;
	private EmployeeServiceImpl employeeService;
	private LoginController loginController;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	try {
    		this.data = new Database();
    		this.properties = new Properties("C:\\Users\\Ryan\\eclipse-workspace\\BankingWebApp\\properties.txt");
    		this.userDao = new UserDaoImpl(properties, data);
    		this.customerDao = new CustomerDaoImpl(properties, data);
    		this.employeeDao = new EmployeeDaoImpl(properties, data);
    		this.userService = new UserServiceImpl();
    		this.employeeService = new EmployeeServiceImpl(userDao, customerDao);
    		this.loginController = new LoginController(userDao, employeeDao, customerDao);
    		data.generateDefaultData();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<title>Login Response</title>");
		out.println("<body>");
		out.println("<p>Username = "+request.getParameter("username")+"</p>");
		out.println("<p>Password = "+request.getParameter("password")+"</p>");
		User user = null;
		
		try {
			user = loginController.login(request.getParameter("username"), request.getParameter("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(user == null) {
			out.println("<p>Invalid user</p>");
		}
		else {
			out.println("<p>Login Success!</p>");
		}
		out.println("</body>");
		out.println("</html>");
		
	}

}
