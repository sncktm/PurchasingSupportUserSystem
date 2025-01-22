<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.StoreBeans" %>
<%@ page import="model.StoreArrayBeans" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>店舗検索</title>
    <link rel="stylesheet" href="key3">
    <link rel="stylesheet" href="css/style.css?v=1.0">
    <script src="key4"></script>
    <script>
        let map;
        let markers = [];
        let userMarker;
        let userLocation;

        function initMap() {
            const defaultLocation = { lat: 35.6895, lng: 139.6917 };
            map = new google.maps.Map(document.getElementById("map"), {
                center: defaultLocation,
                zoom: 12,
            });

            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    (position) => {
                        userLocation = {
                            lat: position.coords.latitude,
                            lng: position.coords.longitude
                        };
                        userMarker = new google.maps.Marker({
                            position: userLocation,
                            map: map,
                            title: "現在地",
                            icon: {
                                url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png"
                            }
                        });
                        map.setCenter(userLocation);
                        calculateDistances();
                    },
                    () => {
                        console.error('位置情報の取得に失敗しました。');
                    }
                );
            } else {
                console.error('このブラウザは位置情報をサポートしていません。');
            }
        }

        function showStoreOnMap(lat, lng, name) {
            const storeLocation = { lat: lat, lng: lng };

            markers.forEach(marker => marker.setMap(null));
            markers = [];

            const storeMarker = new google.maps.Marker({
                position: storeLocation,
                map: map,
                title: name
            });
            markers.push(storeMarker);

            const bounds = new google.maps.LatLngBounds();
            bounds.extend(storeLocation);
            if (userLocation) {
                bounds.extend(userLocation);
            }
            map.fitBounds(bounds);
        }

        function updateDetails(storeNo, name, address, openingTime, closingTime, phone, image, mailAddress) {
            const detailsContainer = document.getElementById("details-container");
            detailsContainer.style.display = "block";
            // この行を削除: detailsContainer.scrollIntoView({ behavior: 'smooth' });
            document.getElementById("detail-image").src = image || 'img/defaultImage.png';
            document.getElementById("detail-name").textContent = name;
            document.getElementById("detail-address").textContent = address;
            document.getElementById("detail-hours").textContent = openingTime + " ～ " + closingTime;
            document.getElementById("detail-phone").textContent = phone;
            document.getElementById("detail-mail").textContent = mailAddress;
        }

        function sortStores() {
            const sortOption = document.getElementById('sort-select').value;
            const storeList = document.querySelector('.store-list');
            const stores = Array.from(storeList.querySelectorAll('.store'));

            stores.sort((a, b) => {
                switch (sortOption) {
                    case 'name-asc':
                        return a.querySelector('.store-name').textContent.localeCompare(b.querySelector('.store-name').textContent);
                    case 'name-desc':
                        return b.querySelector('.store-name').textContent.localeCompare(a.querySelector('.store-name').textContent);
                    case 'distance':
                        return parseFloat(a.dataset.distance || Infinity) - parseFloat(b.dataset.distance || Infinity);
                    case 'opening-asc':
                        return a.dataset.openingTime.localeCompare(b.dataset.openingTime);
                    case 'closing-desc':
                        return b.dataset.closingTime.localeCompare(a.dataset.closingTime);
                    default:
                        return 0;
                }
            });

            stores.forEach(store => storeList.appendChild(store));
        }

        function calculateDistances() {
            if (!userLocation) return;

            const service = new google.maps.DistanceMatrixService();
            const destinations = Array.from(document.querySelectorAll('.store')).map(store => {
                return new google.maps.LatLng(
                    parseFloat(store.dataset.lat),
                    parseFloat(store.dataset.lng)
                );
            });

            service.getDistanceMatrix(
                {
                    origins: [userLocation],
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
                    document.querySelectorAll('.store').forEach((store, index) => {
                        if (distances[index].status === 'OK') {
                            const distanceElement = store.querySelector('.distance');
                            distanceElement.textContent = distances[index].distance.text;
                            store.dataset.distance = distances[index].distance.value;
                        }
                    });

                    if (document.getElementById('sort-select').value === 'distance') {
                        sortStoresByDistance();
                    }
                }
            );
        }

        function sortStoresByDistance() {
            const storeList = document.querySelector('.store-list');
            const stores = Array.from(storeList.querySelectorAll('.store'));
            stores.sort((a, b) => {
                return parseFloat(a.dataset.distance) - parseFloat(b.dataset.distance);
            });
            stores.forEach(store => storeList.appendChild(store));
        }

        window.onload = function() {
            initMap();
            document.getElementById('sort-select').addEventListener('change', sortStores);
            sortStores(); // 初期ソートを適用
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
        .content-wrapper {
            display: flex;
            gap: 20px;
        }
        .store-list {
            flex: 1;
            min-width: 300px; /* 最小幅を設定 */
            list-style: none;
            padding: 0;
        }
        .map-and-details {
            flex: 1;
            min-width: 300px; /* 最小幅を設定 */
            display: flex;
            flex-direction: column;
        }
        #map-container {
            height: 400px;
            margin-bottom: 20px;
        }
        #map {
            height: 100%;
            border-radius: 8px;
        }
        #details-container {
            display: none;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 20px;
            max-width: 100%;
        }
        #detail-image {
            max-width: 100%;
            height: auto;
            object-fit: contain;
            border-radius: 8px;
            margin-bottom: 15px;
        }
        .distance {
            font-size: 0.9em;
            color: #666;
            margin-top: 5px;
        }
        .store-image {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 8px;
            margin-right: 15px;
            float: left;
        }
        .store-info {
            flex: 1;
        }
        .store {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            padding: 15px;
            margin-bottom: 15px;
            cursor: pointer;
            transition: transform 0.3s ease;
            display: flex;
            align-items: start;
            border: 1px solid #e0e0e0; /* 枠線を追加 */
        }
        .store:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
        }
        .status {
            font-weight: bold;
            padding: 3px 8px;
            border-radius: 4px;
            display: inline-block;
            margin-top: 5px;
        }
        .status.red {
            color: #d32f2f;
        }
        .status.gray {
            color: #616161;
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
    <h1>店舗検索</h1>

    <form action="StoreSearch" method="get" class="search-form">
        <input type="text" name="keyword" placeholder="店舗名を検索" value="${param.keyword}">
        <label>
            <input type="checkbox" name="showOnlyAvailable" value="true" 
                   ${param.showOnlyAvailable == 'true' ? 'checked' : ''}> 営業中のみ表示
        </label>
        <button type="submit">検索</button>
    </form>

    <div class="sort-options">
    <label for="sort-select">並び替え:</label>
    <select id="sort-select" name="sortOption">
        <option value="name-asc" ${param.sortOption == 'name-asc' ? 'selected' : ''}>店舗名（昇順）</option>
        <option value="name-desc" ${param.sortOption == 'name-desc' ? 'selected' : ''}>店舗名（降順）</option>
        <option value="distance" ${param.sortOption == 'distance' ? 'selected' : ''}>現在地から近い順</option>
        <option value="opening-asc" ${param.sortOption == 'opening-asc' ? 'selected' : ''}>開店時間が早い順</option>
        <option value="closing-desc" ${param.sortOption == 'closing-desc' ? 'selected' : ''}>閉店時間が遅い順</option>
    </select>
    </div>

    <div class="content-wrapper">
        <ul class="store-list">
            <% 
                StoreArrayBeans storeArray = (StoreArrayBeans) request.getAttribute("storeArray");
                if (storeArray != null) {
                    List<StoreBeans> storeList = storeArray.getStores();
                    if (storeList.isEmpty()) {
            %>
                <p>該当する店舗が見つかりません。</p>
            <% 
                    } else {
                        for (StoreBeans store : storeList) {
                            String status = "";
                            String statusClass = "";

                            if (store.getOpeningTime() != null && store.getClosingTime() != null) {
                                java.time.LocalTime now = java.time.LocalTime.now();
                                if (now.isAfter(store.getOpeningTime().toLocalTime()) &&
                                    now.isBefore(store.getClosingTime().toLocalTime())) {
                                    status = "営業中";
                                    statusClass = "red";
                                } else {
                                    status = "閉店中";
                                    statusClass = "gray";
                                }
                            }
            %>
            <li class="store" data-lat="<%= store.getLatitude() %>" data-lng="<%= store.getLongitude() %>" data-opening-time="<%= store.getOpeningTime() %>" data-closing-time="<%= store.getClosingTime() %>" onclick="showStoreOnMap(<%= store.getLatitude() %>, <%= store.getLongitude() %>, '<%= store.getStoreName() %>'); updateDetails('<%= store.getStoreNo() %>', '<%= store.getStoreName() %>', '<%= store.getAddress() %>', '<%= store.getOpeningTime().toString().substring(0, 5) %>', '<%= store.getClosingTime().toString().substring(0, 5) %>', '<%= store.getPhone() %>', '<%= request.getContextPath() + (store.getImage() != null && !store.getImage().isEmpty() ? store.getImage() : "/img/defaultImage.png") %>', '<%= store.getMailAddress() %>')">
                <img src="<%= request.getContextPath() + (store.getImage() != null && !store.getImage().isEmpty() ? store.getImage() : "/img/defaultImage.png") %>" alt="<%= store.getStoreName() %>" class="store-image">
                <div class="store-info">
                    <div class="store-name"><%= store.getStoreName() %></div>
                    <div><%= store.getAddress() %></div>
                    <div>営業時間: <%= store.getOpeningTime().toString().substring(0, 5) %> ～ <%= store.getClosingTime().toString().substring(0, 5) %></div>
                    <div class="status <%= statusClass %>"><%= status %></div>
                    <div class="distance">距離: 計算中...</div>
                </div>
            </li>
            <% 
                        }
                    }
                }
            %>
        </ul>
        <div class="map-and-details">
            <div id="map-container">
                <div id="map"></div>
            </div>
            <div id="details-container">
                <img id="detail-image" src="<%= request.getContextPath() %>/img/defaultImage.png" alt="店舗画像" onerror="this.src='<%= request.getContextPath() %>/img/defaultImage.png';">
                <h2 id="detail-name"></h2>
                <p><strong>住所:</strong> <span id="detail-address"></span></p>
                <p><strong>営業時間:</strong> <span id="detail-hours"></span></p>
                <p><strong>電話番号:</strong> <span id="detail-phone"></span></p>
                <p><strong>メールアドレス:</strong> <span id="detail-mail"></span></p>
            </div>
        </div>
    </div>
</div>
</body>
</html>

