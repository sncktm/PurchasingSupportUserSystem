<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String errorMsg = (String)request.getAttribute("errorMsg"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<link rel="stylesheet" href="css/style.css">
<style>
body{
	 background-color: #f7f7f7;
}
	.login{
		background-color: #FFF;
		max-width: 400px;
          margin: 0 auto;
          padding: 20px;
          border: 1px solid;
          border-radius: 4px;
	}
	h1{
		text-align: center;
        margin-bottom: 20px;
	}
	.group {
          margin-bottom: 15px;
        }
    label {
          display: block;
          background: #fff2cd;
          padding: 8px;
          border: 1px solid ;
          border-radius: 4px 4px 0 0;
          font-size: 14px;
          width: 30%;
          text-align: center;
        }
        
    input{
          width: 100%;
          padding: 15px;
          border: 1px solid;
          border-radius: 0 0 4px 4px;
          font-size: 16px;
          box-sizing: border-box;
        }
     
</style>
</head>
<body>

<div class="login">
	<h1>ログイン</h1>
	<form action="MemberLoginServlet" method="post">
		<div class="group">
			<label for="email">メールアドレス</label>
			<input type="text" name="email" id="email" placeholder="メールアドレスを入力">
		</div>
		<div class="group">
			<label for="pass">パスワード</label>
			<input type="password" name="pass" id="pass" placeholder="パスワードを入力">
		</div>
	<% if(errorMsg != null){ %>
	<p><%= errorMsg %></p>
	<% } %>
	<input type="submit" value="ログイン" class="button confirmed-button"><br>
	</form>
</div>

</body>
</html>