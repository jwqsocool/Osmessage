package MiniApp.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateData {

    private String name;
    private String value;
    private String color;

    public TemplateData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
//        "first": {
//            "value":"恭喜你购买成功！",
//            "color":"#173177"
//        },
        return "\"" + this.getName() + "\":{\"value\":\"" + this.getValue() +"\",\""+ "\"color\":" + this.getColor() + "\"}";
    }
}
