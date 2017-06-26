package demo.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichengjun on 2017/6/26.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);// 格式化

//        1 把java对象变为json对象
        Weather weather = new Weather("30", "50", "12");
        System.out.println(objectMapper.writeValueAsString(weather));


        // 把数组变为JSON 数组
        List<Weather> weathers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            weathers.add(new Weather("hum"+i,"tmp"+i,"spd"+i));
        }
        System.out.println(objectMapper.writeValueAsString(weathers));

        // 将JSON 对象解析为java 对象
        Weather newWeather = objectMapper.readValue("{\"tmp\":\"30\",\"hum\":\"50\",\"spd\":\"12\"}", Weather.class);
        System.out.println(newWeather);
    }
}
