package com.github.kerner1000.udp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private final DatagramSocket socket;

    private byte[] buffer = new byte[256];

    public ClientHandler(DatagramSocket socket){
        this.socket = socket;
    }

    public void handle() throws IOException {
        logger.info("Handling requests");
        // client request writes to this packet
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        socket.receive(request);
        String msg = new String(buffer, 0, buffer.length);
        logger.info("Received message: {}", msg);
        InetAddress clientAddress = request.getAddress();
        int clientPort = request.getPort();
        // packet back to the client
        String answer = "Got your message: " + msg;
        byte [] byteAnswer = answer.getBytes();
        DatagramPacket response = new DatagramPacket(byteAnswer, byteAnswer.length, clientAddress, clientPort);
        logger.info("Sending answer");
        socket.send(response);
    }

}

