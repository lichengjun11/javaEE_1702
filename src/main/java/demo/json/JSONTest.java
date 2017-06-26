package demo.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jdk.nashorn.internal.scripts.JD;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichengjun on 2017/6/26.
 */
public class JSONTest {
    public static void main(String[] args) {
    User user = new User(1,"user@gmail.com","123");

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            users.add(new User(i,"user"+i+"@gmail.com","123"));
        }

        String jsonObjectString,jsonArrayString;
        System.out.println("\n---json.org---\n");

        // java object to json object
        JSONObject jsonObject = new JSONObject(user);
        System.out.println(jsonObject.toString(4));

        // java collection to json array
        JSONArray jsonArray = new JSONArray(users);
        System.out.println(jsonArray.toString(4));


        System.out.println("\n---jackson---\n");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);

        try {
        // java object to json object
            jsonObjectString = objectMapper.writeValueAsString(user);
            System.out.println(jsonObjectString);

            // java collection to json array
            jsonArrayString = objectMapper.writeValueAsString(users);
            System.out.println(jsonArrayString);

            // json object to java object
            System.out.println(objectMapper.readValue(jsonArrayString,User.class));

            // json array to java collection
            System.out.println((char[]) objectMapper.readValue(jsonArrayString, TypeFactory.defaultInstance().constructCollectionType(List.class, User.class))); // method 1
            System.out.println(objectMapper.readValue(jsonArrayString, new TypeReference<List<User>>() {
            })); // method 2



        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
