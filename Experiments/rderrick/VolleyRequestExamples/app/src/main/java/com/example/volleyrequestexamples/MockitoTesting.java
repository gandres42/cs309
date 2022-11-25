package com.example.volleyrequestexamples;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class MockitoTesting {
//
//    public static void main(String args[]) throws JSONException {
//        testMockito();
//    }
//
//    @Test
//    public static void testMockito() throws JSONException {
//        JSONArray arrayTest = new JSONArray();
//        for(int i = 0; i < 10; ++i) {
//            JSONObject newObject = new JSONObject();
//            newObject.put("id", i + 1);
//            newObject.put("group_name", "Computer Science Club");
//            newObject.put("group_description", "Here is a random description of a group");
//            newObject.put("number_of_members", 127);
//            arrayTest.put(newObject);
//        }
//        JsonRequests jsonRequests = mock(JsonRequests.class);
//        when(jsonRequests.jsonParseArray(arrayTest)).thenReturn(JsonParsing.jsonParseArray(arrayTest));
//
//        ArrayList<String> result = jsonRequests.jsonParseArray(arrayTest);
//        assertEquals(result, result);
//    }
}
