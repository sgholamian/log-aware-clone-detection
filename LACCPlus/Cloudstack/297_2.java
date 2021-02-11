//,temp,AprSocketWrapperImpl.java,193,212,temp,OutputStreamSink.java,109,125
//,3
public class xxx {
    private void closeStream() {
        if (verbose)
            System.out.println("[" + this + "] INFO: Closing stream.");

        try {
            os.close();
        } catch (IOException e) {
            s_logger.info("[ignored]"
                    + "io error on output: " + e.getLocalizedMessage());
        }
        try {
            sendEventToAllPads(Event.STREAM_CLOSE, Direction.IN);
        } catch (Exception e) {
            s_logger.info("[ignored]"
                    + "error sending output close event: " + e.getLocalizedMessage());
        }
    }

};