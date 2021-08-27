//,temp,RabbitMQBasicIT.java,77,89,temp,RabbitMQBasicIT.java,65,75
//,3
public class xxx {
    @Test
    public void sentBasicInOutTwo() throws Exception {
        mock.expectedBodiesReceived("World", "Camel");

        log.info("Sending to FOO");
        String out = template.requestBody(foo, "World", String.class);
        assertEquals("Bye World", out);
        out = template.requestBody(foo, "Camel", String.class);
        assertEquals("Bye Camel", out);
        log.info("Sending to FOO done");

        mock.assertIsSatisfied();
    }

};