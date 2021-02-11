//,temp,RemoveWatchesTest.java,1187,1195,temp,RemoveWatchesCmdTest.java,334,342
//,2
public class xxx {
        public boolean matches() throws InterruptedException {
            if (!latch.await(CONNECTION_TIMEOUT/5, TimeUnit.MILLISECONDS)) {
                LOG.error("Failed waiting to remove the watches");
                return false;
            }
            LOG.debug("Client path : {} eventPath : {}", new Object[] { path,
                    eventPath });
            return path.equals(eventPath);
        }

};