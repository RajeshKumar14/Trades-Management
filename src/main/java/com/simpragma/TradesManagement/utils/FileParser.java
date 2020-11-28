package com.simpragma.TradesManagement.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileParser {
    public JSONObject JsonFileToJSONObject(String resourceName) {
        JSONObject jsonObject = null;
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
            if (inputStream != null) {
                BufferedReader streamReader = null;

                streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                StringBuilder responseStrBuilder = new StringBuilder();
                String inputStr;
                while ((inputStr = streamReader.readLine()) != null) {
                    responseStrBuilder.append(inputStr);
                }
                JSONParser parser = new JSONParser();
                jsonObject = (JSONObject) parser.parse(responseStrBuilder.toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
