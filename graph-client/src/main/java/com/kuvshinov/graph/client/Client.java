package com.kuvshinov.graph.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author Sergey Kuvshinov
 */
public class Client {

    private String host;

    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Client(String host, String port) {
        this(host, Integer.parseInt(port));
    }

    public void start() {
        boolean isStart = true;
        try(SocketChannel socketChannel = SocketChannel.open()) {

            if (socketChannel.isOpen()) {
                socketChannel.configureBlocking(true);
                socketChannel.connect(new InetSocketAddress(host, port));

                if (socketChannel.isConnected()) {
                    System.out.println("Connected");

                    while(isStart) {

                    }

                    socketChannel.close();
                }
            } else {
                System.out.println("Cannot open socket");
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("No connection parameters!");
            return;
        }

        Client client = new Client(args[0], args[1]);
        client.start();

    }

}
