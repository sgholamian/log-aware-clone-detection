//,temp,JavaSocketTests.java,183,251,temp,JavaSocketTests.java,71,176
//,3
public class xxx {
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
                                    case 27: // Escape
                                        log.info("Received Escape - closing connection");
                                        echoSocket.close();
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