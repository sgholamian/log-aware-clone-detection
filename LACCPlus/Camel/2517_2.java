//,temp,JavaSocketTests.java,183,251,temp,JavaSocketTests.java,71,176
//,3
public class xxx {
    @Test
    public void testSocketReadOnClosedConnection() throws Exception {
        final Thread acceptThread = new Thread() {
            Logger log = LoggerFactory.getLogger("acceptThread");

            @Override
            public void run() {
                try {
                    Socket echoSocket = serverSocket.accept();

                    log.info("Accepted connection: {}", echoSocket.getInetAddress());

                    echoSocket.setSoTimeout(2000);

                    while (echoSocket.isConnected() && !echoSocket.isClosed()) {
                        StringBuilder responseBuilder = new StringBuilder(500);
                        InputStream reader = echoSocket.getInputStream();
                        OutputStream writer = echoSocket.getOutputStream();

                        do {
                            int readByte = -1;
                            int available = -1;
                            try {
                                available = reader.available();
                                log.info("InputStream.available returned {}", available);
                                readByte = reader.read();
                                log.trace("Processing byte: {}", readByte);
                                switch (readByte) {
                                    case -1:
                                        if (echoSocket.isConnected() && !echoSocket.isClosed()) {
                                            log.info("Available returned {}", reader.available());
                                            log.warn(
                                                    "Socket claims to still be open, but END_OF_STREAM received - closing echoSocket");
                                            try {
                                                echoSocket.close();
                                            } catch (Exception ex) {
                                                log.warn(
                                                        "Exception encountered closing echoSocket after END_OF_STREAM received",
                                                        ex);
                                            }
                                        }
                                        break;
                                    case 10:
                                        log.info("Complete Message - Sending Response");
                                        byte[] response = responseBuilder.toString().getBytes();
                                        responseBuilder.setLength(0);
                                        writer.write(response, 0, response.length);
                                        writer.write('\n');
                                        break;
                                    default:
                                        responseBuilder.append((char) readByte);
                                }
                            } catch (SocketTimeoutException timeoutEx) {
                                log.info("Timeout reading data - available returned {}", available);
                            }
                        } while (echoSocket.isConnected() && !echoSocket.isClosed());
                    }

                } catch (IOException ioEx) {
                    log.error("IOException in run method", ioEx);
                } finally {
                    try {
                        serverSocket.close();
                    } catch (IOException ioEx) {
                        log.error("Exception encountered closing server socket", ioEx);
                    }
                }

                log.info("Finished processing connection");
            }

        };

        acceptThread.start();

        clientSocket = new Socket();
        clientSocket.setSoTimeout(1000);
        clientSocket.connect(serverSocket.getLocalSocketAddress(), 10000);
        clientSocket.setTcpNoDelay(true);
        log.info("Begining message send loop ");
        byte[] message = "Hello World".getBytes();
        BufferedReader reader;
        for (int i = 1; i <= messageCount; ++i) {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream writer = clientSocket.getOutputStream();
            log.info("Sending payload");
            writer.write(message, 0, message.length);
            writer.flush();
            log.info("Sending terminator");
            writer.write('\n');
            writer.flush();
            log.info("Received Response #{}: {}", i, reader.readLine());
            Thread.sleep(1000);
        }

        log.info("Message send loop complete - closing connection");
        // Javadoc for Socket says closing the InputStream will close the connection
        clientSocket.getInputStream().close();
        if (!clientSocket.isClosed()) {
            log.warn("Closing input stream didn't close socket");
            clientSocket.close();
        }
        log.info("Sleeping ...");
        Thread.sleep(5000);

    }

};