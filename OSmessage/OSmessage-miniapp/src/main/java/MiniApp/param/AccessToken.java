package MiniApp.param;

import MiniApp.Utils.AccessTokenUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {
    private String accessToken;
    private int expiresTime;
}
