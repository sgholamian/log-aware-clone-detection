//,temp,JmsConsumerClient.java,173,188,temp,JmsConsumerClient.java,136,145
//,3
public class xxx {
            @Override
            public void onMessage(Message msg) {
                incThroughput();
                sleep();
                try {
                    commitTxIfNecessary();
                } catch (JMSException ex) {
                    LOG.error("Error committing transaction: " + ex.getMessage());
                }
            }

};