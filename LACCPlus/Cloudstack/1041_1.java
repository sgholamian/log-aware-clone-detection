//,temp,AprSocketSink.java,112,126,temp,OutputStreamSink.java,109,125
//,3
public class xxx {
    private void closeStream() {
        if (socketWrapper.shutdown)
            return;

        if (verbose)
            System.out.println("[" + this + "] INFO: Closing stream.");

        try {
            sendEventToAllPads(Event.STREAM_CLOSE, Direction.IN);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "failing sending sink event to all pads: " + e.getLocalizedMessage());
        }
        socketWrapper.shutdown();
    }

};