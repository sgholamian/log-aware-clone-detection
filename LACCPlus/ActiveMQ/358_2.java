//,temp,QueueZeroPrefetchLazyDispatchPriorityTest.java,165,200,temp,QueueZeroPrefetchLazyDispatchPriorityTest.java,99,128
//,3
public class xxx {
    @Test(timeout=120000)
    @Ignore("Flaky test on Jenkins, should be refactored")
    public void testPriorityMessagesMoreThanPageSize() throws Exception {

        final int numToSend = 5;
        for (int i = 0; i < ITERATIONS; i++) {
            produceMessages(numToSend - 1, 4, "TestQ");

            // ensure we get expiry processing
            Thread.sleep(1000);

            // send 1 message priority HIGH
            produceMessages(1, 5, "TestQ");

            Thread.sleep(2000);

            LOG.info("On iteration {}", i);

            // consume messages
            ArrayList<Message> consumeList = consumeMessages("TestQ");
            LOG.info("Consumed list {}", consumeList.size());

            // compare lists
            assertFalse("Consumed list should not be empty", consumeList.isEmpty());
            assertEquals("message 1 should be priority high", 5, consumeList.get(0).getJMSPriority());
            for (int j = 1; j < (numToSend - 1); j++) {
                assertEquals("message " + j + " should be priority medium", 4, consumeList.get(j).getJMSPriority());
            }
        }
    }

};