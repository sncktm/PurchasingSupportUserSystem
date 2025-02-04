<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>商品管理画面</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        header, footer {
            background-color: #cfe2f3;
            padding: 10px 20px;
            text-align: center;
        }
        header .nav {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        header .nav button {
            background: none;
            border: none;
            padding: 8px 12px;
            font-size: 14px;
            cursor: pointer;
        }
        header .nav button:hover {
            background-color: #e7f1ff;
        }
        main {
            display: flex;
            margin: 20px auto;
            width: 90%;
            height: 80vh;
        }
        .product-list {
            flex: 3;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            margin-right: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .product-photo {
            height: 200px;
            background-color: #fde8cc;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #999;
            margin-bottom: 20px;
            border-radius: 8px;
        }
        .product-details div {
            margin: 5px 0;
        }
        .register-button {
            background-color: #f4a460;
            border: none;
            color: white;
            padding: 10px;
            cursor: pointer;
            border-radius: 4px;
        }
        .register-button:hover {
            background-color: #e59450;
        }
        .list-register {
            flex: 2;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            display: none;
        }
        .list-register h3 {
            margin-bottom: 20px;
            font-size: 18px;
            color: #333;
            text-align: center;
            border-bottom: 2px solid #ddd;
            padding-bottom: 10px;
        }
        .list-buttons button {
            display: block;
            width: 100%;
            padding: 12px 16px;
            margin-bottom: 15px;
            background-color: #e7f1ff;
            border: 1px solid #cdddee;
            cursor: pointer;
            border-radius: 4px;
            font-size: 16px;
            text-align: left;
            transition: background-color 0.3s ease, transform 0.2s;
        }
        .list-buttons button:hover {
            background-color: #cfe2f3;
            transform: translateY(-3px);
        }
        .list-buttons button:active {
            transform: translateY(0);
        }
        .list-buttons button span {
            font-weight: bold;
            color: #555;
        }
        .popup-message {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #4caf50;
            color: white;
            padding: 20px 40px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
            font-size: 18px;
            text-align: center;
            z-index: 1000;
        }
    </style>
</head>
<body>
<header>
    <div class="nav">
        <div>ロゴ</div>
        <div>
            <button>ホーム</button>
            <button>検索</button>
            <button>マイページ</button>
            <button>ポイント</button>
        </div>
        <div>通知</div>
    </div>
</header>
<main>
    <div class="product-list">
        <div class="product-photo">商品写真</div>
        <div class="product-details">
            <div>商品名</div>
            <div>商品金額</div>
            <div>店舗名</div>
            <div>営業時間</div>
        </div>
        <button class="register-button" id="register-button">リストに登録</button>
    </div>
    <div class="list-register" id="list-register">
        <h3>リストに登録</h3>
        <div class="list-buttons">
             <% 
                // セッションから"members"を取得
                java.util.ArrayList<model.ListBeans> members = (java.util.ArrayList<model.ListBeans>) session.getAttribute("members");
                
                if (members != null && !members.isEmpty()) {
            %>
            <table>
                <tbody>
                    <% for (model.ListBeans member : members) { %>
                        <tr>
                            <td>
                                <form action="MylistRegisteredServlet" method="post">
                                    <button name="List_No" value="<%= member.getList_No() %>"><%= member.getList_Name() %></button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
            <% 
                } else { 
            %>
            <p>表示するデータがありません。</p>
            <% 
                } 
            %>
            <button onclick="closePopup()">閉じる</button>
        </div>
    </div>

</main>
<footer>
    フッター
</footer>


<script>
    const registerButton = document.getElementById('register-button');
    const listRegister = document.getElementById('list-register');
    const successPopup = document.getElementById('success-popup');

    // 「リストに登録」ボタンをクリックした際の処理
    registerButton.addEventListener('click', () => {
        listRegister.style.display = 'block'; // リスト表示
    });

   
</script>

</body>
</html>
