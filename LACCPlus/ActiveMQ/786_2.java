//,temp,JettyTestSupport.java,127,143,temp,WaitForJettyListener.java,32,47
//,3
public class xxx {
    public static void waitForJettySocketToAccept(String bindLocation) throws Exception {
        final URL url = new URL(bindLocation);
        assertTrue("Jetty endpoint is available", Wait.waitFor(new Wait.Condition() {

            public boolean isSatisified() throws Exception {
                boolean canConnect = false;
                try {
                    Socket socket = SocketFactory.getDefault().createSocket(url.getHost(), url.getPort());
                    socket.close();
                    canConnect = true;
                } catch (Exception e) {
                    LOG.warn("verify jetty available, failed to connect to " + url + e);
                }
                return canConnect;
            }}, 60 * 1000));
    }

};