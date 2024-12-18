<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <head>
    <meta charset="UTF-8">
    <title>マイリスト</title>
    <link rel="stylesheet" href="css/style.css?v=1.0">
    <style>
    .list-container{
    	display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 2rem;
    }
    
    .list-item{
    	background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 1.5rem;
    
    }
    
    .view-btn{
    flex: 1;
    padding: 0.5rem;
    border: none;
    border-radius: 4px;
    font-size: 0.875rem;
    cursor: pointer;
    transition: background-color 0.3s;
}

.view-btn {
    background-color: #fff9c4;
    color: #f57f17;
}

.view-btn:hover {
    background-color: #fff59d;
}


.details-container{
	display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 1.5rem;
}

.details-item{
	background-color: white;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.goods-name{
font-weight: 500;
    margin-bottom: 1rem;
}

.goods-price{

}


.goods-image {
    width: 100%;
    aspect-ratio: 1;
    background-color: #e0e0e0;
}

.goods-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}
    
    </style>
</head>
</head>
<body>
<header>
<div class="header-content">
	<div class="logo">
    	ろご
    </div>
    <nav>
        <ul class="menu-lists">
            <li class="menu-list">
            	<img alt="" src="img/homeIcon.png" width="40px" height="40px">
                <a href="#">ホーム</a>
            </li>
            <li class="menu-list">
            	<img alt="" src="img/searchIcon.png" width="40px" height="40px">
                <a href="#">検索</a>
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
        <h1 class="title">
            <%
                String viewType = (String) request.getAttribute("viewType");
                if ("list".equals(viewType)) {
                    out.print("リスト情報");
                } else if ("details".equals(viewType)) {
                    out.print("登録リスト内容");
                }
            %>
        </h1>
<main>

        <% 
        if ("list".equals(viewType)) { 
            java.util.List<model.ListInfoBeans> listArray = 
                (java.util.List<model.ListInfoBeans>) request.getAttribute("listArray");

            if (listArray != null && !listArray.isEmpty()) { 
        %>
            <div class="list-container">
                <% 
                for (model.ListInfoBeans item : listArray) { 
                %>
                
                    <div class="list-item">
                    <form action="ListViewServlet" method="GET">
                        <h2>
                            <%= item.getList_Name() %>
                            <span><%= item.getList_Date() %></span>
                        </h2>

						<button type="submit" value="<%= item.getList_No() %>" name="List_No">リストを表示</button>
                       
                        	
                        </form>
                        
                    </div>
                <% 
                } 
                %>
            </div>
        <% 
            } else { 
        %>
            <p>登録リストがありません</p>
        <% 
            } 
        } else if ("details".equals(viewType)) { 
            java.util.List<model.SalesDataBeans> salesDataList = 
                (java.util.List<model.SalesDataBeans>) request.getAttribute("salesDataList");

            if (salesDataList != null && !salesDataList.isEmpty()) { 
        %>
           <div class="details-container">
                <% 
                for (model.SalesDataBeans sales : salesDataList) { 
                %>
                    <div class="details-item">
                     <div clas="goods-image">
                        <img src="<%= sales.getImage() %>" width="200px" height="200px" alt="商品画像">
                        </div>
                        <div class="item-info">
                            <p class="goods-name">商品名: <%= sales.getGoods_Name() %></p>
                            <p class="goods-price"><%= sales.getSales_Price() %>円</p>
                        </div>
                    </div>
                <% 
                } 
                %>
            </div>
        <% 
            } else { 
        %>
            <p>表示する登録リストがありません</p>
        <% 
            } 
      
        } 
        %>
    </div>

    <footer>
        <br/>
    <div class="button-container">
        <button onclick="history.back()">戻る</button>
    </div>
    </main>
</body>
</html>