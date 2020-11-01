package com.github.kerner1000.http.server;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple HTTP Server.
 */
public class SimpleHTTPServer {

    private static Logger logger = LoggerFactory.getLogger(SimpleHTTPServer.class);

    public static final int DEFAULT_PORT = 8080;

    public static final int DEFAULT_THREAD_CNT = 10;

    public static final String DEFAULT_SERVER_URL = "/test";

    /**
     * Main method to start the server.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(DEFAULT_PORT), 0);
        ExecutorService exe = Executors.newFixedThreadPool(DEFAULT_THREAD_CNT);
        server.setExecutor(exe);
        HttpContext context = server.createContext(DEFAULT_SERVER_URL, new SimpleHTTPHandler());
        logger.info(" Server starting");
        server.start();
    }
}
