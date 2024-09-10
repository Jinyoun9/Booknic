package com.booknic.util;
import static com.booknic.api.LibApi.EndPoint.*;
import static com.booknic.api.LibApi.Param.*;

import com.booknic.api.LibApi;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
@Slf4j
public class URLBuilder {


    private static String makeUrl(String url) {
        StringBuffer sbf = new StringBuffer();
        sbf.append(BASEURL.getParam());
        sbf.append(url);
        sbf.append(AUTHKEY.getParam());
        sbf.append("&");
        sbf.append(FORMAT.getParam());
        return sbf.toString();
    }

    public static List<?> fetch(String url) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String htmlString = makeUrl(url);
        URI uri = new URI(htmlString);
        String jsonString = restTemplate.getForObject(uri, String.class);

        // JSON 유효성 검사 추가
        if (jsonString == null || jsonString.trim().isEmpty() || (jsonString.charAt(0) != '{' && jsonString.charAt(0) != '[')) {
            throw new RuntimeException("Invalid response received: " + jsonString);
        }

        JSONParser jsonParser = new JSONParser();
        Object json;
        try {
            json = jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing JSON response: " + e.getMessage());
        }

        // JSON 구조에 따른 처리
        List<JSONObject> resultList = new ArrayList<>();
        if (json instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) json;
            resultList.addAll(parseJsonObject(jsonObject));
        } else if (json instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) json;
            for (Object obj : jsonArray) {
                if (obj instanceof JSONObject) {
                    resultList.addAll(parseJsonObject((JSONObject) obj));
                }
            }
        }

        return resultList.isEmpty() ? Collections.emptyList() : resultList;
    }

    private static List<JSONObject> parseJsonObject(JSONObject jsonObject) {
        List<JSONObject> resultList = new ArrayList<>();

        // JSON 구조를 동적으로 탐색하여 데이터 추출
        for (Object key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);

            if (value instanceof JSONObject) {
                resultList.add((JSONObject) value);
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                for (Object obj : jsonArray) {
                    if (obj instanceof JSONObject) {
                        resultList.add((JSONObject) obj);
                    }
                }
            } else {
                // 필요한 경우 다른 타입의 데이터 처리
                JSONObject singleItem = new JSONObject();
                singleItem.put(key, value);
                resultList.add(singleItem);
            }
        }

        return resultList;
    }
}
