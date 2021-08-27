//,temp,LlapBaseInputFormat.java,542,550,temp,LlapBaseInputFormat.java,532,540
//,2
public class xxx {
    @Override
    public void heartbeatTimeout(String taskAttemptId) {
      try {
        sendOrQueueEvent(ReaderEvent.errorEvent(
            "Timed out waiting for heartbeat for task ID " + taskAttemptId));
      } catch (Exception err) {
        LOG.error("Error during heartbeat responder:", err);
      }
    }

};