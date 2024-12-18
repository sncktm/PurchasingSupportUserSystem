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
import model.SalesDataBeans;

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
        String sessionSalesNo = (String) session.getAttribute("Sales_No");
        if (sessionSalesNo == null || sessionSalesNo.isEmpty()) {
            sessionSalesNo = "00000001"; // デフォルトのSales_No
        }
        
        MemberBeans member = (MemberBeans)session.getAttribute("loginMember");
		String sessionMemberNo = member.getMember_no();


        // リクエストパラメータ "List_No" の取得
        String listNo = request.getParameter("List_No");
        System.out.println("りすと番号");
        System.out.println(listNo);

        if (listNo == null || listNo.isEmpty()) {
            // パラメータがない場合はリスト全体を取得
            List<ListInfoBeans> listArray = dao.getListArray(sessionMemberNo);
            // デバッグ用ログ
            System.out.println("取得したリストの件数: " + (listArray == null ? 0 : listArray.size()));
            // JSP に渡すデータを設定
            request.setAttribute("listArray", listArray);
            session.setAttribute("listArray", listArray);
            request.setAttribute("viewType", "list"); // ビューの種類を指定
        } else {
        	
//        	
//            // パラメータがある場合は詳細リストを取得 商品番号
//            List<ListDetailsBeans> detailsArray = dao.getListdetailsArray(listNo); 
//
//            // デバッグ用ログ
//            System.out.println("詳細情報の件数: " + (detailsArray == null ? 0 : detailsArray.size()));

            // `Sales_No` に基づいて商品データを取得
            List<SalesDataBeans> salesDataList = dao.getSalesDataByListNo(sessionSalesNo);

            // デバッグ用ログ
            System.out.println("取得した商品データの件数: " + (salesDataList == null ? 0 : salesDataList.size()));

            // JSP に渡すデータを設定
//            request.setAttribute("detailsArray", detailsArray);
            request.setAttribute("salesDataList", salesDataList);
            request.setAttribute("listNo", listNo);
            request.setAttribute("viewType", "details");
        }

        // JSP に転送
        RequestDispatcher dispatcher = request.getRequestDispatcher("/listView.jsp");
        dispatcher.forward(request, response);
    }
}