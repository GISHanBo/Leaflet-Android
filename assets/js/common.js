/**
 * 查询是否有指定图层
 * @param {string} name 
 */
function hasLayer(name){
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == name) {
            return true;
        }
    });
    return undefined;
}
/**
 * 根据名字移除图层
 * @param name [string] 图层名称
 */
function removeLayer(name) {
    map.eachLayer(function (layer) {
        if (layer.getAttribution() == name) {
            map.removeLayer(layer);
        }
    });
}