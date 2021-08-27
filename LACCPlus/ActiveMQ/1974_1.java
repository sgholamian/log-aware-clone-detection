//,temp,SocketProxy.java,148,157,temp,SocketProxy.java,131,142
//,3
public class xxx {
    public void halfClose() {
        List<Bridge> connections;
        synchronized(this.connections) {
            connections = new ArrayList<Bridge>(this.connections);
        }            
        LOG.info("halfClose, numConnections=" + connections.size());
        for (Bridge con : connections) {
            halfCloseConnection(con);
        }
    }

};