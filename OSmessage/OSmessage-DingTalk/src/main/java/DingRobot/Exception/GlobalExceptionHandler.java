package DingRobot.Exception;

import DingRobot.Config.GroupConfig;
import DingRobot.DingTalkUtils.DingTalkSendingException;
import com.taobao.api.ApiException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * 全局异常处理
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    GroupConfig group = new GroupConfig(
            "<your webhook>",
            "<your secret>");


    @ExceptionHandler(value = Exception.class)
    public void globalExceptionHandler(Exception e) throws ApiException {
        //全局异常捕获，并使用异常发送工具类进行消息推送
        DingTalkSendingException.dingTalkSendingExceptionByMarkdown(e,group);
//        DingTalkSendingException.dingTalkSendingExceptionByActioncard(e,jwq,"https://github.com/jwqsocool/DingTalkRobot");
    }
}
