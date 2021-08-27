//,temp,SocketProxy.java,148,157,temp,SocketProxy.java,131,142
//,3
public class xxx {
    public void close() {
        List<Bridge> connections;
        synchronized(this.connections) {
            connections = new ArrayList<Bridge>(this.connections);
        }            
        LOG.info("close, numConnections=" + connections.size());
        for (Bridge con : connections) {
            closeConnection(con);
        }
        acceptor.close();
        closed.countDown();
    }

};