package com.github.kerner1000.udp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramSocket;

public class SimpleUDPServer {

    private static final int port = 1117;

    private static final Logger logger = LoggerFactory.getLogger(SimpleUDPServer.class);

    public static void main(String[] args) throws IOException {

        SimpleUDPServer server = new SimpleUDPServer();
        server.start(port);

    }

    public void start(int port) throws IOException {
        DatagramSocket socket = new DatagramSocket(port);
        logger.info("Server created on port {} and waiting for requests", port);
        while(true){
            // this blocks until connection is established
            new ClientHandler(socket).handle();
        }

    }
}
