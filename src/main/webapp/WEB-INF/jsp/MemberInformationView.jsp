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
<style>/* Color variables */
:root {
  --primary-blue: #0055a4;
  --secondary-blue: #003366;
  --light-blue: #f0f7ff;
  --hover-blue: #0066cc;
  --border-blue: #d0e3ff;
  --text-dark: #1a1a1a;
  --text-light: #ffffff;
  --shadow-color: rgba(0, 51, 102, 0.1);
}

/* Typography */
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap');

/* Base styles */


.contents {
  max-width: 900px;
  margin: 3rem auto;
  padding: 2rem;
  background-color: var(--text-light);
  border-radius: 12px;
  box-shadow: 0 8px 24px var(--shadow-color);
  font-family: 'Noto Sans JP', sans-serif;
}

h1 {
  font-size: 2rem;
  font-weight: 700;
  letter-spacing: 0.02em;
  text-align: center;
}

/* Table styles */
table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  margin-bottom: 2.5rem;
  box-shadow: 0 4px 12px var(--shadow-color);
  border-radius: 8px;
  overflow: hidden;
}

th, td {
  padding: 1.2rem;
  text-align: left;
  transition: background-color 0.2s ease;
}

th {
  background-color: var(--primary-blue);
  color: var(--text-light);
  width: 30%;
  font-weight: 500;
  letter-spacing: 0.03em;
}

td {
  width: 70%;
  background-color: var(--text-light);
  color: var(--text-dark);
}

tr:not(:last-child) th,
tr:not(:last-child) td {
  border-bottom: 1px solid var(--border-blue);
}

tr:hover td {
  background-color: var(--light-blue);
}

/* Button styles */
.button-container {
  text-align: center;
  margin-top: 1rem;
}

button {
  padding: 1rem 3rem;
  background: linear-gradient(to bottom right, var(--primary-blue), var(--secondary-blue));
  color: var(--text-light);
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1.1rem;
  font-family: 'Noto Sans JP', sans-serif;
  font-weight: 500;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px var(--shadow-color);
}

button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px var(--shadow-color);
  background: linear-gradient(to bottom right, var(--hover-blue), var(--primary-blue));
}

button:active {
  transform: translateY(0);
  box-shadow: 0 2px 8px var(--shadow-color);
}

/* Media Queries */
@media screen and (max-width: 768px) {
  main {
    margin: 1.5rem;
    padding: 1.5rem;
  }

  h1 {
    font-size: 1.6rem;
  }

  th, td {
    padding: 1rem;
  }

  button {
    padding: 0.9rem 2.5rem;
    font-size: 1rem;
  }
}

@media screen and (max-width: 480px) {
  main {
    margin: 1rem;
    padding: 1rem;
  }

  h1 {
    font-size: 1.4rem;
  }

  table, th, td {
    display: block;
  }

  th {
    width: 100%;
    border-bottom: none;
    padding: 0.8rem 1rem;
  }

  td {
    width: 100%;
    padding: 1rem;
    border-bottom: 1px solid var(--border-blue);
  }

  tr {
    margin-bottom: 1.5rem;
    border: 1px solid var(--border-blue);
    border-radius: 6px;
    overflow: hidden;
  }

  .button-container {
    margin-top: 0.5rem;
  }

  button {
    width: 100%;
    padding: 0.8rem;
  }
}</style>
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
<h1 class="title">利用者情報閲覧画面</h1>
	<div class="contents">
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
	            <th>住所</th>
	            <td><%= member.getmembers_Address1() %><%= member.getmembers_Address2() %></td>
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
	</div>
    
 </main>
</body>

</html>
