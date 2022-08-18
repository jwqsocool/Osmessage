package MiniApp.test;

import MiniApp.param.TemplateData;
import MiniApp.param.TemplateParam;
import MiniApp.param.WeChatParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static MiniApp.Api.impl.SendTemplateServiceImpl.sendTemplate;
import static MiniApp.Api.impl.SendTemplateServiceImpl.sendTemplateV1;


@RestController
public class minitest {

    /**
     * 通过WeChatParam来确定推送消息的微信服务公众号
     */
    WeChatParam weChatParam = new WeChatParam("appId","appSecret");

    /**
     * 若关联小程序则需要对TemplateParam.MiniProgram单独设置
     */
    TemplateParam.MiniProgram miniProgram = new TemplateParam.MiniProgram("appid","pagePath");


    @GetMapping("/miniapp")
    public void test() {
        TemplateParam templateParam = new TemplateParam().builder()
                .toUser("发送给的用户")
                .templateId("模板的ID")
                .miniProgram(miniProgram)
                .build();
        templateParam.addData(new TemplateData("参数1","值1"));
        templateParam.addData(new TemplateData("参数2","值2"));

        sendTemplate(weChatParam,"{\n" +
                "    \"touser\": \"o33xN5gC8ZPix4mfBhLwDMAtKIwg\", \n" +
                "    \"template_id\": \"pRWY7zjTgfIRNw-w5Y09ivcgefxeGPm-KMwyjK-IKOE\", \n" +
                "    \"data\": {\n" +
                "        \"first\": {\n" +
                "            \"value\": \"注意！\", \n" +
                "            \"color\": \"#173177\"\n" +
                "        }, \n" +
                "        \"keyword1\": {\n" +
                "            \"value\": \"Mac\", \n" +
                "            \"color\": \"#173177\"\n" +
                "        }, \n" +
                "        \"keyword2\": {\n" +
                "            \"value\": \"Apiexception\", \n" +
                "            \"color\": \"#173177\"\n" +
                "        }, \n" +
                "        \"keyword3\": {\n" +
                "            \"value\": \"2022年8月3日\", \n" +
                "            \"color\": \"#173177\"\n" +
                "        }, \n" +
                "        \"remark\": {\n" +
                "            \"value\": \"点击查看\", \n" +
                "            \"color\": \"#173177\"\n" +
                "        }\n" +
                "    }\n" +
                "}");
        sendTemplateV1(weChatParam, templateParam);
    }

}
