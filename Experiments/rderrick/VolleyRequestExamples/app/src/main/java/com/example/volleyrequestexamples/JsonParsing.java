package com.example.volleyrequestexamples;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParsing extends AppCompatActivity {

    //Turn array of strings into one final string
    public static String arrayToString(ArrayList<String> stringArray) {
        String result = stringArray.get(0);
        for(int i = 1; i <= stringArray.size() - 1; ++i) {
            result = result + "\n" + stringArray.get(i);
        }
        return result;
    }

    //Parse a Json array into an array of strings
    public static ArrayList<String> jsonParseArray(JSONArray jsonArray) {
        ArrayList<String> parsedJsonArray = new ArrayList<String>();
        for(int i = 0; i <= jsonArray.length() - 1; ++i) {
            try {
                parsedJsonArray.add(jsonParseObject(jsonArray.getJSONObject(i)));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return parsedJsonArray;
    }

    //Parse a Json object into a formatted string
    public static String jsonParseObject(JSONObject object) {
        String currentString = object.toString();
        String parsedJsonObject = currentString.charAt(0) + "\n";

        for(int i = 1; i <= currentString.length() - 2; ++i) {

            if (currentString.charAt(i) == ':') {
                parsedJsonObject = parsedJsonObject + ": ";
            }
            else if (currentString.charAt(i) == ',') {
                parsedJsonObject = parsedJsonObject + "\n";
            }
            else {
                parsedJsonObject = parsedJsonObject + currentString.charAt(i);
            }
        }

        parsedJsonObject = parsedJsonObject + "\n}";
        return parsedJsonObject;
    }

}
