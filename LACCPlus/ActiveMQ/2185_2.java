//,temp,TransportUriTest.java,161,172,temp,TransportUriTest.java,147,159
//,3
public class xxx {
    private void testValidOptionsWork(String options, String msg) {
        String uri = prefix + bindAddress + postfix + options;
        LOG.info("Connecting via: " + uri);

        try {
            connection = new ActiveMQConnectionFactory(uri).createConnection();
            connection.start();
        } catch (Exception unexpected) {
            fail("Valid options '" + options + "' on URI '" + uri + "' should "
                 + "not have caused an exception to be thrown. " + msg
                 + " Exception: " + unexpected);
        }
    }

};