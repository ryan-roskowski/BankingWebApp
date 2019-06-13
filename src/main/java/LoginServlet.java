


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bank.beans.Customer;
import com.bank.beans.Employee;
import com.bank.beans.Properties;
import com.bank.beans.User;
import com.bank.controller.AccountController;
import com.bank.controller.LoginController;
import com.bank.dao.impl.CustomerDaoImpl;
import com.bank.dao.impl.EmployeeDaoImpl;
import com.bank.dao.impl.UserDaoImpl;
import com.bank.dao.impl.AccountDaoImpl;
import com.bank.data.Database;
import com.bank.enums.UserAction;
import com.bank.services.impl.AccountServiceImpl;
import com.bank.services.impl.EmployeeServiceImpl;
import com.bank.services.impl.UserServiceImpl;

import com.web.enums.Purpose;


/**
 * Servlet implementation class FrontController
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
	private AccountDaoImpl accountDao;
	private AccountServiceImpl accountService;
	private AccountController accountController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Inside Front Controller Init ");
		
		try {
    		this.data = new Database();
    		this.properties = new Properties("D:\\eclipse_workspace\\BankingWebApp\\properties.txt");
    		this.userDao = new UserDaoImpl(properties, data);
    		this.customerDao = new CustomerDaoImpl(properties, data);
    		this.employeeDao = new EmployeeDaoImpl(properties, data);
    		this.accountDao = new AccountDaoImpl(properties, data);
    		this.userService = new UserServiceImpl();
    		this.employeeService = new EmployeeServiceImpl(userDao, customerDao);
    		this.loginController = new LoginController(userDao, employeeDao, customerDao);
    		this.accountService = new AccountServiceImpl(accountDao);
    		this.accountController = new AccountController(accountDao);
    		data.generateDefaultData();
    		
    		// share these objects with the other servlets
    		config.getServletContext().setAttribute("data", data);
    		config.getServletContext().setAttribute("properties", properties);
    		config.getServletContext().setAttribute("userDao", userDao);
    		config.getServletContext().setAttribute("customerDao", customerDao);
    		config.getServletContext().setAttribute("employeeDao", employeeDao);
    		config.getServletContext().setAttribute("userService", userService);
    		config.getServletContext().setAttribute("loginController", loginController);
    	} catch(Exception e) {
    		System.out.println("Exception caught");
    		e.printStackTrace();
    	}
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		// TODO Auto-generated method stub
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
		System.out.println("POST REQUEST");
		System.out.println("Request Parameters: "+request.getParameterNames());
		System.out.println("Session Parameters: "+request.getSession().getAttributeNames());
		
		HttpSession session = request.getSession();
		
		if(request.getParameter("purpose") == null) {
			System.out.println("Purpose null - fowarding to login page.");
			if(session.getAttribute("user") == null) {	
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
			else {
				System.out.println("User already logged in, fowarding to menu.");
				// if they've already logged in just send to appropiate menu
				User user = (User) session.getAttribute("user");
				loadMenu(request, response, user);
				return;
			}
		}
		else {
			switch(request.getParameter("purpose")) {
			case "login":
				System.out.println("Purpose = Login - Attempting login and forwarding to appropriate menu page.");
				try {
					User user;
					user = loginController.login(request.getParameter("username"), request.getParameter("password"));
					if (user == null) {
						request.getRequestDispatcher("invalidUser.jsp").forward(request, response);
						return;
					} else {
						session.setAttribute("user", user);
						loadMenu(request, response, user);
						return;
					}
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "action":
				User user = (User)request.getSession().getAttribute("user");
				if(user == null) {
					request.getRequestDispatcher("login.jsp").forward(request, response);
					return;
				}
				String userInput = request.getParameter("menu");
				UserAction userAction = loginController.performAction(user, userInput);
				switch(userAction) {
				case CREATE_ACCOUNT_FOR_CUSTOMER:
					request.getRequestDispatcher("newAccount.jsp").forward(request, response);
					return;
				case CREATE_CUSTOMER:
					request.getRequestDispatcher("newCustomer.jsp").forward(request, response);
					return;
				case DEPOSIT:
					return;
				case INVALID:
					session.invalidate();
					request.getRequestDispatcher("error.jsp").forward(request, response);
					return;
				case LOGOUT:
					session.invalidate();
					request.getRequestDispatcher("login.jsp").forward(request, response);
					return;
				case MANAGER_1:
					request.setAttribute("message", "Performed Manager Option 1");
					request.getRequestDispatcher("messageContainer.jsp").forward(request, response);
					return;
				case MANAGER_2:
					request.setAttribute("message", "Performed Manager Option 2");
					request.getRequestDispatcher("messageContainer.jsp").forward(request, response);
					return;
				case TELLER_1:
					request.setAttribute("message", "Performed Teller Option 1");
					request.getRequestDispatcher("messageContainer.jsp").forward(request, response);
					return;
				case TELLER_2:
					request.setAttribute("message", "Performed Teller Option 2");
					request.getRequestDispatcher("messageContainer.jsp").forward(request, response);
					return;
				case VIEW_BALANCE:
					return;
				case WITHDRAW:
					return;
				default:
					return;
				}
			case "newCustomer":
				try {
					employeeService.createCustomerUser(request.getParameter("username"), request.getParameter("password"), request.getParameter("fname"),
							request.getParameter("lname"), request.getParameter("address"), request.getParameter("phone"));
					request.setAttribute("message", "Added Customer: Username="+request.getParameter("username")+", Password="+request.getParameter("username")+
							", FirstName="+request.getParameter("fname")+", LastName="+request.getParameter("lname")+", Address="+request.getParameter("address")+
							", Phone="+request.getParameter("phone"));
					request.getRequestDispatcher("messageContainer.jsp").forward(request, response);
					return;
					
				} catch(Exception e) {
					request.getRequestDispatcher("error.jsp").forward(request, response);
					return;
				}
			case "newAccount":
				try {
					User accUser = userDao.getUser(request.getParameter("username"));
					if(accUser == null) {
						request.setAttribute("message", "You entered an invalid User.");
						request.getRequestDispatcher("messageContainer.jsp").forward(request, response);
						return;
					}
					else {
						Customer accCustomer = customerDao.getCustomer(accUser.getUserId());
						if(accCustomer == null) {
							request.setAttribute("message", "You entered an invalid User.");
							request.getRequestDispatcher("messageContainer.jsp").forward(request, response);
							return;
						}
						else {
							accountDao.addAccount(accCustomer, request.getParameter("type"));
							request.setAttribute("message", "Added Account: Username="+accUser.getUsername()+", type="+request.getParameter("type")+".");
							request.getRequestDispatcher("messageContainer.jsp").forward(request, response);
						return;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					request.getRequestDispatcher("error.jsp").forward(request, response);
				}
				break;
			}
		}
	}
	protected void loadMenu(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		switch (user.getType()) {
		case "Employee":
			Employee employee = (Employee) user;
			switch (employee.getEmployeeType()) {
			case "Manager":
				request.getRequestDispatcher("managerMenu.jsp").forward(request, response);
				break;
			case "Teller":
				request.getRequestDispatcher("tellerMenu.jsp").forward(request, response);
				break;
			default:
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
			break;
		case "Customer":
			request.getRequestDispatcher("customerMenu.jsp").forward(request, response);
			break;
		default:
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
	}
		
}
