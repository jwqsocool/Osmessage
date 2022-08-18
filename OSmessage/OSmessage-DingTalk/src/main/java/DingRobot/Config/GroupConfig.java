package DingRobot.Config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupConfig {
    private String WEBHOOK;
    private String SECRET;


    @Override
    public String toString() {
        try {
            return WEBHOOK + signV1(SECRET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String signV1(String secret)throws Exception{
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return "&timestamp=" + timestamp + "&sign=" + sign;
    }
}
