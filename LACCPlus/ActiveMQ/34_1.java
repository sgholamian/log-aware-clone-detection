//,temp,DuplexAdvisoryRaceTest.java,146,150,temp,JMSClientTransactionTest.java,286,290
//,3
public class xxx {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("received: " + responseReceived.get());
                return responseReceived.get() >= numMessagesPerDest * numDests;
            }

};