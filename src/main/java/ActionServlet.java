

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.beans.Properties;
import com.bank.beans.User;
import com.bank.controller.LoginController;
import com.bank.dao.impl.CustomerDaoImpl;
import com.bank.dao.impl.EmployeeDaoImpl;
import com.bank.dao.impl.UserDaoImpl;
import com.bank.data.Database;
import com.bank.enums.UserAction;
import com.bank.services.impl.EmployeeServiceImpl;
import com.bank.services.impl.UserServiceImpl;

/**
 * Servlet implementation class ActionServlet
 */
public class ActionServlet extends HttpServlet {
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
    public ActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.data = (Database) config.getServletContext().getAttribute("data");
		this.properties = (Properties) config.getServletContext().getAttribute("properties");
		this.userDao = (UserDaoImpl) config.getServletContext().getAttribute("userDao");
		this.customerDao = (CustomerDaoImpl) config.getServletContext().getAttribute("customerDao");
		this.employeeDao = (EmployeeDaoImpl) config.getServletContext().getAttribute("employeeDao");
		this.userService = (UserServiceImpl) config.getServletContext().getAttribute("userService");
		this.loginController = (LoginController) config.getServletContext().getAttribute("loginController");
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
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
		System.out.println("in post");
		User user = (User)request.getSession().getAttribute("user");
		String userInput = request.getParameter("menu");
		UserAction userAction = loginController.performAction(user, userInput);
		switch(userAction) {
		case CREATE_ACCOUNT_FOR_CUSTOMER:
			break;
		case CREATE_CUSTOMER:
			break;
		case DEPOSIT:
			break;
		case INVALID:
			break;
		case LOGOUT:
			break;
		case MANAGER_1:
			break;
		case MANAGER_2:
			break;
		case TELLER_1:
			break;
		case TELLER_2:
			break;
		case VIEW_BALANCE:
			break;
		case WITHDRAW:
			break;
		default:
			break;
		}
	}

}
