//,temp,AprSocketWrapperImpl.java,193,212,temp,BcoSocketWrapperImpl.java,96,123
//,3
public class xxx {
    @Override
    public void shutdown() {
        if (shutdown)
            return;

        shutdown = true;

        try {
            handleEvent(Event.STREAM_CLOSE, Direction.IN);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "handling stream close event failed on input: " + e.getLocalizedMessage());
        }
        try {
            handleEvent(Event.STREAM_CLOSE, Direction.OUT);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "handling event close event failed on output: " + e.getLocalizedMessage());
        }
    }

};