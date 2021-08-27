//,temp,JettyTestSupport.java,131,142,temp,WaitForJettyListener.java,36,46
//,3
public class xxx {
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

};