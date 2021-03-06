//,temp,InputStreamSource.java,147,163,temp,AprSocketSource.java,156,171
//,3
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