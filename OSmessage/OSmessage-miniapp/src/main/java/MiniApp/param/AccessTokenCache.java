package MiniApp.param;

import MiniApp.Utils.AccessTokenUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 单例模式AccessToken缓存
 **/
public class AccessTokenCache {

    /**
     * 缓存accessToken的Map,map中包含accessToken,expiresIn和缓存的时间戳time
     **/
    private Map<String, String> map = new HashMap<>();
    private AccessTokenCache() { }
    private static volatile AccessTokenCache cache = null;


    /**
     * 静态工厂方法
     */
    public static AccessTokenCache getInstance() {
        if (cache == null) {
            synchronized (AccessTokenCache.class){
                if (cache == null) {
                    cache = new AccessTokenCache();
                }
            }
        }
        return cache;
    }


    public static AccessTokenCache getCache(){
        return cache;
    }

    public static void setCache(AccessTokenCache cache){
        AccessTokenCache.cache = cache;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    /**
     * 获取 accessToken expires_in
     * @param weChatParam 包含appID和appsecret
     * @return Map<String,Object> result
     */
    public Map<String,Object> getAccessTokenAndExpiresIn(WeChatParam weChatParam) {
        Map<String,Object> result = new HashMap<>();
        AccessTokenCache accessTokenCache = AccessTokenCache.getInstance();
        Map<String, String> map = accessTokenCache.getMap();
        String time = map.get("time");
        String accessToken = map.get("access_token");
        String expiresIn = map.get("expires_in");
        Long nowDate = new Date().getTime();
        if (accessToken != null && time != null && nowDate-Long.parseLong(time) < 7000*1000) {
            //这里设置过期时间为微信规定的过期时间减去200秒
            System.out.println("-----从缓存读取access_token：" + accessToken);
            //从缓存中拿数据为返回结果赋值
            result.put("access_token", accessToken);
            result.put("expires_in", expiresIn);
        } else {
            AccessToken info = AccessTokenUtils.updateAccessToken(weChatParam);//实际中这里要改为你自己调用微信接口去获取accessToken和expires_in
            System.out.println("-----通过调用微信接口获取access_token：" + info.getAccessToken());
            //将信息放置缓存中
            map.put("time", nowDate + "");
            map.put("access_token", String.valueOf(info.getAccessToken()));
            map.put("expires_in", info.getExpiresTime() +"");
            //为返回结果赋值
            result.put("access_token", info.getAccessToken());
            result.put("expires_in", info.getExpiresTime());
        }
        return result;
    }

}
