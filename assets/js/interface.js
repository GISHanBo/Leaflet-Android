/**
 * 设置地图中心
 */
function setCenter(lat, lng) {
	map.panTo([lat, lng]);
}
/**
 * 根据坐标范围调整经纬度
 * @param {number} latMin 
 * @param {number} lngMin 
 * @param {number} latMax 
 * @param {number} lngMax 
 */
function setView(latMin, lngMin, latMax, lngMax) {
	map.fitBounds([
		[latMin, lngMin],
		[latMax, lngMax]
	]);
}
/**
 * 缩小
 */
function zoomOut() {
	map.zoomOut(1);
}
/**
 * 放大
 */
function zoomIn() {
	map.zoomIn(1);
}
/**
 * 设置地图缩放等级
 * @param {number} level 
 */
function setZoom(level) {
	map.setZoom(level)
}
/**
 * 添加高亮显示
 */
function addHighlight(lat, lng) {
	if(hasLayer("高亮显示")) {
		clearHighlight()
	}
	L.marker([lat, lng], {
		highlight: "permanent",
		attribution: "高亮显示"
	}).addTo(map);
}
/**
 * 清除高亮
 */
function clearHighlight() {
	removeLayer("高亮显示");
}
/**
 * 开启点击地图弹出经纬度
 */
function showLatLng() {
	map.addEventListener("click", function(e) {
		if(hasLayer("popup")) {
			clearShowLatLng()
		}
		L.popup({
				attribution: "popup"
			})
			.setLatLng([e.latlng.lat, e.latlng.lng])
			.setContent("纬度：" + e.latlng.lat + "<br/>经度：" + e.latlng.lng)
			.openOn(map);
	});
}
/**
 * 关闭点击地图弹出经纬度
 */
function clearShowLatLng() {
	removeLayer("popup");
	map.removeEventListener("click");
}
/**
 * 添加geoJson
 * @param {obj} geoJSON 
 */
function addGeoJson(geoJSON) {
	L.geoJSON(geoJSON, {
		attribution: "geoJson"
	}).addTo(map);
}
/**
 * 添加带图标点
 * @param {Number} lat
 * @param {Number} lng
 * @param {String} icon
 */
function addIcon(lat, lng, icon) {
	var myIcon = L.icon({
		iconUrl: 'icon/' + icon,
		iconSize: [48, 48],
		iconAnchor: [24, 24]
	});
	var marker=L.marker([lat, lng], {
		icon: myIcon,
		attribution: "mssmarker"
	}).addTo(map);
	marker.addEventListener('click',function(e){
		window.Android.iconClick(e.latlng.lat,e.latlng.lng);
	});
}
/**
 * 清除所有带图标点
 */
function removeIcons() {
	removeLayer("mssmarker");
}
/**
 * 清除geoJson图层
 */
function removeGeoJson() {
	removeLayer("geoJson");
}
/**
 * 添加WMS图层
 * @param {String} url 服务地址
 * @param {String} layers 图层名称
 * @param {String} format 格式
 * @param {String} name 自定义名称
 * @param {Number} minZoom 最小缩放等级
 * @param {Number} maxZoom 最大缩放等级
 */
function addWMSLayer(url, layers, format, name, minZoom, maxZoom) {
	L.tileLayer.wms(url, {
		layers: layers,
		format: format,
		transparent: true,
		attribution: name,
		minZoom: minZoom,
		maxZoom: maxZoom,
		zIndex: 2
	}).addTo(map);
}
/**
 * 通过图层名称移除名字
 * @param {String} name 图层名称
 */
function removeLayerByName(name) {
	removeLayer(name);
}
/**
 * 
 */
function addWMTSLayer(url, layer, tilematrixSet, format, name, minZoom, maxZoom, tileSize) {
	var ign = new L.TileLayer.WMTS(url, {
		layer: layer,
		tilematrixSet: tilematrixSet,
		tileSize: tileSize,
		format: format,
		maxZoom: maxZoom,
		minZoom: minZoom,
		attribution: name,
		zIndex: 2
	});
	map.addLayer(ign);
}
/**
 * 切换地图底图
 * @param {String} source 来源-MapBox/高德/谷歌/天地图
 * @param {String} type 地图类型-道路图/卫星图
 */
function switchBaseLayer(source, type) {
	removeLayer("mssBaseLayer");
	switch(source) {
		case "高德":
			if(type == "卫星图") {
				L.tileLayer.chinaProvider('GaoDe.Satellite.Map', {
					maxZoom: 22,
					minZoom: 1,
					attribution: "mssBaseLayer"
				}).addTo(map);
				L.tileLayer.chinaProvider('GaoDe.Satellite.Annotion', {
					maxZoom: 22,
					minZoom: 1,
					attribution: "mssBaseLayer"
				}).addTo(map);
			} else {
				L.tileLayer.chinaProvider('GaoDe.Normal.Map', {
					maxZoom: 22,
					minZoom: 1,
					attribution: "mssBaseLayer"
				}).addTo(map);
			}
			break;
		case "天地图":
			if(type == "卫星图") {
				L.tileLayer.chinaProvider('TianDiTu.Satellite.Map', {
					maxZoom: 22,
					minZoom: 1,
					attribution: "mssBaseLayer"
				}).addTo(map);
				L.tileLayer.chinaProvider('TianDiTu.Satellite.Annotion', {
					maxZoom: 22,
					minZoom: 1,
					attribution: "mssBaseLayer"
				}).addTo(map);
			} else {
				L.tileLayer.chinaProvider('TianDiTu.Normal.Map', {
					maxZoom: 22,
					minZoom: 1,
					attribution: "mssBaseLayer"
				}).addTo(map);
				L.tileLayer.chinaProvider('TianDiTu.Normal.Annotion', {
					maxZoom: 22,
					minZoom: 1,
					attribution: "mssBaseLayer"
				}).addTo(map);
			}
			break;
		case "谷歌":
			if(type == "卫星图") {
				L.tileLayer.chinaProvider('Google.Satellite.Map', {
					maxZoom: 22,
					minZoom: 1,
					attribution: "mssBaseLayer"
				}).addTo(map);
			} else {
				L.tileLayer.chinaProvider('Google.Normal.Map', {
					maxZoom: 22,
					minZoom: 1,
					attribution: "mssBaseLayer"
				}).addTo(map);
			}
			break;
		case "MapBox":
			if(type == "卫星图") {
				L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
					attribution: 'mssBaseLayer',
					maxZoom: 22,
					minZoom: 1,
					id: 'mapbox.streets-satellite',
					accessToken: 'pk.eyJ1IjoiaGFtYnVnZXJkZXZlbG9wIiwiYSI6ImNqNXJtZzczcDB6aHgycW1scXZqd3FpNmcifQ.d4p32JmAhTek8BUIt3WGLw'
				}).addTo(map);
			} else {
				L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
					attribution: 'mssBaseLayer',
					maxZoom: 22,
					minZoom: 1,
					id: 'mapbox.streets',
					accessToken: 'pk.eyJ1IjoiaGFtYnVnZXJkZXZlbG9wIiwiYSI6ImNqNXJtZzczcDB6aHgycW1scXZqd3FpNmcifQ.d4p32JmAhTek8BUIt3WGLw'
				}).addTo(map);
			}
			break;
		default:
			break;
	}
}