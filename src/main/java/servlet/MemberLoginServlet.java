package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LoginConnectionDao;
import model.MemberBeans;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/MemberLoginServlet")
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MemberLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String member_email = request.getParameter("email");
		String member_password = request.getParameter("pass");
		
		MemberBeans member = new MemberBeans();
		
		
		LoginConnectionDao login_connection_dao = new LoginConnectionDao();
		member = login_connection_dao.StoreLoginSearch(member_email,member_password);
		
		
		if(member != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loginmMmber", member);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/LoginCompletion.jsp");
			dispatcher.forward(request, response);
		}else {
			request.setAttribute("errorMsg", "メールアドレスまたはパスワードが異なります。");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/LoginMismatch.jsp");
			dispatcher.forward(request, response);
		}
	}

}
