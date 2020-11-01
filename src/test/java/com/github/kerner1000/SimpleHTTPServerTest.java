package com.github.kerner1000;

import com.github.kerner1000.http.server.SimpleHTTPHandler;
import com.github.kerner1000.http.server.SimpleHTTPServer;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.github.kerner1000.http.server.SimpleHTTPServer.DEFAULT_SERVER_URL;
import static org.junit.Assert.assertEquals;

public class SimpleHTTPServerTest {

    private static final String HOST_ADDR = "localhost";

    private static final int PORT = 8080;

    private static final int THREAD_CNT = 10;

    private static HttpServer server;

    @BeforeClass
    public static void initStatic() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        ExecutorService exe = Executors.newFixedThreadPool(THREAD_CNT);
        server.setExecutor(exe);
        HttpContext context = server.createContext(DEFAULT_SERVER_URL, new SimpleHTTPHandler());
        server.start();
    }

    @AfterClass
    public static void cleanUpStatic(){
        server.stop(0);
    }

    @Before
    public void init(){

    }

    @Test
    public void testCorrectlyExtractedRequestParam() throws IOException {
        URL url = new URL("http://" + HOST_ADDR + ":" + SimpleHTTPServer.DEFAULT_PORT + DEFAULT_SERVER_URL + "?q=4");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        String answer = content.toString();
        in.close();
        con.disconnect();

        assertEquals("Hi there! Your request param was: 4", answer);

    }

    @Test
    public void testNoRequestParam() throws IOException {
        URL url = new URL("http://" + HOST_ADDR + ":" + SimpleHTTPServer.DEFAULT_PORT + DEFAULT_SERVER_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        String answer = content.toString();
        in.close();
        con.disconnect();

        assertEquals("Hi there! Your request param was: " + SimpleHTTPHandler.DEFAULT_NO_PARAMS_FOUND_MSG, answer);

    }
}
