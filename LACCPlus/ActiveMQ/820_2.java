//,temp,TcpTransportServer.java,490,515,temp,TcpTransportServer.java,414,441
//,3
public class xxx {
    private void doRunWithServerSocket(final ServerSocket serverSocket) {
        while (!isStopped()) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                if (socket != null) {
                    if (isStopped() || getAcceptListener() == null) {
                        socket.close();
                    } else {
                        if (useQueueForAccept) {
                            socketQueue.put(socket);
                        } else {
                            handleSocket(socket);
                        }
                    }
                }
            } catch (SocketTimeoutException ste) {
                // expect this to happen
            } catch (Exception e) {
                if (!isStopping()) {
                    onAcceptError(e);
                } else if (!isStopped()) {
                    LOG.warn("run()", e);
                    onAcceptError(e);
                }
            }
        }
    }

};