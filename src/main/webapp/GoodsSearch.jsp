<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.GoodsBeans" %>
<%@ page import="model.GoodsArrayBeans" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>商品検索</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@300;400;500;700&display=swap">
    <link rel="stylesheet" href="css/style.css?v=1.0">
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB8pfuMfdXssPxTbdqtaiKpqN7IvYR5Abo&libraries=places"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
    <script>
        let map;
        let userMarker;
        let storeMarker;
        let userLat, userLon;
        let distanceMatrix;

        function initMap() {
            const defaultLocation = { lat: 35.6895, lng: 139.6917 };
            map = new google.maps.Map(document.getElementById("map"), {
                center: defaultLocation,
                zoom: 12,
            });

            distanceMatrix = new google.maps.DistanceMatrixService();
        }

        function updateDetails(name, maker, classification, price, janCode, storeName, status, statusClass, latitude, longitude, image, timeSaleEndTime) {
            document.getElementById("goods-list").classList.add("minimized");
            document.getElementById("details-container").style.display = "block";

            document.getElementById("detail-image").src = image;
            document.getElementById("detail-name").textContent = name;
            document.getElementById("detail-maker").textContent = maker;
            document.getElementById("detail-classification").textContent = classification;
            document.getElementById("detail-price").innerHTML = price.isTimeSale ?
                `<span class="time-sale">タイムセール中!</span><br>
                 <span class="original-price">${price.original}円</span> ${price.timeSale}円` :
                `${price.original}円`;
            document.getElementById("detail-jan").textContent = janCode;
            document.getElementById("detail-store").textContent = storeName;

            const statusElement = document.getElementById("detail-status");
            statusElement.textContent = status;
            statusElement.className = "status " + statusClass;

            const storeLocation = { lat: latitude, lng: longitude };
            if (storeMarker) {
                storeMarker.setMap(null);
            }
            storeMarker = new google.maps.Marker({
                position: storeLocation,
                map: map,
                title: storeName
            });

            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition((position) => {
                    const userLocation = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude,
                    };
                    if (userMarker) {
                        userMarker.setMap(null);
                    }
                    userMarker = new google.maps.Marker({
                        position: userLocation,
                        map: map,
                        title: "現在地",
                        icon: {
                            url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png"
                        }
                    });
                    fitBounds(userLocation, storeLocation);
                });
            } else {
                fitBounds(null, storeLocation);
            }

            // タイムセール終了時間の表示
            const timeSaleEndElement = document.getElementById("detail-time-sale-end");
            if (price.isTimeSale && timeSaleEndTime) {
                timeSaleEndElement.style.display = "block";
                timeSaleEndElement.querySelector(".detail-time-sale-end-span").textContent = timeSaleEndTime;
            } else {
                timeSaleEndElement.style.display = "none";
            }

            // 地図と詳細情報にスクロール
            document.getElementById("details-container").scrollIntoView({ behavior: 'smooth' });
        }

        function fitBounds(userLocation, storeLocation) {
            const bounds = new google.maps.LatLngBounds();
            if (userLocation) bounds.extend(userLocation);
            if (storeLocation) bounds.extend(storeLocation);
            map.fitBounds(bounds);
        }

        function getUserLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition((position) => {
                    userLat = position.coords.latitude;
                    userLon = position.coords.longitude;
                    calculateDistances();
                }, () => {
                    console.error('位置情報の取得に失敗しました。');
                    document.querySelectorAll('.distance').forEach(el => {
                        el.textContent = '距離不明';
                    });
                });
            } else {
                console.error('このブラウザは位置情報をサポートしていません。');
                document.querySelectorAll('.distance').forEach(el => {
                    el.textContent = '距離不明';
                });
            }
        }

        function calculateDistances() {
            const origins = [{ lat: userLat, lng: userLon }];
            const destinations = Array.from(document.querySelectorAll('.distance')).map(el => {
                return { lat: parseFloat(el.dataset.lat), lng: parseFloat(el.dataset.lon) };
            });

            distanceMatrix.getDistanceMatrix(
                {
                    origins: origins,
                    destinations: destinations,
                    travelMode: 'DRIVING',
                    unitSystem: google.maps.UnitSystem.METRIC,
                },
                (response, status) => {
                    if (status !== 'OK') {
                        console.error('Error was: ' + status);
                        return;
                    }

                    const distances = response.rows[0].elements;
                    document.querySelectorAll('.distance').forEach((el, index) => {
                        if (distances[index].status === 'OK') {
                            el.textContent = distances[index].distance.text;
                            el.dataset.distance = distances[index].distance.value; // メートル単位の距離を保存
                        } else {
                            el.textContent = '距離不明';
                            el.dataset.distance = '999999999'; // 大きな値を設定して最後にソートされるようにする
                        }
                    });

                    if (document.getElementById('sort-select').value === 'distance-asc') {
                        sortByDistance();
                    }
                }
            );
        }

        function sortByDistance() {
            const goodsList = document.querySelector('.goods-grid');
            const goods = Array.from(goodsList.children);
            goods.sort((a, b) => {
                const distanceA = parseFloat(a.querySelector('.distance').dataset.distance);
                const distanceB = parseFloat(b.querySelector('.distance').dataset.distance);
                return distanceA - distanceB;
            });
            goods.forEach(item => goodsList.appendChild(item));
        }

        function sortGoods() {
            const sortOption = document.getElementById('sort-select').value;
            if (sortOption === 'distance-asc') {
                calculateDistances(); // 距離計算と並び替えを行う
            } else if (sortOption === 'price-asc') {
                sortByPrice();
            } else if (sortOption === 'name-asc') {
                sortByNameAsc();
            } else if (sortOption === 'name-desc') {
                sortByNameDesc();
            }
        }

        function sortByPrice() {
            const goodsList = document.querySelector('.goods-grid');
            const goods = Array.from(goodsList.children);
            goods.sort((a, b) => {
                const priceA = parseInt(a.querySelector('.price').textContent);
                const priceB = parseInt(b.querySelector('.price').textContent);
                return priceA - priceB;
            });
            goods.forEach(item => goodsList.appendChild(item));
        }

        function sortByNameAsc() {
            const goodsList = document.querySelector('.goods-grid');
            const goods = Array.from(goodsList.children);
            goods.sort((a, b) => {
                const nameA = a.querySelector('h3').textContent;
                const nameB = b.querySelector('h3').textContent;
                return nameA.localeCompare(nameB);
            });
            goods.forEach(item => goodsList.appendChild(item));
        }

        function sortByNameDesc() {
            const goodsList = document.querySelector('.goods-grid');
            const goods = Array.from(goodsList.children);
            goods.sort((a, b) => {
                const nameA = a.querySelector('h3').textContent;
                const nameB = b.querySelector('h3').textContent;
                return nameB.localeCompare(nameA);
            });
            goods.forEach(item => goodsList.appendChild(item));
        }

        // ページ読み込み時に現在地を取得
        window.onload = function() {
            initMap();
            getUserLocation();
            sortGoods(); // 並び替えを適用
        };
        
    </script>

    <style>
        body {
            font-family: 'Noto Sans JP', sans-serif;
            background-color: #f5f7fa;
            color: #333;
            line-height: 1.6;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .search-form {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .search-form input[type="text"] {
            width: 70%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-form button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .search-form button:hover {
            background-color: #45a049;
        }
        .goods-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
            gap: 10px;
            justify-content: center;
        }
        .goods-grid.minimized {
            grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
        }
        .goods {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 10px;
            cursor: pointer;
            transition: transform 0.3s ease;
        }
        .goods:hover {
            transform: translateY(-5px);
        }
        .goods img {
            width: 100%;
            height: 100px;
            object-fit: cover;
            border-radius: 4px;
        }
        .minimized .goods img {
            height: 100px;
        }
        #details-container {
            display: none;
            margin-top: 20px;
        }
        #details, #map-container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 20px;
            margin-bottom: 20px;
        }
        #map {
            height: 300px;
            width: 100%;
            border-radius: 4px;
        }
        .status {
            font-weight: bold;
            padding: 3px 8px;
            border-radius: 4px;
            display: inline-block;
        }
        .status.red {
            background-color: #ffcccb;
            color: #d32f2f;
        }
        .status.gray {
            background-color: #e0e0e0;
            color: #616161;
        }
        .status.black {
            background-color: #f5f5f5;
            color: #212121;
        }
        .time-sale {
            color: red;
            font-weight: bold;
        }
        .original-price {
            text-decoration: line-through;
            color: #888;
        }
        .goods h3 {
            font-size: 14px;
            margin: 5px 0;
        }
        .goods .store-name {
            font-size: 12px;
            color: #666;
        }
        .goods .price {
            font-size: 14px;
            font-weight: bold;
        }
        .goods .status {
            font-size: 12px;
            padding: 2px 5px;
        }
        .goods .distance {
            font-size: 12px;
        }
        .goods .time-sale-end {
            font-size: 12px;
            color: #d32f2f;
        }
        @media (min-width: 768px) {
            .content-wrapper {
                display: flex;
                gap: 20px;
            }
            #goods-list {
                flex: 1;
            }
            #details-container {
                flex: 1;
            }
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .pagination a {
            color: black;
            float: left;
            padding: 8px 16px;
            text-decoration: none;
            transition: background-color .3s;
            border: 1px solid #ddd;
            margin: 0 4px;
        }
        .pagination a.active {
            background-color: #4CAF50;
            color: white;
            border: 1px solid #4CAF50;
        }
        .pagination a:hover:not(.active) {background-color: #ddd;}
        html {
            scroll-behavior: smooth;
        }
        
        
        
        /* 商品情報 */
        .product-image {
		    position: relative;
		}
		
		.product-image img {
		    width: 100%;
		    height: 300px;
		    object-fit: cover;
		    border-radius: 0.5rem;
		}
		
		.sale-badge {
		    position: absolute;
		    top: 1rem;
		    right: 1rem;
		    background-color: #ef4444;
		    color: white;
		    padding: 0.5rem 1rem;
		    border-radius: 9999px;
		    font-weight: bold;
		    animation: pulse 2s infinite;
		}
		.product-info {
		    margin-top: 2rem;
		}
		
		#detail-name {
		    font-size: 1.875rem;
		    font-weight: bold;
		    color: #1f2937;
		}
		
		.info-list {
			background: #f9fafb;
			border-radius: 5px;
			padding: 15px;
		    margin-top: 1.5rem;
		    display: flex;
		    flex-direction: column;
		    gap: 1rem;
		}
		
		.info-item {
		    display: flex;
		    align-items: center;
		    gap: 0.5rem;
		    color: #4b5563;
		}
		
		.info-item i {
		    width: 1.25rem;
		    height: 1.25rem;
		}
		
		.info-item .label {
		    font-weight: 500;
		}
		
		/* 価格表示 */
		.price {
		    margin-top: 2rem;
		    display: flex;
		    align-items: baseline;
		    gap: 0.5rem;
		}
		.detail-time-sale-end-span{
			margin: auto;
		}
		
		
		/* リストボタン */
		.register-button {
		    margin-top: 2rem;
		    width: 100%;
		    background-color: #2563eb;
		    color: white;
		    padding: 0.75rem 1.5rem;
		    border-radius: 0.5rem;
		    font-weight: 500;
		    transition: background-color 0.2s;
		    border: none;
		    cursor: pointer;
		}
		
		.register-button:hover {
		    background-color: #1d4ed8;
		}
		
		/* モーダル */
		.modal {
		  display: none;
		  position: fixed;
		  top: 0;
		  left: 0;
		  width: 100%;
		  height: 100%;
		  background-color: rgba(0, 0, 0, 0.5);
		  justify-content: center;
		  align-items: center;
		  z-index: 1000;
		}
		
		.modal-content {
		  background-color: white;
		  padding: 2rem;
		  border-radius: 0.5rem;
		  width: 90%;
		  max-width: 500px;
		}
		
		.modal-content h2 {
		  font-size: 1.5rem;
		  font-weight: bold;
		  color: #1f2937;
		  margin-bottom: 1rem;
		}
		
		/* リスト選択 */
		.list-register{
			text-align: center;
		}
		.list-selection {
		  display: flex;
		  flex-direction: column;
		  gap: 0.5rem;
		}
		
		.list-selection li {
		  width: 100%;
		  padding: 0.75rem;
		  list-style: none;
		  text-align: left;
		  background-color: #f3f4f6;
		  border: none;
		  border-radius: 0.25rem;
		  cursor: pointer;
		  transition: background-color 0.2s;
		}
		
		.list-selection li:hover {
		  background-color: #e5e7eb;
		}
						
		/* 完了通知 */
		.completion {
		  text-align: center;
		}
		
		.success-icon {
		  width: 4rem;
		  height: 4rem;
		  color: #22c55e;
		  margin-bottom: 1rem;
		}
		
		.completion-message {
		  color: #4b5563;
		  margin: 1rem 0;
		}
		
		.close-btn {
		  background-color: #2563eb;
		  color: white;
		  padding: 0.75rem 1.5rem;
		  border-radius: 0.25rem;
		  border: none;
		  cursor: pointer;
		  transition: background-color 0.2s;
		}
		
		.close-btn:hover {
		  background-color: #1d4ed8;
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

<div class="container">
	<h1>商品検索</h1>
	
	<form id="searchForm" action="goods" method="get" class="search-form">
	    <input type="text" name="keyword" placeholder="商品名を検索" value="${param.keyword}">
	    <label>
	        <input type="checkbox" name="showOnlyAvailable" value="true" 
	               ${param.showOnlyAvailable == 'true' ? 'checked' : ''}> 販売中のみ表示
	    </label>
	    <label>
	        <input type="checkbox" name="showOnlySale" value="true" 
	               ${param.showOnlySale == 'true' ? 'checked' : ''}> セール中のみ表示
	    </label>
	    <input type="hidden" name="sortOption" value="${param.sortOption}">
	    <button type="submit">検索</button>
	</form>

	<form id="sortForm" action="goods" method="get">
	    <input type="hidden" name="keyword" value="${param.keyword}">
	    <input type="hidden" name="showOnlyAvailable" value="${param.showOnlyAvailable}">
	    <input type="hidden" name="showOnlySale" value="${param.showOnlySale}">
	    <div class="sort-options">
	        <label for="sort-select">並び替え:</label>
	        <select id="sort-select" name="sortOption" onchange="this.form.submit()">
	            <option value="distance-asc" ${param.sortOption == 'distance-asc' ? 'selected' : ''}>現在地から近い順</option>
	            <option value="price-asc" ${param.sortOption == 'price-asc' ? 'selected' : ''}>価格が安い順</option>
	            <option value="name-asc" ${param.sortOption == 'name-asc' ? 'selected' : ''}>商品名 昇順</option>
	            <option value="name-desc" ${param.sortOption == 'name-desc' ? 'selected' : ''}>商品名 降順</option>
	        </select>
	    </div>
	</form>

	<div class="content-wrapper">
 		<div id="goods-list">
 			<div class="goods-grid">
				 <%
				     GoodsArrayBeans goodsArray = (GoodsArrayBeans) request.getAttribute("goodsArray");
				     if (goodsArray != null) {
				         List<GoodsBeans> goodsList = goodsArray.getGoodsArray();
				         String sortOption = request.getParameter("sortOption");
				
				         if (goodsList.isEmpty()) {
				 %>
				     <p>該当する商品が見つかりません。</p>
				 <%
				         } else {
				             for (GoodsBeans goods : goodsList) {
				                 String status = "";
				                 String statusClass = "black";
				
				                 if ("1".equals(goods.getSales_Flag())) {
				                     if (goods.getOpening_Time() != null && goods.getClosing_Time() != null) {
				                         java.time.LocalTime now = java.time.LocalTime.now();
				                         if (now.isAfter(goods.getOpening_Time().toLocalTime()) &&
				                             now.isBefore(goods.getClosing_Time().toLocalTime())) {
				                             status = goods.isTimeSale() ? "タイムセール中" : "販売中";
				                             statusClass = "red";
				                         } else {
				                             status = "店舗閉店中";
				                             statusClass = "black";
				                         }
				                     }
				                 } else {
				                     status = "販売停止";
				                     statusClass = "gray";
				                 }
				
				                 String timeSaleEndTime = "";
				                 if (goods.isTimeSale() && goods.getTimeSaleEndTime() != null) {
				                     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
				                     timeSaleEndTime = goods.getTimeSaleEndTime().format(formatter);
				                 }
				 %>
				 <div class="goods" onclick="updateDetails(
				         '<%= goods.getGoods_Name() %>',
				         '<%= goods.getGoods_Maker() %>',
				         '<%= goods.getClassification() %>',
				         { original: <%= goods.getSales_Price() %>, timeSale: <%= goods.getTimeSalePrice() %>, isTimeSale: <%= goods.isTimeSale() %> },
				         '<%= goods.getJAN_code() %>',
				         '<%= goods.getStore_Name() %>',
				         '<%= status %>',
				         '<%= statusClass %>',
				         <%= goods.getLatitude() %>,
				         <%= goods.getLongitude() %>,
				         '<%= goods.getImage() %>',
				         '<%= timeSaleEndTime %>'
				     )">
				     <img src="<%= goods.getImage() %>" alt="<%= goods.getGoods_Name() %>">
				     <h3><%= goods.getGoods_Name() %></h3>
				     <p class="store-name"><%= goods.getStore_Name() %></p>
				     <p class="price">
				         <% if (goods.isTimeSale()) { %>
				             <span class="time-sale">タイムセール中!</span><br>
				             <span class="original-price"><%= goods.getSales_Price() %>円</span>
				             <%= goods.getTimeSalePrice() %>円
				         <% } else { %>
				             <%= goods.getSales_Price() %>円
				         <% } %>
				     </p>
				     <p class="status <%= statusClass %>"><%= status %></p>
				     <p class="distance" data-lat="<%= goods.getLatitude() %>" data-lon="<%= goods.getLongitude() %>">距離計算中...</p>
				     <% if (goods.isTimeSale()) { %>
				         <p class="time-sale-end">セール終了: <%= timeSaleEndTime %></p>
				     <% } %>
 				</div>
				<%
				            }
				        }
				    }
				%>
 			</div>
        </div>

		<div id="details-container">
			<div id="map-container">
			    <div id="map"></div>
			</div>

			<div id="details">
            	<div class="product-image">
					<img id="detail-image" src="/placeholder.svg" alt="商品画像">
				</div>
				
				<div class="product-info">
					<span id="detail-name"></span> <!-- 商品名 -->
					
					<div class="info-price">
				    	<span id="detail-price"></span>
					</div>
					
	                <div class="info-list">
					    <div class="info-item">
					        <i data-lucide="building-2"></i>
					        <span class="label">メーカー:</span>
					        <span id="detail-maker"></span>
					    </div>
					
					    <div class="info-item">
					        <i data-lucide="tag"></i>
					        <span class="label">分類:</span>
					        <span id="detail-classification"></span>
					    </div>
					
					    <div class="info-item">
					        <i data-lucide="package"></i>
					        <span class="label">JANコード:</span>
					        <span id="detail-jan"></span>
					    </div>
					
					    <div class="info-item">
					        <i data-lucide="map-pin"></i>
					        <span class="label">店舗:</span>
					        <span id="detail-store"></span>
					    </div>
					    
					    <div class="info-item">
					        <i data-lucide="shopping-basket"></i>
					        <span class="label">販売状態:</span>
					        <span id="detail-status" class="status"></span>
					    </div>
					
					    <div id="detail-time-sale-end" class="info-item" style="display: none;">
					        <i data-lucide="clock"></i>
					        <span class="label">セール終了:</span>
					        <span class="detail-time-sale-end-span"></span>
					    </div>
					</div>
				</div>
	
				
				<button class="register-button" id="register-button">リストに登録</button>
               
                
            </div>
        </div>
    </div>
    
    <!-- リスト選択モーダル -->
    <div class="modal" id="list-register">
       	<div class="modal-content list-register">
       		<h2>リストを選択</h2>
			<ul id="list-container" class="list-selection">
    			<!-- ここにリスト-->
			</ul>
			<button onclick="closePopup()" class="close-btn">閉じる</button>
		</div>
	</div>
    
     <!-- 完了通知モーダル -->
    <div id="completionModal" class="modal">
      <div class="modal-content completion">
      
        <h2>リストに追加しました</h2>
        <p class="completion-message">選択したリストに商品を追加しました。</p>
        <button onclick="closeCompletionModal()" class="close-btn">
          閉じる
        </button>
      </div>
    </div>
    

    <!-- ページネーション -->
    <div class="pagination">
        <%
            Integer currentPageObj = (Integer) request.getAttribute("currentPage");
            Integer totalPagesObj = (Integer) request.getAttribute("totalPages");
            int currentPage = (currentPageObj != null) ? currentPageObj : 1;
            int totalPages = (totalPagesObj != null) ? totalPagesObj : 1;
            String keyword = (String) request.getAttribute("keyword");
            String showOnlyAvailable = (String) request.getAttribute("showOnlyAvailable");
            String showOnlySale = (String) request.getAttribute("showOnlySale");
            String sortOption = (String) request.getAttribute("sortOption");

            for (int i = 1; i <= totalPages; i++) {
                String pageUrl = "goods?page=" + i;
                if (keyword != null) pageUrl += "&keyword=" + keyword;
                if (showOnlyAvailable != null) pageUrl += "&showOnlyAvailable=" + showOnlyAvailable;
                if (showOnlySale != null) pageUrl += "&showOnlySale=" + showOnlySale;
                if (sortOption != null) pageUrl += "&sortOption=" + sortOption;
        %>
            <a href="<%= pageUrl %>" <%= (i == currentPage) ? "class='active'" : "" %>><%= i %></a>
        <%
            }
        %>
    </div>
</div>
<script>

//ーーーーここから先リスト登録関連ーーーー
const registerButton = document.getElementById('register-button');
const listRegister = document.getElementById('list-register');
const listContainer = document.getElementById('list-container');
const completionModal = document.getElementById('completionModal');


//リストに登録ボタンがクリックされた場合
registerButton.addEventListener('click', () => {
	fetch('ListSelectServlet')
    .then(response => response.json())
    .then(data => {
        console.log(data);  // データの中身を確認

        listContainer.innerHTML = ''; // 既存のリストをクリア

        // 取得したデータを元に <li> を追加
        data.forEach(item => {
            console.log(item.List_Name); // 各アイテムのリスト名を確認
            const listItem = document.createElement('li'); // <li> 要素を作成
            listItem.textContent = item.List_Name; // List_Nameを表示
            listItem.setAttribute('data-list-no', item.List_No); // List_No をデータ属性に設定
            listContainer.appendChild(listItem); // listContainerに追加
            

            // listItemがクリックされた時に送信する処理
            listItem.addEventListener('click', () => {
                const listNo = listItem.getAttribute('data-list-no');
                console.log('選択されたList_No:', listNo);
                sendListNoToServer(listNo); // サーバーにList_Noを送信
            });
        });
        listRegister.style.display = "flex";
    })
    .catch(error => {
        console.error('Error:', error);
    });

});


function closePopup() {
    listRegister.style.display = 'none';
}


//リスト登録
function sendListNoToServer(listNo) {
    fetch('MylistRegisteredServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain; charset=UTF-8'
        },
        body: listNo // 文字列をそのまま送信
    })
    .then(response => response.text())
    .then(data => {
        console.log('サーバーからのレスポンス:', data);
        console.log("登録したよん");
        listRegister.style.display = "none";
        completionModal.style.display = "flex";
    })
    .catch(error => {
        console.error('Error sending List_No:', error);
    });
}

//完了モーダルを閉じる
function closeCompletionModal() {
  completionModal.style.display = "none";
}
</script>
<script>
        // Lucide Icons を初期化して描画
        lucide.createIcons();
    </script>
</body>
</html>

