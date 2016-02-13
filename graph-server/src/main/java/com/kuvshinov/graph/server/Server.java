package com.kuvshinov.graph.server;

import com.kuvshinov.graph.server.utils.PropertyReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * @author Sergey Kuvshinov
 */
public class Server {

    private static final Logger log = LogManager.getLogger(Server.class);

    private static final int RCVBUF_SIZE = 256 * 1024;

    private InetSocketAddress socketAddress;

    private Map<String, SocketChannel> clients = new HashMap<>();

    private void initProperties() throws IOException {
        PropertyReader properties = new PropertyReader("conf.properties");
        int port = properties.getPropertyAsInt("port");
        socketAddress = new InetSocketAddress("127.0.0.1", port);
    }

    private void accept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);
        String uuid = UUID.randomUUID().toString();
        log.info("Create connection for {} with ID = {}", socketChannel.getRemoteAddress(), uuid);

        clients.put(uuid, socketChannel);

        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();

    }

    public void start() {
        try {
            initProperties();
        } catch (IOException e) {
            log.error("I cannot initialize a properties :(");
            log.debug(e.getMessage());
            return;
        }

        try (Selector selector = Selector.open();
             ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

            if (serverChannel.isOpen() && selector.isOpen()) {
                serverChannel.configureBlocking(false);
                serverChannel.setOption(StandardSocketOptions.SO_RCVBUF, RCVBUF_SIZE);
                serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                serverChannel.bind(socketAddress);
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);

                log.info("Server was started on {}:{}", socketAddress.getHostString(), socketAddress.getPort());

                while (true) {
                    selector.select();

                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                    while(keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();

                        if (!key.isValid()) {
                            continue;
                        }

                        if (key.isAcceptable()) {
                            accept(key, selector);
                        } else if (key.isReadable()) {
                            read(key);
                        }
                    }
                }
            } else {
                log.error("The server socket channel or selector cannot be opened :(");
            }

        } catch (IOException e) {
            log.error("Error was occurred during server execution: {}", e.getMessage());
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
