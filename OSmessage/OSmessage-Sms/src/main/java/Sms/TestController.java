package Sms;


import Sms.Param.SmsConfig;
import Sms.Param.SmsContent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

import static Sms.Utils.SmsUtils.SendSms;

@RestController
public class TestController {

    SmsConfig hand = new SmsConfig(
            "sms.tencentcloudapi.com",
            "ap-nanjing",
            "your secretId",
            "your secretKey",
            "your smsSdkAppId",
            "your SignName",
            "your templateId");

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    SmsContent code = new SmsContent(new String[]{"****"});

    @GetMapping("/sms")
    public void sms(){
        SendSms(new String[]{"130********"}, hand, code);
    }

}
