//,temp,SocketWrapperImpl.java,175,202,temp,BcoSocketWrapperImpl.java,96,123
//,2
public class xxx {
    @Override
    public void shutdown() {
        try {
            handleEvent(Event.STREAM_CLOSE, Direction.IN);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "error sending input close event: " + e.getLocalizedMessage());
        }
        try {
            handleEvent(Event.STREAM_CLOSE, Direction.OUT);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "error sending output close event: " + e.getLocalizedMessage());
        }
        try {
            if (sslSocket != null)
                sslSocket.close();
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "error closing ssl socket: " + e.getLocalizedMessage());
        }
        try {
            socket.close();
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "error closing socket: " + e.getLocalizedMessage());
        }
    }

};