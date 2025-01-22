package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchDao;
import model.GoodsArrayBeans;

@WebServlet("/goods")
public class GoodsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエストからパラメータを取得
        String keyword = request.getParameter("keyword");
        String showOnlyAvailableParam = request.getParameter("showOnlyAvailable");
        boolean showOnlyAvailable = "true".equals(showOnlyAvailableParam);
        String sortOption = request.getParameter("sortOption");
        if (sortOption == null) {
            sortOption = "distance-asc";
        }

        SearchDao searchDao = new SearchDao();

        try {
            GoodsArrayBeans goodsArray;
            if (keyword != null && !keyword.isEmpty()) {
                // キーワード検索を実行
                goodsArray = searchDao.searchGoodsByKeywordAndAvailability(keyword, showOnlyAvailable, sortOption);
            } else {
                // 全商品取得
                goodsArray = searchDao.getAllGoods(showOnlyAvailable, sortOption);
            }

            // 距離による並び替えの場合、サーバーサイドで処理
            if ("distance-asc".equals(sortOption)) {
                // ユーザーの位置情報を取得（実際の実装ではクライアントから送信される必要があります）
                double userLat = 35.6895; // 仮の値
                double userLon = 139.6917; // 仮の値

                Collections.sort(goodsArray.getGoodsArray(), (a, b) -> {
                    double distA = calculateDistance(userLat, userLon, a.getLatitude(), a.getLongitude());
                    double distB = calculateDistance(userLat, userLon, b.getLatitude(), b.getLongitude());
                    return Double.compare(distA, distB);
                });
            }

            request.setAttribute("goodsArray", goodsArray);
            request.setAttribute("keyword", keyword);
            request.setAttribute("showOnlyAvailable", showOnlyAvailableParam);
            request.setAttribute("sortOption", sortOption); //This line was already present in the original code.
            request.getRequestDispatcher("GoodsSearch.jsp").forward(request, response);


        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データベースエラーが発生しました。");
        }
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 地球の半径（km）
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}

