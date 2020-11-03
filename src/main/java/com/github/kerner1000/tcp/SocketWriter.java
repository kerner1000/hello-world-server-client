package com.github.kerner1000.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A simple writer to a socket output stream.
 * {@code SocketWriter} closes implicitly when the socket is closed.
 *
 * Can be used within a server or client.
 */
public class SocketWriter {

    private static final Logger logger = LoggerFactory.getLogger(SocketWriter.class);

    private final PrintWriter out;

    /**
     * Creates a new {@link SocketWriter} on given {@link Socket}.
     *
     * @param socket {@code Socket} to send data to
     * @throws IOException if an I/O error occurs
     */
    public SocketWriter(Socket socket) throws IOException {
        this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    /**
     * Sends a message to the socket.
     *
     * @param msg the message to send
     */
    public synchronized void send(String msg) {
        logger.info("Sending message {}", msg);
        this.out.println(msg);
        this.out.flush();
    }
}
