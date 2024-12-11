package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MembersDAO;
import model.MemberBeans;
import model.MembersInfoBeans;

@WebServlet("/MemberInformationViewServlet")
public class MemberInformationViewServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		
		HttpSession session = request.getSession();
		
		MemberBeans member = (MemberBeans)session.getAttribute("loginMember");
		String member_No = member.getMember_no();
		
		
		MembersDAO dao = new MembersDAO();
		MembersInfoBeans members = dao.findAll(member_No);
		
		
		
        session.setAttribute("members", members);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/MemberInformationView.jsp");
        dispatcher.forward(request, response);
		
	}

}
