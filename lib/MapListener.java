package com.example.administrator.moblieplatform;

/**
 * 地图监听接口
 */
public interface MapListener {
    /**
     * 地图加载完成周执行
     */
    void onMapLoaded();

    /**
     * 图标点击事件
     * @param lat 图标纬度
     * @param lng 图标经度
     */
    void onIconClick(double lat, double lng);
}
