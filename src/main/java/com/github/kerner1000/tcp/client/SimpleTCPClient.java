package com.github.kerner1000.tcp.client;

import com.github.kerner1000.tcp.SocketReader;
import com.github.kerner1000.tcp.SocketWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * A simple TCP client.
 */
public class SimpleTCPClient {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTCPClient.class);

    private final Socket socket;
    /**
     * A {@code SocketWriter} to send data to the server.
     */
    private final SocketWriter toServer;
    /**
     * A {@code SocketReader} to receive data from the server.
     */
    private final SocketReader fromServer;

    /**
     * Starts a new connection to given host and port.
     *
     * @param host host to start a connection to
     * @param port port to use for the new connection
     * @throws IOException if an I/O error occurs
     */
    public SimpleTCPClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        toServer = new SocketWriter(socket);
        fromServer = new SocketReader(socket);
        new Thread(fromServer).start();
    }

    /**
     * Adds a {@link SocketReader.MessageHandler message handler} to this client.
     *
     * @param messageHandler message handler to add
     * @see SocketReader.MessageHandler
     */
    public synchronized void addMessageHandler(SocketReader.MessageHandler messageHandler) {
        fromServer.addMessageHandler(messageHandler);
    }

    /**
     * Sends a string (one line) to the server.
     *
     * @param msg the message to send to the server
     */
    public synchronized void sendMessage(String msg) {
        toServer.send(msg);
    }

    /**
     * Closes the connection.
     *
     * @throws IOException if an I/O error occurs
     */
    public synchronized void close() throws IOException {
        socket.close();
    }

    /**
     * A main method to start a new client.
     * Reads from {@code System.in} and sends those strings to the server.
     * Line by line.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SimpleTCPClient client = new SimpleTCPClient("localhost", 5555);
        logger.info("Connection started");
        String msg = null;
        while (!"bye!".equals(msg)) {
            Scanner scanner = new Scanner(System.in);
            msg = scanner.nextLine();
            logger.info("Sending {}", msg);
            client.sendMessage(msg);
        }
        logger.info("Got bye! signal, closing connection");
        client.close();
        logger.info("Connection closed, terminating");
    }


}
