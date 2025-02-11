<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Time" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.ZonedDateTime" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="model.AdCommodityBeans" %>
<%@ page import="model.AdvertisementManagementBeans" %>
<%@ page import="model.TimesaleBeans" %>
<%@ page import="model.TimesaleGoodsBeans" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>広告一覧</title>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f0f0f0;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
        }
        .tab-container {
            margin: 20px 0;
        }
        .tab-buttons {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }
        .tab-button {
            padding: 10px 20px;
            border: none;
            background-color: #e0e0e0;
            cursor: pointer;
            border-radius: 5px;
            font-size: 16px;
        }
        .tab-button.active {
            background-color: #007bff;
            color: white;
        }
        .tab-content {
            display: none;
        }
        .tab-content.active {
            display: block;
        }
        .recommended-section {
            border: 2px solid #007bff;
            padding: 20px;
            margin-bottom: 30px;
            background-color: #ffffff;
            border-radius: 8px;
        }
        .card-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }
        .card {
            background-color: #ffffff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
        }
        .card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 4px;
            margin-bottom: 10px;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        h3 {
            font-size: 18px;
            margin: 10px 0;
        }
        p {
            font-size: 14px;
            color: #666;
        }
        .product-details {
            margin-top: 10px;
            font-size: 14px;
        }
        .product-details ul {
            padding-left: 20px;
        }
        .highlight {
            background-color: #fffacd;
        }
        #timesale-section {
            margin-top: 30px;
        }
        .timesale-container {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }
        .timesale-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1rem;
            padding: 0.5rem 1rem;
            background-color: #f8f9fa;
            border-radius: 4px;
        }
        .timesale-status {
            padding: 0.25rem 1rem;
            border-radius: 20px;
            font-weight: bold;
        }
        .status-active {
            background-color: #28a745;
            color: white;
        }
        .status-inactive {
            background-color: #dc3545;
            color: white;
        }
        .timesale-products {
            display: flex;
            flex-direction: column;
            gap: 1rem;
            margin-bottom: 1rem;
            flex-grow: 1;
        }
        .product-card {
            background-color: white;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            padding: 1rem;
            margin-bottom: 0.5rem;
        }
        .price-info {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 0.5rem;
        }
        .original-price {
            text-decoration: line-through;
            color: #6c757d;
        }
        .sale-price {
            color: #dc3545;
            font-weight: bold;
            font-size: 1.2em;
        }
        .timesale-footer {
            display: flex;
            justify-content: space-between;
            padding-top: 1rem;
            border-top: 1px solid #dee2e6;
            color: #6c757d;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
<header>
<div class="header-content">
	<div class="logo">
    	
    </div>
    <nav>
        <ul class="menu-lists">
            <li class="menu-list">
            	<img alt="" src="img/homeIcon.png" width="40px" height="40px">
                <a href="homeServlet">ホーム</a>
            </li>
            <li class="menu-list">
            	<img alt="" src="img/searchIcon.png" width="40px" height="40px">
                <a href="#">検索</a>
                <ul class="dropdown-lists">
                    <li class="dropdown-list"><a href="StoreSearch">店舗検索</a></li>
                    <li class="dropdown-list"><a href="goods">商品検索</a></li>
                </ul>
            </li>
            <li class="menu-list">
            	<img alt="" src="img/mypageIcon.png" width="40px" height="40px">
                <a href="MyPageServlet">マイページ</a>
            </li>
            <li class="menu-list">
            	<img alt="" src="img/pointIcon.png" width="40px" height="40px">
                <a href="#">ポイント</a>
            </li>
        </ul>
    </nav>
   <ul class="header-lists">
	   <li class="header-list">
	   		<img alt="" src="img/notificationIcon.png" width="40px" height="40px">
	   		<a href="#"></a>
	   </li>
	   <li class="header-list">
	   		<img alt="" src="img/logoutIcon.png" width="40px" height="40px">
	   		<a href="#"></a>
	   </li>
    </ul>
</div>
</header>
    <div class="container">
        <h1>広告一覧</h1>

        <div class="tab-container">
            <div class="tab-buttons">
                <button class="tab-button active" onclick="showTab('store')">店舗広告</button>
                <button class="tab-button" onclick="showTab('product')">商品広告</button>
                <button class="tab-button" onclick="showTab('timesale')">タイムセール</button>
            </div>

            <!-- 店舗広告タブ -->
            <div id="store-tab" class="tab-content active">
                <div class="recommended-section">
                    <h2>おすすめ店舗広告</h2>
                    <div class="card-container">
                        <%
                        List<AdCommodityBeans> largeStoreAds = (List<AdCommodityBeans>)request.getAttribute("largeStoreAds");
                        if(largeStoreAds != null) {
                            for(AdCommodityBeans ad : largeStoreAds) {
                        %>
                            <div class="card">
                                <img src="${pageContext.request.contextPath}/<%= ad.getAdvertisement_Image() %>" 
                                     alt="<%= ad.getAdvertisement_title() %>"
                                     onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default_ad.jpg';">
                                <h3><%= ad.getAdvertisement_title() %></h3>
                                <p><%= ad.getAdvertisement_Explanation() %></p>
                            </div>
                        <%
                            }
                        }
                        %>
                    </div>
                </div>

                <h2>その他の店舗広告</h2>
                <div class="card-container">
                    <%
                    List<AdCommodityBeans> mediumStoreAds = (List<AdCommodityBeans>)request.getAttribute("mediumStoreAds");
                    if(mediumStoreAds != null) {
                        for(AdCommodityBeans ad : mediumStoreAds) {
                    %>
                        <div class="card">
                            <img src="${pageContext.request.contextPath}/<%= ad.getAdvertisement_Image() %>" 
                                 alt="<%= ad.getAdvertisement_title() %>"
                                 onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default_ad.jpg';">
                            <h3><%= ad.getAdvertisement_title() %></h3>
                            <p><%= ad.getAdvertisement_Explanation() %></p>
                        </div>
                    <%
                        }
                    }
                    %>
                </div>
            </div>

            <!-- 商品広告タブ -->
            <div id="product-tab" class="tab-content">
                <div class="recommended-section">
                    <h2>おすすめ商品広告</h2>
                    <div class="card-container">
                        <%
                        List<AdCommodityBeans> largeProductAds = (List<AdCommodityBeans>)request.getAttribute("largeProductAds");
                        Map<String, List<AdvertisementManagementBeans>> productDetails = 
                            (Map<String, List<AdvertisementManagementBeans>>)request.getAttribute("productDetails");
                            
                        if(largeProductAds != null) {
                            for(AdCommodityBeans ad : largeProductAds) {
                        %>
                            <div class="card">
                                <img src="${pageContext.request.contextPath}/<%= ad.getAdvertisement_Image() %>" 
                                     alt="<%= ad.getAdvertisement_title() %>"
                                     onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default_product.jpg';">
                                <h3><%= ad.getAdvertisement_title() %></h3>
                                <p><%= ad.getAdvertisement_Explanation() %></p>
                                <%
                                if(productDetails != null && productDetails.get(ad.getAdvertisement_No()) != null) {
                                %>
                                    <div class="product-details">
                                        <h4>商品情報:</h4>
                                        <ul>
                                        <%
                                        for(AdvertisementManagementBeans product : productDetails.get(ad.getAdvertisement_No())) {
                                        %>
                                            <li><%= product.getGoods_Name() %> - <%= product.getSales_Price() %>円</li>
                                        <%
                                        }
                                        %>
                                        </ul>
                                    </div>
                                <%
                                }
                                %>
                            </div>
                        <%
                            }
                        }
                        %>
                    </div>
                </div>

                <h2>その他の商品広告</h2>
                <div class="card-container">
                    <%
                    List<AdCommodityBeans> mediumProductAds = (List<AdCommodityBeans>)request.getAttribute("mediumProductAds");
                    if(mediumProductAds != null) {
                        for(AdCommodityBeans ad : mediumProductAds) {
                    %>
                        <div class="card">
                            <img src="${pageContext.request.contextPath}/<%= ad.getAdvertisement_Image() %>" 
                                 alt="<%= ad.getAdvertisement_title() %>"
                                 onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default_product.jpg';">
                            <h3><%= ad.getAdvertisement_title() %></h3>
                            <p><%= ad.getAdvertisement_Explanation() %></p>
                            <%
                            if(productDetails != null && productDetails.get(ad.getAdvertisement_No()) != null) {
                            %>
                                <div class="product-details">
                                    <h4>商品情報:</h4>
                                    <ul>
                                    <%
                                    for(AdvertisementManagementBeans product : productDetails.get(ad.getAdvertisement_No())) {
                                    %>
                                        <li><%= product.getGoods_Name() %> - <%= product.getSales_Price() %>円</li>
                                    <%
                                    }
                                    %>
                                    </ul>
                                </div>
                            <%
                            }
                            %>
                        </div>
                    <%
                        }
                    }
                    %>
                </div>
            </div>

            <!-- タイムセールタブ -->
            <div id="timesale-tab" class="tab-content">
                <div class="recommended-section">
                    <h2>タイムセール情報</h2>
                    <div class="card-container timesale-container">
                        <%
                        List<TimesaleBeans> timesales = (List<TimesaleBeans>)request.getAttribute("timesales");
                        Map<Integer, List<TimesaleGoodsBeans>> timesaleGoodsDetails = 
                            (Map<Integer, List<TimesaleGoodsBeans>>)request.getAttribute("timesaleGoodsDetails");

                        if(timesales != null) {
                            for(TimesaleBeans bean : timesales) {
                                // タイムセールの開始・終了日時を取得
                                Date startDate = bean.getStart_Date();
                                Date endDate = bean.getEnd_Date();
                                Time startTime = bean.getStart_Time();
                                Time endTime = bean.getEnd_Time();

                                // 現在の日時を取得（日本時間）
                                ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
                                ZonedDateTime startDateTime = ZonedDateTime.of(
                                    LocalDate.parse(startDate.toString()),
                                    LocalTime.parse(startTime.toString()),
                                    ZoneId.of("Asia/Tokyo")
                                );
                                ZonedDateTime endDateTime = ZonedDateTime.of(
                                    LocalDate.parse(endDate.toString()),
                                    LocalTime.parse(endTime.toString()),
                                    ZoneId.of("Asia/Tokyo")
                                );

                                boolean isInTimeSale = false;
                                if(bean.getTimesale_Application_Flag().equals("on")) {
                                    if (now.isAfter(startDateTime) && now.isBefore(endDateTime)) {
                                        switch (bean.getRepeat_Pattern()) {
                                            case daily:
                                                isInTimeSale = true;
                                                break;
                                            case weekly:
                                                if (bean.getRepeat_Value() != null && !bean.getRepeat_Value().equals("(NULL)")) {
                                                    String[] daysArray = bean.getRepeat_Value().toLowerCase().split(",");
                                                    String currentDay = now.getDayOfWeek().toString().toLowerCase();
                                                    for(String day: daysArray) {
                                                        if (currentDay.equals(day.trim())) {
                                                            isInTimeSale = true;
                                                            break;
                                                        }
                                                    }
                                                }
                                                break;
                                            case monthly:
                                                if (bean.getRepeat_Value() != null && !bean.getRepeat_Value().equals("(NULL)")) {
                                                    int currentDayOfMonth = now.getDayOfMonth();
                                
                                    int targetDay = Integer.parseInt(bean.getRepeat_Value());
                                    if (currentDayOfMonth == targetDay) {
                                        isInTimeSale = true;
                                    }
                                } else {
                                    // Repeat_Valueが(NULL)の場合は、日付範囲内なら有効
                                    isInTimeSale = true;
                                }
                                break;
                        }
                    }
                }

                String rowClass = isInTimeSale ? "highlight" : "";

                if(timesaleGoodsDetails != null && timesaleGoodsDetails.get(bean.getTime_Sale_No()) != null) {
                    for(TimesaleGoodsBeans goods : timesaleGoodsDetails.get(bean.getTime_Sale_No())) {
        %>
                        <div class="card <%= rowClass %>">
                            <div class="timesale-header">
                                <h3><%= bean.getTime_Sale_Name() %></h3>
                                <span class="timesale-status <%= isInTimeSale ? "status-active" : "status-inactive" %>">
                                    <%= isInTimeSale ? "実施中" : "未実施" %>
                                </span>
                            </div>
                            
                            <div class="timesale-products">
                                <div class="product-card">
                                    <h4><%= goods.getGoods_Name() %></h4>
                                    <h4><%= goods.getGoods_Maker() %></h4>
                                    <div class="price-info">
                                        <div>
                                            <div class="original-price">通常価格: <%= String.format("%,d", goods.getSales_Price()) %>円</div>
                                            <div class="sale-price">セール価格: <%= String.format("%,d", goods.getTime_Sales_Price()) %>円</div>
                                        </div>
                                        <div class="discount-rate">
                                            <%= String.format("%.0f", (1 - (double)goods.getTime_Sales_Price() / goods.getSales_Price()) * 100) %>% OFF
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="timesale-footer">
                                <div>開始: <%= bean.getStart_Date() %> <%= bean.getStart_Time() %></div>
                                <div>終了: <%= bean.getEnd_Date() %> <%= bean.getEnd_Time() %></div>
                            </div>
                        </div>
        <%
                    }
                }
            }
        }
        %>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        function showTab(tabName) {
            var tabContents = document.getElementsByClassName('tab-content');
            for(var i = 0; i < tabContents.length; i++) {
                tabContents[i].classList.remove('active');
            }
            
            var tabButtons = document.getElementsByClassName('tab-button');
            for(var i = 0; i < tabButtons.length; i++) {
                tabButtons[i].classList.remove('active');
            }
            
            document.getElementById(tabName + '-tab').classList.add('active');
            event.currentTarget.classList.add('active');
        }
    </script>
</body>
</html>

