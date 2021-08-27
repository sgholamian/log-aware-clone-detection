//,temp,FtpBadLoginMockNoopConnectionLeakIT.java,150,156,temp,FtpBadLoginMockNoopConnectionLeakIT.java,142,148
//,3
public class xxx {
        @Override
        public void connect(SocketAddress endpoint, int timeout) throws IOException {
            LOG.info("Connecting socket {}", System.identityHashCode(this));
            super.connect(endpoint, timeout);
            boolean[] value = socketAudits.get(System.identityHashCode(this));
            value[0] = true;
        }

};