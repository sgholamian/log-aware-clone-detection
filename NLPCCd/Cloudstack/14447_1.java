//,temp,sample_2509.java,2,15,temp,sample_766.java,2,15
//,2
public class xxx {
private void closeStream() {
if (verbose) System.out.println("[" + this + "] INFO: Closing stream.");
try {
is.close();
} catch (IOException e) {
}
try {
sendEventToAllPads(Event.STREAM_CLOSE, Direction.OUT);
} catch (Exception e) {


log.info("ignored error sending an event to all pods");
}
}

};