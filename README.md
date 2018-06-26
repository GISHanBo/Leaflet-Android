使用说明
========

说明，该平台图层和地图，基于现有情况，为避免数据混乱，减少问题，已全部使用EPSG:3857（WGS84
Web Mercator (Auxiliary
Sphere)）坐标系，该坐标系为在线地图所使用主流坐标系。在添加地图时需使用EPSG:3857坐标系的数据。

下面开始使用到的具体的各方法参数可以参考api文件夹内index.html

开发者需要的权限
----------------

\<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/\>

\<uses-permission android:name="android.permission.INTERNET"/\>

开发准备
--------

将lib文件夹内的.java文件，放到自己的项目的包内

![](media/b6c9b84e678c597730b9f737bb36cc43.png)

在Android建立assets文件夹

![](media/78de354d395cddae831066b168b27a23.png)

将Assests.zip压缩文件解压到刚刚创建的assets文件夹内

![](media/1e84f27e07102b15f543ad65141478a6.png)

开发入门
--------

在界面xml文件中添加

\<自己的包名.MapView

android:id="\@+id/mapView"

android:layout_width="match_parent"

android:layout_height="match_parent"\>

\</自己的包名.MapView\>

实例化

private MapView mapView;

mapView=findViewById(R.id.mapView);

mapView.setListener(new AMapListener());//设置地图监听，必须先执行此方法

class AMapListener implements MapListener{

/\*\*

\* 地图加载完成执行操作

\*/

\@Override

public void onMapLoaded() {

mapView.setCenter(35,120);

mapView.addIcon(35,45,"test.png");

}

/\*\*

\* 点击marker监听

\* \@param lat 图标纬度

\* \@param lng 图标经度

\*/

\@Override

public void onIconClick(double lat,double lng ) {

}

}

### 切换地图底图

默认底图（天地图，道路图），目前支持的地图源有-MapBox/高德/谷歌/天地图，地图类型主要有-道路图/卫星图

mapView.switchBaseLayer("谷歌","卫星图");

mapView.switchBaseLayer("天地图","道路图");

### 缩放地图

mapView.enlarge();//地图放大一级  
mapView.narrow();//地图缩小一级

### 改变视图位置

mapView.setCenter(35,120);//设置地图中心

mapView.setView(21.218092583363195,109.88366064364764,21.223183018468408,109.88923978878034);//根据坐标范围设置视图

mapView.setZoom(11);//设置地图缩放等级

### 添加\\关闭高亮显示

mapView.addHighlight(35.0,40.0);//添加高亮

mapView.clearHighlight();//清除高亮

### 开启\\关闭点击地图弹出经纬度

mapView.setShowLatLng(true);//开启点击地图弹出经纬度

mapView.setShowLatLng(false);//关闭点击地图弹出经纬度

### 添加\\移除geoJson(地图撒点)

String geoJson="{'type': 'FeatureCollection','features': [{'type':
'Feature','properties': {},'geometry': {'type': 'Point','coordinates':
[35.5078125,69.53451763078358]}},{'type': 'Feature','properties': {},'geometry':
{'type': 'Point','coordinates': [20.7421875,48.22467264956519]}}]}";

mapView.addGeoJson(geoJson);//添加geoJson

mapView.removeGeoJson();//移除geoJson

### 添加/移除带图标点

mapView.addIcon(35.5,40.1,"test.png");//添加带图标点

mapView.removeIcons();//移除带图标点

需要注意的是图标需要放在assets/icon/目录下，使用48\*48分辨率图片，图片中心与坐标点对齐。

![](media/3ebdf89f65f98c7114192870f7f699a2.png)

### 带图标点点击监听

点击图标后，会在接口onIconClick事件中输出Icon的纬度和经度

class AMapListener implements MapListener{

/\*\*

\* 地图加载完成执行操作

\*/

\@Override

public void onMapLoaded() {

mapView.setCenter(35,120);

mapView.addIcon(35,45,"test.png");

}

/\*\*

\* 点击marker监听

\* \@param lat 图标纬度

\* \@param lng 图标经度

\*/

\@Override

public void onIconClick(double lat,double lng ) {

Log.e("main", "onIconClick: "+lat+","+lng);

}

}

### 添加\\移除WMS图层

mapView.addWMSLayer("http://192.168.1.177:8080/geoserver/test/wms?","raster:pingyuan4","image/png","测试图层",1,40);//添加WMS图层

mapView.removeLayerByName("测试图层");//移除图层

### 添加\\移除WMTS图层

mapView.addWMTSLayer("http://192.168.1.177:8080/geoserver/gwc/service/wmts","test:pingyuan","EPSG:3857","image/png","测试图层",10,22,256);
//添加WMTS图层

mapView.removeLayerByName("测试图层");//移除图层
