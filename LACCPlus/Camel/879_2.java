//,temp,RabbitMQBasicIT.java,77,89,temp,RabbitMQBasicIT.java,65,75
//,3
public class xxx {
    @Test
    public void sentBasicInOut() throws Exception {
        mock.expectedBodiesReceived("World");

        log.info("Sending to FOO");
        String out = template.requestBody(foo, "World", String.class);
        assertEquals("Bye World", out);
        log.info("Sending to FOO done");

        mock.assertIsSatisfied();
    }

};