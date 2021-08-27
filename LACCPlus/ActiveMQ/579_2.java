//,temp,AmqpConnectTimeoutTest.java,111,121,temp,MQTTConnectTest.java,121,131
//,2
public class xxx {
            @Override
            public void run() {
                try {
                    connection = createConnection();
                    connection.getOutputStream().write(0);
                    connection.getOutputStream().flush();
                } catch (Exception ex) {
                    LOG.error("unexpected exception on connect/disconnect", ex);
                    exceptions.add(ex);
                }
            }

};