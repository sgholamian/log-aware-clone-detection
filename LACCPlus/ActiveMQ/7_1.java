//,temp,DurableSubsOfflineSelectorIndexUseTest.java,215,223,temp,DurableSubsOfflineSelectorConcurrentConsumeIndexUseTest.java,246,255
//,3
public class xxx {
        public void onMessage(Message message) {
            count++;
            if (id != null) {
                try {
                    LOG.info(id + ", " + message.getJMSMessageID());
                } catch (Exception ignored) {
                }
            }
        }

};