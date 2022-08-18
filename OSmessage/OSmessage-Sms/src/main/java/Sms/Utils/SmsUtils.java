package Sms.Utils;

import Sms.Param.SmsConfig;
import Sms.Param.SmsContent;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

public class SmsUtils {

    private static SmsClient SendSmsHandler(SmsConfig smsConfig){
        // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
        // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
        Credential cred = new Credential(smsConfig.getSecretId(), smsConfig.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(smsConfig.getUrl());
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(cred, smsConfig.getRegion(), clientProfile);
    }

    public static void SendSms(String[] phoneNumberSet, SmsConfig smsConfig, SmsContent smsContent){
        try {
            SmsClient client = SendSmsHandler(smsConfig);
            SendSmsRequest req = new SendSmsRequest();
            req.setPhoneNumberSet(phoneNumberSet);
            req.setSmsSdkAppId(smsConfig.getSmsSdkAppId());
            req.setSignName(smsConfig.getSignName());
            req.setTemplateId(smsConfig.getTemplateId());
            req.setTemplateParamSet(smsContent.getContent());
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
        }catch (TencentCloudSDKException e){
            System.out.println(e.toString());
        }
    }

    public static void SendSms(String[] phoneNumberSet, SmsConfig smsConfig){
        try {
            SmsClient client = SendSmsHandler(smsConfig);
            SendSmsRequest req = new SendSmsRequest();
            req.setPhoneNumberSet(phoneNumberSet);
            req.setSmsSdkAppId(smsConfig.getSmsSdkAppId());
            req.setSignName(smsConfig.getSignName());
            req.setTemplateId(smsConfig.getTemplateId());
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
        }catch (TencentCloudSDKException e){
            System.out.println(e.toString());
        }
    }


}
