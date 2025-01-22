package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchDao;
import model.StoreArrayBeans;
import model.StoreBeans;

@WebServlet("/StoreSearch")
public class StoreSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String showOnlyAvailableParam = request.getParameter("showOnlyAvailable");
        boolean showOnlyAvailable = "true".equals(showOnlyAvailableParam);
        String sortOption = request.getParameter("sortOption");
        if (sortOption == null) {
            sortOption = "name-asc"; // デフォルトの並び替えオプション
        }
        
        // 現在地の緯度経度（実際のアプリケーションではクライアントから取得する）
        double currentLat = 35.6895;
        double currentLon = 139.6917;

        SearchDao searchDao = new SearchDao();

        try {
            StoreArrayBeans storeArray = (keyword != null && !keyword.isEmpty())
                    ? searchDao.searchStoresByKeyword(keyword)
                    : searchDao.getAllStores();

            if (showOnlyAvailable) {
                LocalTime now = LocalTime.now();
                storeArray.getStores().removeIf(store -> {
                    boolean isOpen = store.getOpeningTime() != null && store.getClosingTime() != null &&
                                     now.isAfter(store.getOpeningTime().toLocalTime()) &&
                                     now.isBefore(store.getClosingTime().toLocalTime());
                    return !isOpen;
                });
            }

            // 各店舗の距離を計算
            for (StoreBeans store : storeArray.getStores()) {
                double distance = calculateDistance(currentLat, currentLon, store.getLatitude(), store.getLongitude());
                store.setDistance(distance);
            }

            // 並び替え
            switch (sortOption) {
                case "name-asc":
                    storeArray.sortByNameAsc();
                    break;
                case "name-desc":
                    storeArray.sortByNameDesc();
                    break;
                case "distance":
                    storeArray.sortByDistance();
                    break;
                case "opening-asc":
                    storeArray.sortByOpeningTimeAsc();
                    break;
                case "closing-desc":
                    storeArray.sortByClosingTimeDesc();
                    break;
            }

            request.setAttribute("storeArray", storeArray);
            request.setAttribute("keyword", keyword);
            request.setAttribute("showOnlyAvailable", showOnlyAvailableParam);
            request.setAttribute("sortOption", sortOption);

            request.getRequestDispatcher("StoreSearch.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データベースエラーが発生しました。");
        }
    }

    // 2点間の距離を計算するメソッド（ヒュベニの公式を使用）
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earth_radius = 6378.137;  // 地球の半径（km）
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earth_radius * c;
    }
}

