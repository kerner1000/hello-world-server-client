package com.github.kerner1000.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple reader on a socket input stream.
 * Starts reading when {@link #run()} is invoked.
 * Stops reading when the socket is closed.
 *
 * Can be used within a server or client.
 */
public class SocketReader implements Runnable {

    /**
     * A callback handler for incoming messages.
     */
    public interface MessageHandler {
        /**
         * Handles incoming messages.
         *
         * @param msg the received message
         */
        void handleMessage(String msg);
    }

    private static final Logger logger = LoggerFactory.getLogger(SocketReader.class);

    /**
     * A {@link Reader} to read data from given {@link Socket}.
     */
    private final BufferedReader in;

    /**
     * Registered callback handlers.
     */
    private final List<MessageHandler> handlerList = new ArrayList<>();

    /**
     * Creates a new {@code SocketReader} on given {@link Socket}.
     *
     * @param socket the {@link Socket} to read data from
     * @throws IOException if an I/O error occurs
     */
    public SocketReader(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Adds given {@link MessageHandler message handler} to this reader.
     *
     * @param handler the message handler to add
     */
    public synchronized void addMessageHandler(MessageHandler handler) {
        handlerList.add(handler);
    }

    @Override
    public void run() {
        logger.info("Listening for messages");
        while (true) {
            try {
                // blocks until next line is read
                String nextLine = in.readLine();
                if (nextLine == null) {
                    // connection closed/ end of stream reached
                    break;
                }
                processLine(nextLine);
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        }
        logger.info("Listening done");
    }

    private void processLine(String line) {
        logger.info("Read line: {}", line);
        handlerList.forEach(e -> e.handleMessage(line));
    }


}
