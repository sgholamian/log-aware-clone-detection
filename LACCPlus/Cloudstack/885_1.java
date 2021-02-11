//,temp,NioConnection.java,450,465,temp,NioConnection.java,308,320
//,3
public class xxx {
    protected void closeConnection(final SelectionKey key) {
        if (key != null) {
            final SocketChannel channel = (SocketChannel)key.channel();
            key.cancel();
            try {
                if (channel != null) {
                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Closing socket " + channel.socket());
                    }
                    channel.close();
                }
            } catch (final IOException ignore) {
                s_logger.info("[ignored] channel");
            }
        }
    }

};