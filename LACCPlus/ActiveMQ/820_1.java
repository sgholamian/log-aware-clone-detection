//,temp,TcpTransportServer.java,490,515,temp,TcpTransportServer.java,414,441
//,3
public class xxx {
                @Override
                public void run() {
                    try {
                        while (!isStopped() && !isStopping()) {
                            Socket sock = socketQueue.poll(1, TimeUnit.SECONDS);
                            if (sock != null) {
                                try {
                                    handleSocket(sock);
                                } catch (Throwable thrown) {
                                    if (!isStopping()) {
                                        onAcceptError(new Exception(thrown));
                                    } else if (!isStopped()) {
                                        LOG.warn("Unexpected error thrown during accept handling: ", thrown);
                                        onAcceptError(new Exception(thrown));
                                    }
                                }
                            }
                        }

                    } catch (InterruptedException e) {
                        if (!isStopped() || !isStopping()) {
                            LOG.info("socketQueue interrupted - stopping");
                            onAcceptError(e);
                        }
                    }
                }

};