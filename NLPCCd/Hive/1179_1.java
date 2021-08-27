//,temp,sample_531.java,2,10,temp,sample_528.java,2,10
//,3
public class xxx {
public void taskKilled(TezTaskAttemptID taskAttemptId) {
try {
sendOrQueueEvent(ReaderEvent.errorEvent( "Received task killed event for task ID " + taskAttemptId));
} catch (Exception err) {


log.info("error during heartbeat responder");
}
}

};