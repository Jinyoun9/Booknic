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

        System.out.println(sbf.toString());
        return sbf.toString();
    }

    public static List<?> fetch(String url) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String htmlString = makeUrl(url);
        URI uri = new URI(htmlString);
        String jsonString = restTemplate.getForObject(uri, String.class);

        // JSON 유효성 검사 추가
        if (jsonString == null || jsonString.trim().isEmpty() || jsonString.charAt(0) != '{') {
            throw new RuntimeException("Invalid response received: " + jsonString);
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing JSON response: " + e.getMessage());
        }

        JSONObject jsonResponse = (JSONObject) jsonObject.get("response");

        Long pageNo = (Long) jsonResponse.get("pageNo");
        Long pageSize = (Long) jsonResponse.get("pageSize");

        JSONObject jsonPageRes = new JSONObject();
        jsonPageRes.put("pageNo", pageNo);
        jsonPageRes.put("pageSize", pageSize);

        JSONArray libsArray = (JSONArray) jsonResponse.get("libs");
        List<JSONObject> libList = new ArrayList<>();
        libList.add(jsonPageRes);
        for (int i = 0; i < libsArray.size(); i++) {
            JSONObject libObject = (JSONObject) libsArray.get(i);
            JSONObject libDetails = (JSONObject) libObject.get("lib");
            libList.add(libDetails);
        }

        return libList.isEmpty() ? Collections.emptyList() : libList;
    }
}
