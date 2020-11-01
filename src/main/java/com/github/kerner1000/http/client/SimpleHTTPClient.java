package com.github.kerner1000.http.client;

import com.github.kerner1000.http.server.SimpleHTTPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple HTTP client. Note that your browser is a decent HTTP client as well!
 */
public class SimpleHTTPClient {

    public static final String DEFAULT_HOST = "localhost";

    public static final String DEFAULT_REQUEST_METHOD = "GET";

    private static Logger logger = LoggerFactory.getLogger(SimpleHTTPClient.class);

    public static void main(String[] args) throws IOException {
        URL url = new URL("http://" + DEFAULT_HOST + ":" + SimpleHTTPServer.DEFAULT_PORT + SimpleHTTPServer.DEFAULT_SERVER_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(DEFAULT_REQUEST_METHOD);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        logger.info("Server answer: " + content.toString());
        in.close();
        con.disconnect();
    }
}
