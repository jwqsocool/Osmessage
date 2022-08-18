package MiniApp.Utils;

import MiniApp.param.AccessToken;
import MiniApp.param.WeChatParam;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenUtils {

    private static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 更新AccessToken
     */
    public static AccessToken updateAccessToken(WeChatParam weChatParam) {
        String result = HttpUtil.get(GET_ACCESS_TOKEN_URL.replace("APPID",weChatParam.getAppId()).
                replace("APPSECRET", weChatParam.getAppSecret()));
        JSONObject jsonObject = JSON.parseObject(result);
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(jsonObject.getString("access_token"));
        accessToken.setExpiresTime(jsonObject.getInteger("expires_in"));
        System.out.println(result);
        return accessToken;
    }

}
