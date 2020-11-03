package com.github.kerner1000.udp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class SimpleUDPClient {

    private static final Logger logger = LoggerFactory.getLogger(SimpleUDPClient.class);
    private static final String hostname = "localhost";
    private static final int port = 1117;
    private byte[] buffer = new byte[256];


    private void connect(String hostname, int port) {
        try {
            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
            String msg = "test-msg";
            byte [] byteMsg = msg.getBytes();
            DatagramPacket request = new DatagramPacket(byteMsg, byteMsg.length, address, port);
            socket.send(request);
            logger.info("Request sent");
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);
            String quote = new String(buffer, 0, response.getLength());
            logger.info("Response received: {}", quote);
        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        SimpleUDPClient client = new SimpleUDPClient();
        client.connect(hostname, port);


    }


}
