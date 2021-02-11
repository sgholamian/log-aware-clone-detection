//,temp,sample_7767.java,2,14,temp,sample_4735.java,2,14
//,2
public class xxx {
public void shutdown() {
try {
handleEvent(Event.STREAM_CLOSE, Direction.IN);
} catch (Exception e) {
}
try {
handleEvent(Event.STREAM_CLOSE, Direction.OUT);
} catch (Exception e) {


log.info("ignored failure handling close event for bso output stream");
}
}

};