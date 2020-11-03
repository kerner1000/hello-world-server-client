package com.github.kerner1000.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple TCP server.
 */
public class SimpleTCPServer {

    public static final int DEFAULT_PORT = 5555;

    private static Logger logger = LoggerFactory.getLogger(SimpleTCPServer.class);

    private AtomicBoolean running = new AtomicBoolean(true);

    private ServerSocket server;

    public SimpleTCPServer(int port) throws IOException {
        server = new ServerSocket(port);
        logger.info("Server created on port {}", port);
    }

    public void start() throws IOException {
        while(running.get()){
            // this blocks until connection is established
            logger.info("Waiting for a new connection");
            try {
                new SimpleTCPClientHandler(server.accept());
            }catch(SocketException e){
                // connection closed
            }
        }
    }

    public void stop() {
        this.running.set(false);
        try {
            server.close();
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * Main method to start the server.
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        SimpleTCPServer server = new SimpleTCPServer(DEFAULT_PORT);
        server.start();
    }
}
