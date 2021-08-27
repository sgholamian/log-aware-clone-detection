//,temp,StompMissingMessageTest.java,98,125,temp,StompMissingMessageTest.java,55,82
//,3
public class xxx {
    public String doTestProducerConsumer(int index) throws Exception {
        String message = null;

        assertEquals("Should not be any consumers", 0, brokerService.getAdminView().getTopicSubscribers().length);

        StompConnection producer = stompConnect();
        StompConnection consumer = stompConnect();

        subscribe(consumer, Integer.toString(index));

        sendMessage(producer, index);

        try {
            StompFrame frame = consumer.receive();
            LOG.debug("Consumer got frame: " + message);
            assertEquals(index, (int) Integer.valueOf(frame.getBody()));
            message = frame.getBody();
        } catch(Exception e) {
            fail("Consumer["+index+"] got error while consuming: " + e.getMessage());
        }

        unsubscribe(consumer, Integer.toString(index));

        stompDisconnect(consumer);
        stompDisconnect(producer);

        return message;
    }

};