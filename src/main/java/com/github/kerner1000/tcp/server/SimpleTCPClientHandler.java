package com.github.kerner1000.tcp.server;

import com.github.kerner1000.tcp.SocketReader;
import com.github.kerner1000.tcp.SocketWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * A simple TCP request handler.
 */
public class SimpleTCPClientHandler {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTCPClientHandler.class);

    private final Socket socket;

    private final SocketWriter toClient;

    private static int instanceCounter;

    /**
     * Creates a new client handler on given {@link Socket}.
     *
     * @param socket the {@code Socket} to connect to
     * @throws IOException if an I/O error occurs
     */
    public SimpleTCPClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        int instanceId = instanceCounter;
        this.toClient = new SocketWriter(socket);
        SocketReader fromClient = new SocketReader(socket);
        fromClient.addMessageHandler(this::sendMessage);
        logger.info("Connection established, id: {}, {} client(s) connected", instanceId, ++instanceCounter);
        new Thread(fromClient).start();

    }

    /**
     * Stops the handler.
     * Note that the handler is stopped implicitly when the server stops or the client disconnects.
     */
    public synchronized void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            // ignore
        }
    }

    /**
     * Sends given message to the client.
     *
     * @param msg message to send
     */
    public void sendMessage(String msg) {
        toClient.send(msg + " back!");
    }
}
