//,temp,InOutQueueProducerSyncLoadTest.java,88,99,temp,InOutQueueProducerAsyncLoadTest.java,90,101
//,2
public class xxx {
                @Override
                public void run() {
                    try {
                        final String requestText = "Message " + tempI;
                        final String responseText = "Response Message " + tempI;
                        String response = template.requestBody("direct:start", requestText, String.class);
                        assertNotNull(response);
                        assertEquals(responseText, response);
                    } catch (Exception e) {
                        log.warn("Error", e);
                    }
                }

};