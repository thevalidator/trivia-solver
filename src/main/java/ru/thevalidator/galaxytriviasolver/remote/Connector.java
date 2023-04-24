/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import ru.thevalidator.galaxytriviasolver.json.JsonUtil;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia.Question;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Connector {

    public static final String DOMAIN = "https://raceon.ru";//"https://raceon.ru";//"http://localhost:8080"
    public static final String ANSWER_PATH = "/api/v1/trivia/get-answer";//"/api/v1/trivia/get-answer/";
    public static final String STATUS_PATH = "/api/v1/trivia/workstation/";
    public static final String REFRESH_TOKEN_PATH = "/api/v1/trivia/workstations/refresh-token";
    private static String refreshToken;
    private final String key;

    public Connector(String key) {
        this.key = key;
        if (refreshToken == null) {
            refreshToken = getRefreshToken(key);
        }
    }

    public static String getRefreshToken(String key){
        String token = null;
        try {
            URL url = new URL(DOMAIN + REFRESH_TOKEN_PATH);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Trivia solver");
            conn.setRequestProperty("User-data", key);
            conn.getResponseCode();
            token = conn.getHeaderField("Refresh-token");
            conn.disconnect();
        } catch (IOException e) {
            //TODO:
        }
        return token;
    }

    public static int getResponseCode(String key) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL(DOMAIN + STATUS_PATH + key);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Trivia solver");
        int responseCode = conn.getResponseCode();
        conn.disconnect();
        
        return responseCode;
    }

    public int getCorrectAnswerIndex(Question question) throws MalformedURLException, JsonProcessingException, ProtocolException, IOException {
        String link = DOMAIN + ANSWER_PATH;
        URL url = new URL(link);
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Refresh-token", refreshToken);
        conn.setRequestProperty("User-data", key);
        conn.setDoOutput(true);

        String data = JsonUtil.getMapper().writeValueAsString(question);

        try ( OutputStream os = conn.getOutputStream()) {
            byte[] input = data.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responsecode = conn.getResponseCode();
        //refreshToken = conn.getHeaderField("Refresh-token");

        if (responsecode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responsecode);
        } else {
            StringBuffer response;
            try ( BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }
            refreshToken = conn.getHeaderField("Refresh-token");
            return Integer.parseInt(response.toString());
        }
    }

}
