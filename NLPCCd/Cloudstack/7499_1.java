//,temp,sample_7766.java,2,10,temp,sample_4734.java,2,10
//,2
public class xxx {
public void shutdown() {
try {
handleEvent(Event.STREAM_CLOSE, Direction.IN);
} catch (Exception e) {


log.info("ignored failure handling close event for bso input stream");
}
}

};