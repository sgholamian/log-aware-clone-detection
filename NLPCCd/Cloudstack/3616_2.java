//,temp,sample_4675.java,2,12,temp,sample_3810.java,2,12
//,2
public class xxx {
private void closeStream() {
if (socketWrapper.shutdown) return;
if (verbose) System.out.println("[" + this + "] INFO: Closing stream.");
try {
sendEventToAllPads(Event.STREAM_CLOSE, Direction.IN);
} catch (Exception e) {


log.info("ignored failing sending sink event to all pads");
}
}

};