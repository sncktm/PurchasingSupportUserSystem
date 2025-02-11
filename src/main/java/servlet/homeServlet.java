package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AdvertisementDao;
import dao.ProductDAO;
import dao.TimesaleDAO;
import model.AdCommodityBeans;
import model.AdvertisementManagementBeans;
import model.TimesaleBeans;
import model.TimesaleGoodsBeans;

@WebServlet("/homeServlet")
public class homeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    
    System.out.println("HomeServlet: データ取得開始");

    // DAOの初期化
    AdvertisementDao adDao = new AdvertisementDao();
    ProductDAO productDao = new ProductDAO();
    TimesaleDAO timesaleDao = new TimesaleDAO();

    try {
        // 大きな広告（priority=01）を取得
        List<AdCommodityBeans> largeAds = adDao.findByPriority("01");
        System.out.println("大きな広告数: " + largeAds.size());

        // 中サイズの広告（priority=02）を取得
        List<AdCommodityBeans> mediumAds = adDao.findByPriority("02");
        System.out.println("中サイズの広告数: " + mediumAds.size());

        // 大きな広告を店舗広告と商品広告に分類
        List<AdCommodityBeans> largeStoreAds = new ArrayList<>();
        List<AdCommodityBeans> largeProductAds = new ArrayList<>();
        for (AdCommodityBeans ad : largeAds) {
            if (ad.getAdvertisement_type().equals("1")) {
                largeStoreAds.add(ad);
            } else if (ad.getAdvertisement_type().equals("2")) {
                largeProductAds.add(ad);
            }
        }

        // 中サイズの広告を店舗広告と商品広告に分類
        List<AdCommodityBeans> mediumStoreAds = new ArrayList<>();
        List<AdCommodityBeans> mediumProductAds = new ArrayList<>();
        for (AdCommodityBeans ad : mediumAds) {
            if (ad.getAdvertisement_type().equals("1")) {
                mediumStoreAds.add(ad);
            } else if (ad.getAdvertisement_type().equals("2")) {
                mediumProductAds.add(ad);
            }
        }

        // 商品広告の詳細情報を格納するMap
        Map<String, List<AdvertisementManagementBeans>> productDetails = new HashMap<>();

        // 全ての商品広告（大きな広告と中サイズの広告）の詳細情報を取得
        List<AdCommodityBeans> allProductAds = new ArrayList<>(largeProductAds);
        allProductAds.addAll(mediumProductAds);
        for (AdCommodityBeans productAd : allProductAds) {
            String advertisementNo = productAd.getAdvertisement_No();
            List<AdvertisementManagementBeans> products = productDao.findByAdvertisementNo(advertisementNo);
            
            if (products != null) {
                System.out.println("広告番号 " + advertisementNo + " に紐づく商品数: " + products.size());
            } else {
                System.out.println("広告番号 " + advertisementNo + " に紐づく商品情報はありません");
            }

            productDetails.put(advertisementNo, products);
        }
        
        System.out.println("商品詳細数: " + productDetails.size());

        // タイムセール情報の取得
        List<TimesaleBeans> timesales = timesaleDao.findAllTimesales();
        System.out.println("タイムセール数: " + timesales.size());

        // タイムセール商品の詳細情報を格納するMap
        Map<Integer, List<TimesaleGoodsBeans>> timesaleGoodsDetails = new HashMap<>();

        // 各タイムセールの商品情報を取得
        for (TimesaleBeans timesale : timesales) {
            int timeSaleNo = timesale.getTime_Sale_No();
            List<TimesaleGoodsBeans> timesaleGoods = timesaleDao.findTimesaleGoods(timeSaleNo);
            timesaleGoodsDetails.put(timeSaleNo, timesaleGoods);
        }

        System.out.println("タイムセール商品詳細数: " + timesaleGoodsDetails.size());

        // JSPで使用するためのデータをリクエストにセット
        request.setAttribute("largeStoreAds", largeStoreAds);
        request.setAttribute("mediumStoreAds", mediumStoreAds);
        request.setAttribute("largeProductAds", largeProductAds);
        request.setAttribute("mediumProductAds", mediumProductAds);
        request.setAttribute("productDetails", productDetails);
        request.setAttribute("timesales", timesales);
        request.setAttribute("timesaleGoodsDetails", timesaleGoodsDetails);

        System.out.println("HomeServlet: JSPにフォワードします");

        // JSPにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/home.jsp");
        dispatcher.forward(request, response);

    } catch (Exception e) {
        System.out.println("HomeServlet: エラーが発生しました");
        e.printStackTrace();
        throw new ServletException("データ取得中にエラーが発生しました", e);
    }
    }
}