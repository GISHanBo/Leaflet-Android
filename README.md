# Leaflet-Android
基于leaflet地图引擎封装的Android轻量级地图插件
##使用说明
说明，该平台图层和地图，基于现有情况，为避免数据混乱，减少问题，已全部使用EPSG:3857（WGS84 Web Mercator (Auxiliary Sphere)）坐标系，该坐标系为在线地图所使用主流坐标系。在添加地图时需使用EPSG:3857坐标系的数据。
下面开始使用到的具体的各方法参数可以参考api文件夹内index.html<br>
###开发者需要的权限
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><br>
  <uses-permission android:name="android.permission.INTERNET"/>
###开发准备
将lib文件夹内的.java文件，放到自己的项目的包内<br>
在Android建立assets文件夹，将开发Assests文件文件复制到刚刚创建的assets文件夹内
###开发入门
在界面xml文件中添加<br>
<自己的包名.MapView<br>
     android:id="@+id/mapView"<br>
     android:layout_width="match_parent"<br>
     android:layout_height="match_parent"><br>
 </自己的包名.MapView><br>
private MapView mapView;<br>
mapView=findViewById(R.id.mapView);<br>
mapView.setListener(new AMapListener());//设置地图监听，必须先执行此方法<br>
class AMapListener implements MapListener{<br>
        /**<br>
         * 地图加载完成执行操作<br>
         */<br>
        @Override<br>
        public void onMapLoaded() {<br>
            mapView.setCenter(35,120);<br>
            mapView.addIcon(35,45,"test.png");<br>
        }<br>

        /**<br>
         * 点击marker监听<br>
         * @param lat 图标纬度<br>
         * @param lng 图标经度<br>
         */<br>
        @Override<br>
        public void onIconClick(double lat,double lng ) {<br>
            
        }<br>
    }
###切换底图	
默认底图（天地图，道路图），目前支持的地图源有-MapBox/高德/谷歌/天地图，地图类型主要有-道路图/卫星图<br>
mapView.switchBaseLayer("谷歌","卫星图");<br>
mapView.switchBaseLayer("天地图","道路图");
###地图基础操作
mapView.enlarge();//地图放大一级<br>
mapView.narrow();//地图缩小一级<br>
mapView.setCenter(35,120);//设置地图中心<br>
mapView.setView(21.218092583363195,109.88366064364764,21.223183018468408,109.88923978878034);//根据坐标范围设置视图<br>
mapView.setZoom(11);//设置地图缩放等级
###地图高亮显示
mapView.addHighlight(35.0,40.0);//添加高亮<br>
mapView.clearHighlight();//清除高亮<br>
###点击地图弹出经纬度
mapView.setShowLatLng(true);//开启点击地图弹出经纬度<br>
mapView.setShowLatLng(false);//关闭点击地图弹出经纬度
###添加/移除GeoJson
String geoJson="{'type': 'FeatureCollection','features': [{'type': 'Feature','properties': {},'geometry': {'type': 'Point','coordinates': [35.5078125,69.53451763078358]}},{'type': 'Feature','properties': {},'geometry': {'type': 'Point','coordinates': [20.7421875,48.22467264956519]}}]}";<br>
mapView.addGeoJson(geoJson);//添加geoJson<br>
mapView.removeGeoJson();//移除geoJson
###添加/移除带图标点
mapView.addIcon(35.5,40.1,"test.png");//添加带图标点<br>
mapView.removeIcons();//移除带图标点<br>
点击图标后，会在接口onIconClick事件中输出Icon的纬度和经度<br>
    class AMapListener implements MapListener{<br>
        /**<br>
         * 点击marker监听<br>
         * @param lat 图标纬度<br>
         * @param lng 图标经度<br>
         */<br>
        @Override<br>
        public void onIconClick(double lat,double lng ) {<br>
            Log.e("main", "onIconClick: "+lat+","+lng);<br>
        }<br>
    }
###添加/移除WMS图层
mapView.addWMSLayer("http://192.168.1.177:8080/geoserver/test/wms?","raster:pingyuan4","image/png","测试图层",1,40);//添加WMS图层<br>
mapView.removeLayerByName("测试图层");//移除图层
###添加/移除WMTS图层
mapView.addWMTSLayer("http://192.168.1.177:8080/geoserver/gwc/service/wmts","test:pingyuan","EPSG:3857","image/png","测试图层",10,22,256); //添加WMTS图层<br>
mapView.removeLayerByName("测试图层");//移除图层

