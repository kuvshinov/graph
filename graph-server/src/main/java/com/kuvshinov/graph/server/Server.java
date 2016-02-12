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
public class Server extends Thread {

    private static final Logger log = LogManager.getLogger(Server.class);

    private static final int B_SIZE = 256 * 1024;

    private InetSocketAddress hostAddress;

    private Map<String, SocketChannel> clients = new HashMap<>();

    private void initProperties() throws IOException {
        PropertyReader properties = new PropertyReader("conf.properties");
        String host = properties.getProperty("host");
        int port = properties.getPropertyAsInt("port");
        hostAddress = new InetSocketAddress(host, port);
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);
        String uuid = UUID.randomUUID().toString();
        log.info("Create connection for {} with ID = {}", socketChannel.getRemoteAddress(), uuid);

        clients.put(uuid, socketChannel);
    }

    private void read() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
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
                serverChannel.setOption(StandardSocketOptions.SO_RCVBUF, B_SIZE);
                serverChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

                serverChannel.bind(hostAddress);
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);

                log.info("Server was started.");

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
                            accept(key);
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

}
