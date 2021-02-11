//,temp,sample_4675.java,2,12,temp,sample_3810.java,2,12
//,2
public class xxx {
private void closeStream() {
if (socketWrapper.shutdown) return;
if (verbose) System.out.println("[" + this + "] INFO: Closing stream.");
try {
sendEventToAllPads(Event.STREAM_CLOSE, Direction.OUT);
} catch (Exception e) {


log.info("ignored failing sending source event to all pads");
}
}

};