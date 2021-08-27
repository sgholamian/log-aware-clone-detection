//,temp,TransactedConsumeTest.java,47,60,temp,AMQ2439Test.java,45,55
//,3
public class xxx {
    private void validateQueueStats() throws Exception {
       final BrokerView brokerView = brokers.get("BrokerA").broker.getAdminView();
       assertEquals("enequeue is correct", 1000, brokerView.getTotalEnqueueCount());
       
       assertTrue("dequeue is correct", Wait.waitFor(new Wait.Condition() {
           public boolean isSatisified() throws Exception {
               LOG.info("dequeue count (want 1000), is : " + brokerView.getTotalDequeueCount());
               return 1000 == brokerView.getTotalDequeueCount();
           }
       }));
    }

};