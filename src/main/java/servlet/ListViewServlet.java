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

@WebServlet("/ListViewServlet")
public class ListViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // DAO インスタンス
        MyListDao dao = new MyListDao();

        // セッションからデータ取得
        HttpSession session = request.getSession();

        
        MemberBeans member = (MemberBeans)session.getAttribute("loginMember");
		String sessionMemberNo = member.getMember_no();

            // パラメータがない場合はリスト全体を取得
            List<ListInfoBeans> listArray = dao.FindAll(sessionMemberNo);
            // デバッグ用ログ
            System.out.println("取得したリストの件数: " + (listArray == null ? 0 : listArray.size()));
            // JSP に渡すデータを設定
            request.setAttribute("listArray", listArray);
            session.setAttribute("listArray", listArray);

        // JSP に転送
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/ListView.jsp");
        dispatcher.forward(request, response);
    }
}