//,temp,FtpBadLoginMockNoopConnectionLeakIT.java,150,156,temp,FtpBadLoginMockNoopConnectionLeakIT.java,142,148
//,3
public class xxx {
        @Override
        public synchronized void close() throws IOException {
            LOG.info("Disconnecting socket {}", System.identityHashCode(this));
            super.close();
            boolean[] value = socketAudits.get(System.identityHashCode(this));
            value[1] = true;
        }

};