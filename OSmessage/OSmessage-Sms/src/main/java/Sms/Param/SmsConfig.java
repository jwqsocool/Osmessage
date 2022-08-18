package Sms.Param;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 腾讯短信参数
 *
 * 参数示例：
 * [{"sms_10":{"url":"sms.tencentcloudapi.com","region":"ap-guangzhou","secretId":"AKIDhDUUDfffffMEqBF1WljQq","secretKey":"B4h39yWnfffff7D2btue7JErDJ8gxyi","smsSdkAppId":"140025","templateId":"11897","signName":"公众号","supplierId":10,"supplierName":"腾讯云"}}]
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsConfig {

    /**
     * api相关
     */
    private String url;
    private String region;

    /**
     * 账号相关
     */
    private String SecretId;
    private String SecretKey;
    private String SmsSdkAppId;
    private String SignName;
    private String TemplateId;

}
