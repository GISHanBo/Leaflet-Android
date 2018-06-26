package com.example.administrator.moblieplatform;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 地图类
 */
public class MapView extends WebView {
    /**
     * 地图是否已经加载，true为加载
     */
    public boolean hasInit = false;
    private static String TAG = "MapView";
    private MapListener mapListener;

    /**
     * MapView使用方法
     * <包名.MapView
     * android:id="@+id/mapView"
     * android:layout_width="match_parent"
     * android:layout_height="match_parent">
     * </包名.MapView>
     *
     * @param context
     */
    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MapView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    public void setListener(MapListener mapListener) {
        this.mapListener = mapListener;
        init();
    }

    /**
     * 初始化,必须执行此方法后，方可执行其他操作
     */
    public void init() {
        WebSettings webSettings = getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");

        webSettings.setBuiltInZoomControls(true);// 隐藏缩放按钮
        webSettings.setUseWideViewPort(true);// 可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        webSettings.setJavaScriptEnabled(true);
        addJavascriptInterface(new JsInteration(), "Android");
        webSettings.setAppCacheEnabled(true);//缓存
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportMultipleWindows(true);// 新加
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放

        setDrawingCacheEnabled(true);
        buildDrawingCache();
        buildLayer();
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        setWebViewClient(new MWebView());
        loadUrl("file:///android_asset/index.html");
    }

    /**
     * 放大地图
     */
    public void enlarge() {
        loadMethod("zoomIn()");
    }

    /**
     * 缩小地图
     */
    public void narrow() {
        loadMethod("zoomOut()");
    }

    /**
     * 设置地图中心
     *
     * @param lat 纬度
     * @param lng 经度
     */
    public void setCenter(double lat, double lng) {
        loadMethod("setCenter(" + lat + "," + lng + ")");
    }

    /**
     * 根据坐标范围设置地图中心
     *
     * @param latMin 最小纬度
     * @param lngMin 最小经度
     * @param latMax 最大纬度
     * @param lngMax 最大经度
     */
    public void setView(double latMin, double lngMin, double latMax, double lngMax) {
        loadMethod("setView(" + latMin + "," + lngMin + "," + latMax + "," + lngMax + ")");
    }

    /**
     * 设置地图缩放等级
     *
     * @param level 缩放等级
     */
    public void setZoom(Integer level) {
        loadMethod("setZoom(" + level + ")");
    }

    /**
     * 添加高亮显示，会自动覆盖上一个高亮点
     *
     * @param lat 纬度
     * @param lng 经度
     */
    public void addHighlight(Double lat, Double lng) {
        loadMethod("addHighlight(" + lat + "," + lng + ")");
    }

    /**
     * 清除高亮显示
     */
    public void clearHighlight() {
        loadMethod("clearHighlight()");
    }

    /**
     * 开启/关闭点击地图弹出经纬度
     *
     * @param isShow true开启，false关闭
     */
    public void setShowLatLng(boolean isShow) {
        if (isShow) {
            loadMethod("showLatLng()");
        } else {
            loadMethod("clearShowLatLng()");
        }
    }

    /**
     * 添加GeoJson到地图
     *
     * @param geoJson "{'type': 'FeatureCollection','features': [{'type': 'Feature','properties': {},'geometry': {'type': 'Point','coordinates': [35.5078125,69.53451763078358]}},{'type': 'Feature','properties': {},'geometry': {'type': 'Point','coordinates': [20.7421875,48.22467264956519]}}]}"
     */
    public void addGeoJson(String geoJson) {
        loadMethod("addGeoJson(" + geoJson + ")");
    }

    /**
     * 移除geoJson图层
     */
    public void removeGeoJson() {
        loadMethod("removeGeoJson()");
    }

    /**
     * 添加带图标点
     *
     * @param lat  经度
     * @param lng  纬度
     * @param icon 图标名称/test.png，图标必须放在assets/icon目录下，分辨率为48*48，图标中心与坐标对齐
     */
    public void addIcon(double lat, double lng, String icon) {
        loadMethod("addIcon(" + lat + "," + lng + ",\"" + icon + "\")");
    }

    /**
     * 移除全部带图标点
     */
    public void removeIcons() {
        loadMethod("removeIcons()");
    }

    /**
     * 添加WMS图层
     *
     * @param url     服务地址
     * @param layers  图层名称
     * @param format  调用图片格式
     * @param name    自定义图层名称，方便关闭图层，最好使用唯一值
     * @param minZoom 最小缩放等级
     * @param maxZoom 最大缩放等级 minZoom<maxZoom
     */
    public void addWMSLayer(String url, String layers, String format, String name, Integer minZoom, Integer maxZoom) {
        loadMethod("addWMSLayer(\"" + url + "\",\"" + layers + "\",\"" + format + "\",\"" + name + "\"," + minZoom + "," + maxZoom + ")");
    }

    /**
     * 添加WMTS图层
     *
     * @param url           服务地址
     * @param layer         图层名称
     * @param tileMatrixSet 切片使用的网格名称
     * @param format        图片格式
     * @param name          自定义图层名称
     * @param minZoom       最小缩放等级
     * @param maxZoom       最大缩放等级
     * @param tileSize      切片大小
     */
    public void addWMTSLayer(String url, String layer, String tileMatrixSet, String format, String name, Integer minZoom, Integer maxZoom, Integer tileSize) {
        Log.e(TAG, "addWMTSLayer: ++");
        loadMethod("addWMTSLayer(\"" + url + "\",\"" + layer + "\",\"" + tileMatrixSet + "\",\"" + format + "\",\"" + name + "\"," + minZoom + "," + maxZoom + "," + tileSize + ")");
    }

    /**
     * 根据图层名称移除图层
     *
     * @param name 图层名称
     */
    public void removeLayerByName(String name) {
        loadMethod("removeLayerByName(\"" + name + "\")");
    }

    /**
     * 切换地图底图
     *
     * @param source 来源-MapBox/高德/谷歌/天地图
     * @param type   地图类型-道路图/卫星图
     */
    public void switchBaseLayer(String source, String type) {
        loadMethod("switchBaseLayer(\"" + source + "\",\"" + type + "\")");
    }

    private void loadMethod(String method) {
        if (hasInit) {
            loadUrl("javascript:" + method + "");
        } else {

        }
    }

    /**
     * 加载完成事件
     */
    class MWebView extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e("WebView", "加载完成");
            hasInit = true;
            super.onPageFinished(view, url);
            mapListener.onMapLoaded();
        }
    }

    class JsInteration {
        /**
         * 地图点击监听
         */
        @JavascriptInterface
        public void iconClick(double lat,double lng) {
            mapListener.onIconClick(lat,lng);
        }
    }

}
