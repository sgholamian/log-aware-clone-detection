//,temp,InputStreamSource.java,147,163,temp,AprSocketSource.java,156,171
//,3
public class xxx {
    private void closeStream() {
        if (verbose)
            System.out.println("[" + this + "] INFO: Closing stream.");

        try {
            is.close();
        } catch (IOException e) {
            s_logger.info("[ignored]"
                    + "io error on input stream: " + e.getLocalizedMessage());
        }
        try {
            sendEventToAllPads(Event.STREAM_CLOSE, Direction.OUT);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "error sending an event to all pods: " + e.getLocalizedMessage());
        }
    }

};