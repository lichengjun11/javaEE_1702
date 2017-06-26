package demo.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.enterprise.inject.Model;
import javax.json.Json;
import java.lang.reflect.Type;

/**
 * Created by lichengjun on 2017/6/26.
 */
public class TestGson {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //  Java object to JSON object
        Weather weather = new Weather("30", "50", "12");
        System.out.println(gson.toJson(weather));

        // JSON object to JAVA object
        Model model = gson.fromJson(gson.toJson(weather), (Type) Weather.class);
//        System.out.println(model);
    }
}
