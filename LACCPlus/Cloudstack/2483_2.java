//,temp,AprSocketWrapperImpl.java,193,212,temp,BcoSocketWrapperImpl.java,96,123
//,3
public class xxx {
    @Override
    public void shutdown() {
        try {
            handleEvent(Event.STREAM_CLOSE, Direction.IN);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "failure handling close event for bso input stream: " + e.getLocalizedMessage());
        }
        try {
            handleEvent(Event.STREAM_CLOSE, Direction.OUT);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "failure handling close event for bso output stream: " + e.getLocalizedMessage());
        }
        try {
            if (bcoSslSocket != null)
                bcoSslSocket.close();
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "failure handling close event for bso socket: " + e.getLocalizedMessage());
        }
        try {
            socket.close();
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "failure handling close event for socket: " + e.getLocalizedMessage());
        }
    }

};