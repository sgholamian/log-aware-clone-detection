//,temp,MultipleTestsWithEmbeddedBrokerTest.java,39,47,temp,FilesystemBlobTest.java,44,56
//,3
public class xxx {
    @Override
    protected void setUp() throws Exception {
        LOG.info("### starting up the test case: " + getName());

        super.setUp();
        connection = connectionFactory.createConnection();
        connection.start();
        LOG.info("### started up the test case: " + getName());
    }

};