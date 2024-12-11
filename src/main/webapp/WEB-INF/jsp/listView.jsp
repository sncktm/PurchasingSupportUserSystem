<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <head>
    <meta charset="UTF-8">
    <title>マイリスト</title>
    <!-- 外部CSSをリンク -->
    <link rel="stylesheet" type="text/css" href="styles/mypage.css">
</head>
</head>
<body>

    <header>
        <div class="logo">ロゴ</div>
        <nav class="nav-menu">
            <a href="#">ホーム</a>
            <a href="#">検索</a>
            <a href="#">マイページ</a>
            <a href="#">ポイント</a>
        </nav>
    </header>

    <div class="container">
        <h1>
            <%
                String viewType = (String) request.getAttribute("viewType");
                if ("list".equals(viewType)) {
                    out.print("リスト情報");
                } else if ("details".equals(viewType)) {
                    out.print("登録リスト内容");
                }
            %>
        </h1>

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
                        <h2>
                            <%= item.getList_Name() %>
                            <span><%= item.getList_Date() %></span>
                        </h2>
                        <a href="ListViewServlet?List_No=<%= item.getList_No() %>" class="view-button">リストを表示</a>
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
                        <img src="<%= sales.getImage() %>" alt="商品画像: <%= sales.getSales_No() %>">
                        <div class="item-info">
                            <p>商品名: <%= sales.getGoods_Name() %></p>
                            <p>商品番号: <%= sales.getSales_No() %></p>
                            <p>登録商品代金<%= sales.getSales_Price() %></p>
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
        %>
            <a href="ListViewServlet" class="back-button">戻る</a>
        <% 
        } 
        %>
    </div>

    <footer>
        <br/>
    <div class="button-container">
        <button onclick="history.back()">戻る</button>
    </div>
</body>
</html>