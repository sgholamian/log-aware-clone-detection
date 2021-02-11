//,temp,QuorumCnxManager.java,815,825,temp,NIOServerCnxnFactory.java,696,702
//,3
public class xxx {
    private void closeSocket(Socket sock) {
        if (sock == null) {
            return;
        }

        try {
            sock.close();
        } catch (IOException ie) {
            LOG.error("Exception while closing", ie);
        }
    }

};