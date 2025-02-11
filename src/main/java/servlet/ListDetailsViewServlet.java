package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyListDao;
import model.ListInfoBeans;
import model.MemberBeans;

/**
 * Servlet implementation class ListDetailsViewServlet
 */
//リスト閲覧の詳細
@WebServlet("/ListDetailsViewServlet")
public class ListDetailsViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        // DAO インスタンス
        MyListDao dao = new MyListDao();

        // セッションからデータ取得
        HttpSession session = request.getSession();
        
        
        MemberBeans member = (MemberBeans)session.getAttribute("loginMember");
		String sessionMemberNo = member.getMember_no();


        // リクエストパラメータ "List_No" の取得
        String listNo = request.getParameter("List_No");

        List<ListInfoBeans> listArray = (List<ListInfoBeans>)session.getAttribute("listArray");

        if (listArray != null) {

            for (ListInfoBeans beans : listArray) {
            	if (beans.getList_No().equals(listNo)) {
                	request.setAttribute("salesList", beans);
                }
            }
        }
        

        // JSP に転送
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/ListDetailView.jsp");
        dispatcher.forward(request, response);
	}

}
;