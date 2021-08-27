//,temp,OpenWireConnectionTimeoutTest.java,134,144,temp,AutoStompConnectTimeoutTest.java,90,100
//,2
public class xxx {
            @Override
            public void run() {
                try {
                    connection = createSocket();
                    connection.getOutputStream().write('C');
                    connection.getOutputStream().flush();
                } catch (Exception ex) {
                    LOG.error("unexpected exception on connect/disconnect", ex);
                    exceptions.add(ex);
                }
            }

};