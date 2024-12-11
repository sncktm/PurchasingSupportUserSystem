package servlet;

import dao.SearchDao;
import model.GoodsArrayBeans;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/goods")
public class GoodsSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SearchDao searchDao = new SearchDao();

        try {
            // 商品情報を取得
            GoodsArrayBeans goodsArray = searchDao.getAllGoods();

            // デバッグ出力
            System.out.println("配列サイズ: " + goodsArray.getArraySize());

            // リクエストスコープに設定
            request.setAttribute("goodsArray", goodsArray);

            // JSPにフォワード
            request.getRequestDispatcher("goods_list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データベースエラーが発生しました。");
        }
    }
}


