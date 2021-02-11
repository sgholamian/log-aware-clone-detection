//,temp,QuorumCnxManager.java,815,825,temp,NIOServerCnxnFactory.java,696,702
//,3
public class xxx {
    private void tryClose(ServerSocketChannel s) {
        try {
            s.close();
        } catch (IOException sse) {
            LOG.error("Error while closing server socket.", sse);
        }
    }

};