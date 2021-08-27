//,temp,Jms2TestSupport.java,96,118,temp,JmsTestSupport.java,120,138
//,3
public class xxx {
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        DefaultCamelContext dcc = (DefaultCamelContext) context;
        while (!dcc.isStopped()) {
            log.info("Waiting on the Camel Context to stop");
        }
        log.info("Closing JMS Session");
        if (getSession() != null) {
            getSession().close();
            setSession(null);
        }
        log.info("Closing JMS Connection");
        if (connection != null) {
            connection.stop();
            connection = null;
        }
        log.info("Stopping the ActiveMQ Broker");
        if (broker != null) {
            broker.stop();
            broker = null;
        }
    }

};