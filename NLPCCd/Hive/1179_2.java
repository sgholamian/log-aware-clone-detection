//,temp,sample_531.java,2,10,temp,sample_528.java,2,10
//,3
public class xxx {
public void submissionFailed(String fragmentId, Throwable throwable) {
try {
sendOrQueueEvent(ReaderEvent.errorEvent( "Received submission failed event for fragment ID " + fragmentId + ": " + throwable.toString()));
} catch (Exception err) {


log.info("error during heartbeat responder");
}
}

};