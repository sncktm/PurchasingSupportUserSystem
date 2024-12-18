<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%@ page import= "model.MemberBeans" %>
    
    <% MemberBeans loginMmber = (MemberBeans) session.getAttribute("loginMember"); %>
    <% 
    Integer totalPointsObj = (Integer) session.getAttribute("totalPoints");
    int totalPoints = (totalPointsObj != null) ? totalPointsObj : 0;
%>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ポイント情報</title>
    <link rel="stylesheet" href="css/style.css?v=1.0">
</head>
<style>
	main{
		max-width: 800px;
	    margin: 2rem auto;
	    padding: 0 ;
	    
	}
	
	.member-no{
		text-align: center;
	}
	
	.point {
    background-color: #fff;
    border-radius: 8px;
    padding: 0.5rem;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    margin-bottom: 2rem;
	}
	
	.point p {
	    font-size: 1rem;
	    color: #666;
	    margin-bottom: 0.5rem;
	}
	
	.point-value {
	    font-size: 1.5rem;
	    font-weight: bold;
	}
	
	.point-value span {
	    font-size: 1rem;
	    color: #666;
	}

	.mypage-menus {
	    display: grid;
	    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
	    gap: 1rem;
	    list-style-type: none;
	}

	.mypage-menus li a {
	    display: block;
	    background-color: #fff;
	    color: var(--text-color);
	    text-decoration: none;
	    padding: 1rem;
	    margin: 10px;
	    border-radius: 8px;
	    text-align: center;
	    transition: all 0.3s ease;
	    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}
	
	.mypage-menus li a:hover {
	    background-color: var(--primary-color);
	    color: #fff;
	    transform: translateY(-3px);
	    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
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
    <h1 class="title"><%= loginMmber.getFamily_name() %> <%= loginMmber.getFirst_name() %>さんのマイページ</h1>
    <h3 class="member-no">会員番号: <%= loginMmber.getMember_no() %></h3>
    
    <div class="point">
    	<p>現在のポイント</p>
    	<div class="point-value"><%= totalPoints %> <span>ポイント</span></div>
    </div>



	<ul class="mypage-menus">
       <li>
           <a href="ListViewServlet">登録リスト閲覧</a>
       </li>
    	<li>
           <a href="MemberInformationViewServlet">会員情報確認</a>
       </li>
       <li>
           <a href="#">ブックマーク</a>
       </li>
       <li>
           <a href="#">パスワードリセット</a>
       </li>
       <li>
           <a href="#">アカウント削除</a>
       </li>
       <li>
           <a href="#">通知設定</a>
       </li>
	</ul>
 </main>
</body>
</html>
