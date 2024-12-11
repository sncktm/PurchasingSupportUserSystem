<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import= "model.MemberBeans" %>
<% MemberBeans loginmMmber = (MemberBeans) session.getAttribute("loginMember"); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品リスト</title>
    <link rel="stylesheet" href="css/style.css?v=1.0">
</head>
<style>
	body{
		background-color: rgb(253, 259, 252);
	}
	.contents{
		display:flex;
	}
	.goods-list{
		list-style: none;
		display: flex;
		flex-wrap: wrap;
	}
	.goods{
		background-color: #fff;
		border-radius: 5px;
		padding: 10px;
		margin: 10px;
		box-shadow: 0 4px 10px 0 rgba(0, 0, 0, 0.2);
		
	}
	.goods p{
		margin: auto;}
	.map{
		background-colorr: blue;
	}
	.store-name{
		color: #999;
		font-size: 80%;
	}
	.price{
		line-height: 2;
		font-weight: bold;
		font-size: 130%;
	}

	
</style>
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
 <main>
<div class="contents">
		
		<div class="sub-contents">
			<div><!--検索 -->
				<p>検索</p>
			</div>
			<ul class="goods-list"><!--商品リスト -->
				<li class="goods">
					<img alt="商品画像" src="img/goodsImg/apple.jpeg" width="200px" height="200px"> <!-- 商品画像 -->
					<p>りんご</h3><!-- 商品名 -->
					<p class="store-name">タイヨー</p><!-- 店舗名 -->
					<p class="price">100円</p><!-- 価格 -->
				</li>
			</ul>
		</div>
		
		<div class="map"> <!-- 地図 -->
			<p>地図</p>
		</div>

</div>
</main>
</body>
</html>
