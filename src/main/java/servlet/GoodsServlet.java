package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchDao;
import model.GoodsArrayBeans;
import model.GoodsBeans;

@WebServlet("/goods")
public class GoodsServlet extends HttpServlet {

	private static final int ITEMS_PER_PAGE = 21;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String showOnlyAvailableParam = request.getParameter("showOnlyAvailable");
        boolean showOnlyAvailable = "true".equals(showOnlyAvailableParam);
        String sortOption = request.getParameter("sortOption");
        if (sortOption == null || sortOption.isEmpty()) {
            sortOption = "distance-asc";
        }

        // ページ番号を取得（デフォルトは1）
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }

        SearchDao searchDao = new SearchDao();

        try {
            GoodsArrayBeans goodsArray;
            if (keyword != null && !keyword.isEmpty()) {
                goodsArray = searchDao.searchGoodsByKeywordAndAvailability(keyword, showOnlyAvailable);
            } else {
                goodsArray = searchDao.getAllGoods(showOnlyAvailable);
            }

            String showOnlySaleParam = request.getParameter("showOnlySale");
            boolean showOnlySale = "true".equals(showOnlySaleParam);

            LocalTime now = LocalTime.now();

            List<GoodsBeans> filteredGoods = goodsArray.getGoodsArray().stream()
                .filter(goods -> {
                    if (showOnlySale && !goods.isTimeSale()) {
                        return false;
                    }
                    if (showOnlyAvailable) {
                        return "1".equals(goods.getSales_Flag()) &&
                               goods.getOpening_Time() != null &&
                               goods.getClosing_Time() != null &&
                               now.isAfter(goods.getOpening_Time().toLocalTime()) &&
                               now.isBefore(goods.getClosing_Time().toLocalTime());
                    }
                    return true;
                })
                .collect(Collectors.toList());

            // ソート処理
            if ("price-asc".equals(sortOption)) {
                Collections.sort(filteredGoods, (a, b) -> {
                    int priceA = a.isTimeSale() ? a.getTimeSalePrice() : Integer.parseInt(a.getSales_Price());
                    int priceB = b.isTimeSale() ? b.getTimeSalePrice() : Integer.parseInt(b.getSales_Price());
                    return Integer.compare(priceA, priceB);
                });
            } else if ("name-asc".equals(sortOption)) {
                Collections.sort(filteredGoods, Comparator.comparing(GoodsBeans::getGoods_Name));
            } else if ("name-desc".equals(sortOption)) {
                Collections.sort(filteredGoods, Comparator.comparing(GoodsBeans::getGoods_Name).reversed());
            } else if ("distance-asc".equals(sortOption)) {
                double userLat = 35.6895;
                double userLon = 139.6917;

                Collections.sort(filteredGoods, (a, b) -> {
                    double distA = calculateDistance(userLat, userLon, a.getLatitude(), a.getLongitude());
                    double distB = calculateDistance(userLat, userLon, b.getLatitude(), b.getLongitude());
                    return Double.compare(distA, distB);
                });
            }

            // ページネーション処理
            int totalItems = filteredGoods.size();
            int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
            int startIndex = (page - 1) * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, totalItems);

            List<GoodsBeans> pagedGoods = filteredGoods.subList(startIndex, endIndex);

            request.setAttribute("goodsArray", new GoodsArrayBeans(pagedGoods));
            request.setAttribute("keyword", keyword);
            request.setAttribute("showOnlyAvailable", showOnlyAvailableParam);
            request.setAttribute("showOnlySale", showOnlySaleParam);
            request.setAttribute("sortOption", sortOption);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("GoodsSearch.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "データベースエラーが発生しました。");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "予期せぬエラーが発生しました。");
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