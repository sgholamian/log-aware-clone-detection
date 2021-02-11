//,temp,NioConnection.java,450,465,temp,NioConnection.java,308,320
//,3
public class xxx {
    protected void logDebug(final Exception e, final SelectionKey key, final int loc) {
        if (s_logger.isDebugEnabled()) {
            Socket socket = null;
            if (key != null) {
                final SocketChannel ch = (SocketChannel)key.channel();
                if (ch != null) {
                    socket = ch.socket();
                }
            }

            s_logger.debug("Location " + loc + ": Socket " + socket + " closed on read.  Probably -1 returned: " + e.getMessage());
        }
    }

};