//,temp,RemoveWatchesTest.java,1187,1195,temp,RemoveWatchesCmdTest.java,334,342
//,2
public class xxx {
        public boolean matches() throws InterruptedException {
            if (!latch.await(CONNECTION_TIMEOUT / 3, TimeUnit.MILLISECONDS)) {
                LOG.error("Failed to get watch notifications!");
                return false;
            }
            LOG.debug("Client path : {} eventPath : {}", new Object[] { path,
                    eventPath });
            return path.equals(eventPath);
        }

};