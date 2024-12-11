<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.MembersInfoBeans" %>
 <%
    	MembersInfoBeans member = (MembersInfoBeans) session.getAttribute("members");
       
    %>
<html>
<head>
    <meta charset="UTF-8">
    <title>会員情報</title>
    <link rel="stylesheet" href="css/style.css?v=1.0">
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
<main>
    <h1>利用者情報閲覧画面</h1>
    <table>
        <tr>
            <th>会員番号</th>
            <td><%= member.getmembers_number() %></td>
        </tr>
        <tr>
            <th>姓</th>
            <td><%= member.getfamily_name() %></td>
        </tr>
        <tr>
            <th>名</th>
            <td><%= member.getfirst_name() %></td>
        </tr>
        <tr>
            <th>姓ふりがな</th>
            <td><%= member.getfamily_name_furigana() %></td>
        </tr>
        <tr>
            <th>名ふりがな</th>
            <td><%= member.getfirst_name_furigana() %></td>
        </tr>
        <tr>
            <th>性別</th>
            <td><%= member.getgender() %></td>
        </tr>
        <tr>
            <th>郵便番号</th>
            <td><%= member.getmembers_Postal_Code() %></td>
        </tr>
        <tr>
            <th>都道府県</th>
            <td><%= member.getmembers_Prefecture() %></td>
        </tr>
        <tr>
            <th>住所1</th>
            <td><%= member.getmembers_Address1() %></td>
        </tr>
        <tr>
            <th>住所2</th>
            <td><%= member.getmembers_Address2() %></td>
        </tr>
        <tr>
            <th>電話番号</th>
            <td><%= member.getmembers_Phonenumber() %></td>
        </tr>
        <tr>
            <th>メールアドレス</th>
            <td><%= member.getmembers_Mail_Address() %></td>
        </tr>
    </table>
    <br/>
    <div class="button-container">
        <button onclick="history.back()">戻る</button>
    </div>
 </main>
</body>

</html>
