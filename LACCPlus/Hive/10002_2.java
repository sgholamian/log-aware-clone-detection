//,temp,LlapBaseInputFormat.java,542,550,temp,LlapBaseInputFormat.java,532,540
//,2
public class xxx {
    @Override
    public void taskKilled(TezTaskAttemptID taskAttemptId) {
      try {
        sendOrQueueEvent(ReaderEvent.errorEvent(
            "Received task killed event for task ID " + taskAttemptId));
      } catch (Exception err) {
        LOG.error("Error during heartbeat responder:", err);
      }
    }

};