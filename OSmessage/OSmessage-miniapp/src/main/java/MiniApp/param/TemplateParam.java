package MiniApp.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateParam {
    /**
     * 订阅消息模板ID
     */
    private String templateId;

    /**
     * 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }
     */
    private List<TemplateData> data;

    /**
     * 消息接受用户的openid
     */
    private String toUser;

    /**
     * 点击跳转链接
     */
    private String url;

    /**
     * 小程序信息
     */
    private MiniProgram miniProgram;

    public TemplateParam addData(TemplateData datum) {
        if (this.data == null) {
            this.data = new ArrayList();
        }
        this.data.add(datum);
        return this;
    }

    private static List<TemplateData> $default$data() {return new ArrayList();}

    public static TemplateParamBuilder builder() {
        return new TemplateParamBuilder();
    }

    public static class TemplateParamBuilder {
        private String toUser;
        private String templateId;
        private String url;
        private TemplateParam.MiniProgram miniProgram;
        private boolean data$set;
        private List<TemplateData> data;

        TemplateParamBuilder() {
        }

        public TemplateParam.TemplateParamBuilder toUser(String toUser) {
            this.toUser = toUser;
            return this;
        }

        public TemplateParam.TemplateParamBuilder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public TemplateParam.TemplateParamBuilder url(String url) {
            this.url = url;
            return this;
        }

        public TemplateParam.TemplateParamBuilder miniProgram(TemplateParam.MiniProgram miniProgram) {
            this.miniProgram = miniProgram;
            return this;
        }

        public TemplateParam.TemplateParamBuilder data(List<TemplateData> data) {
            this.data = data;
            this.data$set = true;
            return this;
        }

        public TemplateParam build() {
            List<TemplateData> data = this.data;
            if (!this.data$set) {
                data = TemplateParam.$default$data();
            }
            return new TemplateParam(this.toUser, data, this.templateId, this.url, this.miniProgram);
        }

    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MiniProgram{

        private String appid;

        /**
         * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
         */
        private String pagePath;

        public String toString() {
//            "miniprogram":{
//                "appid":"xiaochengxuappid12345",
//                        "pagepath":"index?foo=bar"
//            },
            return "\"miniprogram\":{" +"\"appid\":"+ this.getAppid() + ", \"pagePath\":" + this.getPagePath() + "},";
        }
    }

    public TemplateParam(String templateId, List<TemplateData> data, String toUser) {
        this.templateId = templateId;
        this.data = data;
        this.toUser = toUser;
    }

    public TemplateParam(String templateId, List<TemplateData> data, String toUser, String url) {
        this.templateId = templateId;
        this.data = data;
        this.toUser = toUser;
        this.url = url;
    }

    public TemplateParam(String templateId, List<TemplateData> data, String toUser, MiniProgram miniProgram) {
        this.templateId = templateId;
        this.data = data;
        this.toUser = toUser;
        this.miniProgram = miniProgram;
    }

    public String toString(){
        return "{\n" +
                "           \"touser\":\""+this.getToUser()+"\",\n" +
                "           \"template_id\":\""+this.getTemplateId()+"\",\n" +
                "           \"url\":\""+this.getUrl()+"\", \n" +
                "           " + miniProgram.toString() + "\n" +
                "           \"data\":{\n" +
                "           " + StringUtils.strip(this.getData().toString(),"[]") + "\n" +
                "           }\n" +
                "}\n";
    }
}
