package Email;

import Email.Utils.EmailSendingUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class EmailSendingUtilsTests {

    @Autowired
    EmailSendingUtils emailSendingUtils;

    @Test
    void send(){
        String[] to = new String[]{"641767328@qq.com","1411989890@qq.com","1207340833@qq.com"};
        String subject = "vw50";
        String content = "威武武士,唯物务实,唯我误事,vw50";
        File file = new File("/Users/***/Desktop/IMG_1119.GIF");
        File[] files = new File[2];
        files[0] = new File("/Users/***/Desktop/IMG_1119.GIF");
        files[1] = new File("/Users/***/Desktop/IMG_0981.GIF");
        emailSendingUtils.sendMail(subject, to, content);
    }
}
