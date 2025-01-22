<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.GoodsBeans" %>
<%@ page import="model.GoodsArrayBeans" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>商品検索</title>
    <link rel="stylesheet" href="key1">
    <link rel="stylesheet" href="css/style.css?v=1.0">
    <script src="key2"></script>
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
                zoom: 14,
                styles: [
                    {
                        "featureType": "all",
                        "elementType": "geometry.fill",
                        "stylers": [{"weight": "2.00"}]
                    },
                    {
                        "featureType": "all",
                        "elementType": "geometry.stroke",
                        "stylers": [{"color": "#9c9c9c"}]
                    },
                    {
                        "featureType": "all",
                        "elementType": "labels.text",
                        "stylers": [{"visibility": "on"}]
                    },
                    {
                        "featureType": "landscape",
                        "elementType": "all",
                        "stylers": [{"color": "#f2f2f2"}]
                    },
                    {
                        "featureType": "landscape",
                        "elementType": "geometry.fill",
                        "stylers": [{"color": "#ffffff"}]
                    },
                    {
                        "featureType": "landscape.man_made",
                        "elementType": "geometry.fill",
                        "stylers": [{"color": "#ffffff"}]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "all",
                        "stylers": [{"visibility": "off"}]
                    },
                    {
                        "featureType": "road",
                        "elementType": "all",
                        "stylers": [{"saturation": -100}, {"lightness": 45}]
                    },
                    {
                        "featureType": "road",
                        "elementType": "geometry.fill",
                        "stylers": [{"color": "#eeeeee"}]
                    },
                    {
                        "featureType": "road",
                        "elementType": "labels.text.fill",
                        "stylers": [{"color": "#7b7b7b"}]
                    },
                    {
                        "featureType": "road",
                        "elementType": "labels.text.stroke",
                        "stylers": [{"color": "#ffffff"}]
                    },
                    {
                        "featureType": "road.highway",
                        "elementType": "all",
                        "stylers": [{"visibility": "simplified"}]
                    },
                    {
                        "featureType": "road.arterial",
                        "elementType": "labels.icon",
                        "stylers": [{"visibility": "off"}]
                    },
                    {
                        "featureType": "transit",
                        "elementType": "all",
                        "stylers": [{"visibility": "off"}]
                    },
                    {
                        "featureType": "water",
                        "elementType": "all",
                        "stylers": [{"color": "#46bcec"}, {"visibility": "on"}]
                    },
                    {
                        "featureType": "water",
                        "elementType": "geometry.fill",
                        "stylers": [{"color": "#c8d7d4"}]
                    },
                    {
                        "featureType": "water",
                        "elementType": "labels.text.fill",
                        "stylers": [{"color": "#070707"}]
                    },
                    {
                        "featureType": "water",
                        "elementType": "labels.text.stroke",
                        "stylers": [{"color": "#ffffff"}]
                    }
                ]
            });

            distanceMatrix = new google.maps.DistanceMatrixService();
        }

        function updateDetails(name, maker, classification, price, janCode, storeName, status, statusClass, latitude, longitude, image) {
            document.getElementById("goods-list").classList.add("minimized");
            document.getElementById("details-container").style.display = "block";

            document.getElementById("detail-image").src = image;
            document.getElementById("detail-name").textContent = name;
            document.getElementById("detail-maker").textContent = maker;
            document.getElementById("detail-classification").textContent = classification;
            document.getElementById("detail-price").textContent = price + "円";
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
                label: "店舗",
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
                        label: "現在地",
                    });
                    fitBounds(userLocation, storeLocation);
                });
            } else {
                fitBounds(null, storeLocation);
            }

            map.setCenter(storeLocation);
            map.setZoom(15);
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
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 20px;
            justify-content: center;
        }
        .goods-grid.minimized {
            grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
        }
        .goods {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 15px;
            cursor: pointer;
            transition: transform 0.3s ease;
        }
        .goods:hover {
            transform: translateY(-5px);
        }
        .goods img {
            width: 100%;
            height: 150px;
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
    </style>
</head>
<body>
<header>
<div class="header-content">
    <div class="logo">ろご</div>
    <nav>
        <ul class="menu-lists">
            <li class="menu-list"><img alt="" src="img/homeIcon.png" width="40px" height="40px"><a href="#">ホーム</a></li>
            <li class="menu-list"><img alt="" src="img/searchIcon.png" width="40px" height="40px"><a href="#">検索</a></li>
            <li class="menu-list"><img alt="" src="img/mypageIcon.png" width="40px" height="40px"><a href="MyPageServlet">マイページ</a></li>
            <li class="menu-list"><img alt="" src="img/pointIcon.png" width="40px" height="40px"><a href="#">ポイント</a></li>
        </ul>
    </nav>
</div>
</header>

<div class="container">
    <h1>商品検索</h1>

    <form action="goods" method="get" class="search-form">
        <input type="text" name="keyword" placeholder="商品名を検索" value="${param.keyword}">
        <label>
            <input type="checkbox" name="showOnlyAvailable" value="true" 
                   ${param.showOnlyAvailable == 'true' ? 'checked' : ''}> 販売中のみ表示
        </label>
        <button type="submit">検索</button>
    </form>

    <div class="sort-options">
        <label for="sort-select">並び替え:</label>
        <select id="sort-select" name="sortOption" onchange="sortGoods()">
            <option value="distance-asc" ${param.sortOption == 'distance-asc' ? 'selected' : ''}>現在地から近い順</option>
            <option value="price-asc" ${param.sortOption == 'price-asc' ? 'selected' : ''}>価格が安い順</option>
        </select>
    </div>

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
                                            status = "販売中";
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
                %>
                <div class="goods" onclick="updateDetails(
                        '<%= goods.getGoods_Name() %>',
                        '<%= goods.getGoods_Maker() %>',
                        '<%= goods.getClassification() %>',
                        '<%= goods.getSales_Price() %>',
                        '<%= goods.getJAN_code() %>',
                        '<%= goods.getStore_Name() %>',
                        '<%= status %>',
                        '<%= statusClass %>',
                        <%= goods.getLatitude() %>,
                        <%= goods.getLongitude() %>,
                        '<%= goods.getImage() %>'
                    )">
                    <img src="<%= goods.getImage() %>" alt="<%= goods.getGoods_Name() %>">
                    <h3><%= goods.getGoods_Name() %></h3>
                    <p class="store-name"><%= goods.getStore_Name() %></p>
                    <p class="price"><%= goods.getSales_Price() %>円</p>
                    <p class="status <%= statusClass %>"><%= status %></p>
                    <p class="distance" data-lat="<%= goods.getLatitude() %>" data-lon="<%= goods.getLongitude() %>">距離計算中...</p>
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
                <img id="detail-image" src="/placeholder.svg" alt="商品画像" style="width: 100%; max-width: 300px; height: auto; margin-bottom: 20px;">
                <p><strong>商品名:</strong> <span id="detail-name"></span></p>
                <p><strong>メーカー:</strong> <span id="detail-maker"></span></p>
                <p><strong>分類:</strong> <span id="detail-classification"></span></p>
                <p><strong>価格:</strong> <span id="detail-price"></span></p>
                <p><strong>JANコード:</strong> <span id="detail-jan"></span></p>
                <p><strong>店舗名:</strong> <span id="detail-store"></span></p>
                <p><strong>販売状態:</strong> <span id="detail-status" class="status"></span></p>
            </div>
        </div>
    </div>
</div>
</body>
</html>

