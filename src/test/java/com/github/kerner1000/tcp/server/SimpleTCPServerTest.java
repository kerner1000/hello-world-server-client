package com.github.kerner1000.tcp.server;

import com.github.kerner1000.tcp.client.SimpleTCPClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;

public class SimpleTCPServerTest {

    private static final String HOST_ADDR = "localhost";

    private static final int PORT = 2222;

    private static SimpleTCPServer server;

    @BeforeClass
    public static void initStatic() throws IOException {
        server = new SimpleTCPServer(PORT);
        new Thread(()-> {
            try {
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @AfterClass
    public static void cleanUpStatic(){
        server.stop();
    }

    @Before
    public void init(){

    }
    
    @Test
    public void test01() throws IOException, InterruptedException {
        SimpleTCPClient client = new SimpleTCPClient(HOST_ADDR, PORT);
        AtomicReference<String> message = new AtomicReference<>();
        client.addMessageHandler(m -> message.set(m));
        client.sendMessage("hi!");
        Thread.sleep(2000);
        client.close();
        assertEquals("hi! back!", message.get());
    }
}
