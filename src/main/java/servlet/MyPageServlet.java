package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyPageDao;
import model.MemberBeans;

@WebServlet("/MyPageServlet")
public class MyPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	HttpSession session = request.getSession();
		
		MemberBeans member = (MemberBeans)session.getAttribute("loginMember");
		String member_No = member.getMember_no();

        // DAO を使用してポイント情報を取得
        MyPageDao dao = new MyPageDao();
        int totalPoints = dao.getTotalPoints(member_No);
     
        request.setAttribute("totalPoints", totalPoints);
        System.out.println(totalPoints);

        // JSPにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/MyPage.jsp");
		dispatcher.forward(request, response);
    }
}
