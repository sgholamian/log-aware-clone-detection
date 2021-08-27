//,temp,AMQ4083Test.java,314,323,temp,AMQ4083Test.java,240,247
//,3
public class xxx {
            @Override
            public void onMessage(Message message) {
                try {
                    if (msgCount.incrementAndGet() == 100) {
                        LOG.debug("Acking message: {}", message);
                        message.acknowledge();
                    }
                } catch (JMSException e) {
                }
            }

};