package MiniApp.Api.impl;

import MiniApp.param.AccessTokenCache;
import MiniApp.param.TemplateParam;
import MiniApp.param.WeChatParam;
import cn.hutool.http.HttpUtil;

/**
 * 发送模板消息
 * 用户可以选择使用直接传Json参，或者先组装参数
 */
public class SendTemplateServiceImpl{
    private static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     * 直接传入Json发送模版消息
     */
    public static void sendTemplate(WeChatParam weChatParam, String data){
        String accessToken = (String) AccessTokenCache.getInstance().getAccessTokenAndExpiresIn(weChatParam).get("access_token");
        String result = HttpUtil.post(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN", accessToken),data);
        System.out.println(result);
    }

    /**
     * 传入组装后的信息发送模版消息
     */
    public static void sendTemplateV1(WeChatParam weChatParam, TemplateParam templateParam){
        String accessToken = (String) AccessTokenCache.getInstance().getAccessTokenAndExpiresIn(weChatParam).get("access_token");
        String result = HttpUtil.post(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN", accessToken),templateParam.toString());
        System.out.println(result);
    }

}
