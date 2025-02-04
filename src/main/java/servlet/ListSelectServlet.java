package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.ListDao;
import model.ListBeans;
import model.MemberBeans;

/**
 * Servlet implementation class ListSelectServlet
 */
@WebServlet("/ListSelectServlet")
public class ListSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//商品検索画面でのリスト表示
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		MemberBeans member = (MemberBeans)session.getAttribute("loginMember");
		if (member == null) {
		    response.sendRedirect("/MemberLogin.jsp"); // ログイン画面にリダイレクト
		    return;
		}
	    String user_No = member.getMember_no();
		 
		ListDao dao = new ListDao();

		ArrayList<ListBeans> members = dao.ListView(user_No);
		System.out.println("listのサーぶれとなう");
		for(ListBeans member1 : members) {
			System.out.println("リストfor");
			System.out.println(member1.getList_Name());
		}
        

        Gson gson = new Gson();
        String json = gson.toJson(members);
        System.out.println(json);
        
        // Set response headers
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        
        // Write JSON response
        response.getWriter().write(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
