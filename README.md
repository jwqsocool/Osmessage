# Osmessage
maven类型消息发送工具集合
目前提供了4种消息发送方法，分别对应钉钉机器人群发、短信、邮件、以及微信公众号关联小程序。可以通过引入本maven项目来自行选择使用消息服务方式<br>

## **引入方式**
首先code整个项目，在IDEA中打开并对OSmessage父模块执行maven package -> install操作<br>
再对OSmessage-all子模块进行maven clean -> package -> install操作<br>
即可在需要此消息服务的项目中添加依赖来使用：
```xml
        <dependency>
            <groupId>org.hfcb</groupId>
            <artifactId>OSmessage-all</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```

## **功能介绍**
考虑到不同的使用场景需要不同的消息服务，本消息服务提供了针对钉钉机器人群发、短信、邮件、以及微信公众号关联小程序4种渠道的发信方法，下面详细介绍<br>

### **OSmessage-DingTalk**
在钉钉平台的消息推送，依靠钉钉提供的机器人接入群聊，通过webhook和secret进行验证并发送消息

钉钉群设置类GroupConfig，通过toString()和构造函数实现定义钉钉群，其中包含的参数为：webhook和secret <br>
webhook为发送消息群对应的webhook，可在添加的机器人管理页面复制<br>
secret为机器人测试群中的加签，可在钉钉开发者网页页面对添加的机器人进行调试，通过进入调试群设置加签获得<br> 
设置语句如下：
```Java
GroupConfig group = new GroupConfig(
            "_此处填写自己机器人的webhook_",
            "_此处为机器人测试群中的加签secret_");
```

发送工具类的类名为DingTalkSendingUtilsV1，其中包含四种可供选择的文本格式：text, link, markdown, actioncard <br> 
设置四种方法供使用: sendTextV1, sendLinkV1, sendMarkdownV1, sendActioncardV1   下面对方法进行说明

#### **Text格式发送方法sendTextV1**

Text只包含普通文本, 所以在使用sendTextV1时，只需指定文本内容content和想要发送到的组群即可
```Java
/**
 * 发送普通文本消息
 * @param group      自定义组群  GroupConfig
 * @param content    文本消息    String
 * @return OapiRobotSendResponse
 */
DingTalkSendingUtilsV1.sendTextV1(group, "text");
```
效果如下图<br>
![image](https://github.com/jwqsocool/DingTalkRobot/blob/main/image/IMG_1186.PNG)<br>
当然也可以通过输入想@的人的电话来实现@部分人的功能，还可以直接输入true来@全体成员<br>
        
```Java
/**
* 发送普通文本消息
* @param group       自定义组群     GroupConfig
* @param content     文本消息       String
* @param mobileList  At的人的手机号 List<String>
* @return OapiRobotSendResponse
*/

DingTalkSendingUtilsV1.sendTextV1(group, "text", Arrays.asList("130********"));
```
效果如下图<br>
![image](https://github.com/jwqsocool/DingTalkRobot/blob/main/image/IMG_1188.PNG)<br>
```Java
/**
* 发送普通文本消息
* @param group      自定义组群        GroupConfig
* @param content    文本消息          String
* @param isAtAll    值为true及At全体   boolean
* @return OapiRobotSendResponse
*/

DingTalkSendingUtilsV1.sendTextV1(group, "text", isAtAll);
```
效果如下图<br>
![image](https://github.com/jwqsocool/DingTalkRobot/blob/main/image/IMG_1187.PNG)<br>
#### **Link格式发送方法sendLinkV1**

link类型消息包含**消息标题**、**消息内容**、**点击消息后跳转的url**以及**插入图片的url**，但插入图片的url**不是必须的，可以不填** <br>
@功能方面与text不同，钉钉机器人发送link类型消息**暂时不支持@功能**
```Java
/**
* 发送link 类型消息
* @param group      自定义组群                 GroupConfig
* @param urltitle   消息标题                   String
* @param urltext    消息内容                   String
* @param messageUrl 点击消息后跳转的url         String
* @param picUrl     插入图片的url(非必需可不填)  String
* @return OapiRobotSendResponse
*/
     
DingTalkSendingUtilsV1.sendLinkV1(group, "urltitle", "urltext", "messageUrl"，"picurl");
```
效果如下图<br>
![image](https://github.com/jwqsocool/DingTalkRobot/blob/main/image/IMG_0975.PNG)<br>
#### **Markdown格式发送方法sendMarkdownV1**

markdown类型消息包含**消息标题**与**支持markdown编辑格式的文本信息**，markdown格式具体要求参考钉钉开放文档, @规则与sendTextV1方法一致,就不做展示了
>[消息类型和数据格式](https://open.dingtalk.com/document/group/message-types-and-data-format)

```Java
/**
* 发送Markdown 编辑格式的消息
* @param group         自定义组群                    GroupConfig
* @param markdownTitle 消息标题                      String
* @param markdownText  支持markdown编辑格式的文本信息  String
* @return OapiRobotSendResponse
*/

DingTalkSendUtilsV1.sendMarkdownV1(group, "markdownTitle", "markdownText");
```
效果如下图<br>
![image](https://github.com/jwqsocool/DingTalkRobot/blob/main/image/IMG_0973.PNG)<br>
#### **ActionCard格式发送方法**

ActionCard类型消息除了包含**消息标题**与**同样支持markdown编辑格式的文本信息**以外，还多出了机器人头像的隐藏以及**按钮模块**，其中包括了**按钮标题**、**按钮链接**和**按钮排布**<br>
目前ActionCard暂不支持@功能
```Java
/**
* 独立跳转ActionCard类型 消息发送
* @param group          自定义组群                                      
* @param title          标题
* @param actionCardText 文本
* @param btns           按钮列表
* @param btnOrientation 是否横向排列(true 横向排列, false 纵向排列)
* @param hideAvatar     是否隐藏发消息者头像(true 隐藏头像, false 不隐藏)
* @return OapiRobotSendResponse
*/
DingTalkSendingUtilsV1.sendActioncardV1(group, title, actionCardText, btnTitle, btnUrl, btnOrientation, hideAvatar);     
```
效果如下图<br>
![image](https://github.com/jwqsocool/DingTalkRobot/blob/main/image/IMG_0974.PNG)<br>
#### **FeedCard格式发送方法**

开发中

### **OSmessage-DingTalk使用例**
#### **使用发送到指定钉钉群的工具类**
 
```Java
@RestController
public class Dingtest1 {

    GroupConfig group = new GroupConfig(
            "_此处填写自己机器人的webhook_",
            "_此处为机器人测试群中的加签_");

    @GetMapping("/test")
    public void test() {

        DingTalkSendingUtilsV1.sendTextV1(group, "haha", true);

    }
}
```
#### **使用全局异常监控工具类**
```Java
@RestController
@ControllerAdvice
public class Dingtest2 {

    GroupConfig group = new GroupConfig(
            "_此处填写自己机器人的webhook_",
            "_此处为机器人测试群中的加签_");

    @ExceptionHandler(value = Exception.class)
    public void globalExceptionHandler(Exception e) throws ApiException {
        //全局异常捕获，并使用异常发送工具类进行消息推送
        DingTalkSendingException.dingTalkSendingExceptionByMarkdown(e,group);
    }

    @GetMapping("/test")
    public void test() {
            System.out.println(1/0); //测试异常监控是否运行
    }

}
```

### **OSmessage-Email**
以QQ邮箱为例，首先在发送端的Email客户端设置中打开POP3/SMTP/IMAP服务，保存好提供的password.<br>
在application.properties中配置邮件发送的参数：
```
spring.mail.host=smtp.qq.com
spring.mail.port=465
spring.mail.username=<1145141919810@qq.com>
spring.mail.password=<your password>
spring.mail.default-encoding=UTF-8
spring.mail.properties.mail.smtp.socketFactoryClass=javax.net.ssl.SSLSocketFactory
spring.mail.properties.mail.debug=true

spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.properties.mail.smtp.socketFactory.port=465
```
配置完成后，即可调用发信工具类方法：sendMail
```Java
/**
     * 发送纯文本Email
     *
     * @param  subject  邮件主题
     * @param  to       接受方
     * @param  content  邮件内容
     */
    public void sendMail(String subject, String[] to, String content){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(subject);
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSentDate(new Date());
        msg.setText(content);
        javaMailSender.send(msg);
    }

    /**
     * 发送带附件的Email
     *
     * @param  subject  邮件主题
     * @param  to       接受方
     * @param  content  邮件内容
     * @param  file     附件
     */
    public void sendMail(String subject, String[] to, String content, File file){
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            helper.addAttachment(file.getName(), file);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(msg);
    }

    /**
     * 发送带多个附件的Email
     *
     * @param  subject  邮件主题
     * @param  to       接受方
     * @param  content  邮件内容
     * @param  files    多个附件（文件数组）
     */
    public void sendMail(String subject, String[] to, String content, File[] files){
        MimeMessage msg = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(msg,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            if (files != null && files.length > 0) { // 添加附件（多个）
                for (File file : files) {
                    helper.addAttachment(file.getName(), file);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(msg);
    }

    /**
     * 发送Thymeleaf模版的Email
     *
     */
    public void sendThymeleafMail(String subject, String[] to, String templateName, Context context){
        MimeMessage msg = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSentDate(new Date());
//            context = new Context();
//            context.setVariable("param1", "现在在测试群发功能");
//            context.setVariable("param2", "传入String[]测试");
//            context.setVariable("param3", "收到不要回复");
            String process = templateEngine.process(templateName, context);
            helper.setText(process, true);
        }catch (MessagingException e){
            e.printStackTrace();
        }
        javaMailSender.send(msg);
    }
```
这里需要注意的是，sendThymeleafMail中需要的Context类型的参数为邮件模版templateName中需要自定义输入的内容。邮件模版由Thymeleaf语法组成，放置于resources/templates中。<br>
templateName的格式为xxxx.html, 是带有后缀的文件名字符串 <br>

### OSmessage-Email使用例
```Java
    @Autowired
    EmailSendingUtils emailSendingUtils;

    @Test
    void send(){
        String[] to = new String[]{"*******@qq.com","**********@163.com","**********@gmail.com"};
        String subject = "vw50";
        String content = "威武武士,唯物务实,唯我误事,vw50";
        File file = new File("/Users/***/Desktop/IMG_1119.GIF");
        File[] files = new File[2];
        files[0] = new File("/Users/***/Desktop/IMG_1119.GIF");
        files[1] = new File("/Users/***/Desktop/IMG_0981.GIF");
        emailSendingUtils.sendMail(subject, to, content);
        emailSendingUtils.sendMail(subject, to, content, file);
        emailSendingUtils.sendMail(subject, to, content, files);
    }

```
⚠️注意：使用模版时要多注入一个模版引擎
```Java
    @Autowired
    TemplateEngine templateEngine;
```
后续也会把传入sendMail方法的参数封装成为一个类，便于组装参数。<br>

### **OSmessage-Sms**
#### 初始准备
短信发送使用了腾讯云的API，成功创建账号后会赠送100条免费短信额度，便于测试功能。<br>
创建完成，申请短信相关功能，并按官方的要求创建短信签名、短信模版和应用，并保存下来。<br>
对于SecretId和SecretKey相关的设置，建议使用子账号的方式：即创建子账号并授予短信相关服务的权限，之后便可以使用子账户的SecretId和SecretKey而不暴露主账户的信息。<br>
⚠️注意：子账户的SecretId和SecretKey只会在设置完成的时候显示一次！**请及时保存！请及时保存！请及时保存！**<br>
回归本项目，此处提供[**SmsConfig**](https://github.com/jwqsocool/Osmessage/blob/main/OSmessage/OSmessage-Sms/src/main/java/Sms/Param/SmsConfig.java)类来对之前在腾讯云获取的参数进行组装，[**SmsContent**](https://github.com/jwqsocool/Osmessage/blob/master/OSmessage/OSmessage-Sms/src/main/java/Sms/Param/SmsContent.java)类则是用来组装短信模版中自己设置的自定义变量，[**SmsUtils**](https://github.com/jwqsocool/Osmessage/blob/master/OSmessage/OSmessage-Sms/src/main/java/Sms/Utils/SmsUtils.java)用来实现发送功能<br>
#### **OSmessage-Sms使用例**
```Java
    SmsConfig hand = new SmsConfig(
            "sms.tencentcloudapi.com",
            "ap-nanjing",
            "your secretId",
            "your secretKey",
            "your smsSdkAppId",
            "your SignName",
            "your templateId");
    
    SmsContent code = new SmsContent(new String[]{"****"});

    @GetMapping("/sms")
    public void sms(){
        SendSms(new String[]{"130********"}, hand, code);
    }
```
若不需要自定义内容只发送模版固定内容，则可以不传入SmsContent参数：
```Java
    SmsConfig hand = new SmsConfig(
            "sms.tencentcloudapi.com",
            "ap-nanjing",
            "your secretId",
            "your secretKey",
            "your smsSdkAppId",
            "your SignName",
            "your templateId");

    @GetMapping("/sms")
    public void sms(){
        SendSms(new String[]{"130********"}, hand);
    }
```
⚠️关于营销类信息的说明：因为三大运营商对营销类广告的管控愈发严格，所以可能会出现短信被拦截或是禁发的情况，建议配合定时器小批量发送。<br>
详情请参考[腾讯开发文档](https://cloud.tencent.com/document/product/382/58058)<br>

### **OSmessage-miniapp**
#### **初始准备**
首先需要创建**微信服务号**，普通的订阅号属于个人，许多API腾讯**不提供给个人号**，所以第一步是获得**企业认证**<br>
[微信公众平台](https://mp.weixin.qq.com/)<br>
按照官方指引完成设置后，关联已有小程序。因为微信小程序无法自主推送消息，只能借助关联的服务号进行消息推送<br>
详情请查看[接口权限说明](https://developers.weixin.qq.com/doc/offiaccount/Getting_Started/Explanation_of_interface_privileges.html)<br>
在侧边栏处可以找到小程序相关功能，按照官方指引完成设置<br>
没有小程序可以[注册小程序账号](https://mp.weixin.qq.com/wxopen/waregister?action=step1),注册完成后同样在微信公众平台登录。<br>
⚠️注意：保存服务号和小程序的APPID与APPSECRET，调用消息推送方法需要这些参数<br>

#### **OSmessage-miniapp使用例**
目前采用模板消息推送，实现方法的类为[SendTemplateServiceImpl](https://github.com/jwqsocool/Osmessage/blob/master/OSmessage/OSmessage-miniapp/src/main/java/MiniApp/Api/impl/SendTemplateServiceImpl.java)<br>
目前有两种方法提供使用，sendTemplate与sendTemplateV1<br>
你可以选择按照微信文档中的完整Json格式传入sendTemplate实现模版发送，也可以先组装TemplateParam，再传入sendTemplateV1实现模版发送。
```Java
@RestController
public class minitest {

    /**
     * 通过WeChatParam来确定推送消息的微信服务公众号
     */
    WeChatParam weChatParam = new WeChatParam("appid","appsecret");

    /**
     * 若关联小程序则需要对TemplateParam.MiniProgram单独设置
     */
    TemplateParam.MiniProgram miniProgram = new TemplateParam.MiniProgram("appid","pagepath");


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
                "           \"touser\":\"模板的ID\",\n" +
                "           \"template_id\":\"发送给的用户\",\n" +
                "           \"url\":\"null\", \n" +
                "           \"miniprogram\":{\"appid\":appid, \"pagePath\":pagepath},\n" +
                "           \"data\":{\n" +
                "           \"参数1\":{\"value\":\"值1\",\"\"color\":null\"}, \"参数2\":{\"value\":\"值2\",\"\"color\":null\"}\n" +
                "           }\n" +
                "}");
        sendTemplateV1(weChatParam, templateParam);
    }

}

```
关于OpenID的获取可以参考微信官方文档中小程序分类的页面<br>
[微信官方文档|小程序|开放接口|wx.login](https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/wx.login.html)<br>
[微信官方文档|小程序|快速接入](https://developers.weixin.qq.com/doc/aispeech/miniprogram/quickuse.html)<br>


#### **相关参考文档**
你可以在[模板消息｜微信开发文档](https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html)中找到相关参数的说明，不是每个参数都是必要的，根据实际需求可以删改<br>


## **完善方向**
**1.** 现在四种消息的处理方式还很粗糙，把发信账号的配置放在了方法中，是因为本着让用户可以方便切换账号的原则。以后会考虑引入MySQL，直接读取用户存入的数据，省去用户自主配置的代码行数<br>
**2.** 由于腾讯的小程序提供的Access_Token只有2个小时的有效期，而且即便是企业认证的服务号每天也只有2000次请求，且每次请求后之前的Access_Token就会过期，所以需要认真规划获取机制。
目前的解决方案是使用单例模式，虽然节省内存，但是由于没有抽象层，因此扩展困难。且如果实例化的对象长时间不被利用，系统会认为是垃圾而被回收，这将导致对象状态的丢失。所以到以后业务拓展时期可能需要舍弃单例模式转而使用其他降低耦合的中间件来存储Access_Token<br>
**3.** 本服务没有系统监控，当处理量大时，很难掌握消息推送的运行状态，本人能力不足，还在学习如何使用中，尽快实装。。。
 


## **问题反馈**
项目仍然在开发中，有bug和问题或是什么开发建议可以联系我<br>
Email: jwqsocool@foxmail.com<br>
QQ: 1014349338
