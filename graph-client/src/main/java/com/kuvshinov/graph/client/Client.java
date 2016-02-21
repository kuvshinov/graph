package com.kuvshinov.graph.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author Sergey Kuvshinov
 */
public class Client {

    private String host;

    private int port;

    private Reader reader;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.reader = new InputStreamReader(System.in);
    }

    public Client(String host, String port) {
        this(host, Integer.parseInt(port));
    }

    private byte[] readStatement() throws IOException {
        char ch;
        String result = "";
        while ((ch = (char) reader.read()) != ';') {
            result += ch;
        }
        return result.getBytes();
    }

    public void start() {

        try(SocketChannel socketChannel = SocketChannel.open()) {

            if (socketChannel.isOpen()) {
                socketChannel.configureBlocking(true);
                socketChannel.connect(new InetSocketAddress(host, port));

                if (socketChannel.isConnected()) {
                    System.out.println("Connected");

                    while(true) {
                        ByteBuffer statement = ByteBuffer.wrap(readStatement());
                        socketChannel.write(statement);

                    }

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
