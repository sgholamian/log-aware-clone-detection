//,temp,AprSocketSink.java,112,126,temp,AprSocketSource.java,156,171
//,2
public class xxx {
    private void closeStream() {

        if (socketWrapper.shutdown)
            return;

        if (verbose)
            System.out.println("[" + this + "] INFO: Closing stream.");

        try {
            sendEventToAllPads(Event.STREAM_CLOSE, Direction.OUT);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "failing sending source event to all pads: " + e.getLocalizedMessage());
        }
        socketWrapper.shutdown();
    }

};