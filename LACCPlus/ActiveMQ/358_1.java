//,temp,QueueZeroPrefetchLazyDispatchPriorityTest.java,165,200,temp,QueueZeroPrefetchLazyDispatchPriorityTest.java,99,128
//,3
public class xxx {
    @Test(timeout=120000)
    public void testPriorityMessagesWithJmsBrowser() throws Exception {

        final int numToSend = 5;

        for (int i = 0; i < ITERATIONS; i++) {
            produceMessages(numToSend - 1, 4, "TestQ");

            ArrayList<Message> browsed = browseMessages("TestQ");

            LOG.info("Browsed: {}", browsed.size());

            // send 1 message priority HIGH
            produceMessages(1, 5, "TestQ");

            Thread.sleep(1000);

            LOG.info("On iteration {}", i);

            Message message = consumeOneMessage("TestQ");
            assertNotNull(message);
            assertEquals(5, message.getJMSPriority());

            // consume messages
            ArrayList<Message> consumeList = consumeMessages("TestQ");
            LOG.info("Consumed list {}", consumeList.size());

            // compare lists
            // assertEquals("Iteration: " + i
            // +", message 1 should be priority high", 5,
            // consumeList.get(0).getJMSPriority());
            for (int j = 1; j < (numToSend - 1); j++) {
                assertEquals("Iteration: " + i + ", message " + j + " should be priority medium", 4, consumeList.get(j).getJMSPriority());
            }
        }
    }

};