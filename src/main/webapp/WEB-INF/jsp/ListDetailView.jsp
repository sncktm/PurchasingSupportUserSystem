<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.SalesDataBeans, model.ListInfoBeans" %>
<% ListInfoBeans salesList = (ListInfoBeans) request.getAttribute("salesList");%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <head>
    <meta charset="UTF-8">
    <title>マイリスト</title>
    <link rel="stylesheet" href="css/style.css?v=1.0">
    <style>
    /* リストグリッド */
    .list-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
        gap: 1.5rem;
    }

    /* リストカード */
    .list-card {
        background: white;
        border-radius: 10px;
        padding: 20px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        transition: box-shadow 0.3s ease-in-out;
    }

    .list-card:hover {
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
    }

    /* リストのヘッダー */
    .list-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .list-title {
        font-size: 18px;
        font-weight: bold;
        color: #333;
    }

    .date {
        display: flex;
        align-items: center;
        font-size: 14px;
        color: #666;
        margin-top: 5px;
    }
    .icon {
        margin-right: 5px;
    }

    /* 商品グリッド */
    .product-grid {
        display: flex;
        flex-wrap: wrap;  /* 画面幅に応じて折り返し */
        gap: 20px;        /* アイテム間にギャップを追加 */
    }

    /* 商品カード */
    .product-card {
        background: #f8f9fa;
        border-radius: 8px;
        overflow: hidden;
        text-align: center;
        padding-bottom: 10px;
        width: 289px;  /* 幅を設定 */
        flex-shrink: 0;  /* アイテムが縮まないように設定 */
    }

    .product-card img {
        width: 300px;
        height: 150px;
        object-fit: cover;
    }

    .product-info {
        padding: 10px;
    }

    .product-info h3 {
        font-size: 16px;
        color: #333;
        margin-bottom: 5px;
    }

    .product-info p {
        font-size: 14px;
        font-weight: bold;
        color: #000;
    }

    /* 詳細表示セクション */
    .detail-box {
        background-color: #ffffff;
        border-radius: 1rem;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        padding: 2rem;
        margin-top: 2rem;
    }

    .detail-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1.5rem;
    }

    .detail-header h2 {
        font-size: 1.75rem;
        font-weight: 600;
        color: #1f2937;
    }

    .detail-header .total-text {
        font-size: 1rem;
        color: #6b7280;
    }

    .detail-header button {
        background-color: #2563eb;
        color: white;
        padding: 0.75rem 1.5rem;
        border-radius: 0.75rem;
        font-weight: 500;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .detail-header button:hover {
        background-color: #1d4ed8;
    }
    .button-container{
    	margin: 20px 0;
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
<main>
	<h1 class="title">リスト詳細</h1>
    <div class="container">
		<!-- リスト詳細表示 -->
		<div class="detail-box">
			<div class="detail-header">
                <h2>買い物リスト</h2>
                <div class="detail-actions">
                    <span class="total-text">合計: ¥2,450</span>
                    
                </div>
            </div>
	        <% if (salesList.getSalesDataBeans() != null && !salesList.getSalesDataBeans().isEmpty()) {  %>
	        <div class="product-grid">
                <% 
                for (SalesDataBeans sales : salesList.getSalesDataBeans()) { 
                %>
                <div class="product-card">
                    <img src="<%= sales.getImage() %>" alt="商品">
                    <div class="product-info">
                        <h3><%= sales.getGoods_Name() %></h3>
                        <p>¥<%= sales.getSales_Price() %></p>
                    </div>
                </div>
                <%   } %>
			</div>
	        <% } else { %>
	            <p>表示する登録リストがありません</p>
	        <%  } %>
        </div>
    </div>

    <div class="button-container">
        <button onclick="history.back()" class="button cancel-button">戻る</button>
    </div>
</main>
</body>
</html>