//,temp,TransportUriTest.java,161,172,temp,TransportUriTest.java,147,159
//,3
public class xxx {
    private void testInvalidOptionsDoNotWork(String options, String msg) {
        String uri = prefix + bindAddress + postfix + options;
        LOG.info("Connecting via: " + uri);

        try {
            connection = new ActiveMQConnectionFactory(uri).createConnection();
            connection.start();
            fail("Invalid options '" + options + "' on URI '" + uri + "' should"
                 + " have caused an exception to be thrown. " + msg);
        } catch (Exception expected) {
        }
    }

};