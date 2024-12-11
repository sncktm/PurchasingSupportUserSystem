<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import= "model.MemberBeans" %>
    
    <% MemberBeans loginmMmber = (MemberBeans) session.getAttribute("loginMember"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css?v=1.0">
<style>
	.login{
		background-color: #FFF;
		max-width: 400px;
          margin: 0 auto;
          padding: 20px;
          border: 1px solid;
          border-radius: 4px;
          margin-top: 50px;
	}

	
</style>
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
<main>
	<div class="login">
		<h1>ログインしました</h1>
		<a href="GoodsInfomationViewServlet" class="button confirmed-button">ホームへ</a>
	</div>
</main>

</body>
</html>